package com.materials.client.widgets.model.stat.cellist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;

import gwt.material.design.client.constants.IconType;

public class StatisticList extends BaseList implements StatisticListView {

	private Presenter presenter;
	private MDCellList<StatisticSO> cellList;
	private static Consumer<Boolean> detailCallBack, statisticContentCallBack, dashboardCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addIcon, deleteIcon;

	public StatisticList() {
		super();

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

		detailCallBack = x -> presenter.swipeToStatisticDetail(cellList.getSelectedItem());
		statisticContentCallBack = x -> presenter.showStatisticContent(cellList.getSelectedItem());
		dashboardCallBack = x -> presenter.swipeToDashboard(cellList.getSelectedItem());
		addIcon.addClickHandler(e -> presenter.newStatistic());
		deleteIcon.addClickHandler(c -> presenter.deleteStatistics(getSelectedStatistic()));
	}

	@Override
	public void setData(List<StatisticSO> statisticSos) {

		Collections.sort(statisticSos, Comparator.comparing(StatisticSO::getCreationDate));
		cellList.setData(statisticSos);
	}

	private CellListRender<StatisticSO> getRender() {
		CellListRender<StatisticSO> render = new CellListRender<StatisticSO>("statisticDetailsClick()",
				"statisticContentClick()");
		render.setCellProperties(new CellPropertie<StatisticSO>() {

			@Override
			public String getTitel(StatisticSO d) {

				return "::" + d.getTitel();
			}

			@Override
			public String getImageUrl(StatisticSO d) {
				return d.getImageUrl();
			}

			@Override
			public Long getId(StatisticSO d) {
				return d.getId();
			}

			@Override
			public String getDescription(StatisticSO d) {
				return d.getStatisticChoices().toString();
			}

			@Override
			public String getDetailIconTooltip(StatisticSO d) {
				return "Voir les differentes statistics";
			}

			@Override
			public String getDetailIcon(StatisticSO d) {
				return CoolPasCherUI.APP_RESOURCE.grafBar().getSafeUri().asString();
			}

			@Override
			public String getToolsIcon(StatisticSO d) {
				return CoolPasCherUI.APP_RESOURCE.statistic().getSafeUri().asString();
			}

			@Override
			public String getToolsIconTooltip(StatisticSO d) {
				return "Afficher la vue statistique";
			}

			@Override
			public boolean addToolsClick(StatisticSO d) {
				return true;
			}
		});
		return render;
	}

	@Override
	public MDCellList<StatisticSO> getCellList() {
		return cellList;
	}

	private static void contentToolsClick() {
		new Timer() {
			@Override
			public void run() {
				dashboardCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static void statisticDetailsClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static void statisticContentClick() {
		new Timer() {
			@Override
			public void run() {
				statisticContentCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private List<StatisticSO> getSelectedStatistic() {
		if (cellList.isMultiModus()) {
			return new ArrayList<StatisticSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static native void exportCellListFunction()/*-{
		$wnd.statisticDetailsClick = @com.materials.client.widgets.model.stat.cellist.StatisticList::statisticDetailsClick();
		$wnd.statisticContentClick = @com.materials.client.widgets.model.stat.cellist.StatisticList::statisticContentClick();
		$wnd.contentToolsClick = @com.materials.client.widgets.model.stat.cellist.StatisticList::contentToolsClick();
	}-*/;

	@Override
	protected void buildTable() {
		exportCellListFunction();
		getElement().getStyle().setMarginRight(10, Unit.PX);
		cellList = new MDCellList<StatisticSO>("Statistics", new ArrayList<>(), getRender());
		add(cellList);
		cellList.setMulti(false);
	}

	@Override
	public void iniIcons() {

		addIcon = new ControlButton(IconType.ADD);
		deleteIcon = new ControlButton(IconType.DELETE);
		buttons.add(addIcon);
		buttons.add(deleteIcon);

		if (!CoolPasCherUI.checkAdminMember()) {
			addIcon.setEnabled(false);
			deleteIcon.setEnabled(false);
		}
	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}
}
