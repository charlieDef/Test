package com.materials.client.places.configurations.rsociaux;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.model.rsocial.celllist.RSociauxListView;
import com.materials.client.widgets.model.rsocial.details.RSociauxDetailView;

import gwt.material.design.client.base.MaterialWidget;

public interface RSociauxConfigView extends IsWidget {

	RSociauxListView getRSociauxListView();

	RSociauxDetailView getRSociauxDetail();

	void showRSocialList();

	void showRSocialDetail();

	public void goBackToRSocialList(MaterialWidget materialWidget);

	interface Presenter {

	}

}
