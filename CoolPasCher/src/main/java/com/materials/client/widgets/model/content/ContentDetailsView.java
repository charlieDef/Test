package com.materials.client.widgets.model.content;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ContentSO;

public interface ContentDetailsView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setContentSO(ContentSO contentSO);

	void setEdit();

	interface Presenter {

		void backToContentList();

		void saveContent(ContentSO contentSO);
	}
}
