package com.materials.client.widgets.model.slider.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.materials.client.model.ByteDataSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.image.SimpleUploader;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class SliderImageDetails extends BaseDetails2 implements SliderImageDetailsView {

	private static SliderDetailUiBinder uiBinder = GWT.create(SliderDetailUiBinder.class);

	interface SliderDetailUiBinder extends UiBinder<MDFormPanel, SliderImageDetails> {
	}

	private ByteDataSO byteDataSO;
	private Presenter presenter;

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MaterialIntegerBox indexUi;

	@UiField
	MaterialTextBox heightUi, widthUi;

	@UiField
	MaterialCheckBox paddingUi;

	@UiField
	MaterialImage sliderImageUi;

	@UiHandler("backButtonUi")
	void onBackToCommentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.swipeBackToImagesList();
	}

	public SliderImageDetails(boolean config) {
		super(config);

		add(uiBinder.createAndBindUi(this));
		mdFormUi.setFormHandler(new BBarHandler());

		fileUploadUi.setVisible(false);
		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.addValueChangeHandler(x -> {
			sliderImageUi.setUrl(fileUploadUi.getValue());
			byteDataSO.setRandomId(fileUploadUi.getRandomID());
		});

		handleEditEnable();
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			fileUploadUi.setVisible(true);
		}

		@Override
		public void onSave() {
			fileUploadUi.setVisible(false);
			byteDataSO.setHeight(heightUi.getValue());
			byteDataSO.setWidth(widthUi.getValue());
			byteDataSO.setIndex(indexUi.getValue());
			byteDataSO.setPadding(paddingUi.getValue());
			presenter.saveImage(byteDataSO);
		}

		@Override
		public void onCancel() {
			fileUploadUi.setVisible(false);
			if (byteDataSO.getId() == -10) {
				presenter.swipeBackToImagesList();
			}
		}
	}

	@Override
	public void setEdit() {
		mdFormUi.edit();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setByteData(ByteDataSO byteDataSO) {

		this.byteDataSO = byteDataSO;

		// boxes
		heightUi.setValue(byteDataSO.getHeight());
		widthUi.setValue(byteDataSO.getWidth());
		indexUi.setValue(byteDataSO.getIndex());
		sliderImageUi.setUrl(byteDataSO.getImageUrl());
		paddingUi.setValue(byteDataSO.isPadding());
		// textHeaderLabelUi.setText("Comment_Contenu:: " +
		// commentSO.getContentSO().getTitel());
		textHeaderLabelUi.setText("Image Details:: ");

	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}
}
