package com.materials.client.views.content;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.views.footer.APPFooterPanel;
import com.materials.client.views.header.APPHeaderPanel;
import com.materials.client.views.navbar.APPNavBarPanel;

public interface StartView extends IsWidget {

	APPHeaderPanel getAppHeaderPanel();

	APPNavBarPanel getAppNavBarPanel();

	APPFooterPanel getAppFooterPanel();

	void showConfiguration(boolean show);

	void addWidget(IsWidget widget);

	AcceptsOneWidget getContentUi();

	void setPresenter(Presenter presenter);

	Presenter getPresenter();

	interface Presenter {

	}

	void showRegisterPanel(boolean edit);

}
