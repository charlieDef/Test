package com.materials.client.widgets.search;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.places.content.ContentPlace;
import com.materials.client.utils.ConstantsUtils;
import com.materials.client.widgets.icon.MDWidgetMorph;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialTextBox;

public class SearchPanel2 extends Composite implements SearchPanel2View {

	private static SearchPanelUiBinder uiBinder = GWT.create(SearchPanelUiBinder.class);

	interface SearchPanelUiBinder extends UiBinder<MaterialCard, SearchPanel2> {
	}

	private Presenter presenter;

	@UiField
	MaterialCard cardUi;

	@UiField
	MaterialCardAction cardActionUi;

	@UiField
	MaterialComboBox<String> provinceUi, villeUi;

	@UiField
	MaterialTextBox autresVilleUi;

	@UiField
	MaterialRadioButton rechercheCheckUi;

	private MDWidgetMorph buttonMorph;

	public SearchPanel2() {
		initWidget(uiBinder.createAndBindUi(this));

		MaterialButton buttonSearch = new MaterialButton("Rechercher", IconType.SEARCH);
		buttonSearch.setWaves(WavesType.LIGHT);
		buttonSearch.setIconPosition(IconPosition.RIGHT);
		buttonSearch.getElement().getStyle().setProperty("backgroundColor", " #960018");
		buttonSearch.getElement().getStyle().setProperty("color", " #ffb74d");

		MaterialButton buttonCreation = new MaterialButton("Configurer", IconType.SETTINGS);
		buttonCreation.setIconPosition(IconPosition.RIGHT);
		buttonCreation.getElement().getStyle().setProperty("backgroundColor", " #960018");
		buttonCreation.getElement().getStyle().setProperty("color", " #ffb74d");
		buttonCreation.setWaves(WavesType.LIGHT);

		// categorieUi.setAllowBlank(true);

		buttonCreation.addClickHandler(x -> {

			ContentPlace contentPlace = new ContentPlace("article", "annonce", true);
			// contentPlace.setCategory(categorieUi.getSingleValue());
			contentPlace.setVille(villeUi.getSingleValue());
			contentPlace.setRegion(provinceUi.getSingleValue());

			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(contentPlace);

		});
		buttonSearch.addClickHandler(x -> {
		});

		buttonMorph = new MDWidgetMorph(buttonSearch, buttonCreation);
		buttonMorph.getElement().getStyle().setMarginTop(-8, Unit.PX);
		cardActionUi.add(buttonMorph);

		CoolPasCherUI.CLIENT_FACTORY.getAllBaseMenus(menus -> {
			List<String> list = menus.stream().filter(x -> x.isMenuCategory() && !x.getSubMenuSos().isEmpty())
					.map(x -> {
						return x.getName();
					}).collect(Collectors.toList());
			// categorieUi.setAcceptableValues(list);
		});

		// type2BienUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		provinceUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		villeUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		// categorieUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		// type2BienUi.setAcceptableValues(ConstantsUtils.RUBRIQUES_KEYS.get(ConstantsUtils.LOCATIONS));
		provinceUi.setAcceptableValues(ConstantsUtils.PROVINCES);
		villeUi.setAcceptableValues(ConstantsUtils.VILLES_EXTREME_NORD);

		// categorieUi.addValueChangeHandler(x -> {
		// String value = categorieUi.getSingleValue();
		// // type2BienUi.setAcceptableValues(ConstantsUtils.RUBRIQUES_KEYS.get(value));
		// });

		provinceUi.addValueChangeHandler(x -> {
			String value = provinceUi.getSingleValue();

			villeUi.setAcceptableValues(ConstantsUtils.PROVINCES_KEYS.get(value));
			villeUi.setSingleValue(ConstantsUtils.PROVINCES_KEYS.get(value).get(1), true);
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

		// // Menu animation
		// MaterialAnimation animationMenu = new MaterialAnimation();
		// animationMenu.setTransition(Transition.FADEIN);
		// animationMenu.setDelay(6000);
		// animationMenu.setDuration(6000);
		// animationMenu.animate(cardUi);
		//
		// new Timer() {
		// @Override
		// public void run() {
		// cardUi.setOpacity(1);
		// }
		// }.schedule(2000);

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
