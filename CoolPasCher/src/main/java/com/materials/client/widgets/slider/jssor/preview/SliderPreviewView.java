package com.materials.client.widgets.slider.jssor.preview;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.SliderSO;

public interface SliderPreviewView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setSliderSO(SliderSO sliderSO);

	interface Presenter {
		void swipeBackToSliderList();
	}
}
