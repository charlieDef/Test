package com.materials.client.widgets.notallowed;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.widgets.display.DisplayWidget;

import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialRow;

public class EmptyStatePanel extends Composite {

	private DisplayWidget displayWidget = new DisplayWidget();
	private MaterialEmptyState emptyState;

	public EmptyStatePanel() {

		initWidget(displayWidget);

		MaterialEmptyState emptyState = new MaterialEmptyState(Color.GREY_LIGHTEN_2, Color.AMBER_DARKEN_3,
				IconType.VPN_KEY, "Espace reservé", "Accès pour membres uniquement!!!");
		emptyState.getElement().getStyle().setProperty("minHeight", "650px");
		emptyState.getElement().getStyle().setProperty("fontFamily", "times new roman, times, serif");

		emptyState.setIconColor(Color.AMBER_DARKEN_3);
		displayWidget.setContent(emptyState);

	}

	public EmptyStatePanel(Color bgColor, Color textColor, Color iconColor, IconType iconType, String title,
			String description) {

		initWidget(displayWidget);

		MaterialEmptyState emptyState = new MaterialEmptyState(bgColor, textColor, iconType, title, description);
		emptyState.getElement().getStyle().setProperty("minHeight", "650px");
		emptyState.getElement().getStyle().setProperty("fontFamily", "times new roman, times, serif");

		emptyState.setIconColor(iconColor);
		displayWidget.setContent(emptyState);

	}

	public void addWidget(Widget widget) {
		new Timer() {
			@Override
			public void run() {
				MaterialRow materialRow = new MaterialRow();
				materialRow.add(widget);
				emptyState.getContainer().add(materialRow);
			}
		}.schedule(100);
	}

	public EmptyStatePanel(IconType iconType, String title, String description) {

		initWidget(displayWidget);

		emptyState = new MaterialEmptyState(Color.GREY_LIGHTEN_2, Color.AMBER_DARKEN_3, iconType, title, description);
		emptyState.getElement().getStyle().setProperty("minHeight", "650px");
		emptyState.getElement().getStyle().setProperty("fontFamily", "times new roman, times, serif");
		emptyState.setIconColor(Color.AMBER_DARKEN_3);
		displayWidget.setContent(emptyState);

	}

}
