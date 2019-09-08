package com.materials.client.widgets.slider.jssor;

import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class HTMLFlexImageGallery extends Composite {
	private MaterialPanel materialPanel;

	private HTML sliderPanel;

	private static final String BASE_STYLE = "position:relative;margin:0 auto;top:0px;left:0px;width:1000px;height:600px;overflow:hidden;visibility:hidden;border:1px solid #e0e0e0;background-color:#eee;";
	private static final String BASE_STYLE_SLIDES = "cursor:default;position:relative;top:0px;right:1px;left:1px;width:1000px;height:600px;overflow:hidden;";
	private static final String BASE_STYLE_BULL = "position:absolute;bottom:12px;right:12px;";
	private static final String BASE_STYLE_NAVIGATOR_LEFT = "width:55px;height:55px;top:162px;left:30px;";
	private static final String BASE_STYLE_NAVIGATOR_RIGHT = "width:55px;height:55px;top:162px;right:30px;";
	private static final String STYLE_NAVIGATOR_SVG = "position:absolute;top:0;left:0;width:100%;height:100%;";

	public HTMLFlexImageGallery(List<CAreaSO> areaSOs) {
		materialPanel = new MaterialPanel();
		sliderPanel = insertImages(areaSOs);
		materialPanel.add(sliderPanel);

		initWidget(materialPanel);
		materialPanel.getElement().getStyle().setOpacity(0);
	}

	public HTMLFlexImageGallery() {

		materialPanel = new MaterialPanel();

		// CAreaSO areaSO = new CAreaSO();
		// areaSO.setTextInfo("img/031.jpg");
		// areaSO.setId(-15);
		//
		// CAreaSO areaSO2 = new CAreaSO();
		// areaSO2.setTextInfo("img/032.jpg");
		// areaSO2.setId(-15);
		//
		// CAreaSO areaSO02 = new CAreaSO();
		// areaSO02.setTextInfo("img/girl1.jpg");
		// areaSO02.setId(-15);
		//
		// CAreaSO areaSO002 = new CAreaSO();
		// areaSO002.setTextInfo("img/kff3.jpg");
		// areaSO002.setId(-15);
		//
		// CAreaSO areaSO4a = new CAreaSO();
		// areaSO4a.setTextInfo("img/141.jpg");
		// areaSO4a.setId(-15);
		//
		// CAreaSO areaSO3 = new CAreaSO();
		// areaSO3.setTextInfo("img/033.jpg");
		// areaSO3.setId(-15);
		//
		// CAreaSO areaSOw = new CAreaSO();
		// areaSOw.setTextInfo("img/celest.JPG");
		// areaSOw.setId(-15);
		//
		// CAreaSO areaSO4 = new CAreaSO();
		// areaSO4.setTextInfo("img/034.jpg");
		// areaSO4.setId(-15);

		// sliderPanel = insertImages(
		// Arrays.asList(areaSO, areaSO2, areaSOw, areaSO002, areaSO4a, areaSO3,
		// areaSO02, areaSO4));
		// materialPanel.add(sliderPanel);

		initWidget(materialPanel);
		materialPanel.getElement().getStyle().setOpacity(0);

	}

	private HTML insertImages(List<CAreaSO> cAreaSOs) {

		String builder = new String();

		if (cAreaSOs != null && cAreaSOs.size() > 0) {
			builder += "<div id=\"jssor_3\" style=\"" + BASE_STYLE + "\">";

			builder += "<div data-u=\"slides\" style=\"" + BASE_STYLE_SLIDES + "\">";
			for (CAreaSO cAreaSO : cAreaSOs) {
				builder += getImgHtml(cAreaSO);

			}
			builder += "</div>";
			builder += getBulletNavigator();

			builder += getArrowNavigator(true);
			builder += getArrowNavigator(false);

			builder += "</div>";
			return new HTML(builder);

		}
		return null;
	}

	private String getImgHtml(CAreaSO cAreaSO) {

		String html = "<div data-p=\"150.00\">";
		html += "<img  data-u=\"image\" src=\"" + cAreaSO.getImageUrl()
				+ "\"  style=\"padding:10px 11px 10px 10px;background-color:white;\"/>";
		html += "</div>";
		return html;
	}

	private String getBulletNavigator() {
		String html = "<div data-u=\"navigator\" class=\"jssorb035\" style=\"" + BASE_STYLE_BULL
				+ "\" data-autocenter=\"1\" data-scale=\"0.5\" data-scale-bottom=\"0.75\" >";
		html += "<div data-u=\"prototype\" class=\"i\" style=\"width:16px;height:16px;\">";
		html += "<svg viewbox=\"0 0 16000 16000\" style=\"" + STYLE_NAVIGATOR_SVG + "\">"
				+ "<rect class=\"b\" x=\"2200\" y=\"2200\" width=\"11600\" height=\"11600\"></rect></svg>";
		html += "</div></div>";
		return html;
	}

	private String getArrowNavigator(boolean arrowLeft) {

		String styleNavigator = arrowLeft ? BASE_STYLE_NAVIGATOR_LEFT : BASE_STYLE_NAVIGATOR_RIGHT;
		String styleArrow = arrowLeft ? "arrowleft" : "arrowright";
		String polylinePoints = arrowLeft ? "11040,1920 4960,8000 11040,14080" : "4960,1920 11040,8000 4960,14080";
		String dataScale = arrowLeft ? "data-scale-left=\"0.75\" " : "data-scale-right=\"0.75\"";

		String html = "<div data-u=\"" + styleArrow + "\" class=\"jssora055\"  style=\"" + styleNavigator
				+ "\" data-autocenter=\"2\"  data-scale=\"0.75\" " + dataScale + " >";
		html += "<svg viewbox=\"0 0 16000 16000\" style=\"" + STYLE_NAVIGATOR_SVG + "\">";
		html += "<polyline class =\"a\" points=\"" + polylinePoints + "\"> </polyline></svg>";
		html += "</div>";

		return html;
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		CoolPasCherUI.CLIENT_FACTORY.startJESSORFlexImageGallery();

		// Menu animation
		MaterialAnimation animationMenu = new MaterialAnimation();
		animationMenu.setTransition(Transition.FADEIN);
		animationMenu.setDelay(1000);
		animationMenu.setDuration(2000);
		animationMenu.animate(materialPanel);

		new Timer() {
			@Override
			public void run() {
				materialPanel.getElement().getStyle().setOpacity(1);
			}
		}.schedule(1000);
	}

	public void test() {
		materialPanel.clear();

		// CAreaSO areaSO3 = new CAreaSO();
		// areaSO3.setTextInfo("img/033.jpg");
		// areaSO3.setId(-15);
		//
		// CAreaSO areaSOw = new CAreaSO();
		// areaSOw.setTextInfo("img/celest.JPG");
		// areaSOw.setId(-15);
		//
		// CAreaSO areaSO4 = new CAreaSO();
		// areaSO4.setTextInfo("img/034.jpg");
		// areaSO4.setId(-15);
		//
		// sliderPanel = insertImages(Arrays.asList(areaSOw, areaSO3, areaSO4));
		// materialPanel.add(sliderPanel);
		//
		// MBoaOnlineUI.CLIENT_FACTORY.startJESSORFlexImageGallery();
	}
}
