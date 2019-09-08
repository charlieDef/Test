package com.materials.client.widgets.model.slider.images;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ByteDataSO;
import com.materials.client.model.SliderSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface SliderImagesListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(SliderSO sliderSO);

	MDCellList<ByteDataSO> getCellList();

	interface Presenter {

		void swipeBackToSliderList();

		void swipeToSliderImageDetail(ByteDataSO byteDataSO);

		void newSliderImage();

		void deleteSliderImages(List<ByteDataSO> byteDataSOs);
	}
}
