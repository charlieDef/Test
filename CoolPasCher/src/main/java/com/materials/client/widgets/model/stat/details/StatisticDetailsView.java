package com.materials.client.widgets.model.stat.details;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.stat.StatisticSO;

public interface StatisticDetailsView extends IsWidget {

	void setStatistic(StatisticSO statisticSO);

	void setPresenter(Presenter presenter);

	void setEdit();

	interface Presenter {

		void save(StatisticSO statisticSO);

		void backToStatisticList();
	}

}
