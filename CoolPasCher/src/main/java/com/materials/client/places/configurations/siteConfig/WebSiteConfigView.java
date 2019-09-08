package com.materials.client.places.configurations.siteConfig;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.model.site.WebSiteDetailView;

public interface WebSiteConfigView extends IsWidget {

	WebSiteDetailView getSiteDetailView();

	void showWebsiteDetail();

	interface Presenter {

	}
}
