package com.materials.client.widgets.slider.jssor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ByteDataSO;
import com.materials.client.model.SliderSO;
import com.materials.shared.MethodsUtils;

import gwt.material.design.client.ui.MaterialCard;

public class JssorSlider extends Composite {
	private static final String SLIDER_ID = "jssor_1";
	private MaterialCard materialCard;
	boolean startet = false;
	private Map<Integer, String> capTionsContainer;
	private List<Integer> listindex = new ArrayList<Integer>();
	private Random randomGenerator = new Random();
	private static final String BASE_STYLE = "position:relative;margin:0 auto;top:0px;left:0px;width:1380px;height:460px;overflow:hidden;visibility:hidden;";
	private static final String BASE_STYLE_SLIDES = "cursor:default;position:relative;top:0px;left:0px;width:1380px;height:460px;overflow:hidden;";

	public JssorSlider() {
		initMap();
	}

	public JssorSlider(List<SliderSO> sliderItemSOs) {
		initMap();
		Collections.sort(sliderItemSOs, Comparator.comparing(SliderSO::getIndex));

		final HTML sliderPanel = insertSliders(sliderItemSOs);

		materialCard = new MaterialCard();
		// materialCard.setOpacity(0);
		materialCard.getElement().getStyle().setMargin(10, Unit.PX);
		materialCard.getElement().getStyle().setMarginBottom(40, Unit.PX);
		materialCard.getElement().getStyle().setPadding(10, Unit.PX);
		materialCard.getElement().getStyle().setProperty("borderRadius", "5px");
		materialCard.add(sliderPanel);

		initWidget(materialCard);

	}

	private HTML insertSliders(List<SliderSO> sliderItemSOs) {

		String builder = new String();

		if (sliderItemSOs != null && sliderItemSOs.size() > 0) {
			builder += "<div id=\"" + SLIDER_ID + "\" style=\"" + BASE_STYLE + "\">";

			builder += "<div data-u=\"slides\" style=\"" + BASE_STYLE_SLIDES + "\">";
			for (SliderSO sliderItem : sliderItemSOs) {
				if (sliderItem.isActive()) {
					builder += getImgHtml(sliderItem);
				}
			}
			builder += "</div>";
			builder += getNavigation();
			builder += "</div>";
			return new HTML(builder);

		}
		return null;
	}

	private String getImgHtml(SliderSO sliderItem) {

		String html = "";
		String caption = getCaption(sliderItem);
		if (sliderItem.isPresentation()) {
			html = "<div data-b=\"0\" data-idle=\"8000\" data-p=\"230.00\" >";
		} else {
			html = "<div data-p=\"230.00\">";
		}
		html += "<img  data-u=\"image\" src=\"" + sliderItem.getSliderImageUrl() + "\"  />";
		html += caption;
		html += "</div>";
		return html;
	}

	private String getCaption(SliderSO sliderSO) {
		String divCaption = "";

		if (sliderSO.isPresentation()) {
			divCaption = getPresentationCaption(sliderSO);
		} else if (MethodsUtils.isStringOK(sliderSO.getTitel()) && MethodsUtils.isStringOK(sliderSO.getTitel1())
				&& MethodsUtils.isStringOK(sliderSO.getTitel2())) {

			divCaption = getCaption3(sliderSO.getTitel(), sliderSO.getTitel1(), sliderSO.getTitel2());

		} else {

			Integer integer = randomGenerator.nextInt(6);
			while (!listindex.contains(integer) && listindex.size() <= 6) {
				listindex.add(integer);
				if (listindex.size() == 6) {
					listindex.clear();
				}
				break;
			}

			divCaption = capTionsContainer.get(integer);
			divCaption = divCaption.replaceAll("CAPTION_PLACE", sliderSO.getTitel());
			divCaption = divCaption.replaceAll("CAPTION1_PLACE1", sliderSO.getTitel1());

		}

		return divCaption;
	}

	private String getCaption2A() {

		String divCaption = "";
		divCaption = "<div data-u=\"caption\" data-t=\"0\"  class=\"jssorText jssorCaption0\">CAPTION_PLACE</div>";
		divCaption += "<div data-u=\"caption\" data-t=\"1\"  class=\"jssorText jssorCaption1\">CAPTION1_PLACE1</div>";

		return divCaption;
	}

	private String getCaption2B() {

		String divCaption = "";
		divCaption = "<div data-u=\"caption\" data-t=\"5\"  class=\"jssorText jssorCaption5\">CAPTION_PLACE</div>";
		divCaption += "<div data-u=\"caption\" data-t=\"6\"  class=\"jssorText jssorCaption6\">CAPTION1_PLACE1</div>";

		return divCaption;
	}

