package com.materials.client.widgets.confirm;

import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLabel;

public class ConfirmationWidget extends Composite {

	private static ConfirmationWidgetUiBinder uiBinder = GWT.create(ConfirmationWidgetUiBinder.class);

	interface ConfirmationWidgetUiBinder extends UiBinder<Widget, ConfirmationWidget> {
	}

	private Consumer<Boolean> consumer;

	@UiField
	MaterialCardTitle cardTitelUi;

	@UiField
	MaterialCardContent cardContenUi;

	@UiField
	MaterialDialog materialModalUi;

	@UiField
	MaterialButton noButtonUi;

	@UiField
	MaterialLabel contentTextLabelUI;

	public ConfirmationWidget(Consumer<Boolean> consumer) {
		this.consumer = consumer;
		initWidget(uiBinder.createAndBindUi(this));

	}

	public ConfirmationWidget(String text) {
		initWidget(uiBinder.createAndBindUi(this));
		contentTextLabelUI.setText(text);
	}

	public ConfirmationWidget(String text, Consumer<Boolean> consumer) {
		this.consumer = consumer;
		initWidget(uiBinder.createAndBindUi(this));
		contentTextLabelUI.setText(text);
	}

	@UiHandler("okButtonUi")
	void onOk(ClickEvent clickEvent) {
		if (consumer != null) {
			consumer.accept(Boolean.TRUE);
		}
		close();
	}

	@UiHandler("noButtonUi")
	void onNo(ClickEvent clickEvent) {
		if (consumer != null) {
			consumer.accept(Boolean.FALSE);
		}
		close();
	}

	public void show() {
		materialModalUi.open();
	}

	public void show(String text, Consumer<Boolean> consumer) {
		this.consumer = consumer;
		contentTextLabelUI.setText(text);
		materialModalUi.open();

	}
	
	public void show(String titleHeader,String textContent, Consumer<Boolean> consumer) {
		this.consumer = consumer;
		cardTitelUi.setText(titleHeader);
		contentTextLabelUI.setText(textContent);
		materialModalUi.open();

	}

	public void show(Consumer<Boolean> consumer) {
		this.consumer = consumer;
		materialModalUi.open();
	}

	public void close() {
		materialModalUi.close();
	}

	public void setWidgetContent(String titre, Widget widget) {
		clearContent();
		MaterialCardTitle cardTitle = new MaterialCardTitle();
		cardTitle.add(new MaterialLabel(titre));
		cardContenUi.add(cardTitle);
		cardContenUi.add(widget);
	}

	public void clearContent() {
		cardContenUi.clear();
	}

	public void desactiveNoButton(boolean desactive) {
		noButtonUi.setVisible(!desactive);
	}

	public void setBoxTitle(String titel) {
		cardTitelUi.setText(titel);
	}

	public void setLabelContent(String label) {
		contentTextLabelUI.setText(label);
	}

}
