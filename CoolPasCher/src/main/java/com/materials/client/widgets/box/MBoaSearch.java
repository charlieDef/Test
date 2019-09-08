package com.materials.client.widgets.box;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.utils.ConstantsUtils;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class MBoaSearch extends Composite {

	private static MBoaSearchUiBinder uiBinder = GWT.create(MBoaSearchUiBinder.class);

	interface MBoaSearchUiBinder extends UiBinder<MaterialRow, MBoaSearch> {
	}

	@UiField
	MaterialTextBox autresVilleUi;
	@UiField
	MaterialButton configButtonUi;

	@UiField
	MaterialComboBox<String> type2BienUi, provinceUi, villeUi;

	public MBoaSearch() {
		initWidget(uiBinder.createAndBindUi(this));
		// tabCarousel.setTabNavigation(tab);

		type2BienUi.setAcceptableValues(ConstantsUtils.RUBRIQUES_KEYS.get(ConstantsUtils.LOCATIONS));
		type2BienUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		provinceUi.setAcceptableValues(ConstantsUtils.PROVINCES);
		provinceUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		villeUi.setAcceptableValues(ConstantsUtils.VILLES_EXTREME_NORD);
		villeUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		provinceUi.addValueChangeHandler(x -> {
			String value = provinceUi.getSingleValue();
			villeUi.setAcceptableValues(ConstantsUtils.PROVINCES_KEYS.get(value));
		});

		villeUi.addValueChangeHandler(x -> {
			String value = villeUi.getSingleValue();

			autresVilleUi.setVisible(value.equals("Autres Villes"));
			autresVilleUi.setValue("");

		});
	}

}
