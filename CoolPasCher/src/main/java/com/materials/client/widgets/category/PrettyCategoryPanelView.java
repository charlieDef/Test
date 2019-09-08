package com.materials.client.widgets.category;

import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;

public interface PrettyCategoryPanelView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setNavBarConsumer(Consumer<Boolean> navBarConsumer);

	void swippeToBase();

	interface Presenter {

	}

}
