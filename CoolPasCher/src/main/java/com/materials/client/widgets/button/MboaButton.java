package com.materials.client.widgets.button;

import com.google.gwt.uibinder.client.UiConstructor;
import com.materials.client.utils.ConstantsUtils;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;

public class MboaButton extends MaterialButton {

	public MboaButton() {
		super();
		addStyle();
	}

	@UiConstructor
	public MboaButton(String text) {
		super(text);
		addStyle();
	}

	public MboaButton(String text, IconType icon) {
		super(text, icon);
		addStyle();
	}

	public MboaButton(ButtonType type) {
		super(type);
		addStyle();
	}

	private void addStyle() {
		getElement().getStyle().setProperty("backgroundColor", ConstantsUtils.MBOA_BUTTON_BACKGROUND_COLOR);
		getElement().getStyle().setProperty("color", ConstantsUtils.MBOA_BUTTON_TEXT_COLOR);
		getElement().getStyle().setProperty("fontFamily", ConstantsUtils.MBOA_FONT_FAMILY);
		// font-family: times new roman, times, serif;
		setType(ButtonType.OUTLINED);
		getElement().getStyle().setProperty("border", "1px solid rgb(150, 0, 24)");
		if (getIcon() != null) {
			// getIcon().setIconColor(Color.AMBER_DARKEN_1);
			getIcon().getElement().getStyle().setProperty("color", ConstantsUtils.MBOA_BUTTON_ICON_COLOR);
		}
	}

}
