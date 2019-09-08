package com.materials.client.widgets.articles.sortable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
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
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class SortableArticle extends Composite {

	private static SortableArticleUiBinder uiBinder = GWT.create(SortableArticleUiBinder.class);

	interface SortableArticleUiBinder extends UiBinder<MaterialPanel, SortableArticle> {
	}

	private int size;

	private String name;

	private boolean onlyShowHome = true, only8Articles = false;

	private Map<String, MaterialMasonry> categories = new HashMap<String, MaterialMasonry>();
	private Map<String, MaterialRow> mansorieRows = new HashMap<String, MaterialRow>();

	@UiField
	Style style;

	@UiField
	MaterialPanel materialPanelUi, baseUi;

	@UiField
	MaterialRadioButton sortTitelUi, sortVilleUi, sortCreationUi, sortDeleteUi;

	private List<ContentSO> contentSOs;

	public SortableArticle(List<ContentSO> contentSOs, int animationDelais, int animationDuration) {
		initWidget(uiBinder.createAndBindUi(this));
		this.contentSOs = contentSOs;
		this.size = contentSOs.size();

		init(contentSOs, animationDelais, animationDuration);

	}

	public SortableArticle(List<ContentSO> contentSOs, int animationDelais, int animationDuration, String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.contentSOs = contentSOs;
		this.name = name;
		init(contentSOs, animationDelais, animationDuration);
	}

	private void init(List<ContentSO> contentSOs, int animationDelais, int animationDuration) {
		sortTitelUi.getElement().getStyle().setMarginLeft(-12, Unit.PX);
		sortVilleUi.getElement().getStyle().setMarginLeft(-12, Unit.PX);
		sortCreationUi.getElement().getStyle().setMarginLeft(-12, Unit.PX);
		sortDeleteUi.getElement().getStyle().setMarginLeft(-12, Unit.PX);

		initialise(contentSOs, animationDelais, animationDuration);

		sortTitelUi.addClickHandler(x -> {
			this.contentSOs.sort(new Comparator<ContentSO>() {
				@Override
				public int compare(ContentSO o1, ContentSO o2) {
					return o1.getTitel().compareTo(o2.getTitel());
				}
			});
			doSort(this.contentSOs);
		});

		sortVilleUi.addClickHandler(x -> {
			this.contentSOs.sort(new Comparator<ContentSO>() {
				@Override
				public int compare(ContentSO o1, ContentSO o2) {
					return o1.getVille().compareTo(o2.getVille());
				}
			});
			doSort(this.contentSOs);
		});

		sortCreationUi.addClickHandler(x -> {
			this.contentSOs.sort(new Comparator<ContentSO>() {
				@Override
				public int compare(ContentSO o1, ContentSO o2) {
					return o1.getCreationDate().compareTo(o2.getCreationDate());
				}
			});

			doSort(this.contentSOs);
		});

		sortDeleteUi.addClickHandler(x -> {
			this.contentSOs.sort(new Comparator<ContentSO>() {
				@Override
				public int compare(ContentSO o1, ContentSO o2) {
					return o1.getRandomId().compareTo(o2.getRandomId());
				}
			});

			doSort(this.contentSOs);
		});
	}

	public void doSort(List<ContentSO> contentSOs) {
		categories.clear();
		mansorieRows.clear();
		materialPanelUi.clear();

		initialise(contentSOs, 1, 3500);

	}

	public void addArticle(ContentSO saved) {
		this.contentSOs.add(saved);
		categories.clear();
		mansorieRows.clear();
		materialPanelUi.clear();
		initialise(contentSOs, 1, 2000);
	}

	public void fillToMap(ContentSO contentSO) {
		String nameCategory = contentSO.getMenuSO() != null ? contentSO.getMenuSO().getName() : contentSO.getCategory();
		if (nameCategory.contains("_")) {
			nameCategory = nameCategory.split("_")[0];
		}

		MaterialMasonry materialMasonry = categories.get(nameCategory);
		if (materialMasonry == null) {
			MaterialRow materialRow = new MaterialRow();

			HTML html = new HTML(nameCategory);
			if (this.name != null) {
				html = new HTML(name);
			}
			html.getElement().getStyle().setMarginLeft(10, Unit.PX);
			html.getElement().getStyle().setMarginRight(10, Unit.PX);
			html.addStyleName("rightContainerTitel");
			materialRow.add(html);

			materialMasonry = new MaterialMasonry();
			categories.put(nameCategory, materialMasonry);
			mansorieRows.put(nameCategory, materialRow);
		}

		MaterialColumn materialColumn = new MaterialColumn();
		materialColumn.setGrid("s12 m6 l3");
		materialMasonry.add(materialColumn);

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

		// titel
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

		// MaterialLabel cardTitle = new MaterialLabel();
		// cardTitle.getElement().getStyle().setCursor(Cursor.POINTER);
		// cardTitle.addStyleName(style.cardTitle());
		// cardTitle.setText(contentSO.getTitel());
		//
		// cardContent.add(cardTitle);
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

		// MaterialRow materialRow = new MaterialRow();
		// materialRow.setMarginBottom(5);
		// MaterialChip prix = new MaterialChip(contentSO.getPrix());
		// prix.getElement().getStyle().setProperty("borderRadius", "5px");
		//
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
		//
		// materialRow.add(prix);
		// cardContent.add(materialRow);

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

	}

	interface Style extends CssResource {

		String important();

		String imageCard();

		String cardTitle();

		String cardContent();

		String filter();

		String myCard();

	}

	private Map<String, MaterialRow> doSort() {

		Map<String, MaterialRow> sortedMap = mansorieRows.entrySet().stream().sorted(Entry.comparingByKey())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortedMap;
	}

	private void initialise(List<ContentSO> contentSOs, int delay, int duration) {

		for (ContentSO contentSO : contentSOs) {
			// if (contentSO.isShowToHome() &&
			// !contentSO.getType().contains("ADVERTISEMENT"))
			// fillToMap(contentSO);

			if (!contentSO.getType().contains("ADVERTISEMENT")) {
				fillToMap(contentSO);
			}

		}

		Map<String, MaterialRow> sortedMap = doSort();

		for (String key : sortedMap.keySet()) {

			MaterialRow materialRow = mansorieRows.get(key);
			MaterialMasonry masonry = categories.get(key);

			MaterialAnimation animationMenu = new MaterialAnimation();
			animationMenu.setDelay(delay);
			animationMenu.setTransition(Transition.FADEIN);
			animationMenu.setDuration(duration);
			materialRow.add(masonry);

			materialPanelUi.add(materialRow);
			animationMenu.animate(masonry);
		}
	}

	public List<ContentSO> getContentSOs() {
		return contentSOs;
	}

	public void reload() {
		for (MaterialMasonry masonry : categories.values()) {
			masonry.reload();
		}
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();

		;
	}

	public int getSize() {
		return size;
	}

}