	private String getCaption2C() {

		String divCaption = "";
		divCaption = "<div data-u=\"caption\" data-t=\"7\"  class=\"jssorText jssorCaption7\">CAPTION_PLACE</div>";
		divCaption += "<div data-u=\"caption\" data-t=\"8\"  class=\"jssorText jssorCaption8\">CAPTION1_PLACE1</div>";

		return divCaption;
	}

	private String getCaption2D() {

		String divCaption = "";
		divCaption = "<div data-u=\"caption\" data-t=\"9\"  class=\"jssorText jssorCaption9\">CAPTION_PLACE</div>";
		divCaption += "<div data-u=\"caption\" data-t=\"10\"  class=\"jssorText jssorCaption10\">CAPTION1_PLACE1</div>";

		return divCaption;
	}

	private String getCaption2E() {

		String divCaption = "";
		divCaption = "<div data-u=\"caption\" data-t=\"11\"  class=\"jssorText jssorCaption11\">CAPTION_PLACE</div>";
		divCaption += "<div data-u=\"caption\" data-t=\"12\"  class=\"jssorText jssorCaption12\">CAPTION1_PLACE1</div>";

		return divCaption;
	}

	private String getCaption2F() {

		String divCaption = "";
		divCaption = "<div data-u=\"caption\" data-t=\"13\"  class=\"jssorText jssorCaption13\">CAPTION_PLACE</div>";
		divCaption += "<div data-u=\"caption\" data-t=\"14\"  class=\"jssorText jssorCaption14\">CAPTION1_PLACE1</div>";

		return divCaption;
	}

	private String getCaption3(String caption, String caption1, String caption2) {

		String divCaption = "";
		divCaption = "<div data-u=\"caption\" data-t=\"2\"  class=\"jssorText jssorCaption2\">" + caption + "</div>";
		divCaption += "<div data-u=\"caption\" data-t=\"3\"  class=\"jssorText jssorCaption3\">" + caption1 + "</div>";
		divCaption += "<div data-u=\"caption\" data-t=\"4\"  class=\"jssorText jssorCaption4\">" + caption2 + "</div>";

		return divCaption;
	}

	private String getNavigation() {

		String html = " <div data-u=\"arrowleft\" class=\"jssora053\" style=\"width:35px;height:35px;top:0px;left:25px;\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-left=\"0.75\">";
		html += "<svg viewbox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
		html += "<polyline class=\"a\" points=\"11040,1920 4960,8000 11040,14080 \"></polyline> </svg></div>";
		html += "<div data-u=\"arrowright\" class=\"jssora053\" style=\"width:35px;height:35px;top:0px;right:25px;\" data-autocenter=\"2\" data-scale=\"0.75\" data-scale-right=\"0.75\">";
		html += "<svg viewbox=\"0 0 16000 16000\" style=\"position:absolute;top:0;left:0;width:100%;height:100%;\">";
		html += " <polyline class=\"a\" points=\"4960,1920 11040,8000 4960,14080 \"></polyline></svg></div>";
		return html;
	}

	private String getPresentationCaption(SliderSO sliderSO) {
		String divCaption = "";
		List<ByteDataSO> byteDataSOs = sliderSO.getByteDataSOs();

		for (int i = 0; i < byteDataSOs.size(); i++) {

			int styleNr = i + 15;

			ByteDataSO byteDataSO = sliderSO.getByteData(i + 1);
			String urlImg = byteDataSO != null ? byteDataSO.getImageUrl() : "";
			divCaption += "<img data-u=\"caption\" data-t=\"" + styleNr + "\"  class=\" jssorText jssorCaption"
					+ styleNr + "\"  src=\"" + urlImg + "\" >";
		}
		divCaption += "<div data-u=\"caption\" data-t=\"23\"  class=\"jssorText jssorCaption23\">" + sliderSO.getTitel()
				+ "</div>";
		return divCaption;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		if (!startet) {
			CoolPasCherUI.CLIENT_FACTORY.startJESSORSlider(SLIDER_ID, false);
			startet = true;
		}
	}

	private void initMap() {
		capTionsContainer = new HashMap<Integer, String>();

		capTionsContainer.put(0, getCaption2A());
		capTionsContainer.put(1, getCaption2B());
		capTionsContainer.put(2, getCaption2C());
		capTionsContainer.put(3, getCaption2D());
		capTionsContainer.put(4, getCaption2E());
		capTionsContainer.put(5, getCaption2F());

	}

}
