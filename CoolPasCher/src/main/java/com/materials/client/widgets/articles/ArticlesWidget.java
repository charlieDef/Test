package com.materials.client.widgets.articles;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
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
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialCardReveal;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class ArticlesWidget extends Composite {

	private static ArticlesWidgetUiBinder uiBinder = GWT.create(ArticlesWidgetUiBinder.class);

	interface ArticlesWidgetUiBinder extends UiBinder<MaterialCard, ArticlesWidget> {
	}

	List<MaterialCard> cards = new ArrayList<MaterialCard>();

	private boolean startet = false;

	private Map<String, MaterialMasonry> categories = new HashMap<String, MaterialMasonry>();

	@UiField
	MaterialCard htmlPanelUi;

	private boolean special = false;
	DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

	@UiField
	Style style;

	public ArticlesWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		htmlPanelUi.getElement().getStyle().setHeight(100, Unit.PCT);
		htmlPanelUi.setOpacity(1);
		MaterialRow materialRow = new MaterialRow();
		htmlPanelUi.add(materialRow);

		HTML html = new HTML("Articles");
		html.getElement().getStyle().setMarginLeft(10, Unit.PX);
		html.getElement().getStyle().setMarginRight(10, Unit.PX);
		html.addStyleName("rightContainerTitel");
		materialRow.add(html);

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

	public ArticlesWidget(List<ContentSO> contentSOs) {
		initWidget(uiBinder.createAndBindUi(this));

		// MaterialRow materialRow = new MaterialRow();
		// htmlPanelUi.add(materialRow);

		contentSOs.forEach(content -> {
			if (content.isShowToHome() && !content.getType().contains("ADVERTISEMENT"))
				addArticle(content);
		});
	}

	public ArticlesWidget(List<ContentSO> contentSOs, boolean special) {
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

		MaterialColumn materialColumn = new MaterialColumn(12, 6, 3);
		materialMasonry.add(materialColumn);

		MaterialCard card = new MaterialCard();
		card.setHoverable(true);
		card.setBackgroundColor(Color.WHITE);

		// card.setTextAlign(TextAlign.CENTER);

		// image
		MaterialCardImage cardImage = new MaterialCardImage();

		cardImage.setWaves(WavesType.LIGHT);

		// contentSO.setTitelImageUrl(Index.MODUL_BASE_FILEHELPER + "?cRandomId=" +
		// contentSO.getRandomId());
		MaterialImage image = new MaterialImage(contentSO.getTitelImageUrl());
		image.addStyleName(style.imageCard());
		cardImage.add(image);

		// MaterialCardTitle cardTitle2 = new MaterialCardTitle();
		// cardTitle2.getElement().getStyle().setProperty("fontFamily", "times new
		// roman, times, serif");
		// cardTitle2.setText(contentSO.getCategory());
		// cardImage.add(cardTitle2);

		MaterialCardContent cardContent = new MaterialCardContent();
		cardContent.addStyleName(style.cardContent());

		// titel
		MaterialLabel cardTitle = new MaterialLabel();
		cardTitle.getElement().getStyle().setCursor(Cursor.POINTER);
		cardTitle.addStyleName(style.cardTitle());
		cardTitle.setText(contentSO.getTitel());

		cardContent.add(cardTitle);
		// description
		// MaterialLabel description = new MaterialLabel(contentSO.getDescription());

		MaterialRow materialRow = new MaterialRow();
		MaterialLink description = new MaterialLink(IconType.DATE_RANGE);

		Date creation = contentSO.getCreationDate() != null ? contentSO.getCreationDate() : new Date();

		description.setText(ttipFormat.format(creation));
		// description.setIconColor(Color.GREY_DARKEN_1);
		description.setTextColor(Color.GREY_DARKEN_1);
		materialRow.add(description);

		MaterialRow materialRow1 = new MaterialRow();
		materialRow1.setMarginTop(-15);
		MaterialLink description1 = new MaterialLink(IconType.VISIBILITY);
		description1.setText(String.valueOf(contentSO.getViewed()));

		// description.setIconColor(Color.RED);
		description1.setTextColor(Color.GREY_DARKEN_1);
		materialRow1.add(description1);

		MaterialIcon icon = new MaterialIcon(IconType.PERSON);
		String auteur = contentSO.getCreator() == null ? "prebaal.com" : contentSO.getCreator();
		String at = "Auteur: " + auteur;

		icon.setTitle(at);
		icon.setIconPosition(IconPosition.RIGHT);
		description1.add(icon);

		cardContent.add(materialRow);
		cardContent.add(materialRow1);

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

		MaterialAnchorButton anchorButton = new MaterialAnchorButton(ButtonType.LINK);
		anchorButton.setText("lire la suite");
		cardReveal.add(anchorButton);

		MaterialCardAction action = new MaterialCardAction();

		action.setMarginTop(-20);
		action.setPadding(5);
		MaterialLink link = new MaterialLink();
		link.addStyleName(style.cardContent());
		link.setText("Lire ...");
		action.add(link);

		card.add(cardImage);
		card.add(cardContent);
		card.add(cardReveal);
		card.add(action);
		materialColumn.add(card);

		cards.add(card);

		ContentPlace contentPlace = new ContentPlace(contentSO.getType(), "article", contentSO.getTitel());

		if (contentSO.getViewAble() == 1) {
			link.addClickHandler(c -> {
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(contentPlace);
			});

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
			animationMenu.setDelay(4000);
			animationMenu.setDuration(4000);
			animationMenu.animate(htmlPanelUi);

			new Timer() {
				@Override
				public void run() {
					htmlPanelUi.setOpacity(1);
				}

			}.schedule(4000);
		}

		// Window.alert("Plattform: " + Navigator.getPlatform());
	}

	public List<MaterialCard> getCards() {
		return cards;
	}

}
