package com.materials.client.widgets.model.stat.dashboard;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.stat.StatisticSO;

public interface MDDashboardView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setStatisticSO(StatisticSO statisticSO);

	interface Presenter {
		void swipeBackToStatisticList();
	}

}
