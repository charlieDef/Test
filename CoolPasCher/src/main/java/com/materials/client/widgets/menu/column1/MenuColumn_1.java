package com.materials.client.widgets.menu.column1;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.DOM;
import com.materials.client.widgets.menu.MenuColumn;
import com.materials.client.widgets.menu.MenuLink;

public class MenuColumn_1 extends MenuColumn {

	private LIElement captionElement;
	private DivElement dropdownColumn1, col1Element;

	public MenuColumn_1() {
		super("");
	}

	public MenuColumn_1(String name, boolean dropdown, String target) {
		super(name);

		captionElement = Document.get().createLIElement();

		// captionElement.addClassName(style.menu_right());

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.setInnerText(name);
		anchorElement.addClassName(style.drop());

		if (target != null) {
			anchorElement.setHref(target);
		}

		DOM.appendChild(captionElement, anchorElement);
		if (dropdown) {
			dropdownColumn1 = Document.get().createDivElement();
			dropdownColumn1.addClassName(style.dropdown_1column());
			dropdownColumn1.addClassName(style.align_right());
			DOM.appendChild(captionElement, dropdownColumn1);

			col1Element = Document.get().createDivElement();
			col1Element.addClassName(style.col_1());
			DOM.appendChild(dropdownColumn1, col1Element);
		}

	}

	public void addMenuLinks(MenuLink[] menuLinks, boolean buttonStyle) {

		UListElement ulElement = Document.get().createULElement();
		ulElement.addClassName(buttonStyle ? style.greybox() : style.linkText());
		DOM.appendChild(col1Element, ulElement);

		for (MenuLink link : menuLinks) {
			LIElement liElement = Document.get().createLIElement();
			DOM.appendChild(ulElement, liElement);
			AnchorElement anchorElement = Document.get().createAnchorElement();
			anchorElement.setHref(link.getTarget());
			anchorElement.setInnerText(link.getName());
			DOM.appendChild(liElement, anchorElement);
		}
	}

	public void addMenuLink(String link, String target, boolean buttonStyle) {

		DivElement ulElement = Document.get().createDivElement();
		
		ulElement.addClassName(buttonStyle ? style.greybox() : style.linkText());
		DOM.appendChild(col1Element, ulElement);

		LIElement liElement = Document.get().createLIElement();
		liElement.getStyle().setWidth(100, Unit.PCT);
		DOM.appendChild(ulElement, liElement);
		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.setHref(target);
		anchorElement.setInnerText(link);

		DOM.appendChild(liElement, anchorElement);

	}

	public void addText(String text) {

		DivElement divElement = Document.get().createDivElement();
		divElement.addClassName(style.fCaisse());
		DOM.appendChild(col1Element, divElement);

		divElement.setInnerHTML(text);

	}

	public void addTitleArea(String title, int size) {

		HeadingElement headingElement = Document.get().createHElement(size);
		headingElement.setInnerText(title);
		DOM.appendChild(col1Element, headingElement);
	}

	public void addImage(String src, String target) {

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.addClassName(style.appNoHover());
		if (target != null) {
			anchorElement.setHref(target);
		}

		DOM.appendChild(col1Element, anchorElement);

		ImageElement imageElement = Document.get().createImageElement();
		imageElement.addClassName(style.menuImage());
		imageElement.setSrc(src);
		DOM.appendChild(anchorElement, imageElement);
	}

	@Override
	public LIElement getCaptionElement() {
		return captionElement;
	}

	@Override
	public DivElement getDropdownColumn() {
		return dropdownColumn1;
	}

}
