package com.materials.client.widgets.contact;

import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;

public interface ContactWidgetView extends IsWidget {

	void setPresenter(Presenter presenter);

	interface Presenter {

		void sendMessage(String nom, String prenom, String email, String message);
	}

	void showConfirmationLighBox(String headerLabel, String contentLabel, Consumer<Boolean> consumer);

}
