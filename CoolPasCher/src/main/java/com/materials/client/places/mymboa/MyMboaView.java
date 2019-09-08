package com.materials.client.places.mymboa;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonceView;
import com.materials.client.widgets.model.content.celllist.ContentListView;

public interface MyMboaView extends IsWidget {

	void setPresenter(Presenter presenter);

	ContentListView getContentListView();

	EditAbleAnnonceView getEditAbleAnnonceView();

	void showContentList();

	void showContentDetail();

	void backToContentList();

	interface Presenter {

	}
}
