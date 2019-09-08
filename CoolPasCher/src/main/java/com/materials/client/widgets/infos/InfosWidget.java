package com.materials.client.widgets.infos;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.places.content.ContentPlace;
import com.materials.shared.MethodsUtils;

import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.ImageType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.jquery.client.api.Functions.Func;

public class InfosWidget extends Composite {

	private static InfosWidgetUiBinder uiBinder = GWT.create(InfosWidgetUiBinder.class);

	interface InfosWidgetUiBinder extends UiBinder<MaterialCard, InfosWidget> {
	}

	private Map<String, MaterialRow> map = new HashMap<String, MaterialRow>();

	@UiField
	Style style;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MaterialRow topUi, middleUi, bottomUi;

	public InfosWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		// materialCardUi.setOpacity(0);
		// materialCardUi.getElement().getStyle().setProperty("minWidth", "250px");
	}

	public void addHCardItem(String areaName, ContentSO contentSO) {

		MaterialRow row = getMaterialRow(areaName);
		MaterialCard card = new MaterialCard();
		card.setHoverable(true);
		// card.setOrientation(Orientation.LANDSCAPE);
		card.addStyleName(style.card());
		row.add(card);

		MaterialCardImage cardImage = new MaterialCardImage();
		cardImage.setWaves(WavesType.LIGHT);
		MaterialImage materialImage = new MaterialImage(contentSO.getTitelImageUrl());
		materialImage.addStyleName(style.imageCard());
		cardImage.add(materialImage);
		card.add(cardImage);

		MaterialCardContent cardContent = new MaterialCardContent();
		cardContent.addStyleName(style.cardContent());

		MaterialLabel titel = new MaterialLabel(contentSO.getTitel());
		titel.addStyleName(style.cardTitle());
		MaterialLabel description = new MaterialLabel(contentSO.getTitel());

		cardContent.add(titel);
		cardContent.add(description);

		card.add(cardContent);
	}

	public void addCardItem(String areaName, ContentSO contentSO, IconType iconType, Color color) {

		MaterialRow row = getMaterialRow(areaName);
		MaterialCard card = new MaterialCard();
		card.getElement().getStyle().setProperty("borderRadius", "4px");
		card.addStyleName(style.card());
		card.setTitle(contentSO.getDescription());
		row.add(card);

		MaterialCollection materialCollection = new MaterialCollection();
		materialCollection.getElement().getStyle().setProperty("borderRadius", "4px");
		materialCollection.getElement().getStyle().setProperty("minWidth", "230px");
		MaterialCollectionItem collectionItem = new MaterialCollectionItem();
		collectionItem.setType(CollectionType.AVATAR);
		// collectionItem.setWaves(WavesType.LIGHT);
		collectionItem.setHoverable(true);
		materialCollection.add(collectionItem);

		collectionItem.setBackgroundColor(Color.BLUE_GREY_DARKEN_1);
		collectionItem.setActive(true);

		// materialCollection.setHeader(contentSO.getCategory());

		MaterialImage image = new MaterialImage(contentSO.getTitelImageUrl());
		image.addStyleName(style.imageCircle());
		image.setType(ImageType.CIRCLE);

		collectionItem.add(image);

		MaterialLabel titel = new MaterialLabel(contentSO.getTitel());
		titel.setMarginLeft(15);
		titel.setFontSize("1.4em");
		titel.addStyleName(style.cardTitle());

		collectionItem.add(titel);

		MaterialLabel description = new MaterialLabel(contentSO.getDescription());
		description.setMarginLeft(15);

		collectionItem.add(description);

		if (iconType != null) {
			MaterialCollectionSecondary secondary = new MaterialCollectionSecondary();
			MaterialIcon icon = new MaterialIcon(iconType);
			icon.setIconColor(color);
			secondary.add(icon);

			collectionItem.add(secondary);
		}

		if (contentSO.getViewAble() == 1) {
			ContentPlace contentPlace = new ContentPlace(contentSO.getType(), "article", contentSO.getTitel());
			card.getElement().getStyle().setCursor(Cursor.POINTER);
			card.addClickHandler(c -> {
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(contentPlace);
			});
		} else if (MethodsUtils.isStringOK(contentSO.getTextInfo())
				&& (contentSO.getTextInfo().contains("http//") || contentSO.getTextInfo().contains("www."))) {
			card.getElement().getStyle().setCursor(Cursor.POINTER);
			card.addClickHandler(c -> {
				Window.open(contentSO.getTextInfo(), "_blank", "");
			});
		}

		card.add(materialCollection);

	}

	public void addWidgetItem(String areaName, Widget widget, TextAlign textAlign, boolean asCard) {
		MaterialRow row = getMaterialRow(areaName);

		if (asCard) {
			MaterialCard card = new MaterialCard();
			card.setTextAlign(textAlign);

			card.getElement().getStyle().setProperty("borderRadius", "4px");
			card.addStyleName(style.card());
			row.add(card);
			card.add(widget);
		} else {
			row.add(widget);
		}
	}

	public void addWidgetItemTop(String areaName, Widget widget, TextAlign textAlign, boolean asCard) {
		MaterialRow materialRow = getMaterialRow(areaName, true);
		if (asCard) {
			MaterialCard card = new MaterialCard();
			card.setTextAlign(textAlign);

			card.getElement().getStyle().setProperty("borderRadius", "4px");
			card.addStyleName(style.card());
			materialRow.add(card);
			card.add(widget);
		} else {
			materialRow.add(widget);
		}
	}

	public void addWidgetItemBottom(String areaName, Widget widget, TextAlign textAlign, boolean asCard) {

		MaterialRow materialRow = getMaterialRow(areaName, false);

		if (asCard) {
			MaterialCard card = new MaterialCard();
			card.setTextAlign(textAlign);

			card.getElement().getStyle().setProperty("borderRadius", "4px");
			card.addStyleName(style.card());
			materialRow.add(card);
			card.add(widget);
		} else {
			materialRow.add(widget);
		}
	}

	interface Style extends CssResource {

		String areaTitle();

		String rightContainer();

		String card();

		String imageCard();

		String imageCircle();

		String cardTitle();

		String cardContent();

	}

	private MaterialRow getMaterialRow(String areaName) {

		MaterialRow materialRow = map.get(areaName);
		if (materialRow == null) {
			materialRow = new MaterialRow();
			HTML html = new HTML(areaName);
			html.addStyleName(style.areaTitle());
			materialRow.add(html);

			middleUi.add(materialRow);
			map.put(areaName, materialRow);
		}
		return materialRow;
	}

	private MaterialRow getMaterialRow(String areaName, boolean top) {

		MaterialRow materialRow = map.get(areaName);
		if (materialRow == null) {
			materialRow = new MaterialRow();
			HTML html = new HTML(areaName);
			html.addStyleName(style.areaTitle());
			materialRow.add(html);

			if (top) {
				topUi.add(materialRow);
			} else {
				bottomUi.add(materialRow);
			}
			map.put(areaName, materialRow);
		}
		return materialRow;
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();

		MaterialAnimation animationMenu = new MaterialAnimation();
		animationMenu.setTransition(Transition.FADEIN);
		animationMenu.setDelay(5000);
		animationMenu.setDuration(3000);
		// animationMenu.animate(materialCardUi);

		animationMenu.animate(materialCardUi, new Func() {

			@Override
			public void call() {
				// TODO Auto-generated method stub
				materialCardUi.setOpacity(1);
			}
		});

		// new Timer() {
		// @Override
		// public void run() {
		// materialCardUi.setOpacity(1);
		// }
		//
		// }.schedule(80000);

	}

	// @Override
	// protected void onLoad() {
	// super.onLoad();
	// if (!startet) {
	// Index.CLIENT_FACTORY.startJESSORSlider();
	// startet = true;
	// // Menu animation
	// MaterialAnimation animationMenu = new MaterialAnimation();
	// animationMenu.setTransition(Transition.FADEIN);
	// animationMenu.setDelay(7000);
	// animationMenu.setDuration(4000);
	// animationMenu.animate(htmlPanelUi);
	//
	// new Timer() {
	// @Override
	// public void run() {
	// htmlPanelUi.setOpacity(1);
	// }
	//
	// }.schedule(7000);
	// }
	//
	// // Window.alert("Plattform: " + Navigator.getPlatform());
	// }
}
