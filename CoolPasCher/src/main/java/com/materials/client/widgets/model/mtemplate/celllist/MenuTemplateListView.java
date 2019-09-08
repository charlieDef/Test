package com.materials.client.widgets.model.mtemplate.celllist;

import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.MenuTemplateSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface MenuTemplateListView extends IsWidget {

	void setPresenter(Presenter presenter, Consumer<Boolean> backCallBack);

	void setData(List<MenuTemplateSO> menuTemplateSOs);

	MDCellList<MenuTemplateSO> getCellList();

	void handleAddIconVisibility();

	interface Presenter {

		void swipeBackToMenuList();

		void showTemplateDetail(MenuTemplateSO menuTemplateSO);

		void newTemplate(int colNr);

		void deleteTemplate(List<MenuTemplateSO> menuTemplateSOs);
	}

}
