/**
 * Copyright (c) 2013 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package ac.soton.fmusim.components.diagram.sheet.custom.common.experimental;

import org.eclipse.emf.ecore.EObject;

import ac.soton.fmusim.components.NamedElement;

/**
 * Name property section.
 * 
 * @author vitaly
 *
 */
public class NamePropertySection extends AbstractTextPropertySection {

	@Override
	protected String getPropertyNameLabel() {
		return "Name:";
	}

	@Override
	protected void setPropertyValue(EObject object, Object value) {
		assert object instanceof NamedElement;
		((NamedElement) object).setName((String) value);
	}
	
	@Override
	protected String getPropertyValue() {
		return ((NamedElement) getEObject()).getName();
	}

	@Override
	protected String getPropertyChangeCommandName() {
		return "change name";
	}

	@Override
	protected boolean isReadOnlyProperty() {
		return false;
	}

}
