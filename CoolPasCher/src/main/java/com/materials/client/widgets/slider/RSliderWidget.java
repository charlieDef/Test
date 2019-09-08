package com.materials.client.widgets.slider;

import java.util.List;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.SliderSO;

public class RSliderWidget extends Widget {

	private final String urlPrefix = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?caRandomID=";

	@UiField
	UListElement ulElementUi;

	public RSliderWidget() {
		setElement(getDivElement());

		CAreaSO areaSO = new CAreaSO();
		// areaSO.setImageUrl("img/slider1.jpg");
		CAreaSO areaSO2 = new CAreaSO();
		// areaSO2.setImageUrl("img/ptwo.JPG");
		// CAreaSO areaSO3 = new CAreaSO();
		// areaSO3.setImageUrl("img/pone.JPG");
		//
		// CAreaSO areaSO4 = new CAreaSO();
		// areaSO4.setImageUrl("img/ddd.JPG");
		//
		addImage(areaSO);
		addImage(areaSO2);
		// addImage(areaSO3);
		// addImage(areaSO4);
		CoolPasCherUI.CLIENT_FACTORY.startRSlider();
	}

	public RSliderWidget(List<CAreaSO> areaSOs) {
		setElement(getDivElement());

		if (areaSOs != null && !areaSOs.isEmpty()) {
			for (CAreaSO area : areaSOs) {
				addImage(area);
			}
		}
		CoolPasCherUI.CLIENT_FACTORY.startRSlider();
	}

	public RSliderWidget(List<SliderSO> areaSOs, boolean ff) {
		setElement(getDivElement());

		if (areaSOs != null && !areaSOs.isEmpty()) {
			for (SliderSO area : areaSOs) {
				addImage(area);
			}
		}
		CoolPasCherUI.CLIENT_FACTORY.startRSlider();
	}

	public void addImage(CAreaSO areaSO) {

		String url = (areaSO.getImageUrl() != null && !areaSO.getImageUrl().isEmpty()) ? areaSO.getImageUrl()
				: urlPrefix + areaSO.getRandomId();

		LIElement liElement = Document.get().createLIElement();
		ImageElement imageElement = Document.get().createImageElement();
		imageElement.setSrc(url);
		DOM.appendChild(liElement, imageElement);

		// DOM.appendChild(ulElementUi, liElement);
		DOM.insertChild(ulElementUi, liElement, 0);
	}

	public void addImage(SliderSO areaSO) {

		String url = (areaSO.getSliderImageUrl() != null && !areaSO.getSliderImageUrl().isEmpty())
				? areaSO.getSliderImageUrl()
				: urlPrefix + areaSO.getRandomId();

		LIElement liElement = Document.get().createLIElement();
		ImageElement imageElement = Document.get().createImageElement();
		imageElement.setSrc(url);
		DOM.appendChild(liElement, imageElement);

		// DOM.appendChild(ulElementUi, liElement);
		DOM.insertChild(ulElementUi, liElement, 0);
	}

	private DivElement getDivElement() {
		DivElement divElement = Document.get().createDivElement();
		divElement.addClassName("callbacks_container");
		ulElementUi = Document.get().createULElement();
		ulElementUi.setId("slider4");
		ulElementUi.addClassName("rslides");
		DOM.appendChild(divElement, ulElementUi);

		divElement.getStyle().setBackgroundColor("white");
		divElement.getStyle().setPadding(10, Unit.PX);

		return divElement;
	}
}
