package com.materials.client.widgets.model.content.celllist;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.MDCellList;

public interface ContentListView extends IsWidget {

	void setPresenter(Presenter presenter, Consumer<Boolean> backCallBack);

	void setData(List<ContentSO> contentSOs);

	MDCellList<ContentSO> getCellList();

	void setNewContentAble(boolean multiContents);

	LinkedList<ControlButton> getButtons();

	interface Presenter {

		void swipeToContentDetail(ContentSO contentSO, boolean isNew);

		void showContentAreaList(ContentSO contentSO);

		void showContentTools(ContentSO contentSO);

		void newContent();

		void deleteContents(List<ContentSO> contentSO);

		void backToMenuList();
	}

}
