package com.materials.client.widgets.login;

import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;

public interface LoginWidgetView extends IsWidget {

	void close();

	void show();

	void handleWrongPwd();

	void showPwdResetSuccesMessage();

	void setPresenter(Presenter presenter);

	Presenter getPresenter();

	interface Presenter {

		void checkLogin(String userEmail, String pwd);

		void fetchUser(String email, Consumer<String> userImage);

		void resetPwd(String userEmail);

		void performLogout();

	}

}
