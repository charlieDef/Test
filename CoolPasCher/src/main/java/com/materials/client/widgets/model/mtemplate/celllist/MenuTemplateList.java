package com.materials.client.widgets.model.mtemplate.celllist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.materials.client.model.MenuTemplateSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;

import gwt.material.design.client.constants.IconType;

public class MenuTemplateList extends BaseList implements MenuTemplateListView {

	private Presenter presenter;
	private MDCellList<MenuTemplateSO> cellList;
	private static Consumer<Boolean> detailCallBack, listCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addOneColButton, addTwoColButton, deleteButton;

	public MenuTemplateList() {
		super();

	}

	@Override
	public void setPresenter(Presenter presenter, Consumer<Boolean> backCallBack) {
		this.presenter = presenter;
		detailCallBack = x -> presenter.showTemplateDetail(cellList.getSelectedItem());
		listCallBack = x -> presenter.swipeBackToMenuList();
		addOneColButton.addClickHandler(e -> presenter.newTemplate(1));
		addTwoColButton.addClickHandler(e -> presenter.newTemplate(2));
		if (backCallBack != null) {
			cellList.setBackButtonVisible(backCallBack);
		}

		deleteButton.addClickHandler(c -> presenter.deleteTemplate(getSelectedMTemplate()));

	}

	@Override
	public void setData(List<MenuTemplateSO> sSocialSOs) {
		cellList.setData(sSocialSOs);
		handleAddIconVisibility();
	}

	private CellListRender<MenuTemplateSO> getRender() {
		CellListRender<MenuTemplateSO> render = new CellListRender<MenuTemplateSO>("mTemplateDetailClick()", null);
		render.setCellProperties(new CellPropertie<MenuTemplateSO>() {

			@Override
			public String getTitel(MenuTemplateSO d) {
				return "::" + d.getTemplateLinks().toString();
			}

			@Override
			public String getImageUrl(MenuTemplateSO d) {
				return d.getImgUrl();
			}

			@Override
			public Long getId(MenuTemplateSO d) {
				return d.getId();
			}

			@Override
			public String getDescription(MenuTemplateSO d) {
				return "";
			}

			@Override
			public String getDetailIconTooltip(MenuTemplateSO d) {
				return "Details du Template " + d.getId();
			}

		});
		return render;
	}

	@Override
	public MDCellList<MenuTemplateSO> getCellList() {
		return cellList;
	}

	private List<MenuTemplateSO> getSelectedMTemplate() {
		if (cellList.isMultiModus()) {
			return new ArrayList<MenuTemplateSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static void mTemplateDetailClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static native void exportCellListFunction()/*-{
		$wnd.mTemplateDetailClick = @com.materials.client.widgets.model.mtemplate.celllist.MenuTemplateList::mTemplateDetailClick();
	}-*/;

	private int getColNr() {
		int i = 0;
		if (!getCellList().getData().isEmpty()) {

			i = getCellList().getData().get(0).getColNr();
		}
		return i;
	}

	@Override
	public void handleAddIconVisibility() {
		addOneColButton.setVisible(getColNr() == 1 || getColNr() == 0);
		addTwoColButton.setVisible(getColNr() == 2 || getColNr() == 0);

	}

	@Override
	protected void buildTable() {
		exportCellListFunction();
		getElement().getStyle().setMarginRight(10, Unit.PX);
		cellList = new MDCellList<MenuTemplateSO>("MenuTemplate", new ArrayList<>(), getRender());
		add(cellList);

		cellList.setMulti(false);

	}

	@Override
	public void iniIcons() {
		addOneColButton = new ControlButton(IconType.LOOKS_ONE);
		addOneColButton.setTitle("nouveau MenuLayout: 1 Column");
		addTwoColButton = new ControlButton(IconType.LOOKS_TWO);
		addTwoColButton.setTitle("nouveau MenuLayout: 2 Column");
		deleteButton = new ControlButton(IconType.DELETE);
		buttons.add(addOneColButton);
		buttons.add(addTwoColButton);
		buttons.add(deleteButton);
	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}
}
