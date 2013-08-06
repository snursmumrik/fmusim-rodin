/*
 * Copyright (c) 2013 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package ac.soton.fmusim.components.diagram.parsers;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import ac.soton.fmusim.components.EventBVariable;

/**
 * @generated
 */
public class EventBVariableExpressionLabelParser implements IParser {
	/**
	 * @generated
	 */
	public EventBVariableExpressionLabelParser() {
	}

	/**
	 * @generated
	 */
	public String getPrintString(IAdaptable element, int flags) {
		return evaluatePrintExpression((EObject) element
				.getAdapter(EObject.class));
	}

	/**
	 * @generated
	 */
	public boolean isAffectingEvent(Object event, int flags) {
		// XXX Any event is recognized as important, unless there's a way to extract this information from expression itself.
		// TODO analyze expressions (e.g. using OCL parser) to find out structural features in use  
		return true;
	}

	/**
	 * @generated
	 */
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}

	/**
	 * @generated
	 */
	public IParserEditStatus isValidEditString(IAdaptable element,
			String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

	/**
	 * @generated
	 */
	public ICommand getParseCommand(IAdaptable element, final String newString,
			int flags) {
		final EObject target = (EObject) element.getAdapter(EObject.class);
		if (!validateValues(target, newString)) {
			return UnexecutableCommand.INSTANCE;
		}
		TransactionalEditingDomain editingDomain = TransactionUtil
				.getEditingDomain(target);
		if (editingDomain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		IFile affectedFile = WorkspaceSynchronizer.getFile(target.eResource());
		return new AbstractTransactionalCommand(
				editingDomain,
				"Set Values", affectedFile == null ? null : Collections.singletonList(affectedFile)) { //$NON-NLS-1$ 
			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				return new CommandResult(updateValues(target, newString));
			}
		};
	}

	/**
	 * @generated
	 */
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}

	/**
	 * @generated
	 */
	private boolean validateValues(EObject target, String newString) {
		// TODO implement as needed
		return true;
	}

	/**
	 * @generated
	 */
	private IStatus updateValues(EObject target, String newString)
			throws ExecutionException {
		// TODO implement this method
		// DO NOT FORGET to remove @generated tag or mark method @generated NOT
		throw new ExecutionException(
				"Please implement parsing and value modification");
	}

	/**
	 * Returns the view name of the Event-B variable label,
	 * consisting of the name of the variable and its value.
	 * 
	 * @generated NOT
	 */
	private String evaluatePrintExpression(EObject self) {
		if (self instanceof EventBVariable) {
			EventBVariable var = (EventBVariable) self;
			String name = var.getName() == null || var.getName().isEmpty() ? "<name>"
					: var.getName();
			String value = var.getValue() == null ? "" : (" = " + var
					.getValue().toString());
			return name + value;
		}
		throw new UnsupportedOperationException(
				"No user java implementation provided in 'evaluatePrintExpression' operation"); //$NON-NLS-1$
	}

}
