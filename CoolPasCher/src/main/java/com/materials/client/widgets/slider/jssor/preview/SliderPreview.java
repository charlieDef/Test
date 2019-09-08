package com.materials.client.widgets.slider.jssor.preview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.SliderSO;
import com.materials.client.widgets.slider.jssor.master.MasterSlider;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class SliderPreview extends MaterialPanel implements SliderPreviewView {

	private static SliderPreviewUiBinder uiBinder = GWT.create(SliderPreviewUiBinder.class);

	interface SliderPreviewUiBinder extends UiBinder<Widget, SliderPreview> {
	}

	private Presenter presenter;

	@UiField
	MaterialIcon backButtonUi;

	@UiField
	MaterialRow detailContentUi;

	public SliderPreview() {
		add(uiBinder.createAndBindUi(this));

		backButtonUi.addClickHandler(x -> {
			presenter.swipeBackToSliderList();
			new Timer() {
				@Override
				public void run() {
					detailContentUi.clear();
				}

			}.schedule(1000);

		});
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setSliderSO(SliderSO sliderSO) {

		MasterSlider masterSlider = new MasterSlider(sliderSO, "jssor_44");
		new Timer() {
			@Override
			public void run() {
				detailContentUi.add(masterSlider);
			}

		}.schedule(1);
	}

}
