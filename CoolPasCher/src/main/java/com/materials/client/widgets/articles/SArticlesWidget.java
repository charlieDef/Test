package com.materials.client.widgets.articles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.places.content.ContentPlace;

import gwt.material.design.addins.client.masonry.MaterialMasonry;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class SArticlesWidget extends Composite {

	private static SArticlesWidgetUiBinder uiBinder = GWT.create(SArticlesWidgetUiBinder.class);

	interface SArticlesWidgetUiBinder extends UiBinder<MaterialCard, SArticlesWidget> {
	}

	private Map<String, MaterialMasonry> categories = new HashMap<String, MaterialMasonry>();

	@UiField
	MaterialCard htmlPanelUi;

	@UiField
	Style style;

	public SArticlesWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		// MaterialRow materialRow = new MaterialRow();
		// htmlPanelUi.add(materialRow);

		getTempContentSos().forEach(content -> addArticle(content));
		//
		// MaterialRow materialRow2 = new MaterialRow();
		// htmlPanelUi.add(materialRow2);

		// HTML html2 = new HTML("Beauty");
		// html2.getElement().getStyle().setMarginLeft(10, Unit.PX);
		// html2.getElement().getStyle().setMarginRight(10, Unit.PX);
		// html2.addStyleName("rightContainerTitel");
		// materialRow2.add(html2);
		//
		// getTempContentSos().forEach(content -> addArticle(content));
		//
		// MaterialAnimation gridAnimation = new MaterialAnimation();
		// gridAnimation.setTransition(Transition.SHOW_GRID);
		//
		// gridAnimation.animate(materialRow);
	}

	public SArticlesWidget(List<ContentSO> contentSOs) {
		initWidget(uiBinder.createAndBindUi(this));

		// MaterialRow materialRow = new MaterialRow();
		// htmlPanelUi.add(materialRow);

		contentSOs.forEach(content -> {
			if (content.isActive()) {
				if (content.isIntern()) {
					if (CoolPasCherUI.checkLoggedMember()) {
						addArticle(content);
					}
				} else {
					addArticle(content);
				}
			}

		});
	}

	public void addArticle(ContentSO contentSO) {

		String nameCategory = contentSO.getCategory();

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
		cardImage.getElement().getStyle().setCursor(Cursor.POINTER);
		// cardImage.setWaves(WavesType.LIGHT);

		// contentSO.setTitelImageUrl(Index.MODUL_BASE_FILEHELPER + "?cRandomId=" +
		// contentSO.getRandomId());
		MaterialImage image = new MaterialImage(contentSO.getTitelImageUrl());
		image.addStyleName(style.imageCard());
		cardImage.add(image);

		MaterialCardContent cardContent = new MaterialCardContent();
		cardContent.addStyleName(style.cardContent());

		// titel
		MaterialLabel cardTitle = new MaterialLabel();
		cardTitle.addStyleName(style.cardTitle());
		cardTitle.setText(contentSO.getTitel());

		// description
		MaterialLabel description = new MaterialLabel(contentSO.getDescription());

		cardContent.add(cardTitle);
		cardContent.add(description);

		MaterialLabel description2 = new MaterialLabel(contentSO.getDescription2());
		cardContent.add(description2);
		description.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		description.getElement().getStyle().setFontSize(1.1, Unit.EM);
		description.getElement().getStyle().setMarginTop(-2, Unit.PX);

		card.add(cardImage);
		card.add(cardContent);
		materialColumn.add(card);

		if (contentSO.getViewAble() == 1) {

			if (contentSO.getType().equals(ContentSO.TYPE_VIDEO) && !contentSO.getcAreaSOs().isEmpty()) {
				String url = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?caRandomID=" + contentSO.getcAreaSOs().get(0).getRandomId()
						+ "&dType=" + contentSO.getcAreaSOs().get(0).getAreaType() + "&fName="
						+ contentSO.getcAreaSOs().get(0).getTitel();
				image.addClickHandler(c -> Window.open(url, "_blank", ""));

			} else {
				ContentPlace contentPlace = new ContentPlace(contentSO.getType(), "article", contentSO.getTitel());
				image.addClickHandler(c -> CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(contentPlace));
			}

		}

	}

	interface Style extends CssResource {

		String important();

		String imageCard();

		String cardTitle();

		String cardContent();
	}

	private List<ContentSO> getTempContentSos() {

		List<ContentSO> list = new ArrayList<ContentSO>();

		ContentSO contentSO = new ContentSO("Articles Recents", "Botte Timberland",
				"Premiere participation de l 'equipe de football prebaloise au tournoi...", "img/s2.jpg");
		contentSO.setDescription2("La description de  l'article ici1");

		ContentSO contentSO1 = new ContentSO("Articles Recents", "Velo de course",
				"Visite du Château de Fontainebleau au départ de Paris à 90km de..", "img/b1.jpg");
		contentSO1.setDescription2("La description de  l'article ici 2");

		ContentSO contentSO2 = new ContentSO("Articles Recents", "Ordinateur Apple",
				"Premiere participation de l 'equipe de football prebaloise au tournoi...", "img/c1.jpg");
		contentSO2.setDescription2("La description de  l'article ici 3");

		ContentSO contentSO3 = new ContentSO("Articles Recents", "Chaussure Nike",
				"Premiere participation de l 'equipe de football prebaloise au tournoi...", "img/s1.jpg");
		contentSO3.setDescription2("La description de  l'article ici 4");

		list.add(contentSO);
		list.add(contentSO1);
		list.add(contentSO2);
		list.add(contentSO3);

		return list;
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		// Menu animation
		MaterialAnimation animationMenu = new MaterialAnimation();
		animationMenu.setTransition(Transition.SHOW_GRID);
		animationMenu.setDelay(1000);
		animationMenu.setDuration(5000);
		animationMenu.animate(htmlPanelUi);

		new Timer() {
			@Override
			public void run() {
				htmlPanelUi.setOpacity(1);
			}

		}.schedule(7000);

		// Window.alert("Plattform: " + Navigator.getPlatform());
	}

}
