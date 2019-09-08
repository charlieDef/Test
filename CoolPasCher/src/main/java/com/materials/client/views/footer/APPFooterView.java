package com.materials.client.views.footer;

import com.google.gwt.user.client.ui.IsWidget;

public interface APPFooterView extends IsWidget {

	void setPresenter(Presenter presenter);

	void refreshFooter();

	interface Presenter {

	}

}
