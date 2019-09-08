package com.materials.client.widgets.model.user.cellist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.UserSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface UserListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(List<UserSO> userSOs);

	MDCellList<UserSO> getCellList();

	interface Presenter {

		void showUserAnnonces(UserSO userSO);

		void showUserComments(UserSO userSO);

		void swipeToUserDetail(UserSO userSO);

		void newUser();

		void deleteUsers(List<UserSO> userSOs);
	}

}
