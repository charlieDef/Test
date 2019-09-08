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
import com.materials.client.model.stat.StatItemSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;

import gwt.material.design.client.constants.IconType;

public class StatisticItemList extends BaseList implements StatisticItemListView {

	private Presenter presenter;
	private MDCellList<StatItemSO> cellList;
	private static Consumer<Boolean> detailCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addIcon, addIconPresentation, deleteIcon;
	private StatisticSO statisticSO;

	public StatisticItemList() {
		super();

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		cellList.setBackButtonVisible(x -> presenter.swipeBackToStatisticList());
		detailCallBack = x -> presenter.swipeToStatisticItemDetail(cellList.getSelectedItem());
		addIcon.addClickHandler(e -> presenter.newStatisticItem());

		deleteIcon.addClickHandler(c -> presenter.deleteStatisticItems(getSelectedStatistic()));
	}

	@Override
	public void setData(StatisticSO statItemSOs) {
		this.statisticSO = statItemSOs;

		List<StatItemSO> itemSOs = statItemSOs.getStatItemSOs();

		Collections.sort(itemSOs, Comparator.comparing(StatItemSO::getCreationDate));

		cellList.setData(itemSOs);
	}

	private CellListRender<StatItemSO> getRender() {
		CellListRender<StatItemSO> render = new CellListRender<StatItemSO>("statisticItemDetailsClick()", null);
		render.setCellProperties(new CellPropertie<StatItemSO>() {

			@Override
			public String getTitel(StatItemSO d) {

				return d.getCreatorEmail() + "::" + d.getChoice();
			}

			@Override
			public String getImageUrl(StatItemSO d) {
				return CoolPasCherUI.APP_RESOURCE.pieChart().getSafeUri().asString();
			}

			@Override
			public Long getId(StatItemSO d) {
				return d.getId();
			}

			@Override
			public String getDescription(StatItemSO d) {
				return d.getMessage();
			}
		});
		return render;
	}

	@Override
	public MDCellList<StatItemSO> getCellList() {
		return cellList;
	}

	private static void statisticItemDetailsClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private List<StatItemSO> getSelectedStatistic() {
		if (cellList.isMultiModus()) {
			return new ArrayList<StatItemSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static native void exportCellListFunction()/*-{
		$wnd.statisticItemDetailsClick = @com.materials.client.widgets.model.stat.cellist.StatisticItemList::statisticItemDetailsClick();
	}-*/;

	@Override
	protected void buildTable() {
		exportCellListFunction();
		getElement().getStyle().setMarginRight(10, Unit.PX);
		cellList = new MDCellList<StatItemSO>("StatisticItems", new ArrayList<>(), getRender());
		add(cellList);
		cellList.setMulti(false);
	}

	@Override
	public StatisticSO getStatisticSO() {
		return statisticSO;
	}

	@Override
	public void iniIcons() {

		addIcon = new ControlButton(IconType.ADD);
		deleteIcon = new ControlButton(IconType.DELETE);
		buttons.add(addIcon);
		buttons.add(deleteIcon);
		addIcon.setEnabled(false);
		deleteIcon.setEnabled(false);
	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}

}
