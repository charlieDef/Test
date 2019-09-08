package com.materials.client.widgets.model.rsocial.celllist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.materials.client.model.RSocialSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;

import gwt.material.design.client.constants.IconType;

public class RSocialList extends BaseList implements RSociauxListView {

	private Presenter presenter;
	private MDCellList<RSocialSO> cellList;
	private static Consumer<Boolean> detailCallBack, listCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addButton, deleteButton;

	public RSocialList() {
		super();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		detailCallBack = x -> presenter.swipeToRSocialDetail(cellList.getSelectedItem());
		listCallBack = x -> presenter.showRSocialList();
		addButton.addClickHandler(e -> presenter.newRSocial());
		deleteButton.addClickHandler(c -> presenter.deleteRSocial(getSelectedRSocial()));
	}

	@Override
	public void setData(List<RSocialSO> sSocialSOs) {
		cellList.setData(sSocialSOs);
	}

	private CellListRender<RSocialSO> getRender() {
		CellListRender<RSocialSO> render = new CellListRender<RSocialSO>("rSocialDetailClick()", null);
		render.setCellProperties(new CellPropertie<RSocialSO>() {

			@Override
			public String getTitel(RSocialSO d) {
				return "::" + d.getName();
			}

			@Override
			public String getImageUrl(RSocialSO d) {
				return d.getHtmlImg();
			}

			@Override
			public Long getId(RSocialSO d) {
				return d.getId();
			}

			@Override
			public String getDescription(RSocialSO d) {
				return "Quick Message::" + d.getTooltip();
			}

			@Override
			public String getDetailIconTooltip(RSocialSO d) {
				return "Details du reseau social " + d.getName();
			}

		});
		return render;
	}

	@Override
	public MDCellList<RSocialSO> getCellList() {
		return cellList;
	}

	private List<RSocialSO> getSelectedRSocial() {
		if (cellList.isMultiModus()) {
			return new ArrayList<RSocialSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static void rSocialDetailClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static native void exportCellListFunction()/*-{
		$wnd.rSocialDetailClick = @com.materials.client.widgets.model.rsocial.celllist.RSocialList::rSocialDetailClick();
	}-*/;

	@Override
	public void setReadOnly(boolean readOnly) {
		addButton.setVisible(!readOnly);
		deleteButton.setVisible(!readOnly);

	}

	@Override
	protected void buildTable() {
		exportCellListFunction();
		getElement().getStyle().setMarginRight(10, Unit.PX);
		cellList = new MDCellList<RSocialSO>("RSociaux", new ArrayList<>(), getRender());
		add(cellList);

		cellList.setMulti(false);

	}

	@Override
	public void iniIcons() {
		addButton = new ControlButton(IconType.ADD);
		deleteButton = new ControlButton(IconType.DELETE);
		addButton.setTitle("Nouveau reseau social");
		deleteButton.setTitle("Supprimer reseau social");
		buttons.add(addButton);
		buttons.add(deleteButton);
	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}
}
