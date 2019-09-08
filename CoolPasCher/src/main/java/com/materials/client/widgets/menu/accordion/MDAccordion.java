package com.materials.client.widgets.menu.accordion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class MDAccordion extends Widget {

	private static MDAccordionUiBinder uiBinder = GWT.create(MDAccordionUiBinder.class);

	interface MDAccordionUiBinder extends UiBinder<DivElement, MDAccordion> {
	}

	@UiField
	Style style;

	@UiField
	DivElement theContainer;

	public MDAccordion() {

		setElement(uiBinder.createAndBindUi(this));

		UListElement uElement = Document.get().createULElement();
		LIElement liElement = Document.get().createLIElement();
		liElement.addClassName(style.dropdown());
		DOM.appendChild(uElement, liElement);

		InputElement inputElement = Document.get().createTextInputElement();
		inputElement.setAttribute("type", "checkbox");
		DOM.appendChild(liElement, inputElement);

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.setHref("#");
		anchorElement.setAttribute("data-toggle", "dropdown");
		anchorElement.setInnerText("First MEnu");
		DOM.appendChild(liElement, anchorElement);

		UListElement uElement2 = Document.get().createULElement();
		uElement2.addClassName(style.dropdownMenu());
		DOM.appendChild(liElement, uElement2);

		LIElement liHome = Document.get().createLIElement();
		liHome.setInnerText("Home");
		LIElement about = Document.get().createLIElement();
		about.setInnerText("About");
		DOM.appendChild(uElement2, liHome);
		DOM.appendChild(uElement2, about);

		DOM.appendChild(theContainer, uElement);
	}

	interface Style extends CssResource {

		String dropdown();

		String dropdownMenu();

		String container();

	}

}
