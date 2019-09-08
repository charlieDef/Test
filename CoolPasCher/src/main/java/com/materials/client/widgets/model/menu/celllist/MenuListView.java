package com.materials.client.widgets.model.menu.celllist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.MenuSO;
import com.materials.client.widgets.datagrid.DataGridWidget;

public interface MenuListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(List<MenuSO> menuSOs);

	DataGridWidget<MenuSO> getCellList();

	interface Presenter {

		void showContentList(MenuSO menuSO);

		void showTemplateLayoutList(MenuSO menuSO);

		void swipeToMenuDetail(MenuSO menuSO);

		// void swipeToMenuList();

		void newMenu();

		void deleteMenus(List<MenuSO> menuSOs);
	}

}
