package com.materials.client.widgets.search;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.widgets.boxSearch.BoxSearch;
import com.materials.client.widgets.button.MboaButton;
import com.materials.client.widgets.utils.WidgetUtils;

import gwt.material.design.addins.client.masonry.MaterialMasonry;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.DialogType;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogContent;
import gwt.material.design.client.ui.MaterialDialogFooter;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.jquery.client.api.Functions.Func;

public class SearchPanel5 extends Composite implements SearchPanel5View {

	private static SearchPanelUiBinder uiBinder = GWT.create(SearchPanelUiBinder.class);

	interface SearchPanelUiBinder extends UiBinder<MaterialRow, SearchPanel5> {
	}

	boolean startet = false;

	private Presenter presenter;

	@UiField
	SearchStyle style;

	@UiField
	MaterialCard cardUi;

	@UiField
	MaterialColumn sBoxUi, linkUi;

	@UiField
	MaterialCardAction cardActionUi;

	@UiField
	BoxSearch motCleUi;

	@UiField
	MaterialRow baseRowUi;
	// @UiField
	// MaterialLabel rechercheCheckUi;

	private Consumer<Boolean> consumer;

	public SearchPanel5() {
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

		WidgetUtils.addToViewPortButton(new Widget[] { buttonCreation });

		motCleUi.setConsumer(value -> presenter.searchAnnonce(value, "", ""));

		buttonCreation.addClickHandler(x -> {
			presenter.newAnnonce("", "", "");

		});

		cardActionUi.add(buttonCreation);

		CoolPasCherUI.CLIENT_FACTORY.getAllBaseMenus(menus -> {
			List<String> list = menus.stream().filter(x -> x.isMenuCategory() && !x.getSubMenuSos().isEmpty())
					.map(x -> {
						return x.getName();
					}).collect(Collectors.toList());

			Collections.sort(list);

			MaterialMasonry masonry = new MaterialMasonry();
			linkUi.add(masonry);

			for (String menu : list) {
				masonry.add(getColumn(menu));
			}

		});

		// villeUi.setSingleValue(ConstantsUtils.PROVINCES_KEYS.get(value).get(1),
		// true);

		// // LAPTOP_4K
		// ViewPort.when(new WidthBoundary(1201, 2560)).then(viewPortChange -> {
		// linkUi.setVisible(true);
		// });
		//
		// ViewPort.when(Resolution.ALL_MOBILE, Resolution.TABLET, new
		// WidthBoundary(1025, 1200)).then(viewPortChange -> {
		// linkUi.setVisible(false);
		// });

		linkUi.setHideOn(HideOn.HIDE_ON_MED_DOWN);

	}

	private MaterialColumn getColumn(String menuName) {

		MaterialColumn column = new MaterialColumn(12, 3, 4);
		MaterialLink link = new MaterialLink(menuName);
		link.setMargin(10);
		link.addStyleName(style.theLink());
		column.add(link);

		link.addClickHandler(x -> {
			MenuPlace menuPlace = new MenuPlace().handleCategory("Tout afficher", menuName);
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);
		});

		return column;

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

	interface SearchStyle extends CssResource {

		String theLink();

	}

}
