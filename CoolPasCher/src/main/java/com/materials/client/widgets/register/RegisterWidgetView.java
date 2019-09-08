package com.materials.client.widgets.register;

import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.UserSO;

public interface RegisterWidgetView extends IsWidget {

	void setPresenter(Presenter presenter);

	Presenter getPresenter();

	void reset();

	void showFailText(String failText);

	void showConfirmationLighBox(String headerLabel, String contentLabel, Consumer<Boolean> confirmationConsumer);

	void setUserSO(UserSO userSO);

	interface Presenter {
		void saveMember(UserSO userSO);
	}

	void editMode(boolean editMode);

}
