package com.materials.client.widgets.model.slider.images;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ByteDataSO;

public interface SliderImageDetailsView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setByteData(ByteDataSO byteDataSO);

	void setEdit();

	interface Presenter {

		void swipeBackToImagesList();

		void saveImage(ByteDataSO byteDataSO);
	}
}
