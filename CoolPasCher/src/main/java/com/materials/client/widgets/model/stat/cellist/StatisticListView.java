package com.materials.client.widgets.model.stat.cellist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface StatisticListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(List<StatisticSO> statisticSOs);

	MDCellList<StatisticSO> getCellList();

	interface Presenter {

		void showStatisticContent(StatisticSO statisticSO);

		void swipeToStatisticDetail(StatisticSO statisticSO);

		void swipeToDashboard(StatisticSO statisticSO);

		void newStatistic();

		void deleteStatistics(List<StatisticSO> statisticSO);
	}

}
