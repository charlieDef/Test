package com.materials.client.widgets.model.menu;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.MenuSO;

public interface MenuDetailsView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setMenuSO(MenuSO contentSO);

	void setEdit();

	void initMenuSelectBoxData(Map<Long, String> map);

	interface Presenter {

		void backToMenuList();

		void saveMenu(MenuSO menuSO);
	}

}
