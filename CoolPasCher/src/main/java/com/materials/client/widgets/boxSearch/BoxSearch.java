package com.materials.client.widgets.boxSearch;

import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;

public class BoxSearch extends Composite {

	private static BoxSearchUiBinder uiBinder = GWT.create(BoxSearchUiBinder.class);

	interface BoxSearchUiBinder extends UiBinder<MaterialPanel, BoxSearch> {
	}

	private Consumer<String> consumer;

	@UiField
	MaterialTextBox boxUi;

	@UiField
	MaterialPanel materialPanelUi;

	@UiField
	MaterialIcon iconUi;

	@UiConstructor
	public BoxSearch(String theLabel, String toolTipp) {
		initWidget(uiBinder.createAndBindUi(this));

		iconUi.setTitle(toolTipp);
		boxUi.setLabel(theLabel);
		boxUi.setType(InputType.TEXT);
		
		

		boxUi.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					consumer.accept(boxUi.getValue());
				}
			}
		});

	}

	@UiHandler("iconUi")
	void onClick(ClickEvent clickEvent) {
		if (consumer != null) {
			consumer.accept(boxUi.getValue());
		}
	}

	public void setConsumer(Consumer<String> consumer) {
		this.consumer = consumer;
	}

	public void reset() {
		boxUi.setValue("");
	}

	public MaterialTextBox getBoxUi() {
		return boxUi;
	}

	public MaterialIcon getIconUi() {
		return iconUi;
	}

	public void setPlaceHolder(String text) {
		boxUi.setPlaceholder(text);
	}

	public MaterialPanel getBoxPanel() {
		return materialPanelUi;
	}

}
