/**
 * Copyright (c) 2013 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package ac.soton.fmusim.components.master;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jscience.mathematics.number.Real;

import ac.soton.fmusim.components.AbstractVariable;
import ac.soton.fmusim.components.Component;
import ac.soton.fmusim.components.ComponentDiagram;
import ac.soton.fmusim.components.DisplayComponent;
import ac.soton.fmusim.components.Port;
import ac.soton.fmusim.components.exceptions.SimulationException;

/**
 * Master algorithm for FMU-EventB co-simulation
 * from component diagrams.
 * 
 * @author vitaly
 *
 */
public class Master {

	private static final String SEPARATOR = ",";

	/**
	 * Master exception thrown if simulation goes wrong.
	 * 
	 * @author vitaly
	 *
	 */
	@SuppressWarnings("serial")
	public class MasterException extends Exception {
		public MasterException(String string) {
			super(string);
		}
	}

	private ComponentDiagram diagram;
	private Real tCurrent;
	private Real tStart;
	private Real tStop;
	private Real step;
	private File resultFile;
	private BufferedWriter resultOut;
	private boolean simulating;

	/**
	 * Constructs master simulation instance
	 * that can be used to drive the simulation.
	 * 
	 * @param diagram component diagram that defines component composition graph
	 * @param tStart simulation start time
	 * @param tStop simulation stop time
	 * @param step simulation step size
	 * @param resultFile simulation results output file
	 */
	public Master(ComponentDiagram diagram, double tStart, double tStop, double step, File resultFile) {
		this.diagram = diagram;
		this.tStart = Real.valueOf(tStart);
		this.tStop = Real.valueOf(tStop);
		this.step = Real.valueOf(step);
		this.resultFile = resultFile;
	}
	
	public boolean isSimulating() {
		return simulating;
	}
	
	public void simulateStep() {
		if (!simulating) {
			// instantiate components
			try {
				for (Component c : diagram.getComponents())
					c.instantiate();
			} catch (SimulationException e) {
				e.printStackTrace();
				// TODO: terminate instantiated fmus
				return;
			}
	
			// create output file
			resultOut = apiCreateOutput((File) resultFile);
			if (resultOut == null) {
				//TODO: add proper output error handling
				return;
			}
	
			// initialisation step
			for (Component c : diagram.getComponents())
				c.initialise(tStart.doubleValue(), tStop.doubleValue());
			
			// initial output
			apiOutputColumns(diagram, resultOut);
			apiOutput(diagram, tStart, resultOut);
	
			// set simulation time
			tCurrent = tStart;
			
			// marks simulation start
			simulating = true;
		}
		
		// simulation step
		if (simulating) {
			if (tCurrent.isLessThan(tStop)) {
				
				// read port values
				for (Component c : diagram.getComponents())
					c.writeOutputs();
				
				// write port values
				for (Component c : diagram.getComponents())
					try {
						c.readInputs();
					} catch (SimulationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				// do step
				for (Component c : diagram.getComponents())
					c.doStep(tCurrent.doubleValue(), step.doubleValue());
				
				// progress the time
				tCurrent = tCurrent.plus(step);
				diagram.setTime(tCurrent.doubleValue());
				
				// write output
				apiOutput(diagram, tCurrent, resultOut);
			}
			
			// termination step, if finished
			if (tCurrent.compareTo(tStop) >= 0) {
				simulating = false;
				
				for (Component c : diagram.getComponents())
					c.terminate();
				
				// close file
				try {
					resultOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Runs the simulation to completion.
	 */
	public void simulateAll() {
		//XXX: hack to reset simulation for step simulation if during that an error occurs
		simulating = false;
		
		// instantiate components
		try {
			for (Component c : diagram.getComponents())
				c.instantiate();
		} catch (SimulationException e) {
			e.printStackTrace();
			//TODO: terminate instantiated fmus
			return;
		}
		
		// create output file
		resultOut = apiCreateOutput((File) resultFile);
		if (resultOut == null) {
			//TODO: add output error handling
			return;
		}
		
		// initialisation step
		for (Component c : diagram.getComponents())
			c.initialise(tStart.doubleValue(), tStop.doubleValue());
		
		// initial output
		apiOutputColumns(diagram, resultOut);
		apiOutput(diagram, tStart, resultOut);
		
		// set simulation time
		tCurrent = tStart;

		// simulation loop
		while (tCurrent.isLessThan(tStop)) {
			
			// read port values
			for (Component c : diagram.getComponents())
				c.writeOutputs();
			
			// write port values
			for (Component c : diagram.getComponents())
				try {
					c.readInputs();
				} catch (SimulationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			// do step
			for (Component c : diagram.getComponents())
				c.doStep(tCurrent.doubleValue(), step.doubleValue());
			
			// progress the time
			tCurrent = tCurrent.plus(step);
			diagram.setTime(tCurrent.doubleValue());
			
			// write output
			apiOutput(diagram, tCurrent, resultOut);
		}

		// termination step
		for (Component c : diagram.getComponents())
			c.terminate();
		
		// close file
		try {
			resultOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void apiOutputColumns(ComponentDiagram diagram, BufferedWriter writer) {
		try {
			writer.write("time");
			for (Component c : diagram.getComponents()) {
				//XXX: current hack to ignore display component for outputs
				if (c instanceof DisplayComponent)
					continue;
				
				String name = c.getName();
				for (Port p : c.getInputs())
					writer.write(SEPARATOR + name + "." + p.getName());
				for (AbstractVariable v : c.getVariables())
					writer.write(SEPARATOR + name + "." + v.getName());
				for (Port p : c.getOutputs())
					writer.write(SEPARATOR + name + "." + p.getName());
			}
			writer.write('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void apiOutput(ComponentDiagram diagram, Real time, BufferedWriter writer) {
		try {
			writer.write(time.toText().toString());
			for (Component c : diagram.getComponents()) {
				//XXX: current hack to ignore display component for outputs
				if (c instanceof DisplayComponent)
					continue;
				
				for (Port p : c.getInputs()) {
					writer.write(SEPARATOR + toPlotValue(p.getValue().toString()));
				}
				for (AbstractVariable v : c.getVariables()) {
					writer.write(SEPARATOR + toPlotValue(v.getValue().toString()));
				}
				for (Port p : c.getOutputs()) {
					writer.write(SEPARATOR + toPlotValue(p.getValue().toString()));
				}
			}
			writer.write('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String toPlotValue(String value) {
		assert value != null;
		if ("false".equals(value.toLowerCase()))
			return "0";
		if ("true".equals(value.toLowerCase()))
			return "1";
		return value;
	}

	private BufferedWriter apiCreateOutput(File file) {
		try {
			if (!file.exists())
				file.createNewFile();
			return new BufferedWriter(new FileWriter((File) file));
		} catch (IOException e) {
			e.printStackTrace();
			//TODO: log output file error
			return null;
		}
	}

}