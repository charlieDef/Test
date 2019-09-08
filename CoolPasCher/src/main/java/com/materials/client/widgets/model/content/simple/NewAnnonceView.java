package com.materials.client.widgets.model.content.simple;

import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ContentSO;

public interface NewAnnonceView extends IsWidget {

	public boolean enableSaveButton();

	void setEdit(boolean edit);

	void setPresenter(Presenter presenter);

	void setContent(ContentSO contentSO);

	void updateContentSO(ContentSO contentSO);

	void extractFromAnnonce(ContentSO contentSO);

	void setSaveControllConsumer(Consumer<Boolean> saveControllConsumer);
	// ContentSO getContentSO();

	interface Presenter {

	}

}
