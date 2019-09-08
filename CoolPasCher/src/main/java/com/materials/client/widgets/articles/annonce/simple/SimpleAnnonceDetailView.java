package com.materials.client.widgets.articles.annonce.simple;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.buttonbar.FloatingButtonBar;

public interface SimpleAnnonceDetailView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setContentSO(ContentSO contentSO);

	FloatingButtonBar getButtonBar();

	interface Presenter {
		void backToContentList();

		void saveContent(ContentSO contentSO);
	}

	void setEdit();

}
