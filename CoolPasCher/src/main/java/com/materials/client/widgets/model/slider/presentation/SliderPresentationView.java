package com.materials.client.widgets.model.slider.presentation;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.SliderSO;

public interface SliderPresentationView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setSliderSO(SliderSO sliderSO);

	void setEdit();

	interface Presenter {

		void backToSliderList();

		void saveSliderPresentation(SliderSO sliderSO);
	}

}
