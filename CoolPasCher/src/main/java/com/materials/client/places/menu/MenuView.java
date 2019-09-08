package com.materials.client.places.menu;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.articles.sortable.SortableArticle;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonce;

import gwt.material.design.client.ui.MaterialPanel;

public interface MenuView extends IsWidget {

	MaterialPanel getPanelForSearchResult();

	void setPresenter(Presenter presenter);

	void setContent(Widget widget);

	void setContents(List<ContentSO> contentSOs);

	MaterialPanel getContent();

	void clearContent();

	void startAcceuil(boolean isHome);

	void loadFavorits(List<ContentSO> contentSOs);

	void setContentAreas(List<CAreaSO> getcAreaSOs);

	void showNewAnnoncePanel(EditAbleAnnonce editAbleAnnonceView);

	interface Presenter {

	}

	void showStartPanel();

	void swipeBack(boolean isNewAnnonce);

	void clearAnnoncePanel();

	void swippeToAcceuil();

	void swippeToSearchResult();

	void swippeToCategory(SortableArticle article, String categoryName);
	
	void swippeToCategory();

	String getLastCategory();

}
