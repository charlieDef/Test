package com.materials.client.widgets.category;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.views.navbar.AppNavBarView;

public interface ZCategoriePanelView extends IsWidget {

	void setPresenter(AppNavBarView.Presenter presenter);

	interface Presenter {

		void click(String clickName);
	}
}
