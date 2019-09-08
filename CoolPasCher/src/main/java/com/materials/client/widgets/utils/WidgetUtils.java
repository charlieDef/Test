package com.materials.client.widgets.utils;

import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;

public class WidgetUtils {

	// public static List<CAreaSO> getTestAreaList() {
	//
	// CAreaSO areaSO = new CAreaSO();
	// areaSO.setTitel("The Girl1");
	// areaSO.setTextInfo("img/031.jpg");
	// areaSO.setId(-15);
	//
	// CAreaSO areaSO2 = new CAreaSO();
	// areaSO2.setTextInfo("img/032.jpg");
	// areaSO2.setTitel("The Girl1");
	// areaSO2.setId(-15);
	//
	// CAreaSO areaSO02 = new CAreaSO();
	// areaSO02.setTextInfo("img/girl1.jpg");
	// areaSO02.setId(-15);
	// areaSO02.setTitel("The Girl1");
	//
	// CAreaSO areaSO002 = new CAreaSO();
	// areaSO002.setTextInfo("img/kff3.jpg");
	// areaSO002.setId(-15);
	// areaSO002.setTitel("The Girl1");
	//
	// CAreaSO areaSO4a = new CAreaSO();
	// areaSO4a.setTextInfo("img/141.jpg");
	// areaSO4a.setId(-15);
	// areaSO4a.setTitel("The Girl1");
	//
	// CAreaSO areaSO3 = new CAreaSO();
	// areaSO3.setTextInfo("img/033.jpg");
	// areaSO3.setId(-15);
	// areaSO3.setTitel("The Girl1");
	//
	// CAreaSO areaSOw = new CAreaSO();
	// areaSOw.setTextInfo("img/celest.JPG");
	// areaSOw.setId(-15);
	// areaSOw.setTitel("The Girl1");
	//
	// CAreaSO areaSO4 = new CAreaSO();
	// areaSO4.setTextInfo("img/034.jpg");
	// areaSO4.setId(-15);
	// areaSO4.setTitel("The Girl1");
	//
	// return Arrays.asList(areaSO, areaSO2, areaSOw, areaSO002, areaSO4a, areaSO3,
	// areaSO02, areaSO4);
	//
	// }

	public static void addToViewPort(Widget widget) {

		ViewPort.when(Resolution.ALL_MOBILE).then(viewPortChange -> {
			widget.getElement().getStyle().setProperty("fontSize", "3.5vw");
		});

		ViewPort.when(Resolution.TABLET).then(viewPortChange -> {
			widget.getElement().getStyle().setProperty("fontSize", "3vw");
		});

		ViewPort.when(Resolution.LAPTOP).then(viewPortChange -> {
			widget.getElement().getStyle().setProperty("fontSize", "1.8vw");
		});

		ViewPort.when(Resolution.LAPTOP_LARGE).then(viewPortChange -> {
			widget.getElement().getStyle().setProperty("fontSize", "1.4vw");
		});

		ViewPort.when(Resolution.LAPTOP_4K).then(viewPortChange -> {
			widget.getElement().getStyle().setProperty("fontSize", "1vw");
		});

	}

	public static void addToViewPortArticle(Widget[] widgets) {

		ViewPort.when(Resolution.ALL_MOBILE).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "4vw");
			}
		});

		ViewPort.when(Resolution.TABLET).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "2.6vw");
			}

		});

		ViewPort.when(Resolution.LAPTOP).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "1.4vw");
			}

		});

		ViewPort.when(Resolution.LAPTOP_LARGE).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "1.2vw");
			}

		});

		ViewPort.when(Resolution.LAPTOP_4K).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "1vw");
			}

		});

	}

	public static void addToViewPortButton(Widget[] widgets) {

		ViewPort.when(Resolution.ALL_MOBILE).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "2.6vw");
			}
		});

		ViewPort.when(Resolution.TABLET).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "2.0vw");
			}

		});

		ViewPort.when(Resolution.LAPTOP).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "1.6vw");
			}

		});

		ViewPort.when(Resolution.LAPTOP_LARGE).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "1.2vw");
			}

		});

		ViewPort.when(Resolution.LAPTOP_4K).then(viewPortChange -> {
			for (Widget widget : widgets) {
				widget.getElement().getStyle().setProperty("fontSize", "0.8vw");
			}

		});

	}

}
