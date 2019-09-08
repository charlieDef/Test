package com.materials.client.places.configurations.sliderConfig;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.model.slider.celllist.SliderListView;
import com.materials.client.widgets.model.slider.details.SliderDetailView;
import com.materials.client.widgets.model.slider.images.SliderImageDetailsView;
import com.materials.client.widgets.model.slider.images.SliderImagesListView;
import com.materials.client.widgets.slider.jssor.preview.SliderPreviewView;

import gwt.material.design.client.base.MaterialWidget;

public interface SliderConfigView extends IsWidget {

	SliderListView getSliderListView();

	SliderDetailView getSliderDetailView();

	SliderImageDetailsView getSliderImageDetailsView();

	SliderImagesListView getSliderImagesListView();

	SliderPreviewView getSliderPreviewView();

	void showSliderList();

	void showSliderDetail();

	void showSliderImagesList();

	void showSliderImageDetail();

	void showSliderPreview();

	void goBackToImagesList(MaterialWidget materialWidget);

	interface Presenter {

	}

	void goBackToSliderList(MaterialWidget materialWidget);

}
