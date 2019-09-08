package com.materials.client.widgets.model.rsocial.celllist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.RSocialSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface RSociauxListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(List<RSocialSO> rSocialSOs);

	MDCellList<RSocialSO> getCellList();

	interface Presenter {

		void showRSocialList();

		void swipeToRSocialDetail(RSocialSO rSocialSO);

		void newRSocial();

		void deleteRSocial(List<RSocialSO> menuSOs);
	}

	void setReadOnly(boolean readOnly);

}
