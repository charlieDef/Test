package com.materials.client.views.header;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.MenuSO;

public interface APPHeaderView extends IsWidget {

	void rebuildHeader(List<MenuSO> list);

	void refreshHeader();

	public void clearSearchBox();

	void setPresenter(Presenter presenter);

	interface Presenter {

		void newAnnonce(String label, String region, String ville);
	}

}
