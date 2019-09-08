package com.materials.client.widgets.display;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCard;

public class DisplayWidget extends MaterialCard {

	public DisplayWidget() {

		setPadding(10);
		setMarginLeft(10);
		setMarginRight(10);
		getElement().getStyle().setProperty("borderRadius", "5px");
	}

	public DisplayWidget(String html) {

		setPadding(10);
		setMarginLeft(10);
		setMarginRight(10);

		setContent(new HTML(html));
	}

	public void setContent(Widget widget) {
		clear();

		add(widget);

	}
}
