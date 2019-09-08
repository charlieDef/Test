package com.materials.client.widgets.menu.single;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.widgets.menu.resource.MenuResources;
import com.materials.client.widgets.menu.resource.MenuResources.MenuStyle;

import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialImage;

public class SingleColumn extends Widget {

	public MenuStyle style = MenuResources.INSTANCE.menuStyles();
	private LIElement captionElement;
	private DivElement dropdownColumn1, col1Element;
	private UListElement ulElementButtons, ulElementLinks;

	public SingleColumn(String name) {
		style.ensureInjected();
		captionElement = Document.get().createLIElement();
		setElement(captionElement);

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.setInnerText(name);
		anchorElement.addClassName(style.drop());

		DOM.appendChild(captionElement, anchorElement);
		dropdownColumn1 = Document.get().createDivElement();
		dropdownColumn1.addClassName(style.dropdown_1column());
		dropdownColumn1.addClassName(style.align_right());
		DOM.appendChild(captionElement, dropdownColumn1);

		col1Element = Document.get().createDivElement();
		col1Element.addClassName(style.col_1());
		DOM.appendChild(dropdownColumn1, col1Element);

	}

	public void addTitleArea(String title, int size) {

		HeadingElement headingElement = Document.get().createHElement(size);
		headingElement.setInnerText(title);
		DOM.appendChild(col1Element, headingElement);
	}

	public void addImage(String src, String target, String titel) {

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.addClassName(style.appNoHover());
		anchorElement.setHref(target);
		DOM.appendChild(col1Element, anchorElement);

		// image
		MaterialCardImage cardImage = new MaterialCardImage();
		cardImage.setWaves(WavesType.LIGHT);

		MaterialImage image = new MaterialImage(src);
		image.addStyleName(style.menuImage());
		cardImage.add(image);
		DOM.appendChild(anchorElement, cardImage.getElement());
	}

	public void addMenuLink(String name, String target, boolean buttonStyle) {

		UListElement ulElement = buttonStyle ? getUlElementButtons() : getUlElementLinks();

		LIElement liElement = Document.get().createLIElement();
		DOM.appendChild(ulElement, liElement);
		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.setHref(target);
		anchorElement.setInnerText(name);
		DOM.appendChild(liElement, anchorElement);
	}

	public UListElement getUlElementButtons() {
		if (ulElementButtons == null) {
			ulElementButtons = Document.get().createULElement();
			ulElementButtons.addClassName(style.greybox());
			DOM.appendChild(col1Element, ulElementButtons);
		}
		return ulElementButtons;
	}

	public UListElement getUlElementLinks() {
		if (ulElementLinks == null) {
			ulElementLinks = Document.get().createULElement();
			ulElementLinks.addClassName("other");
			DOM.appendChild(col1Element, ulElementLinks);
		}
		return ulElementLinks;
	}
}
