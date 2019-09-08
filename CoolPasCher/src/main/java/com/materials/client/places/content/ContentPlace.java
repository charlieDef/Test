package com.materials.client.places.content;

import com.google.gwt.place.shared.Place;
import com.materials.client.model.MenuSO;

/**
 * Token handling on url and start activity
 * 
 * @author i01002512
 *
 */
public class ContentPlace extends Place {

	private String articleClicked, area, type;
	private int srollValue = -1;

	public static final String PREFIX = "#!article/";

	private boolean newAnnonce = false;

	private String category, region, ville, localite;

	// @Prefix("!article/")
	// public static class Tokenizer implements PlaceTokenizer<ArticlePlace> {
	//
	// @Override
	// public ArticlePlace getPlace(String token) {
	// ArticlePlace mediaPlace = token != null ? new
	// ArticlePlace().handleArticle("article", token) : new ArticlePlace();
	// return mediaPlace;
	// }
	//
	// @Override
	// public String getToken(ArticlePlace startPlace) {
	// String token = startPlace.getArticleClicked() != null ?
	// startPlace.getArticleClicked() : "noMedia";
	// return token;
	// }
	// }

	public ContentPlace(String area, String articleClicked) {
		setArticleClicked(articleClicked);
		setArea(area);
		this.newAnnonce = MenuSO.NEW_ANNONCE.equals(articleClicked);
	}

	public ContentPlace(String area, String articleClicked, boolean newAnnonce) {
		setArticleClicked(articleClicked);
		setArea(area);
		this.newAnnonce = newAnnonce;
	}

	public ContentPlace(String type, String area, String articleClicked) {
		setArticleClicked(articleClicked);
		setArea(area);
		setType(type);
		this.newAnnonce = MenuSO.NEW_ANNONCE.equals(articleClicked);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArticleClicked() {
		return articleClicked;
	}

	public void setArticleClicked(String articleClicked) {
		this.articleClicked = articleClicked;
	}

	public int getSrollValue() {
		return srollValue;
	}

	public void setSrollValue(int srollValue) {
		this.srollValue = srollValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getLocalite() {
		return localite;
	}

	public void setLocalite(String localite) {
		this.localite = localite;
	}

	public boolean isNewAnnonce() {
		return newAnnonce;
	}

}
