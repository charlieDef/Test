package com.materials.client.widgets.area;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CAreaSO;

public interface ContentAreaView extends IsWidget {

	void setContentArea(CAreaSO areaSO);

	void setContentAreas(List<CAreaSO> areaSOs);

	void setPresenter(Presenter presenter);

	interface Presenter {

		void backToContentAreaList();

		void saveContentArea(CAreaSO cAreaSO);

	}
}
