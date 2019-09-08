package com.materials.client.widgets.slider.jssor;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialPanel;

public class JssorImgGallery extends Composite {

	boolean startet = false;
	private HTML html;
	private MaterialPanel materialPanel;
	private MaterialCard materialCard;
	private static final String BASE_STYLE = "position:relative;margin:0 auto;top:0px;left:0px;width:980px;height:480px;overflow:hidden;visibility:hidden;background-color:rgba(0,0,0,0);";
	private static final String BASE_STYLE_SLIDES = "cursor:default;position:relative;top:0px;left:0px;width:980px;height:380px;overflow:hidden;";
	private static final String BASE_STYLE_THUMB = "position:absolute;left:0px;bottom:0px;width:980px;height:100px;background-color:#eee;";
	private static final String BASE_STYLE_NAVIGATOR_LEFT = "width:45px;height:45px;top:162px;left:30px;";
	private static final String BASE_STYLE_NAVIGATOR_RIGHT = "width:45px;height:45px;top:162px;right:30px;";
	private static final String STYLE_NAVIGATOR_SVG = "position:absolute;top:0;left:0;width:100%;height:100%;";

	public JssorImgGallery() {
		materialPanel = new MaterialPanel();

		materialCard = new MaterialCard();
		materialCard.setOpacity(0);
		materialCard.setMarginTop(0);
		materialCard.setMarginBottom(20);
		materialCard.setPadding(10);
		materialCard.setBorderRadius("5px");
		materialPanel.add(materialCard);

		// CAreaSO areaSO = new CAreaSO();
		// areaSO.setTextInfo("img/031.jpg");
		// areaSO.setId(-15);
		//
		// CAreaSO areaSO2 = new CAreaSO();
		// areaSO2.setTextInfo("img/032.jpg");
		// areaSO2.setId(-15);
		//
		// CAreaSO areaSO4a = new CAreaSO();
		// areaSO4a.setTextInfo("img/141.jpg");
		// areaSO4a.setId(-15);
		//
		// CAreaSO areaSO3 = new CAreaSO();
		// areaSO3.setTextInfo("img/033.jpg");
		// areaSO3.setId(-15);
		//
		// CAreaSO areaSO4 = new CAreaSO();
		// areaSO4.setTextInfo("img/034.jpg");
		// areaSO4.setId(-15);
		//
		// final HTML sliderPanel = insertImages(Arrays.asList(areaSO, areaSO2,
		// areaSO4a, areaSO3, areaSO4));
		//
		// materialCard = new MaterialCard();
		// // materialCard.setOpacity(0);
		//
		// materialCard.getElement().getStyle().setMarginBottom(40, Unit.PX);
		// materialCard.getElement().getStyle().setPadding(10, Unit.PX);
		//
		// materialCard.add(sliderPanel);

		initWidget(materialPanel);

	}

	public void init(List<CAreaSO> cAreaSOs) {
		html = insertImages(cAreaSOs);
		if (html != null) {
			html.getElement().getStyle().setBackgroundColor("#eee");
			materialCard.add(html);
		}
	}

	public JssorImgGallery(List<CAreaSO> cAreaSOs) {
		// Collections.sort(cAreaSOs, Comparator.comparing(CAreaSO::getIndex));
		materialPanel = new MaterialPanel();

		materialCard = new MaterialCard();
		// materialCard.setOpacity(0);
		materialCard.setMarginTop(0);
		materialCard.setMarginBottom(20);
		materialCard.setPadding(10);
		materialCard.setBorderRadius("5px");
		materialPanel.add(materialCard);

		html = insertImages(cAreaSOs);
		if (html != null) {
			html.getElement().getStyle().setBackgroundColor("#eee");
			materialCard.add(html);
		}
		initWidget(materialPanel);
		// MaterialLoader.loading(true, materialPanel);
	}

