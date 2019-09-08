package com.materials.client.places.search;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.articles.sortable.SortableArticle;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.content.ContentAction;
import com.materials.shared.action.content.ContentCommand;

public class SearchActivity extends AbstractActivity implements SearchView.Presenter {

	private AppClientFactory clientFactory;
	private SearchPlace place;
	private SearchView view;

	public SearchActivity(AppClientFactory clientFactory, SearchPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;
		view = clientFactory.getSearchView();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		panel.setWidget(view);
		String searchTerm = (place.getSearchTerm() != null && !place.getSearchTerm().isEmpty()) ?  place.getSearchTerm().toLowerCase() : "";

		if (!searchTerm.isEmpty()) {
			if (clientFactory.getSearchArticle().containsKey(searchTerm)) {
				if(view.getLastSearchName()!= null && view.getLastSearchName().equals(searchTerm)){
					view.showLast();
				}
				else{
					clientFactory.setScrollIndex(0);
					SortableArticle sortableArticle = clientFactory.getSearchArticle().get(searchTerm);
					view.showSearchResult(sortableArticle, searchTerm, true);		
				
				}
			} else {
				view.showLoading();
				clientFactory.setScrollIndex(0);
				clientFactory.execute(new ContentAction(DBAction.READ, ContentCommand.ALL_CONTENTS_LABEL, searchTerm),
						result -> {
							final List<ContentSO> list = MethodsUtils.castList(result.getObjects(), ContentSO.class);
							clientFactory.updateCache(list);
							SortableArticle sortableArticle = new SortableArticle(list, 1, 3000);
							view.showSearchResult(sortableArticle, searchTerm, false);

							clientFactory.getStartView().getAppNavBarPanel().deselectSelected();
							//clientFactory.getSearchArticle().clear();
							clientFactory.getSearchArticle().put(searchTerm, sortableArticle);
						});
			
			}
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
}
