package com.materials.client.places.configurations.rsociaux;

import com.google.gwt.place.shared.Place;

/**
 * Token handling on url and start activity
 * 
 * @author i01002512
 *
 */
public class RSociauxConfigPlace extends Place {

	public static final String NAME = "rsociaux";
	private String area;

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
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
