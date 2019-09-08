package com.materials.client.places.content;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.articles.annonce.simple.SimpleViewAbleAnnonceDetailsView;

public interface ContentView extends IsWidget {

	void setPresenter(Presenter presenter);

	void showContentAreas(ContentSO contentSO);

	void setContentImages(ContentSO contentSO);

	interface Presenter {

	}

	void showAnnonceDetails(SimpleViewAbleAnnonceDetailsView annonceDetailsView);

	void setContent(Widget widget);

}
