package com.materials.client.widgets.menu.column1;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.DOM;
import com.materials.client.widgets.menu.MenuColumn;
import com.materials.client.widgets.menu.MenuLink;

import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialImage;

public class MenuColumn_2 extends MenuColumn {

	private LIElement captionElement;
	private DivElement dropdownColumn2;

	public MenuColumn_2() {
		super("");
	}

	public MenuColumn_2(String name) {
		super(name);

		captionElement = Document.get().createLIElement();

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.addClassName(style.drop());
		// anchorElement.setHref("");
		anchorElement.setInnerText(name);
		DOM.appendChild(captionElement, anchorElement);

		dropdownColumn2 = Document.get().createDivElement();
		dropdownColumn2.addClassName(style.dropdown_2columns());
		DOM.appendChild(captionElement, dropdownColumn2);
	}

	public void addTitleArea(String title, int size) {

		DivElement divElementCaption = Document.get().createDivElement();
		divElementCaption.addClassName(style.col_2());
		DOM.appendChild(dropdownColumn2, divElementCaption);

		HeadingElement headingElement = Document.get().createHElement(size);
		headingElement.setInnerText(title);
		DOM.appendChild(divElementCaption, headingElement);
	}

	public void addImageAndText(String imgSrc, String text, String target, boolean imgLeft) {

		DivElement imgDiv = Document.get().createDivElement();
		imgDiv.addClassName(style.col_1());

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.addClassName(style.appNoHover());
		anchorElement.setHref(target);
		DOM.appendChild(imgDiv, anchorElement);

		// ImageElement imageElement = Document.get().createImageElement();
		// imageElement.addClassName(style.menuImage());
		// imageElement.setSrc(imgSrc);
		// DOM.appendChild(anchorElement, imageElement);

		// image
		MaterialCardImage cardImage = new MaterialCardImage();
		cardImage.setWaves(WavesType.LIGHT);

		MaterialImage image = new MaterialImage(imgSrc);
		image.addStyleName(style.menuImage());
		cardImage.add(image);

		DOM.appendChild(anchorElement, cardImage.getElement());

		DivElement textDiv = Document.get().createDivElement();
		textDiv.getStyle().setColor("black");
		textDiv.addClassName(style.col_1());
		textDiv.setInnerText(text);

		if (imgLeft) {
			DOM.appendChild(dropdownColumn2, imgDiv);
			DOM.appendChild(dropdownColumn2, textDiv);
		} else {
			DOM.appendChild(dropdownColumn2, textDiv);
			DOM.appendChild(dropdownColumn2, imgDiv);

		}

	}

	public void addImageAndLinks(String imgSrc, MenuLink[] menuLinks, boolean imgLeft, boolean isButton) {

		DivElement imgDiv = Document.get().createDivElement();
		imgDiv.addClassName(style.col_1());

		// ImageElement imageElement = Document.get().createImageElement();
		// imageElement.addClassName(style.menuImage());
		// imageElement.setSrc(imgSrc);

		// image
		MaterialCardImage cardImage = new MaterialCardImage();
		cardImage.setWaves(WavesType.LIGHT);

		MaterialImage image = new MaterialImage(imgSrc);
		image.addStyleName(style.menuImage());
		cardImage.add(image);

		DOM.appendChild(imgDiv, cardImage.getElement());

		DivElement linksDiv = Document.get().createDivElement();
		linksDiv.addClassName(style.col_1());

		UListElement ulElement = Document.get().createULElement();
		ulElement.addClassName(isButton ? style.greybox() : style.linkText());
		DOM.appendChild(linksDiv, ulElement);

		for (MenuLink link : menuLinks) {
			LIElement liElement = Document.get().createLIElement();
			DOM.appendChild(ulElement, liElement);
			AnchorElement anchorElement = Document.get().createAnchorElement();
			anchorElement.getStyle().setFontSize(15, Unit.PX);
			anchorElement.getStyle().setMargin(4, Unit.PX);
			anchorElement.setHref(link.getTarget());
			anchorElement.setInnerText(link.getName());
			DOM.appendChild(liElement, anchorElement);
		}

		if (imgLeft) {
			DOM.appendChild(dropdownColumn2, imgDiv);
			DOM.appendChild(dropdownColumn2, linksDiv);
		} else {
			DOM.appendChild(dropdownColumn2, linksDiv);
			DOM.appendChild(dropdownColumn2, imgDiv);

		}

	}

	public void addImage(String src, String target, String titel, MenuLink[] links, boolean linksButtonStyle) {

		DivElement div = Document.get().createDivElement();
		div.addClassName(style.col_1());
		DOM.appendChild(dropdownColumn2, div);

		HeadingElement headingElement = Document.get().createHElement(2);
		headingElement.setInnerText(titel);
		DOM.appendChild(div, headingElement);

		AnchorElement anchorElement = Document.get().createAnchorElement();
		anchorElement.addClassName(style.appNoHover());
		if (target != null) {
			anchorElement.setHref(target);
		}

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
		dropdownColumn2.addClassName(style.align_right());
		// dropdownColumn3.getStyle().setRight(value, unit);
	}

	@Override
	public LIElement getCaptionElement() {
		return captionElement;
	}

	@Override
	public DivElement getDropdownColumn() {
		return dropdownColumn2;
	}

}
