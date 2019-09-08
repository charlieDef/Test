package com.materials.client.widgets.menu.column1;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.DOM;
import com.materials.client.widgets.menu.MenuColumn;
import com.materials.client.widgets.menu.MenuLink;

import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialImage;

public class MenuColumn_4 extends MenuColumn {

	private LIElement captionElement;
	private DivElement dropdownColumn4;

	public MenuColumn_4() {
		super("");
	}

	public MenuColumn_4(String name) {
		super(name);

		captionElement = Document.get().createLIElement();

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.addClassName(style.drop());
		// anchorElement.setHref("");
		anchorElement.setInnerText(name);
		DOM.appendChild(captionElement, anchorElement);

		dropdownColumn4 = Document.get().createDivElement();
		dropdownColumn4.addClassName(style.dropdown_4columns());
		DOM.appendChild(captionElement, dropdownColumn4);
	}

	public void addTitleArea(String title, int size) {

		DivElement divElementCaption = Document.get().createDivElement();
		divElementCaption.addClassName(style.col_4());
		DOM.appendChild(dropdownColumn4, divElementCaption);

		HeadingElement headingElement = Document.get().createHElement(size);
		headingElement.setInnerText(title);
		DOM.appendChild(divElementCaption, headingElement);
	}

	public void addImage(String src, String target, String titel, MenuLink[] links, boolean linksButtonStyle) {

		DivElement div = Document.get().createDivElement();
		div.addClassName(style.col_1());
		DOM.appendChild(dropdownColumn4, div);

		HeadingElement headingElement = Document.get().createHElement(2);
		headingElement.setInnerText(titel);
		DOM.appendChild(div, headingElement);

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.addClassName(style.appNoHover());
		anchorElement.setHref(target);
		DOM.appendChild(div, anchorElement);

		// image
		MaterialCardImage cardImage = new MaterialCardImage();
		cardImage.setWaves(WavesType.LIGHT);

		MaterialImage image = new MaterialImage(src);
		image.addStyleName(style.menuImage());
		cardImage.add(image);
		DOM.appendChild(anchorElement, cardImage.getElement());

		if (links != null && links.length > 0) {
			for (MenuLink link : links) {
				addMenuLink(div, link.getName(), link.getTarget(), linksButtonStyle);
			}
		}

	}

	private void addMenuLink(DivElement parent, String link, String target, boolean buttonStyle) {

		UListElement ulElement = Document.get().createULElement();
		ulElement.addClassName(buttonStyle ? style.greybox() : style.linkText());
		DOM.appendChild(parent, ulElement);

		LIElement liElement = Document.get().createLIElement();
		DOM.appendChild(ulElement, liElement);
		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.setHref(target);
		anchorElement.setInnerText(link);
		DOM.appendChild(liElement, anchorElement);

	}

	public void setAlignRight() {
		dropdownColumn4.addClassName(style.align_right());
		// dropdownColumn3.getStyle().setRight(value, unit);
	}

	@Override
	public LIElement getCaptionElement() {
		return captionElement;
	}

	@Override
	public DivElement getDropdownColumn() {
		return dropdownColumn4;
	}

}
