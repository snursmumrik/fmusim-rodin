/**
 * Copyright (c) 2013 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package ac.soton.fmusim.components.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

import ac.soton.fmusim.components.ComponentsPackage;
import ac.soton.fmusim.components.diagram.edit.parts.DisplayPortNameEditPart;
import ac.soton.fmusim.components.diagram.edit.parts.EventBComponentNameEditPart;
import ac.soton.fmusim.components.diagram.edit.parts.EventBVariableEditPart;
import ac.soton.fmusim.components.diagram.edit.parts.FMUComponentNameEditPart;
import ac.soton.fmusim.components.diagram.edit.parts.FMUVariableEditPart;
import ac.soton.fmusim.components.diagram.parsers.EventBVariableExpressionLabelParser;
import ac.soton.fmusim.components.diagram.parsers.FMUVariableExpressionLabelParser;
import ac.soton.fmusim.components.diagram.parsers.MessageFormatParser;
import ac.soton.fmusim.components.diagram.part.ComponentsVisualIDRegistry;

/**
 * @generated
 */
public class ComponentsParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser fMUComponentName_5001Parser;

	/**
	 * @generated
	 */
	private IParser getFMUComponentName_5001Parser() {
		if (fMUComponentName_5001Parser == null) {
			EAttribute[] features = new EAttribute[] { ComponentsPackage.eINSTANCE
					.getNamedElement_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			fMUComponentName_5001Parser = parser;
		}
		return fMUComponentName_5001Parser;
	}

	/**
	 * @generated
	 */
	private IParser eventBComponentName_5002Parser;

	/**
	 * @generated
	 */
	private IParser getEventBComponentName_5002Parser() {
		if (eventBComponentName_5002Parser == null) {
			EAttribute[] features = new EAttribute[] { ComponentsPackage.eINSTANCE
					.getNamedElement_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			eventBComponentName_5002Parser = parser;
		}
		return eventBComponentName_5002Parser;
	}

	/**
	 * @generated
	 */
	private FMUVariableExpressionLabelParser fMUVariable_3009Parser;

	/**
	 * @generated
	 */
	private IParser getFMUVariable_3009Parser() {
		if (fMUVariable_3009Parser == null) {
			fMUVariable_3009Parser = new FMUVariableExpressionLabelParser();
		}
		return fMUVariable_3009Parser;
	}

	/**
	 * @generated
	 */
	private EventBVariableExpressionLabelParser eventBVariable_3010Parser;

	/**
	 * @generated
	 */
	private IParser getEventBVariable_3010Parser() {
		if (eventBVariable_3010Parser == null) {
			eventBVariable_3010Parser = new EventBVariableExpressionLabelParser();
		}
		return eventBVariable_3010Parser;
	}

	/**
	 * @generated
	 */
	private IParser displayPortName_5010Parser;

	/**
	 * @generated
	 */
	private IParser getDisplayPortName_5010Parser() {
		if (displayPortName_5010Parser == null) {
			EAttribute[] features = new EAttribute[] { ComponentsPackage.eINSTANCE
					.getNamedElement_Name() };
			MessageFormatParser parser = new MessageFormatParser(features);
			displayPortName_5010Parser = parser;
		}
		return displayPortName_5010Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case FMUComponentNameEditPart.VISUAL_ID:
			return getFMUComponentName_5001Parser();
		case EventBComponentNameEditPart.VISUAL_ID:
			return getEventBComponentName_5002Parser();
		case FMUVariableEditPart.VISUAL_ID:
			return getFMUVariable_3009Parser();
		case EventBVariableEditPart.VISUAL_ID:
			return getEventBVariable_3010Parser();
		case DisplayPortNameEditPart.VISUAL_ID:
			return getDisplayPortName_5010Parser();
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object,
			String parserHint) {
		return ParserService.getInstance().getParser(
				new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(ComponentsVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(ComponentsVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (ComponentsElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
