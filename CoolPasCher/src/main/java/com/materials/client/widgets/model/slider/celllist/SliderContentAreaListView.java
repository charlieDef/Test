package com.materials.client.widgets.model.slider.celllist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface SliderContentAreaListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(ContentSO contentSOs);

	MDCellList<CAreaSO> getCellList();

	interface Presenter {

		void swipeBackToSliderList();

		void swipeToSliderContentAreaDetail(CAreaSO cAreaSO);

		void newSliderContentArea(String type);

		void deleteSliderContentAreas(List<CAreaSO> cAreaSOs);
	}

}
