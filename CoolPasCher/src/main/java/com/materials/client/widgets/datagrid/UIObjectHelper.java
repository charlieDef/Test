package com.materials.client.widgets.datagrid;

public class UIObjectHelper {

	private String titel;
	private String iconUrl;

	public UIObjectHelper(String titel, String iconUrl) {
		this.iconUrl = iconUrl;
		this.titel = titel;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getTitel() {
		return titel;
	}
}
