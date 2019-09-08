package com.materials.client.places.configurations.stat;

import com.materials.client.places.configurations.AbstractControlPanel;
import com.materials.client.widgets.model.stat.cellist.StatisticItemList;
import com.materials.client.widgets.model.stat.cellist.StatisticItemListView;
import com.materials.client.widgets.model.stat.cellist.StatisticList;
import com.materials.client.widgets.model.stat.cellist.StatisticListView;
import com.materials.client.widgets.model.stat.dashboard.MDDashboard;
import com.materials.client.widgets.model.stat.dashboard.MDDashboardView;
import com.materials.client.widgets.model.stat.details.StatisticDetails;
import com.materials.client.widgets.model.stat.details.StatisticDetailsView;
import com.materials.client.widgets.stats.StatisticPanel;
import com.materials.client.widgets.stats.StatisticPanelView;

import gwt.material.design.client.base.MaterialWidget;

public class StatisticConfigViewImpl extends AbstractControlPanel implements StatisticConfigView {

	private String titel = "";
	private StatisticDetails statisticDetails;
	private StatisticList statisticList;
	private StatisticItemList statisticItemList;
	private StatisticPanel statisticPanel;

	private MDDashboard dashboard;

	public StatisticConfigViewImpl() {

		super(true);

		statisticList = new StatisticList();
		statisticDetails = new StatisticDetails(false);
		statisticItemList = new StatisticItemList();
		dashboard = new MDDashboard();
		statisticPanel = new StatisticPanel(true);

		swipperWidget.addSwipeItem("statisticList", statisticList, true);
		swipperWidget.addSwipeItem("statisticDetails", statisticDetails, false);
		swipperWidget.addSwipeItem("statisticItemList", statisticItemList, false);
		swipperWidget.addSwipeItem("dashboard", dashboard, false);
		swipperWidget.addSwipeItem("statisticItemPanel", statisticPanel, false);

	}

	@Override
	public StatisticListView getStatisticListView() {
		return statisticList;
	}

	@Override
	public StatisticDetailsView getStatisticDetail() {
		return statisticDetails;
	}

	@Override
	public StatisticItemListView getStatisticItemListView() {
		return statisticItemList;
	}

	@Override
	public MDDashboardView getMdDashboard() {
		return dashboard;
	}

	@Override
	public void showStatisticList() {
		swipperWidget.show("statisticList");
		configControlPanel.showControll(statisticList.getButtons());
	}

	@Override
	public void showStatisticDetail() {
		swipperWidget.swipeTo("statisticDetails");
		configControlPanel.showControll(statisticDetails.getButtonBar());
	}

	@Override
	public void showStatisticItemsList() {
		swipperWidget.swipeTo("statisticItemList");
		configControlPanel.showControll(statisticItemList.getButtons());
	}

	@Override
	public void showDashboard() {
		swipperWidget.swipeTo("dashboard");
		configControlPanel.clearControll();
	}

	@Override
	public void goBackToStatisticItemsList(MaterialWidget materialWidget) {
		swipperWidget.swipeBackTo("statisticList");
		configControlPanel.showControll(statisticList.getButtons());
	}

	@Override
	public void goBackToStatisticList(MaterialWidget materialWidget) {
		swipperWidget.swipeBackTo("statisticList");
		configControlPanel.showControll(statisticList.getButtons());
	}

	@Override
	public StatisticPanelView getStatisticItemPanelView() {
		return statisticPanel;
	}

	@Override
	public void showStatisticItemPanel() {
		swipperWidget.swipeTo("statisticItemPanel");
		configControlPanel.clearControll();
	}

}
