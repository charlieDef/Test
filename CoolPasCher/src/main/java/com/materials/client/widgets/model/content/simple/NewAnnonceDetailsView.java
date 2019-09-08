package com.materials.client.widgets.model.content.simple;

import java.util.function.Consumer;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ContentSO;

public interface NewAnnonceDetailsView extends IsWidget {

	public boolean enableSaveButton();

	void setEdit(boolean edit);

	void setPresenter(Presenter presenter);

	void setContentSO(ContentSO contentSO);

	ContentSO getContentSO();

	void extractFromDetail(ContentSO contentSO);

	void setSaveControllConsumer(Consumer<Boolean> saveControllConsumer);

	interface Presenter {

	}
}
