package com.materials.client.widgets.area.editable;

import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.CAreaSO;
import com.materials.client.widgets.area.editable.image.ImageEditable;
import com.materials.client.widgets.area.editable.text.TextEditable;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialIntegerBox;

public class HAreaEditable extends Composite implements IAreaEditable {

	private static HAreaEditableUiBinder uiBinder = GWT.create(HAreaEditableUiBinder.class);

	interface HAreaEditableUiBinder extends UiBinder<Widget, HAreaEditable> {
	}

	private CAreaSO careaSO;
	private Consumer<Boolean> backCallback;
	private Consumer<CAreaSO> saveCallback;

	@UiField
	TextEditable textTitelUi, textContentUi;

	@UiField
	MaterialIntegerBox indexUi;

	@UiField
	ImageEditable imageUi;

	@UiField
	MaterialIcon backButtonUi;

	@UiField
	MaterialIcon saveButtonUi;

	public HAreaEditable() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HAreaEditable(CAreaSO areaSO) {
		this.careaSO = areaSO;
		initWidget(uiBinder.createAndBindUi(this));

		setModel(careaSO);
	}

	@UiHandler("saveButtonUi")
	void onSave(ClickEvent event) {
		careaSO.setIndex(indexUi.getValue());
		saveButtonUi.setEnabled(false);
		saveCallback.accept(careaSO);
	}

	@UiHandler("indexUi")
	void onIndexChanged(ValueChangeEvent<Integer> event) {
		careaSO.setIndex(indexUi.getValue());
		saveButtonUi.setEnabled(true);
	}

	@UiHandler("backButtonUi")
	void onBack(ClickEvent clickEvent) {
		backCallback.accept(Boolean.TRUE);
		imageUi.setValue("");
	}

	@UiHandler("textTitelUi")
	void onTitelChange(ValueChangeEvent<String> event) {
		String text = event.getValue().replaceAll("<strong>", "<b>");
		text = text.replaceAll("</strong>", "</b>");
		careaSO.setTitel(text);
		saveButtonUi.setEnabled(true);
	}

	@UiHandler("textContentUi")
	void onTextContentChange(ValueChangeEvent<String> event) {
		String text = event.getValue().replaceAll("<strong>", "<b>");
		text = text.replaceAll("</strong>", "</b>");
		careaSO.setTextContent(text);
		saveButtonUi.setEnabled(true);
	}

	@UiHandler("imageUi")
	void onImageChange(ValueChangeEvent<String> event) {
		// careaSO.setImageUrl(event.getValue());
		careaSO.setRandomId(imageUi.getRandomID());
		saveButtonUi.setEnabled(true);
	}

	@Override
	public void setModel(CAreaSO areaSO) {
		this.careaSO = areaSO;
		textTitelUi.setValue(careaSO.getTitel());
		textContentUi.setValue(careaSO.getTextContent());
		// String urlImg = Index.MODUL_BASE_FILEHELPER + "?caRandomID=" +
		// careaSO.getRandomId();
		// if (careaSO.getRandomId() != null) {
		// careaSO.setImageUrl(urlImg);
		// }
		indexUi.setValue(areaSO.getIndex());
		imageUi.setValue(careaSO.getImageUrl());
	}

	public void setBackCallback(Consumer<Boolean> backCallback) {
		this.backCallback = backCallback;
	}

	public void setSaveCallback(Consumer<CAreaSO> saveCallback) {
		this.saveCallback = saveCallback;
	}

}
