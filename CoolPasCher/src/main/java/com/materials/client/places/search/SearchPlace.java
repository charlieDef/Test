package com.materials.client.places.search;

import com.google.gwt.place.shared.Place;

public class SearchPlace extends Place {

	private String searchTerm;

	public SearchPlace(String searchTerm) {
		setSearchTerm(searchTerm);
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

}
