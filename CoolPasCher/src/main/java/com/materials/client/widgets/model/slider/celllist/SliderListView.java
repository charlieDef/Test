package com.materials.client.widgets.model.slider.celllist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.SliderSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface SliderListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(List<SliderSO> sliderSOs);

	MDCellList<SliderSO> getCellList();

	interface Presenter {

		void showSliderContent(SliderSO sliderSO);

		void showSliderPreview(SliderSO sliderSO);

		void swipeToSliderDetail(SliderSO sliderSO);

		// void swipeToSliderPresentation(SliderSO sliderSO);

		void newSlider(boolean presentation);

		void deleteSliders(List<SliderSO> sliderSOs);
	}

}
