package com.materials.client.widgets.search;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.utils.ConstantsUtils;
import com.materials.client.widgets.boxSearch.BoxSearch;
import com.materials.client.widgets.button.MboaButton;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.DialogType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogContent;
import gwt.material.design.client.ui.MaterialDialogFooter;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.jquery.client.api.Functions.Func;

public class SearchPanel3 extends Composite implements SearchPanel3View {

	private static SearchPanelUiBinder uiBinder = GWT.create(SearchPanelUiBinder.class);

	interface SearchPanelUiBinder extends UiBinder<MaterialCard, SearchPanel3> {
	}

	boolean startet = false;

	private Presenter presenter;

	// private MDWidgetMorph widgetMorph;

	@UiField
	MaterialCard cardUi;

	@UiField
	MaterialCardAction cardActionUi;

	@UiField
	MaterialComboBox<String> regionUi, villeUi;

	@UiField
	BoxSearch motCleUi;

	@UiField
	MaterialTextBox autresVilleUi;

	// @UiField
	// MaterialLabel rechercheCheckUi;

	private Consumer<Boolean> consumer;

	public SearchPanel3() {
		initWidget(uiBinder.createAndBindUi(this));
		cardUi.getElement().getStyle().setProperty("borderRadius", "5px");

		MboaButton buttonCreation = new MboaButton("Créer une Annonce", IconType.ADD_CIRCLE);
		buttonCreation.setType(ButtonType.OUTLINED);
		buttonCreation.setIconPosition(IconPosition.LEFT);
		buttonCreation.getElement().getStyle().setProperty("backgroundColor", " #960018");
		buttonCreation.getElement().getStyle().setProperty("color", " #ffb74d");
		buttonCreation.getElement().getStyle().setProperty("border", " 1px solid rgb(150, 0, 24)");
		buttonCreation.setMarginLeft(-10);
		// buttonCreation.setMarginTop(-5);
		buttonCreation.setWaves(WavesType.LIGHT);

		motCleUi.setConsumer(
				value -> presenter.searchAnnonce(value, regionUi.getSingleValue(), villeUi.getSingleValue()));

		buttonCreation.addClickHandler(x -> {

			presenter.newAnnonce("", regionUi.getSingleValue(), villeUi.getSingleValue());

		});

		cardActionUi.add(buttonCreation);

		CoolPasCherUI.CLIENT_FACTORY.getAllBaseMenus(menus -> {
			List<String> list = menus.stream().filter(x -> x.isMenuCategory() && !x.getSubMenuSos().isEmpty())
					.map(x -> {
						return x.getName();
					}).collect(Collectors.toList());
		});

		regionUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		villeUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		regionUi.setAcceptableValues(ConstantsUtils.PROVINCES);
		villeUi.setAcceptableValues(ConstantsUtils.TOUTES_LES_VILLES);
		// villeUi.setSingleValue(ConstantsUtils.PROVINCES_KEYS.get(value).get(1),
		// true);

		regionUi.addValueChangeHandler(x -> {
			String value = regionUi.getSingleValue();

			villeUi.setAcceptableValues(ConstantsUtils.PROVINCES_KEYS.get(value));
			villeUi.setSingleValue(ConstantsUtils.PROVINCES_KEYS.get(value).get(1), true);
		});

		villeUi.addValueChangeHandler(x -> {
			String value = villeUi.getSingleValue();

			autresVilleUi.setVisible(value.equals("Autres Villes"));
			autresVilleUi.setValue("");

		});

	}

	@Override
	protected void onLoad() {
		super.onLoad();

		if (!startet) {
			MaterialAnimation animationMenu = new MaterialAnimation();
			animationMenu.setTransition(Transition.FADEIN);
			animationMenu.setDelay(1400);
			animationMenu.setDuration(1700);
			animationMenu.animate(cardUi, new Func() {
				@Override
				public void call() {
					cardUi.setOpacity(1);
				}
			});
			startet = true;
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	// private MaterialRow getRowPanel() {
	//
	// MaterialRow materialRow = new MaterialRow();
	// materialRow.setMarginLeft(-30);
	//
	// MaterialColumn columnLeft = new MaterialColumn();
	// MaterialColumn columnRight = new MaterialColumn();
	// MaterialColumn columnLast = new MaterialColumn();
	// materialRow.add(columnLeft);
	// materialRow.add(columnRight);
	// materialRow.add(columnLast);
	//
	// MaterialLink linkLogin = new MaterialLink("Se Connecter");
	// // linkLogin.setIconType(IconType.VPN_KEY);
	// // linkLogin.setIconPosition(IconPosition.RIGHT);
	//
	// linkLogin.addClickHandler(x -> {
	// MBoaOnlineUI.CLIENT_FACTORY.getLoginWidget().show();
	// });
	//
	// MaterialLink linkEnregistrement = new MaterialLink("S ' enregistrer");
	// // linkEnregistrement.setIconType(IconType.PERM_IDENTITY);
	// // linkEnregistrement.setIconPosition(IconPosition.RIGHT);
	//
	// linkEnregistrement.addClickHandler(x -> {
	//
	// MaterialDialog dialog = getDialog();
	// cardActionUi.add(dialog);
	//
	// dialog.open();
	// });
	//
	// MaterialIcon icon = new MaterialIcon(IconType.CLOSE);
	// icon.setIconColor(Color.RED_DARKEN_4);
	// icon.setTitle("Fermer");
	//
	// columnLeft.add(linkLogin);
	// columnRight.add(linkEnregistrement);
	// columnLast.add(icon);
	//
	// icon.addClickHandler(c -> {
	// widgetMorph.showWidgetSource();
	// });
	//
	// return materialRow;
	// }

	// public void resetButtonMorph() {
	// widgetMorph.showWidgetSource();
	// }

	private MaterialDialog getDialog() {

		MaterialDialog dialog = new MaterialDialog();
		dialog.setType(DialogType.FIXED_FOOTER);
		dialog.setDismissible(false);
		dialog.setInDuration(500);
		dialog.setOutDuration(500);

		// MaterialDialogHeader dialogHeader = new MaterialDialogHeader();
		//
		// MaterialRow materialRow = new MaterialRow();
		// materialRow.add(new Label("Nouvel Enregistrement"));
		//
		// dialogHeader.add(materialRow);
		// ;

		// dialog.add(dialogHeader);

		MaterialDialogContent dialogContent = new MaterialDialogContent();
		dialogContent.add(CoolPasCherUI.CLIENT_FACTORY.getRegisterWidget());
		dialog.add(dialogContent);

		MaterialDialogFooter dialogFooter = new MaterialDialogFooter();
		dialog.add(dialogFooter);

		MaterialLink icon = new MaterialLink("FERMER");
		icon.setIconColor(Color.RED_DARKEN_4);
		icon.setTitle("Fermer");
		icon.setPadding(10);

		dialogFooter.add(icon);

		icon.addClickHandler(c -> {
			dialog.close();
		});

		return dialog;
	}

	public void setConsumer(Consumer<Boolean> consumer) {
		this.consumer = consumer;
	}

	public void clear() {
		motCleUi.reset();

	}

	// public void showSource() {
	// if (widgetMorph.isTargetActif()) {
	// widgetMorph.showWidgetSource();
	// }
	//
	// }

}
