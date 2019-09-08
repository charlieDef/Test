package com.materials.client.widgets.model.site;

import com.materials.client.model.WebSiteSO;

public interface WebSiteDetailView {

	void setWebSite(WebSiteSO webSiteSO);

	void setPresenter(Presenter presenter);

	interface Presenter {

		void save(WebSiteSO siteSO);
	}

}
