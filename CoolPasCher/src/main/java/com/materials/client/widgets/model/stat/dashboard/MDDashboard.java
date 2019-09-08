package com.materials.client.widgets.model.stat.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.options.ChartArea;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.materials.client.model.stat.StatItemSO;
import com.materials.client.model.stat.StatisticSO;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class MDDashboard extends MaterialPanel implements MDDashboardView {

	private static MDDashboardUiBinder uiBinder = GWT.create(MDDashboardUiBinder.class);

	interface MDDashboardUiBinder extends UiBinder<MaterialRow, MDDashboard> {
	}

	private Presenter presenter;

	@UiField
	MaterialIcon backButtonUi;
	@UiField
	MaterialRow detailContentUi;

	public MDDashboard() {
		add(uiBinder.createAndBindUi(this));
		backButtonUi.addClickHandler(x -> {
			presenter.swipeBackToStatisticList();
		});
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setStatisticSO(StatisticSO statisticSO) {
		// TODO Auto-generated method stub

		List<StatItemSO> itemSOs = statisticSO.getStatItemSOs();

		// List<String> legends =
		// itemSOs.stream().map(StatItemSO::getChoice).collect(Collectors.toList());

		initialize3DChart2(itemSOs);
	}

	private void initialize3DChart2(List<StatItemSO> itemSOs) {
		detailContentUi.clear();

		MaterialColumn column = new MaterialColumn();
		// detailContentUi.add(column);

		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				PieChart chart = new PieChart();

				detailContentUi.add(chart);
				DataTable dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Task");
				dataTable.addColumn(ColumnType.NUMBER, "Hours per Day");
				dataTable.addRows(itemSOs.size());

				List<String> temp = new ArrayList<String>();

				for (int i = 0; i < itemSOs.size(); i++) {

					StatItemSO itemSO = itemSOs.get(i);
					String choice = itemSO.getChoice();

					if (!temp.contains(choice)) {
						dataTable.setValue(i, 0, choice);
						dataTable.setValue(i, 1, itemSOs.stream().filter(x -> x.getChoice().equals(choice)).count());
						temp.add(choice);
					}
				}

				PieChartOptions opt = PieChartOptions.create();
				opt.setFontSize(12);
				opt.setColors("008080", "FF5A36", "77DD77", "5D8AA8");
				opt.setIs3D(true);
				// opt.setPieSliceText(pieSliceText);;
				// opt.setPieSliceText(PieSliceText.LABEL);

				Legend legend = Legend.create(LegendPosition.TOP);
				opt.setLegend(legend);
				opt.setWidth(300);
				opt.setHeight(270);

				ChartArea area = ChartArea.create();
				area.setTop(50);
				// area.setLeft(0);
				area.setHeight(600);
				area.setWidth(400);
				opt.setChartArea(area);

				chart.draw(dataTable, opt);
			}
		});
	}

	private void initialize3DChart() {
		detailContentUi.clear();

		MaterialColumn column = new MaterialColumn();
		// detailContentUi.add(column);

		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				PieChart chart = new PieChart();

				// chart.setWidth("100%");
				// chart.setHeight("100%");
				detailContentUi.add(chart);
				DataTable dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Task");
				dataTable.addColumn(ColumnType.NUMBER, "Hours per Day");
				dataTable.addRows(4);
				dataTable.setValue(0, 0, "Work");
				dataTable.setValue(1, 0, "Sleep");
				dataTable.setValue(2, 0, "Watch TV");
				dataTable.setValue(3, 0, "Eat");

				dataTable.setValue(0, 1, 11);
				dataTable.setValue(1, 1, 7);
				dataTable.setValue(2, 1, 5);
				dataTable.setValue(3, 1, 15);

				PieChartOptions opt = PieChartOptions.create();
				opt.setFontSize(12);
				opt.setColors("008080", "FF5A36", "77DD77", "5D8AA8");
				opt.setIs3D(true);

				Legend legend = Legend.create(LegendPosition.TOP);
				opt.setLegend(legend);
				opt.setWidth(280);
				opt.setHeight(270);

				ChartArea area = ChartArea.create();
				area.setTop(50);
				// area.setLeft(0);
				area.setHeight(400);
				area.setWidth(400);
				opt.setChartArea(area);

				chart.draw(dataTable, opt);
			}
		});
	}

}
