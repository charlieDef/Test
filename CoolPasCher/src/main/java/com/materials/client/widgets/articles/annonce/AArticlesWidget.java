package com.materials.client.widgets.articles.annonce;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.places.content.ContentPlace;
import com.materials.client.widgets.utils.WidgetUtils;
import com.materials.shared.APPConstants;

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
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class AArticlesWidget extends Composite {

	private static AArticlesWidgetUiBinder uiBinder = GWT.create(AArticlesWidgetUiBinder.class);

	interface AArticlesWidgetUiBinder extends UiBinder<MaterialPanel, AArticlesWidget> {
	}

	List<MaterialColumn> cardColumns = new ArrayList<MaterialColumn>();

	private boolean startet = false;

	private Map<String, MaterialMasonry> categories = new HashMap<String, MaterialMasonry>();

	@UiField
	MaterialPanel htmlPanelUi;

	private boolean special = false;
	DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

	@UiField
	Style style;

	public AArticlesWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		htmlPanelUi.getElement().getStyle().setHeight(100, Unit.PCT);
		htmlPanelUi.setOpacity(1);
		MaterialRow materialRow = new MaterialRow();
		htmlPanelUi.add(materialRow);

		HTML html = new HTML("Articles");
		html.getElement().getStyle().setMarginLeft(10, Unit.PX);
		html.getElement().getStyle().setMarginRight(10, Unit.PX);
		html.addStyleName("rightContainerTitel");

		MaterialColumn columnLeft = new MaterialColumn();
		MaterialColumn columnRight = new MaterialColumn();
		columnRight.setFloat(Float.RIGHT);

		materialRow.add(columnLeft);
		materialRow.add(columnRight);
		columnLeft.add(html);

		MaterialIcon iconFilter = new MaterialIcon(IconType.FILTER);
		columnRight.add(iconFilter);

		getTempContentSos().forEach(content -> addArticle(content));

		MaterialRow materialRow2 = new MaterialRow();
		htmlPanelUi.add(materialRow2);

		HTML html2 = new HTML("Beauty");
		html2.getElement().getStyle().setMarginLeft(10, Unit.PX);
		html2.getElement().getStyle().setMarginRight(10, Unit.PX);
		html2.addStyleName("rightContainerTitel");
		materialRow2.add(html2);

		getTempContentSos().forEach(content -> addArticle(content));

	}

	public AArticlesWidget(List<ContentSO> contentSOs) {
		initWidget(uiBinder.createAndBindUi(this));

		contentSOs.sort(new Comparator<ContentSO>() {
			@Override
			public int compare(ContentSO o1, ContentSO o2) {
				return o2.getRandomId().compareTo(o1.getRandomId());
			}
		});

		contentSOs.forEach(content -> {
			if (content.isShowToHome() && !content.getType().contains("ADVERTISEMENT"))
				addArticle(content);
		});
	}

	public AArticlesWidget(List<ContentSO> contentSOs, boolean special) {
		this.special = special;
		initWidget(uiBinder.createAndBindUi(this));
		htmlPanelUi.getElement().getStyle().setHeight(100, Unit.PC);
		htmlPanelUi.setOpacity(1);

		// MaterialRow materialRow = new MaterialRow();
		// htmlPanelUi.add(materialRow);

		contentSOs.forEach(content -> {
			if (!content.getType().contains("ADVERTISEMENT"))
				addArticle(content);
		});

	}

	public void addArticle(ContentSO contentSO) {
		String nameCategory = contentSO.getMenuSO() != null ? contentSO.getMenuSO().getName() : contentSO.getCategory();
		if (nameCategory.contains("_")) {
			nameCategory = nameCategory.split("_")[0];
		}

		MaterialMasonry materialMasonry = categories.get(nameCategory);
		if (materialMasonry == null) {
			MaterialRow materialRow = new MaterialRow();

			HTML html = new HTML(nameCategory);
			html.getElement().getStyle().setMarginLeft(10, Unit.PX);
			html.getElement().getStyle().setMarginRight(10, Unit.PX);
			html.addStyleName("rightContainerTitel");
			materialRow.add(html);

			materialMasonry = new MaterialMasonry();
			categories.put(nameCategory, materialMasonry);
			materialRow.add(materialMasonry);

			htmlPanelUi.add(materialRow);
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

		// cardImage.setWaves(WavesType.LIGHT);

		MaterialImage image = new MaterialImage(contentSO.getTitelImageUrl());
		image.addStyleName(style.imageCard());
		cardImage.add(image);

		MaterialCardContent cardContent = new MaterialCardContent();
		cardContent.addStyleName(style.cardContent());

		// titel
		MaterialLabel cardTitle = new MaterialLabel();
		cardTitle.getElement().getStyle().setCursor(Cursor.POINTER);
		cardTitle.addStyleName(style.cardTitle());
		cardTitle.setText(contentSO.getTitel());

		cardContent.add(cardTitle);
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
		materialLabel.getElement().getStyle().setMarginBottom(15, Unit.PX);
		cardReveal.add(materialLabel);

		MaterialRow materialRow = new MaterialRow();
		materialRow.setMarginBottom(5);
		MaterialChip prix = new MaterialChip(contentSO.getPrix());
		prix.getElement().getStyle().setProperty("borderRadius", "5px");

		MaterialIcon icon = new MaterialIcon(IconType.LOCATION_CITY);
		icon.setIconColor(Color.GREY_DARKEN_1);
		MaterialLabel materialLabel2 = new MaterialLabel(contentSO.getVille());
		materialLabel2.getElement().getStyle().setProperty("fontStyle", "italic");
		materialLabel2.getElement().getStyle().setProperty("fontWeight", "bold");
		materialLabel2.getElement().getStyle().setProperty("marginTop", "5px");
		materialLabel2.getElement().getStyle().setProperty("color", "#424242 ");
		materialLabel2.setMarginRight(-10);
		materialLabel2.setFloat(Float.RIGHT);

		icon.setIconPosition(IconPosition.RIGHT);
		materialRow.add(icon);
		materialRow.add(materialLabel2);

		materialRow.add(prix);
		cardContent.add(materialRow);

		MaterialAnchorButton anchorButton = new MaterialAnchorButton(ButtonType.LINK);
		anchorButton.setText("Aller à l'annonce");
		cardReveal.add(anchorButton);

		card.add(cardImage);
		card.add(cardContent);
		card.add(cardReveal);

		materialColumn.add(card);

		cardColumns.add(materialColumn);

		ContentPlace contentPlace = new ContentPlace(contentSO.getType(), "article", contentSO.getTitel());

		if (contentSO.getViewAble() == 1) {

			cardTitle.addClickHandler(c -> {
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(contentPlace);
			});

			anchorButton.addClickHandler(c -> {
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
	}

	private List<ContentSO> getTempContentSos() {

		List<ContentSO> list = new ArrayList<ContentSO>();

		return list;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		if (!startet) {
			startet = true;
			// Menu animation
			MaterialAnimation animationMenu = new MaterialAnimation();
			animationMenu.setTransition(Transition.FADEIN);
			// animationMenu.setDelay(2000);
			animationMenu.setDuration(2000);
			animationMenu.animate(htmlPanelUi);

			new Timer() {
				@Override
				public void run() {
					htmlPanelUi.setOpacity(1);
				}

			}.schedule(2000);
		}

		// Window.alert("Plattform: " + Navigator.getPlatform());
	}

	private void doSort(List<ContentSO> contentSOs) {
		categories.clear();
		// cards.clear();
		htmlPanelUi.clear();

		contentSOs.forEach(content -> {
			if (content.isShowToHome() && !content.getType().contains("ADVERTISEMENT"))
				addArticle(content);
		});

	}
}
