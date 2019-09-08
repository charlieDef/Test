package com.materials.client.widgets.articles.recentes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.places.content.ContentPlace;
import com.materials.client.widgets.swipe.SwipperWidget;
import com.materials.client.widgets.utils.WidgetUtils;

import gwt.material.design.addins.client.masonry.MaterialMasonry;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialCardReveal;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.incubator.client.toggle.GroupToggleButton;

public class ArticleRecentes extends Composite {

	private static ArticleRecentesUiBinder uiBinder = GWT.create(ArticleRecentesUiBinder.class);

	interface ArticleRecentesUiBinder extends UiBinder<Widget, ArticleRecentes> {
	}

	@UiField
	SwipperWidget swipperWidgetUi;

	@UiField
	GroupToggleButton<Integer> groupToggle;

	@UiField
	Style style;
	private int pageNr = 1;

	private Map<Integer, MaterialMasonry> pages = new HashMap<Integer, MaterialMasonry>();

	private List<ContentSO> contentSOs;

	public ArticleRecentes(List<ContentSO> contentSOs) {
		initWidget(uiBinder.createAndBindUi(this));
		this.contentSOs = contentSOs;

		init();

		swipperWidgetUi.show("0");

		// WidgetUtils.addToViewPortButton(new Widget[] { groupToggle });

		groupToggle.addSelectionHandler(e -> {

			int index = e.getSelectedItem();
			if (CoolPasCherUI.CLIENT_FACTORY.getPageIndex() != e.getSelectedItem()) {

				if (pages.get(index) != null) {
					swipperWidgetUi.swipeTo("" + index, Transition.FADE_IN_IMAGE);
					pages.get(index).reload();
				} else {

					MaterialMasonry materialMasonry = new MaterialMasonry();
					// MaterialLoader.loading(true, swipperWidgetUi);

					CoolPasCherUI.CLIENT_FACTORY.getContentsByPage(String.valueOf(index), list -> {
						// MaterialLoader.loading(false, swipperWidgetUi);

						for (ContentSO contentSO : list) {
							MaterialColumn column = getColumnCard(contentSO);
							materialMasonry.add(column);
						}
						pages.put(index, materialMasonry);

						swipperWidgetUi.addSwipeItem("" + index, materialMasonry, true);
						swipperWidgetUi.swipeTo("" + index, Transition.FADE_IN_IMAGE);

						materialMasonry.reload();
					});
				}
				CoolPasCherUI.CLIENT_FACTORY.setPageIndex(index);

			}

		});
	}

	public void reload() {

		pages.get(CoolPasCherUI.CLIENT_FACTORY.getPageIndex()).reload();
	}

