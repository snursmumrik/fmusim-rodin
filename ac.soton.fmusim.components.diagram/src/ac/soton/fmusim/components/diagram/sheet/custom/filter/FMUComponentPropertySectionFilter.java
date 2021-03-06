/**
 * Copyright (c) 2013 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package ac.soton.fmusim.components.diagram.sheet.custom.filter;

import ac.soton.fmusim.components.FMUComponent;
import ac.soton.fmusim.components.diagram.sheet.custom.common.AbstractPropertySectionFilter;

/**
 * Section filter for FMUComponent.
 * 
 * @author vitaly
 *
 */
public class FMUComponentPropertySectionFilter extends
		AbstractPropertySectionFilter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		return unwrap(toTest) instanceof FMUComponent;
	}

}
