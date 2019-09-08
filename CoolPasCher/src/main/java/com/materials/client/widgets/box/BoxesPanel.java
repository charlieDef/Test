package com.materials.client.widgets.box;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.utils.ConstantsUtils;
import com.materials.client.widgets.icon.MDWidgetMorph;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;

public class BoxesPanel extends Composite {

	private static BoxesPanelUiBinder uiBinder = GWT.create(BoxesPanelUiBinder.class);

	interface BoxesPanelUiBinder extends UiBinder<MaterialPanel, BoxesPanel> {
	}

	@UiField
	HTMLPanel htmlPanelUi;

	@UiField
	MaterialPanel morphWidgetUi;

	private MDWidgetMorph buttonMorph;

	@UiField
	MaterialTextBox autresVilleUi;
	// @UiField
	// MaterialButton buttonUi;

	@UiField
	MaterialComboBox<String> rubriqueUi, type2BienUi, provinceUi, villeUi;

	public BoxesPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		// searchButtonUi.setText("Chercher");
		// searchButtonUi.setIconType(IconType.SEARCH);
		// searchButtonUi.setIconPosition(IconPosition.RIGHT);

		rubriqueUi.setAllowBlank(true);

		rubriqueUi.setPlaceholder("Selectionnez svp !");
		rubriqueUi.setAcceptableValues(ConstantsUtils.RUBRIQUES);
		rubriqueUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		rubriqueUi.getListbox().getElement().getStyle().setHeight(35, Unit.PX);

		type2BienUi.setAcceptableValues(ConstantsUtils.RUBRIQUES_KEYS.get(ConstantsUtils.LOCATIONS));
		type2BienUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		provinceUi.setAcceptableValues(ConstantsUtils.PROVINCES);
		provinceUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		villeUi.setAcceptableValues(ConstantsUtils.VILLES_EXTREME_NORD);
		villeUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		rubriqueUi.addValueChangeHandler(x -> {
			String value = rubriqueUi.getSingleValue();
			type2BienUi.setAcceptableValues(ConstantsUtils.RUBRIQUES_KEYS.get(value));
		});

		provinceUi.addValueChangeHandler(x -> {
			String value = provinceUi.getSingleValue();
			villeUi.setAcceptableValues(ConstantsUtils.PROVINCES_KEYS.get(value));
		});

		villeUi.addValueChangeHandler(x -> {
			String value = villeUi.getSingleValue();

			autresVilleUi.setVisible(value.equals("Autres Villes"));
			autresVilleUi.setValue("");

		});

		MaterialButton buttonSearch = new MaterialButton("Chercher", IconType.SEARCH);
		buttonSearch.setIconPosition(IconPosition.RIGHT);
		buttonSearch.getElement().getStyle().setProperty("backgroundColor", " #960018");
		buttonSearch.getElement().getStyle().setProperty("color", " #ffb74d");

		MaterialButton buttonCreation = new MaterialButton("Creer", IconType.ADD_BOX);
		buttonCreation.setIconPosition(IconPosition.RIGHT);
		buttonCreation.getElement().getStyle().setProperty("backgroundColor", " #960018");
		buttonCreation.getElement().getStyle().setProperty("color", " #ffb74d");

		buttonMorph = new MDWidgetMorph(buttonSearch, buttonCreation);
		buttonMorph.getElement().getStyle().setMarginLeft(20, Unit.PX);
		morphWidgetUi.add(buttonMorph);
		// searchButtonUi.addClickHandler(c -> {
		//
		// PushNotificationOptions options = new PushNotificationOptions();
		// options.body = "I love GWT Material";
		// options.icon = "https://i.imgur.com/VaBxpGj.png";
		// options.link = "https://prebaal.com";
		// options.timeout = 5000;
		// options.onClick = () -> {
		// MaterialToast.fireToast("Clicked");
		// };
		//
		// PushNotification.create("Gwt Material Design", options);
		//
		// });
	}

	@UiChild
	public void addChild(Widget widget) {
		htmlPanelUi.add(widget);
	}

	public void setPanelAction(boolean isSearch) {

		if (isSearch) {
			buttonMorph.showWidgetSource();
		} else {
			buttonMorph.showWidgetTarget();
		}
	}

}
