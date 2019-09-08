package com.materials.client.widgets.model.user;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.UserSO;

public interface UserDetailsView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setUserSO(UserSO userSO);

	void setEdit();

	interface Presenter {

		void backToUserList();

		void saveUser(UserSO userSO);
	}
}
