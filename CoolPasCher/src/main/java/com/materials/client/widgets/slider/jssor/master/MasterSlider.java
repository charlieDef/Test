package com.materials.client.widgets.slider.jssor.master;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.SliderSO;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class MasterSlider extends Composite {

	private MaterialCard materialCard;
	boolean startet = false, preview = false;

	private String sliderId;
	private static final String BASE_STYLE = "position:relative;margin:0 auto;top:0px;left:0px;width:1380px;height:460px;overflow:hidden;visibility:hidden;";
	private static final String BASE_STYLE_SLIDES = "cursor:default;position:relative;top:0px;left:0px;width:1380px;height:460px;overflow:hidden;";

	public MasterSlider(List<SliderSO> sliderItemSOs) {
		// initMap();
		Collections.sort(sliderItemSOs, Comparator.comparing(SliderSO::getIndex));

		setSliderId("jssor_11");

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

	public MasterSlider(SliderSO sliderItemSO, String sliderId) {
		// initMap();

		setSliderId(sliderId);
		setPreview(true);

		final HTML sliderPanel = insertSliders(Arrays.asList(sliderItemSO));

		materialCard = new MaterialCard();
		materialCard.setOpacity(0);
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
			builder += "<div id=\"" + getSliderId() + "\" style=\"" + BASE_STYLE + "\">";

			builder += "<div data-u=\"slides\" style=\"" + BASE_STYLE_SLIDES + "\">";
			for (SliderSO sliderItem : sliderItemSOs) {
				if (sliderItem.isActive() || isPreview()) {
					builder += sliderItem.isPresentation() ? MasterUtils.getImgHtmlPresentation(sliderItem)
							: MasterUtils.getImgHtml(sliderItem);
				}
			}
			builder += "</div>";
			builder += getNavigation();
			builder += "</div>";
			return new HTML(builder);

		}
		return null;
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

	@Override
	protected void onLoad() {
		super.onLoad();
		if (!startet) {
			CoolPasCherUI.CLIENT_FACTORY.startJESSORSlider(getSliderId(), isPreview());
			startet = true;
		}

		if (isPreview()) {
			MaterialAnimation animationMenu = new MaterialAnimation();
			animationMenu.setTransition(Transition.FADEIN);
			animationMenu.setDelay(1000);
			animationMenu.setDuration(1200);
			animationMenu.animate(materialCard);

			new Timer() {
				@Override
				public void run() {
					materialCard.setOpacity(1);

				}
			}.schedule(1500);
		}
	}

	public String getSliderId() {
		return sliderId;
	}

	public void setSliderId(String sliderId) {
		this.sliderId = sliderId;
	}

	public void setPreview(boolean preview) {
		this.preview = preview;
	}

	public boolean isPreview() {
		return preview;
	}
}
