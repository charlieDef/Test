package com.materials.client.widgets.stats;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.stat.StatItemSO;

public interface StatisticPanelView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setStatisticItem(StatItemSO statItem);

	interface Presenter {

		void save(StatItemSO statItem);

		void goBackToItemList();
	}
}
