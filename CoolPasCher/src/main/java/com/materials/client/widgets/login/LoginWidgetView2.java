package com.materials.client.widgets.login;

import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.UserSO;

public interface LoginWidgetView2 extends IsWidget {

	void close();

	void show();

	void handleWrongPwd();

	void handleWrongPwd(String message);

	void handleSuccessPwd();

	void showPwdResetSuccesMessage();

	void showPwdResetCodeSuccesMessage();

	void setPresenter(Presenter presenter);

	Presenter getPresenter();

	interface Presenter {

		void checkLogin(String userEmail, String pwd);

		void fetchUser(String email, Consumer<UserSO> userImage);

		void resetPwd(String userEmail, String pwdResetCode, String newPwd, String newPwdRepeat);

		void resetPwdCode(String userEmail);

		void performLogout();

	}

}