	private MaterialColumn getColumnCard(ContentSO contentSO) {

		MaterialColumn materialColumn = new MaterialColumn();
		materialColumn.setGrid("s12 m6 l3");

		MaterialCard card = new MaterialCard();

		card.setHoverable(true);
		card.setBackgroundColor(Color.WHITE);

		// image
		MaterialCardImage cardImage = new MaterialCardImage();
		cardImage.getElement().getStyle().setCursor(Cursor.POINTER);

		MaterialImage image = new MaterialImage(contentSO.getTitelImageUrl());
		image.addStyleName(style.imageCard());
		image.getElement().getStyle().setBackgroundColor("white");
		image.getElement().getStyle().setPadding(7, Unit.PX);
		cardImage.add(image);

		MaterialCardContent cardContent = new MaterialCardContent();
		cardContent.addStyleName(style.cardContent());

		MaterialRow materialRow = new MaterialRow();
		materialRow.setMarginBottom(5);
		MaterialChip prix = new MaterialChip(contentSO.getPrix());
		prix.getElement().getStyle().setProperty("borderRadius", "5px");
		prix.setFloat(Float.RIGHT);
		materialRow.add(prix);

		// titel
		MaterialLabel cardTitle = new MaterialLabel();
		cardTitle.getElement().getStyle().setCursor(Cursor.POINTER);
		cardTitle.addStyleName(style.cardTitle());
		cardTitle.setText(contentSO.getTitel());
		cardTitle.setFloat(Float.LEFT);
		materialRow.add(cardTitle);
		cardContent.add(materialRow);

		// description
		MaterialLabel description = new MaterialLabel(contentSO.getDescription());
		description.getElement().getStyle().setMarginBottom(15, Unit.PX);
		cardContent.add(description);

		MaterialCardReveal cardReveal = new MaterialCardReveal();
		cardReveal.getElement().getStyle().setProperty("fontFamily", "times new roman, times, serif");
		MaterialCardTitle cardTitleReveal = new MaterialCardTitle();
		cardTitleReveal.setIconType(IconType.CLOSE);
		cardTitleReveal.setIconPosition(IconPosition.RIGHT);
		cardTitleReveal.setTextColor(Color.BLACK);
		cardReveal.add(cardTitleReveal);

		MaterialLabel materialLabel = new MaterialLabel(contentSO.getDescription2());
		materialLabel.getElement().getStyle().setMarginBottom(10, Unit.PX);
		cardReveal.add(materialLabel);

		// MaterialIcon icon = new MaterialIcon(IconType.LOCATION_CITY);
		// icon.setIconColor(Color.GREY_DARKEN_1);
		// MaterialLabel materialLabel2 = new MaterialLabel(contentSO.getVille());
		// materialLabel2.getElement().getStyle().setProperty("fontStyle", "italic");
		// materialLabel2.getElement().getStyle().setProperty("fontWeight", "bold");
		// materialLabel2.getElement().getStyle().setProperty("marginTop", "5px");
		// materialLabel2.getElement().getStyle().setProperty("color", "#424242 ");
		// materialLabel2.setMarginRight(-10);
		// materialLabel2.setFloat(Float.RIGHT);
		//
		// icon.setIconPosition(IconPosition.RIGHT);
		// materialRow.add(icon);
		// materialRow.add(materialLabel2);

		MaterialAnchorButton anchorButton = new MaterialAnchorButton(ButtonType.LINK);
		anchorButton.setText("Aller à l'annonce");
		cardReveal.add(anchorButton);

		card.add(cardImage);
		card.add(cardContent);
		// card.add(cardReveal);

		materialColumn.add(card);

		card.addStyleName(style.myCard());

		ContentPlace contentPlace = new ContentPlace(contentSO.getType(), "article", contentSO.getTitel());

		if (contentSO.getViewAble() == 1) {
			cardTitle.addClickHandler(c -> {
				CoolPasCherUI.CLIENT_FACTORY.setScrollIndex(Window.getScrollTop());
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(contentPlace);
			});
			anchorButton.addClickHandler(c -> {
				CoolPasCherUI.CLIENT_FACTORY.setScrollIndex(Window.getScrollTop());
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(contentPlace);
			});
			cardImage.addClickHandler(c -> {
				CoolPasCherUI.CLIENT_FACTORY.setScrollIndex(Window.getScrollTop());
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(contentPlace);
			});
		}

		WidgetUtils.addToViewPortArticle(new Widget[] { cardTitle });

		return materialColumn;
	}

	interface Style extends CssResource {

		String important();

		String imageCard();

		String cardTitle();

		String cardContent();

		String filter();

		String myCard();

	}

	@Override
	protected void onLoad() {
		super.onLoad();

		new Timer() {
			@Override
			public void run() {
				groupToggle.setActive(CoolPasCherUI.CLIENT_FACTORY.getPageIndex());
			}
		}.schedule(250);
	}

	private void init() {

		groupToggle.addItem("1");
		groupToggle.addItem("2");
		groupToggle.addItem("3");
		pageNr = 3;

		int contentNr = 0;
		// groupToggle.addItem(index);
		MaterialMasonry materialMasonry = new MaterialMasonry();

		for (int j = 0; j < 8; j++) {
			int actualIndex = j + contentNr;
			if ((actualIndex < contentSOs.size()) && contentSOs.get(actualIndex) != null) {
				ContentSO contentSO = contentSOs.get(actualIndex);
				MaterialColumn column = getColumnCard(contentSO);
				materialMasonry.add(column);
			}
		}
		contentNr += 8;
		pages.put(0, materialMasonry);
		swipperWidgetUi.addSwipeItem("0", materialMasonry, true);

	}

	public void addArticle(ContentSO saved) {
		this.contentSOs.add(saved);

	}

	// private void init() {
	//
	// groupToggle.addItem("1");
	// groupToggle.addItem("2");
	// groupToggle.addItem("3");
	// pageNr = 3;
	//
	// // pageNr = contentSOs.size() / 8;
	// // if ((contentSOs.size() % 8) != 0) {
	// // pageNr += 1;
	// // }
	//
	// int contentNr = 0;
	// for (int i = 0; i < pageNr; i++) {
	// int index = i + 1;
	// // groupToggle.addItem(index);
	// MaterialMasonry materialMasonry = new MaterialMasonry();
	//
	// for (int j = 0; j < 8; j++) {
	// int actualIndex = j + contentNr;
	// if ((actualIndex < contentSOs.size()) && contentSOs.get(actualIndex) != null)
	// {
	// ContentSO contentSO = contentSOs.get(actualIndex);
	// MaterialColumn column = getColumnCard(contentSO);
	// materialMasonry.add(column);
	// }
	// }
	// contentNr += 8;
	// pages.put(i, materialMasonry);
	// swipperWidgetUi.addSwipeItem("" + i, materialMasonry, true);
	//
	// }
	//
	// }
}
