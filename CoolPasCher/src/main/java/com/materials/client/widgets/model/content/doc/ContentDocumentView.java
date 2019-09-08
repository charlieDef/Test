package com.materials.client.widgets.model.content.doc;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CAreaSO;

public interface ContentDocumentView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setCAreaSO(CAreaSO cAreaSO);

	void setEdit();

	interface Presenter {

		void goBackToContentList();

		void saveCArea(CAreaSO cAreaSO);
	}
}
