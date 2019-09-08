package com.materials.client.places.configurations.stat;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.model.stat.cellist.StatisticItemListView;
import com.materials.client.widgets.model.stat.cellist.StatisticListView;
import com.materials.client.widgets.model.stat.dashboard.MDDashboardView;
import com.materials.client.widgets.model.stat.details.StatisticDetailsView;
import com.materials.client.widgets.stats.StatisticPanelView;

import gwt.material.design.client.base.MaterialWidget;

public interface StatisticConfigView extends IsWidget {

	StatisticListView getStatisticListView();

	StatisticDetailsView getStatisticDetail();

	StatisticItemListView getStatisticItemListView();

	MDDashboardView getMdDashboard();

	StatisticPanelView getStatisticItemPanelView();

	void showStatisticList();

	void showStatisticDetail();

	void showStatisticItemsList();

	void showStatisticItemPanel();

	void showDashboard();

	void goBackToStatisticList(MaterialWidget materialWidget);

	void goBackToStatisticItemsList(MaterialWidget materialWidget);

	interface Presenter {

	}

}
