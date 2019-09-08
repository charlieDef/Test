package com.materials.client.widgets.model.stat.cellist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.stat.StatItemSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface StatisticItemListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(StatisticSO statisticSO);

	StatisticSO getStatisticSO();

	MDCellList<StatItemSO> getCellList();

	interface Presenter {

		void swipeBackToStatisticList();

		void swipeToStatisticItemDetail(StatItemSO statItemSO);

		void newStatisticItem();

		void deleteStatisticItems(List<StatItemSO> StatItemSOs);
	}
}
