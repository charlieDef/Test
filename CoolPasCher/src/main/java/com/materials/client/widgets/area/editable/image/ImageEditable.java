package com.materials.client.widgets.area.editable.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;

import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialRow;

public class ImageEditable extends Composite implements HasValueChangeHandlers<String>, HasValue<String> {

	private static ImageEditableUiBinder uiBinder = GWT.create(ImageEditableUiBinder.class);

	interface ImageEditableUiBinder extends UiBinder<Widget, ImageEditable> {
	}

	private String randomID;

	@UiField
	MaterialImage imageUi;

	@UiField
	MaterialFileUploader uploaderUi;

	@UiField
	MaterialIcon editButtonUi;

	@UiField
	MaterialRow materialRowUi;

	@UiConstructor
	public ImageEditable(String imageUrl) {
		initWidget(uiBinder.createAndBindUi(this));
		setValue(imageUrl);
		uploaderUi.setUrl(CoolPasCherUI.MODUL_BASE_FILEUPLOAD);

		uploaderUi.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {

				String tempFileId = event.getResponse().getBody();
				String[] array = tempFileId.split("_");
				setRandomID(array[0]);
				String urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + getRandomID();
				setValue(urlImg, true);
			}
		});
	}

	@UiHandler("materialRowUi")
	public void onMouseOver(MouseOverEvent event) {
		editButtonUi.setVisible(true);
	}

	@UiHandler("materialRowUi")
	public void onMouseOut(MouseOutEvent event) {
		editButtonUi.setVisible(false);
	}

	@Override
	public String getValue() {
		return imageUi.getUrl();
	}

	@Override
	public void setValue(String url, boolean fireEvents) {
		imageUi.setUrl(url);
		if (fireEvents) {
			ValueChangeEvent.fire(this, getValue());
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public void setValue(String value) {
		setValue(value, false);
	}

	public String getRandomID() {
		return randomID;
	}

	public void setRandomID(String randomID) {
		this.randomID = randomID;
	}

	public MaterialImage getImageUi() {
		return imageUi;
	}
}
