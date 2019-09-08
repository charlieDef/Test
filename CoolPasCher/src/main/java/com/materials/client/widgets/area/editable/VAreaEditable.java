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

public class VAreaEditable extends Composite implements IAreaEditable {

	private static VAreaEditableUiBinder uiBinder = GWT.create(VAreaEditableUiBinder.class);

	interface VAreaEditableUiBinder extends UiBinder<Widget, VAreaEditable> {
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

	public VAreaEditable() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public VAreaEditable(CAreaSO careaSO) {
		this.careaSO = careaSO;
		initWidget(uiBinder.createAndBindUi(this));
		setModel(careaSO);
	}

	@UiHandler("saveButtonUi")
	void onSave(ClickEvent event) {
		careaSO.setIndex(indexUi.getValue());
		saveButtonUi.setEnabled(false);
		saveCallback.accept(careaSO);
		// Index.CLIENT_FACTORY.execute(new SaveAction(careaSO), x -> {
		// saveButtonUi.setEnabled(false);
		// });
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
		indexUi.setValue(careaSO.getIndex());
		imageUi.setValue(careaSO.getImageUrl());
	}

	public void setSaveCallBack(Consumer<CAreaSO> saveCallBack) {
		this.saveCallback = saveCallBack;
	}

	public void setBackCallback(Consumer<Boolean> backCallback) {
		this.backCallback = backCallback;
	}
}
