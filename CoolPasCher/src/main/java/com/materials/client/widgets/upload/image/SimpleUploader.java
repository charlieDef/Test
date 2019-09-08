package com.materials.client.widgets.upload.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.materials.client.CoolPasCherUI;

import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.events.CompleteEvent;
import gwt.material.design.addins.client.fileuploader.events.CompleteEvent.CompleteHandler;
import gwt.material.design.addins.client.fileuploader.events.ErrorEvent;
import gwt.material.design.addins.client.fileuploader.events.ErrorEvent.ErrorHandler;
import gwt.material.design.addins.client.fileuploader.events.SendingEvent;
import gwt.material.design.addins.client.fileuploader.events.SendingEvent.SendingHandler;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;

public class SimpleUploader extends Composite implements HasValueChangeHandlers<String>, HasValue<String> {

	private static SimpleUploaderUiBinder uiBinder = GWT.create(SimpleUploaderUiBinder.class);

	interface SimpleUploaderUiBinder extends UiBinder<MaterialRow, SimpleUploader> {
	}

	private MaterialWidget widgetContainer;

	private String randomID;
	private String fileName;
	private String url;

	@UiField
	MaterialFileUploader uploaderUi;

	@UiField
	MaterialLabel labelTextUi;

	@UiField
	MaterialIcon uploadButtonUi;

	@UiConstructor
	public SimpleUploader(String labelText) {
		initWidget(uiBinder.createAndBindUi(this));
		labelTextUi.setText(labelText);
		uploaderUi.setUrl(CoolPasCherUI.MODUL_BASE_FILEUPLOAD);

		uploaderUi.addSendingHandler(new SendingHandler<UploadFile>() {
			@Override
			public void onSending(SendingEvent<UploadFile> event) {

				if (widgetContainer != null) {
					MaterialLoader.loading(true, widgetContainer);
				}

			}
		});

		uploaderUi.addCompleteHandler(new CompleteHandler<UploadFile>() {
			@Override
			public void onComplete(CompleteEvent<UploadFile> event) {
				if (widgetContainer != null) {
					new Timer() {
						@Override
						public void run() {
							MaterialLoader.loading(false, widgetContainer);
						}
					}.schedule(2000);
				}
			}
		});

		uploaderUi.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {
				String tempFileId = event.getResponse().getBody();
				String[] array = tempFileId.split("_");
				setRandomID(array[0]);
				setFileName(array[1]);
				url = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + getRandomID();
				setValue(url, true);
			}
		});

		uploaderUi.addErrorHandler(new ErrorHandler<UploadFile>() {
			@Override
			public void onError(ErrorEvent<UploadFile> event) {
				Window.alert(event.toString());
			}
		});
	}

	public void setIconSize(IconSize iconSize) {
		uploadButtonUi.setIconSize(iconSize);
	}

	public void setIconColor(Color color) {
		uploadButtonUi.setIconColor(color);
	}

	@Override
	public String getValue() {
		return url;
	}

	@Override
	public void setValue(String url, boolean fireEvents) {
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

	public void setUploadTitel(String titel) {
		uploadButtonUi.setTitle(titel);
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setWidgetContainer(MaterialWidget widgetContainer) {
		this.widgetContainer = widgetContainer;
	}

	public MaterialWidget getWidgetContainer() {
		return widgetContainer;
	}

}
