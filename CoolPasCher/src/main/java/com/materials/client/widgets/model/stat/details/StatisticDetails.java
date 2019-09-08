package com.materials.client.widgets.model.stat.details;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.image.SimpleUploader;
import com.materials.shared.APPConstants;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class StatisticDetails extends BaseDetails2 implements StatisticDetailsView {

	private static StatisticDetailsUiBinder uiBinder = GWT.create(StatisticDetailsUiBinder.class);

	interface StatisticDetailsUiBinder extends UiBinder<MaterialPanel, StatisticDetails> {
	}

	private StatisticSO statisticSO;

	@UiField
	MaterialImage itemImageUi;

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	MaterialDatePicker creationUi, activeFromUi, activeToUi;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialTextBox titelUi, categoryUi, creatorUi, labelUi, choice1Ui, choice2Ui, choice3Ui, choice4Ui, choice5Ui;

	@UiField
	MaterialTextArea descriptionUi;

	@UiField
	MaterialCheckBox actifUi, internUi;

	@UiField
	MaterialComboBox<String> choiceNrUi;

	private Presenter presenter;

	@UiHandler("backButtonUi")
	void onBackToStatisticList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToStatisticList();
	}

	public StatisticDetails(boolean config) {
		super(config);

		add(uiBinder.createAndBindUi(this));

		mdFormUi.setFormHandler(new BBarHandler());

		choiceNrUi.setItems(Arrays.asList("2", "3", "4", "5"));
		choiceNrUi.addSelectionHandler(x -> {

			String value = choiceNrUi.getSingleValue();

			switch (value) {
			case "3":
				choice3Ui.setVisible(true);
				choice4Ui.setVisible(false);
				choice5Ui.setVisible(false);
				break;
			case "4":
				choice3Ui.setVisible(true);
				choice4Ui.setVisible(true);
				choice5Ui.setVisible(false);
				break;
			case "5":
				choice3Ui.setVisible(true);
				choice4Ui.setVisible(true);
				choice5Ui.setVisible(true);
				break;
			default:
				choice3Ui.setVisible(false);
				choice4Ui.setVisible(false);
				choice5Ui.setVisible(false);
				break;
			}
		});

		fileUploadUi.setVisible(false);
		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.addValueChangeHandler(x -> {
			itemImageUi.setUrl(fileUploadUi.getValue());
			statisticSO.setRandomId(fileUploadUi.getRandomID());
		});

		handleEditEnable();

		if (!CoolPasCherUI.checkAdminMember()) {
			mdFormUi.getButtonbar().setFloatingEnabled(false);
		}
	}

	@Override
	public void setStatistic(StatisticSO statisticSO) {

		this.statisticSO = statisticSO;
		String detscription = statisticSO.getDescription() != null ? statisticSO.getDescription() : "";
		String titel = statisticSO.getTitel() != null ? statisticSO.getTitel() : "";
		String category = statisticSO.getCategory() != null ? statisticSO.getCategory() : "";
		String imageUrl = statisticSO.getImageUrl() != null ? statisticSO.getImageUrl() : "";
		Date creation = statisticSO.getCreationDate() != null ? statisticSO.getCreationDate() : new Date();
		DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

		// item
		itemImageUi.setUrl(imageUrl);
		creationUi.setValue(creation);
		actifUi.setValue(statisticSO.isActive());
		descriptionUi.setValue(statisticSO.getDescription());
		titelUi.setValue(statisticSO.getTitel());
		categoryUi.setValue(statisticSO.getCategory());
		internUi.setValue(statisticSO.isIntern());
		creatorUi.setValue(statisticSO.getCreator());
		activeFromUi.setValue(statisticSO.getActiveFrom());
		activeToUi.setValue(statisticSO.getActiveTo());
		labelUi.setValue(statisticSO.getLabel());
		choiceNrUi.setSingleValue(String.valueOf(statisticSO.getChoicesNr()));
		choice1Ui.setValue(statisticSO.getChoiceValue("1"));
		choice2Ui.setValue(statisticSO.getChoiceValue("2"));

		if (statisticSO.getChoicesNr() == 3) {
			choice3Ui.setValue(statisticSO.getChoiceValue("3"));
			choice3Ui.setVisible(true);
		}
		if (statisticSO.getChoicesNr() == 4) {
			choice4Ui.setValue(statisticSO.getChoiceValue("4"));
			choice3Ui.setValue(statisticSO.getChoiceValue("3"));
			choice3Ui.setVisible(true);
			choice4Ui.setVisible(true);
		}
		if (statisticSO.getChoicesNr() == 5) {
			choice5Ui.setValue(statisticSO.getChoiceValue("5"));
			choice4Ui.setValue(statisticSO.getChoiceValue("4"));
			choice3Ui.setValue(statisticSO.getChoiceValue("3"));
			choice5Ui.setVisible(true);
			choice3Ui.setVisible(true);
			choice4Ui.setVisible(true);
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			fileUploadUi.setVisible(true);
		}

		@Override
		public void onSave() {
			fileUploadUi.setVisible(false);

			statisticSO.setActive(actifUi.getValue());
			statisticSO.setDescription(descriptionUi.getValue());
			statisticSO.setTitel(titelUi.getValue());
			statisticSO.setCategory(categoryUi.getValue());
			statisticSO.setIntern(internUi.getValue());
			statisticSO.setCreator(creatorUi.getValue());
			statisticSO.setActiveFrom(activeFromUi.getValue());
			statisticSO.setActiveTo(activeToUi.getValue());
			statisticSO.setLabel(labelUi.getValue());
			statisticSO.setChoicesNr(Integer.valueOf(choiceNrUi.getSingleValue()));
			statisticSO.addChoice("1", choice1Ui.getValue());
			statisticSO.addChoice("2", choice2Ui.getValue());

			if (choice3Ui.isVisible()) {
				statisticSO.addChoice("3", choice3Ui.getValue());
			}
			if (choice4Ui.isVisible()) {
				statisticSO.addChoice("4", choice4Ui.getValue());
			}
			if (choice5Ui.isVisible()) {
				statisticSO.addChoice("5", choice5Ui.getValue());
			}
			presenter.save(statisticSO);
		}

		@Override
		public void onCancel() {
			fileUploadUi.setVisible(false);
			if (statisticSO.getId() == -10) {
				presenter.backToStatisticList();
				;
			}
		}
	}

	@Override
	public void setEdit() {
		mdFormUi.edit();

	}

	@Override
	protected void onLoad() {
		super.onLoad();
		CoolPasCherUI.CLIENT_FACTORY.addAnimation(materialCardUi, null, 1200);
	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;
	}

}
