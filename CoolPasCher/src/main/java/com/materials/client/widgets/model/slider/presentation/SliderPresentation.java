package com.materials.client.widgets.model.slider.presentation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ByteDataSO;
import com.materials.client.model.SliderSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.datagrid.DataGridWidget;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.MDFileUploader;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class SliderPresentation extends BaseDetails2 implements SliderPresentationView {

	private static SliderPresentationUiBinder uiBinder = GWT.create(SliderPresentationUiBinder.class);

	interface SliderPresentationUiBinder extends UiBinder<Widget, SliderPresentation> {
	}

	private DataGridWidget<ByteDataSO> cellList;
	private SliderSO sliderSO;
	private ByteDataSO byteDataSO;
	private Presenter presenter;

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	FlowPanel cellListPanelUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialIntegerBox indexUi;

	@UiField
	MaterialDatePicker creation2Ui;

	@UiField
	MDFileUploader fileUploadUi;

	@UiField
	MaterialTextBox titelUi, titel1Ui, titel2Ui;

	@UiField
	MaterialCheckBox activeUi, lockUi, showBaseImageUi;

	@UiField
	MaterialImage sliderImageUi;

	@UiHandler("backButtonUi")
	void onBackToCommentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToSliderList();
		sliderImageUi.setUrl("img/newUser.jpg");
	}

	@UiHandler("showBaseImageUi")
	void onValueChanged(ValueChangeEvent<Boolean> event) {

		if (event.getValue()) {
			cellListPanelUi.clear();
			sliderImageUi.setUrl(sliderSO.getSliderImageUrl());
		} else {
			cellListPanelUi.add(cellList);
			sliderImageUi.setUrl(byteDataSO != null ? byteDataSO.getImageUrl() : "");
		}
	}

	public SliderPresentation(boolean config) {
		super(config);
		add(uiBinder.createAndBindUi(this));
		mdFormUi.setFormHandler(new BBarHandler());

		initCellList();
		fileUploadUi.setDeleteLast(false);
		fileUploadUi.setUploadCompletListener((tempUploaID, fileType) -> {

			// String fileName = array[1];
			String urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + tempUploaID;

			sliderImageUi.setUrl(urlImg);
			if (showBaseImageUi.getValue()) {
				sliderSO.setRandomId(tempUploaID);
			} else {
				byteDataSO.setRandomId(tempUploaID);
				cellList.refresh();
			}
		});
		handleEditEnable();
	}

	private void initCellList() {
		cellList = new DataGridWidget<ByteDataSO>("ByteData", ByteDataSO::getId);

		cellList.addTextColumn("Index", x -> {
			return String.valueOf(x.getIndex());
		}, null);
		cellList.addTextColumn("R_ID", ByteDataSO::getRandomId, null);
		// cellList.setData(sliderSO.getByteDataSOs());
		// cellListPanelUi.add(cellList);

		cellList.setSelectionConsumer(x -> {
			this.byteDataSO = x;
			if (this.byteDataSO != null) {
				if (byteDataSO.getRandomId() != null) {
					sliderImageUi.setUrl(byteDataSO.getImageUrl());
				} else {
					sliderImageUi.setUrl("");
				}

			}

		});
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
		}

		@Override
		public void onSave() {
			sliderSO.setActive(activeUi.getValue());
			sliderSO.setLock(lockUi.getValue());
			sliderSO.setTitel(titelUi.getValue());
			sliderSO.setTitel1(titel1Ui.getValue());
			sliderSO.setTitel2(titel2Ui.getValue());
			sliderSO.setIndex(indexUi.getValue());
			presenter.saveSliderPresentation(sliderSO);
		}

		@Override
		public void onCancel() {
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

		// boxes
		titelUi.setValue(sliderSO.getTitel());
		titel1Ui.setValue(sliderSO.getTitel1());
		titel2Ui.setValue(sliderSO.getTitel2());
		creation2Ui.setValue(sliderSO.getCreationDate());
		indexUi.setValue(sliderSO.getIndex());
		activeUi.setValue(sliderSO.isActive());
		lockUi.setValue(sliderSO.isLock());
		sliderImageUi.setUrl(CoolPasCherUI.MODUL_BASE_FILEHELPER + "?sliderID=" + sliderSO.getRandomId());
		// textHeaderLabelUi.setText("Comment_Contenu:: " +
		// commentSO.getContentSO().getTitel());
		textHeaderLabelUi.setText("Slider:: ");

		cellList.setData(sliderSO.getByteDataSOs());

	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}

}
