package com.materials.client.places.search;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.articles.sortable.SortableArticle;

public interface SearchView extends IsWidget {

	void setPresenter(Presenter presenter);

	interface Presenter {

	}

	void showSearchResult(SortableArticle article, String searchTerm, boolean isLast);

	void showLoading();

	void showLast();

	String getLastSearchName();



}
