package com.materials.client.places.configurations.sliderConfig;

import com.materials.client.places.configurations.AbstractControlPanel;
import com.materials.client.widgets.model.slider.celllist.SliderList;
import com.materials.client.widgets.model.slider.celllist.SliderListView;
import com.materials.client.widgets.model.slider.details.SliderDetail;
import com.materials.client.widgets.model.slider.details.SliderDetailView;
import com.materials.client.widgets.model.slider.images.SliderImageDetails;
import com.materials.client.widgets.model.slider.images.SliderImageDetailsView;
import com.materials.client.widgets.model.slider.images.SliderImagesList;
import com.materials.client.widgets.model.slider.images.SliderImagesListView;
import com.materials.client.widgets.slider.jssor.preview.SliderPreview;
import com.materials.client.widgets.slider.jssor.preview.SliderPreviewView;

import gwt.material.design.client.base.MaterialWidget;

public class SliderConfigViewImpl extends AbstractControlPanel implements SliderConfigView {

	private String titel = "";

	private SliderList sliderList;
	private SliderDetail sliderDetail;
	private SliderImagesList sliderImagesList;
	private SliderImageDetails sliderImagesDetails;
	private SliderPreview sliderPreview;

	public SliderConfigViewImpl() {

		super(true);

		sliderList = new SliderList();
		sliderDetail = new SliderDetail(false);
		sliderImagesList = new SliderImagesList();
		sliderImagesDetails = new SliderImageDetails(false);
		sliderPreview = new SliderPreview();

		swipperWidget.addSwipeItem("sliderList", sliderList, true);
		swipperWidget.addSwipeItem("sliderDetail", sliderDetail, false);
		swipperWidget.addSwipeItem("sliderImagesList", sliderImagesList, true);
		swipperWidget.addSwipeItem("sliderImagesDetails", sliderImagesDetails, false);
		swipperWidget.addSwipeItem("sliderPreview", sliderPreview, false);
	}

	@Override
	public void showSliderList() {
		swipperWidget.show("sliderList");
		configControlPanel.showControll(sliderList.getButtons());
	}

	@Override
	public void goBackToSliderList(MaterialWidget materialWidget) {
		swipperWidget.swipeBackTo("sliderList");
		configControlPanel.showControll(sliderList.getButtons());
	}

	@Override
	public void goBackToImagesList(MaterialWidget materialWidget) {
		swipperWidget.swipeBackTo("sliderImagesList");
		configControlPanel.showControll(sliderImagesList.getButtons());
	}

	@Override
	public void showSliderImageDetail() {
		swipperWidget.swipeTo("sliderImagesDetails");
		configControlPanel.showControll(sliderImagesDetails.getButtonBar());
	}

	@Override
	public void showSliderPreview() {
		swipperWidget.swipeTo("sliderPreview");
		configControlPanel.clearControll();

	}

	@Override
	public void showSliderDetail() {
		swipperWidget.swipeTo("sliderDetail");
		configControlPanel.showControll(sliderDetail.getButtonBar());
	}

	@Override
	public void showSliderImagesList() {
		swipperWidget.swipeTo("sliderImagesList");
		configControlPanel.showControll(sliderImagesList.getButtons());
	}

	@Override
	public SliderListView getSliderListView() {
		return sliderList;
	}

	@Override
	public SliderDetailView getSliderDetailView() {
		return sliderDetail;
	}

	@Override
	public SliderImagesListView getSliderImagesListView() {
		return sliderImagesList;
	}

	@Override
	public SliderImageDetailsView getSliderImageDetailsView() {
		return sliderImagesDetails;
	}

	@Override
	public SliderPreviewView getSliderPreviewView() {
		return sliderPreview;
	}

}
