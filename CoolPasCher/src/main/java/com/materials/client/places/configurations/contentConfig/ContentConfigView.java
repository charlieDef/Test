package com.materials.client.places.configurations.contentConfig;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.articles.annonce.simple.SimpleAnnonceDetailView;
import com.materials.client.widgets.model.content.celllist.ContentAreaListView;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.content.pic.ContentPicturesView;

public interface ContentConfigView extends IsWidget {

	ContentListView getContentListView();

	ContentAreaListView getContentAreaListView();

	SimpleAnnonceDetailView getSimpleAnnonceDetailView();

	void showContentAreaList();

	void showContentList();

	void backToContentList();

	interface Presenter {

	}

	void backToContentArealist();

	void backToContentListFromAreaList();

	void showContentDetails(boolean isNew);

	ContentPicturesView getContentPicturesView();

	void showContentPictures();

}
