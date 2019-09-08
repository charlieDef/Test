package com.materials.client.widgets.menu;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Mouafo Deffo Charles
 *
 */
public class HeaderMenu extends Widget {

	private Map<String, LIElement> dropdownContainer = new HashMap<String, LIElement>();

	private UListElement baseUl;

	public HeaderMenu() {

		baseUl = Document.get().createULElement();
		baseUl.setId("menu");
		baseUl.getStyle().setMarginTop(30, Unit.PX);
		setElement(baseUl);
	}

	public void addMenu(MenuColumn menuColumn) {
		dropdownContainer.put(menuColumn.getName(), menuColumn.getCaptionElement());
		DOM.appendChild(baseUl, menuColumn.getCaptionElement());
	}

	public void addMenuAfter(MenuColumn menuColumn, String refAfterChild) {

		Element element = dropdownContainer.get(refAfterChild);
		baseUl.insertAfter(menuColumn.getCaptionElement(), element);
		dropdownContainer.put(menuColumn.getName(), menuColumn.getCaptionElement());

	}

}
