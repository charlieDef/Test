package com.materials.client.widgets.config;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialFAB;

public class ConfigurationWidget extends Composite {

	private static ConfigurationWidgetUiBinder uiBinder = GWT.create(ConfigurationWidgetUiBinder.class);

	interface ConfigurationWidgetUiBinder extends UiBinder<MaterialFAB, ConfigurationWidget> {
	}

	@UiField
	MaterialButton configButtonUi;

	public ConfigurationWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		configButtonUi.getElement().getStyle().setProperty("backgroundColor", "#960018");
		configButtonUi.getElement().getStyle().setProperty("color", "#ffb74d");
		configButtonUi.getIcon().setIconColor(Color.AMBER_DARKEN_1);

	}

	// @UiHandler({ "menuUi", "slidersUi", "usersUi", "contentUi" })
	// void onClick(ClickEvent clickEvent) {
	//
	// }

}
