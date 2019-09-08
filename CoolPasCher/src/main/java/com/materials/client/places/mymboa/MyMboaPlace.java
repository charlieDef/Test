package com.materials.client.places.mymboa;

import com.google.gwt.place.shared.Place;

public class MyMboaPlace extends Place {

	private String menuClicked;

	public MyMboaPlace(String menuClicked) {
		setMenuClicked(menuClicked);

	}

	public String getMenuClicked() {
		return menuClicked;
	}

	public void setMenuClicked(String menuClicked) {
		this.menuClicked = menuClicked;
	}

}