	private HTML insertImages(List<CAreaSO> cAreaSOs) {

		String builder = new String();

		if (cAreaSOs != null && cAreaSOs.size() > 0) {
			builder += "<div id=\"jssor_2a\" style=\"" + BASE_STYLE + "\">";

			builder += "<div data-u=\"slides\" style=\"" + BASE_STYLE_SLIDES + "\">";
			for (CAreaSO cAreaSO : cAreaSOs) {
				builder += getImgHtml(cAreaSO);
			}
			builder += "</div>";
			builder += getThumbnailNavigator();

			builder += getArrowNavigator(true);
			builder += getArrowNavigator(false);

			builder += "</div>";
			return new HTML(builder);
		}
		return new HTML(builder);
	}

	public void setImages(List<CAreaSO> cAreaSOs) {
		materialCard.clear();
		html = insertImages(cAreaSOs);
		if (html != null) {
			html.getElement().getStyle().setBackgroundColor("#eee");
			materialCard.add(html);
		}
	}

	private String getImgHtml(CAreaSO cAreaSO) {

		String html = "<div>";
		html += "<img  data-u=\"image\" src=\"" + cAreaSO.getImageUrl() + "\"  />";
		html += "<img  data-u=\"thumb\" src=\"" + cAreaSO.getImageUrl() + "\"  />";
		html += "</div>";
		return html;
	}

	private String getThumbnailNavigator() {

		String html = "<div data-u=\"thumbnavigator\" class=\"jssort101\" style=\"" + BASE_STYLE_THUMB
				+ "\" data-autocenter=\"1\" data-scale-bottom=\"0.75\" >";

		html += "<div data-u=\"slides\">";
		html += "<div data-u=\"prototype\" class=\"p\" style=\"width:190px;height:90px;\">";
		html += "<div data-u=\"thumbnailtemplate\" class=\"t\"></div>";
		html += "<svg viewbox=\"0 0 16000 16000\" class=\"cv\">"
				+ "<circle class=\"a\" cx=\"8000\" cy=\"8000\" r=\"3238.1\"></circle>"
				+ "<line class=\"a\" x1=\"6190.5\" y1=\"8000\" x2=\"9809.5\" y2=\"8000\"></line>"
				+ "<line class=\"a\" x1=\"8000\" y1=\"9809.5\" x2=\"8000\" y2=\"6190.5\"></line>" + "</svg>";
		html += "</div></div></div>";

		return html;
	}

	private String getArrowNavigator(boolean arrowLeft) {

		String styleNavigator = arrowLeft ? BASE_STYLE_NAVIGATOR_LEFT : BASE_STYLE_NAVIGATOR_RIGHT;
		String styleArrow = arrowLeft ? "arrowleft" : "arrowright";
		String polylinePoints = arrowLeft ? "11040,1920 4960,8000 11040,14080" : "4960,1920 11040,8000 4960,14080";
		String dataScale = arrowLeft ? "data-scale-left=\"0.75\" " : "data-scale-right=\"0.75\"";
		String html = "<div data-u=\"" + styleArrow + "\" class=\"jssora053\"  style=\"" + styleNavigator
				+ "\" data-autocenter=\"2\"  data-scale=\"0.75\" " + dataScale + " >";
		html += "<svg viewbox=\"0 0 16000 16000\" style=\"" + STYLE_NAVIGATOR_SVG + "\">";
		html += "<polyline class =\"a\" points=\"" + polylinePoints + "\"> </polyline></svg>";
		html += "</div>";

		return html;
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		CoolPasCherUI.CLIENT_FACTORY.startJESSORImageGallery();

		CoolPasCherUI.CLIENT_FACTORY.addAnimation(materialCard, null, 500);

		// // Menu animation
		// MaterialAnimation animationMenu = new MaterialAnimation();
		// animationMenu.setTransition(Transition.FADEIN);
		// animationMenu.setDelay(400);
		// animationMenu.setDuration(1000);
		// animationMenu.animate(materialCard);
		//
		// new Timer() {
		// @Override
		// public void run() {
		// materialCard.setOpacity(1);
		// // MaterialLoader.loading(false, materialPanel);
		// }
		// }.schedule(600);
	}

}
