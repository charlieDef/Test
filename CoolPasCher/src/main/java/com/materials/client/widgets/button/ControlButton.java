package com.materials.client.widgets.button;

import com.google.gwt.uibinder.client.UiConstructor;

import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;

public class ControlButton extends MaterialButton {

	public ControlButton() {
		super();
		addStyle();
	}

	public ControlButton(String text) {
		super(text);
		addStyle();
	}

	public ControlButton(IconType iconType) {
		super(null, iconType);
		addStyle();
	}

	@UiConstructor
	public ControlButton(String text, IconType iconType) {
		super(text, iconType);
		addStyle();
	}

	private void addStyle() {
		setType(ButtonType.FLOATING);
		getElement().getStyle().setProperty("backgroundColor", "#ffb74d");
		getElement().getStyle().setProperty("color", "#960018");
		getIcon().getElement().getStyle().setProperty("color", "#960018");
		// getIcon().setIconColor(Color.AMBER_DARKEN_1);
		setMarginBottom(5);
		setSize(ButtonSize.LARGE);
		// setWaves(WavesType.RED);

		// ViewPort.when(Resolution.ALL_MOBILE).then(viewPortChange -> {
		// getIcon().setFontSize("20px");
		// setSize(ButtonSize.MEDIUM);
		// });
		//
		// ViewPort.when(Resolution.ALL_LAPTOP, Resolution.TABLET).then(viewPortChange
		// -> {
		// setSize(ButtonSize.LARGE);
		// getIcon().setFontSize("25px");
		// });

	}
}
