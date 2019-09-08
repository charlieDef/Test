package com.materials.client.places.menu;

import com.google.gwt.place.shared.Place;
import com.materials.client.model.stat.StatisticSO;

/**
 * Token handling on url and start activity
 * 
 * @author i01002512
 *
 */
public class MenuPlace extends Place {

	private String menuClicked;
	private String area;
	private boolean isHome = false;
	private boolean reloadContent = false;
	private boolean favorit = false;
	private boolean newAnnonce = false;
	private boolean search = false;

	private boolean categoryShowALl = false;
	private String category;
	private String parentCategory;
	private StatisticSO statisticSO;
	private String parameter;

	// @Prefix("!media/")
	// public static class Tokenizer implements PlaceTokenizer<MediaPlace> {
	//
	// @Override
	// public MediaPlace getPlace(String token) {
	// MediaPlace mediaPlace = token != null ? new
	// MediaPlace().handleMedia("media", token) : new MediaPlace();
	// return mediaPlace;
	// }
	//
	// @Override
	// public String getToken(MediaPlace startPlace) {
	//
	// String token = startPlace.getMediaClicked() != null ?
	// startPlace.getMediaClicked() : "noMedia";
	//
	// return token;
	// }
	// }

	public MenuPlace() {
		setArea("");
		setMenuClicked("");
	}

	public MenuPlace handleStatistic(StatisticSO statisticSO) {
		setArea("start");
		setMenuClicked("stat");
		this.statisticSO = statisticSO;
		return this;
	}

	public MenuPlace handleSearch(String searchText, boolean newSearch) {
		setArea("search");
		setMenuClicked(searchText);
		setParameter(searchText);
		setSearch(newSearch);
		return this;
	}

	public MenuPlace handleMenu(String area, String menuClicked) {
		setMenuClicked(menuClicked);
		setArea(area);
		return this;
	}

	public MenuPlace handleMenu(String area, String menuClicked, boolean reloadContent) {
		setMenuClicked(menuClicked);
		setArea(area);
		setReloadContent(reloadContent);
		return this;
	}

	public MenuPlace handleCategory(String category, String parentCategory) {
		setArea("area");
		setMenuClicked(parentCategory + "/" + category);
		setCategory(category);
		setParentCategory(parentCategory);
		return this;
	}

	public MenuPlace handleCategory(boolean showAll, String category, String parentCategory) {
		this.categoryShowALl = showAll;
		setArea("area");
		setMenuClicked(parentCategory + "/" + category);
		setCategory(category);
		setParentCategory(parentCategory);
		return this;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getMenuClicked() {
		return menuClicked;
	}

	public void setMenuClicked(String menuClicked) {
		this.menuClicked = menuClicked;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	public void setReloadContent(boolean reloadContent) {
		this.reloadContent = reloadContent;
	}

	public boolean isReloadContent() {
		return reloadContent;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public boolean isFavorit() {
		return favorit;
	}

	public void setFavorit(boolean favorit) {
		this.favorit = favorit;
	}

	public StatisticSO getStatisticSO() {
		return statisticSO;
	}

	public boolean isCategoryShowALL() {
		return categoryShowALl;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public boolean isNewAnnonce() {
		return newAnnonce;
	}

	public void setNewAnnonce(boolean newAnnonce) {
		this.newAnnonce = newAnnonce;
	}
	
	public boolean isSearch() {
		return search;
	}
	
	public void setSearch(boolean search) {
		this.search = search;
	}

}
