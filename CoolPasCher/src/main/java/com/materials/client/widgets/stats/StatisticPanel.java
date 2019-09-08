package com.materials.client.widgets.stats;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.materials.client.model.stat.StatItemSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.widgets.button.MboaButton;

import gwt.material.design.client.constants.RadioButtonType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class StatisticPanel extends MaterialPanel implements StatisticPanelView {

	private Presenter presenter;
	private StatItemSO statItemSO;
	private boolean isConfig = false;
	private List<MaterialRadioButton> radioButtons = new ArrayList<MaterialRadioButton>();

	private static StatisticPanelUiBinder uiBinder = GWT.create(StatisticPanelUiBinder.class);

	interface StatisticPanelUiBinder extends UiBinder<MaterialRow, StatisticPanel> {
	}

	@UiField
	MaterialRow validatorUi, boxesUi;

	@UiField
	MaterialCard materialCardUI;

	@UiField
	MaterialImage imageUi;
	
	@UiField
	MaterialCardTitle cardLabelUi;

	@UiField
	MaterialLabel titelLabelUi,descriptionUi;

	@UiField
	MboaButton sendUi, backButtonUi;

	@UiField
	MaterialTextBox emailUi, nameUi, prenomUi;

	@UiField
	MaterialTextArea textAreaUi;

	public StatisticPanel(boolean isConfig) {
		this.isConfig = isConfig;
		MaterialRow materialRow = uiBinder.createAndBindUi(this);
		add(materialRow);

		backButtonUi.addClickHandler(c -> {
			presenter.goBackToItemList();
		});

		sendUi.addClickHandler(c -> {
			if (!statItemSO.getStatisticSO().isIntern()) {
				statItemSO.setCreatorEmail(emailUi.getValue());
				statItemSO.setCreator(nameUi.getValue());
			}

			statItemSO.setCreationDate(new Date());
			statItemSO.setMessage(textAreaUi.getValue());
			presenter.save(statItemSO);
		});

	}
	


	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setStatisticItem(StatItemSO statItemSO) {

		StatisticSO statisticSO = statItemSO.getStatisticSO();
		radioButtons.clear();

		this.statItemSO = statItemSO;
		textAreaUi.setValue(statItemSO.getMessage());
		cardLabelUi.setText(statisticSO.getLabel());
		titelLabelUi.setText(statisticSO.getTitel());
		imageUi.setUrl(statisticSO.getImageUrl());
		descriptionUi.setText(statisticSO.getDescription());
		backButtonUi.setVisible(isConfig);
		sendUi.setVisible(!isConfig);
		boxesUi.clear();

		// if (!statisticSO.isIntern()) {
		// emailUi.setVisible(true);
		// nameUi.setVisible(true);
		// }

		for (int i = 0; i < statisticSO.getChoicesNr(); i++) {
			String choice = statisticSO.getChoiceValue(String.valueOf(i + 1));
			MaterialColumn column = getCheckBoxRow(choice, statItemSO.getChoice());
			boxesUi.add(column);
		}
	}

	private MaterialColumn getCheckBoxRow(String choiceName, String itemChoice) {
		MaterialColumn column = new MaterialColumn(12, 3, 3);
		MaterialRadioButton radioButton = new MaterialRadioButton("choice", choiceName);
		radioButton.setValue(itemChoice.equals(choiceName));
		radioButton.setType(RadioButtonType.GAP);
		column.add(radioButton);
		radioButtons.add(radioButton);
		radioButton.addValueChangeHandler(x -> {
			if (x.getValue()) {
				statItemSO.setChoice(choiceName);
			}
		});
		return column;
	}

	public void enabled(boolean enable) {
		for (MaterialRadioButton radioButton : radioButtons) {
			radioButton.setEnabled(enable);
		}
		textAreaUi.setEnabled(enable);
		sendUi.setEnabled(enable);
	}

}
