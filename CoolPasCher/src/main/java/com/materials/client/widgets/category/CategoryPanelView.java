package com.materials.client.widgets.category;

import com.google.gwt.user.client.ui.IsWidget;

public interface CategoryPanelView extends IsWidget {

	public void swippeToMenu();

	void setPresenter(Presenter presenter);

	interface Presenter {

	}

}
