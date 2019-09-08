package com.materials.client.widgets.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.utils.ConstantsUtils;
import com.materials.client.widgets.icon.MDWidgetMorph;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class SearchPanel extends Composite {

	private static SearchPanelUiBinder uiBinder = GWT.create(SearchPanelUiBinder.class);

	interface SearchPanelUiBinder extends UiBinder<MaterialCard, SearchPanel> {
	}

	@UiField
	MaterialCard cardUi;

	@UiField
	MaterialCardAction cardActionUi;

	@UiField
	MaterialComboBox<String> rubriqueUi, type2BienUi, provinceUi, villeUi;

	@UiField
	MaterialTextBox autresVilleUi;

	private MDWidgetMorph buttonMorph;

	public SearchPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		MaterialButton buttonSearch = new MaterialButton("Chercher", IconType.SEARCH);
		buttonSearch.setIconPosition(IconPosition.RIGHT);
		buttonSearch.getElement().getStyle().setProperty("backgroundColor", " #960018");
		buttonSearch.getElement().getStyle().setProperty("color", " #ffb74d");

		MaterialButton buttonCreation = new MaterialButton("Creer", IconType.ADD_BOX);
		buttonCreation.setIconPosition(IconPosition.RIGHT);
		buttonCreation.getElement().getStyle().setProperty("backgroundColor", " #960018");
		buttonCreation.getElement().getStyle().setProperty("color", " #ffb74d");

		buttonMorph = new MDWidgetMorph(buttonSearch, buttonCreation);
		buttonMorph.getElement().getStyle().setMarginTop(-8, Unit.PX);

		cardActionUi.add(buttonMorph);

		rubriqueUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		rubriqueUi.setAcceptableValues(ConstantsUtils.RUBRIQUES);

		type2BienUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		provinceUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		villeUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		type2BienUi.setAcceptableValues(ConstantsUtils.RUBRIQUES_KEYS.get(ConstantsUtils.LOCATIONS));
		provinceUi.setAcceptableValues(ConstantsUtils.PROVINCES);
		villeUi.setAcceptableValues(ConstantsUtils.VILLES_EXTREME_NORD);

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

	}

	@UiHandler("rechercheCheckUi")
	public void onRecherche(ValueChangeEvent<Boolean> changeEvent) {
		if (buttonMorph != null && changeEvent.getValue()) {
			buttonMorph.showWidgetSource();

		}
	}

	@UiHandler("creationCheckUi")
	public void onCreation(ValueChangeEvent<Boolean> changeEvent) {
		if (buttonMorph != null && changeEvent.getValue()) {
			buttonMorph.showWidgetTarget();

		}
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		// Menu animation
		MaterialAnimation animationMenu = new MaterialAnimation();
		animationMenu.setTransition(Transition.FADEIN);
		animationMenu.setDelay(2000);
		animationMenu.setDuration(6000);
		animationMenu.animate(cardUi);

		new Timer() {
			@Override
			public void run() {
				cardUi.setOpacity(1);
			}
		}.schedule(2000);

	}

}
