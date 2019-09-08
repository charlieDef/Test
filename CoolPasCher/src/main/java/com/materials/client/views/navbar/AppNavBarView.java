package com.materials.client.views.navbar;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.MenuSO;

public interface AppNavBarView extends IsWidget {

	void deselectSelected();

	void deselecAll();

	void swipeBack();

	void rebuildNavbar();

	void rebuildNavbar(List<MenuSO> list);

	void refreshNavbar();

	void clearSearch();

	void setPresenter(Presenter presenter);

	interface Presenter {
		void menuClick(String menuName, String parentMenu);
	}

}
