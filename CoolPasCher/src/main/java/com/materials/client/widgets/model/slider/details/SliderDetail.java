package com.materials.client.widgets.model.slider.details;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.materials.client.model.SliderSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.image.SimpleUploader;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class SliderDetail extends BaseDetails2 implements SliderDetailView {

	private static SliderDetailUiBinder uiBinder = GWT.create(SliderDetailUiBinder.class);

	interface SliderDetailUiBinder extends UiBinder<MDFormPanel, SliderDetail> {
	}

	private SliderSO sliderSO;
	private Presenter presenter;

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialDatePicker creation2Ui;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MaterialComboBox<String> typeUi;

	@UiField
	MaterialIntegerBox indexUi;

	@UiField
	MaterialTextBox titelUi, titel1Ui, titel2Ui, textInfoUi, titelColorUi;

	@UiField
	MaterialCheckBox activeUi, lockUi;

	@UiField
	MaterialImage sliderImageUi;

	@UiHandler("backButtonUi")
	void onBackToCommentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToSliderList();
	}

	public SliderDetail(boolean config) {
		super(config);

		add(uiBinder.createAndBindUi(this));
		mdFormUi.setFormHandler(new BBarHandler());

		fileUploadUi.setVisible(false);
		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.addValueChangeHandler(x -> {
			sliderImageUi.setUrl(fileUploadUi.getValue());
			sliderSO.setRandomId(fileUploadUi.getRandomID());
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
			sliderSO.setActive(activeUi.getValue());
			sliderSO.setLock(lockUi.getValue());
			sliderSO.setTitel(titelUi.getValue());
			sliderSO.setTitel1(titel1Ui.getValue());
			sliderSO.setTitel2(titel2Ui.getValue());
			sliderSO.setTextInfo(textInfoUi.getValue());
			sliderSO.setIndex(indexUi.getValue());
			sliderSO.setType(typeUi.getSingleValue());
			sliderSO.setTitelColor(titelColorUi.getValue());

			presenter.saveSlider(sliderSO);
		}

		@Override
		public void onCancel() {
			fileUploadUi.setVisible(false);
			if (sliderSO.getId() == -10) {
				presenter.backToSliderList();
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
	public void setSliderSO(SliderSO sliderSO) {

		this.sliderSO = sliderSO;

		if (sliderSO.isPresentation()) {
			typeUi.setItems(Arrays.asList(SliderSO.TYPE_1CARD_A, SliderSO.TYPE_1CARD_B, SliderSO.TYPE_2CARD_A,
					SliderSO.TYPE_2CARD_B, SliderSO.TYPE_3CARD_A, SliderSO.TYPE_3CARD_B, SliderSO.TYPE_3CARD_C));
		} else {
			typeUi.setItems(Arrays.asList(SliderSO.TYPE_SIMPLE_A, SliderSO.TYPE_SIMPLE_B, SliderSO.TYPE_SIMPLE_C,
					SliderSO.TYPE_SIMPLE_D, SliderSO.TYPE_SIMPLE_E, SliderSO.TYPE_SIMPLE_F, SliderSO.TYPE_SIMPLE_G));
		}

		// boxes
		titelUi.setValue(sliderSO.getTitel());
		titel1Ui.setValue(sliderSO.getTitel1());
		titel2Ui.setValue(sliderSO.getTitel2());
		titelColorUi.setValue(sliderSO.getTitelColor());
		typeUi.setSingleValue(sliderSO.getType());
		creation2Ui.setValue(sliderSO.getCreationDate());
		activeUi.setValue(sliderSO.isActive());
		lockUi.setValue(sliderSO.isLock());
		textInfoUi.setValue(sliderSO.getTextInfo());
		indexUi.setValue(sliderSO.getIndex());
		sliderImageUi.setUrl(sliderSO.getSliderImageUrl());
		// textHeaderLabelUi.setText("Comment_Contenu:: " +
		// commentSO.getContentSO().getTitel());
		textHeaderLabelUi.setText(sliderSO.isPresentation() ? "Slider Presentation" : "Slider Normal");

	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}
}
