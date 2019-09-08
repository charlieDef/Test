package com.materials.client.widgets.model.content.celllist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface ContentAreaListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(ContentSO contentSOs);

	MDCellList<CAreaSO> getCellList();

	interface Presenter {

		void swipeBackToContentList();

		void swipeToContentAreaDetail(CAreaSO cAreaSO);

		void newContentArea(String type);

		void deleteContentAreas(List<CAreaSO> cAreaSOs);
	}

}
