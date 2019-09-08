package com.materials.client.widgets.model.menu.celllist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.client.widgets.datagrid.DataGridWidget;

import gwt.material.design.client.constants.IconType;

public class MenuList extends BaseList implements MenuListView {

	private Presenter presenter;
	private DataGridWidget<MenuSO> cellList;
	private static Consumer<Boolean> detailCallBack, menuContentCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addButton, deleteButton;

	public MenuList() {
		// exportCellListFunction()
		super();
		// getElement().getStyle().setMarginRight(10, Unit.PX);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		detailCallBack = x -> presenter.swipeToMenuDetail(cellList.getSelectedItem());
		addButton.addClickHandler(e -> presenter.newMenu());
		deleteButton.addClickHandler(c -> presenter.deleteMenus(getSelectedMenus()));
	}

	@Override
	public void setData(List<MenuSO> contentSOs) {
		cellList.setData(
				contentSOs.stream().filter(m -> m.getCategory().equals("BaseMenu")).collect(Collectors.toList()));
	}

	private CellListRender<MenuSO> getRender() {
		CellListRender<MenuSO> render = new CellListRender<MenuSO>("menuDetailsClick()", "menuContentsClick()");
		render.setCellProperties(new CellPropertie<MenuSO>() {

			@Override
			public String getTitel(MenuSO d) {
				return "::" + d.getName();
			}

			@Override
			public String getImageUrl(MenuSO d) {
				return "img/menu.jpg";
			}

			@Override
			public Long getId(MenuSO d) {
				return d.getId();
			}

			@Override
			public String getDescription(MenuSO d) {
				return "Category::" + d.getCategory() != null ? d.getCategory() : "--//--";
			}

			@Override
			public boolean addToolsClick(MenuSO d) {
				return d.getCategory().equals("Photo");
			}

		});
		return render;
	}

	@Override
	public DataGridWidget<MenuSO> getCellList() {
		return cellList;
	}

	private List<MenuSO> getSelectedMenus() {
		if (cellList.isMultiModus()) {
			return new ArrayList<MenuSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	@Override
	protected void buildTable() {
		cellList = new DataGridWidget<MenuSO>("Menus", MenuSO::getId);

		cellList.addSubTopColumn("Tous les Menus", MenuSO::getName, null, x -> x.getMenuLevel() == 0,
				MenuSO::getMenuLevel);

		cellList.addIconColumn(m -> CoolPasCherUI.APP_RESOURCE.details(), m -> "Menu details pour " + m.getName(),
				x -> true, x -> presenter.swipeToMenuDetail(x));

		cellList.addIconColumn(m -> CoolPasCherUI.APP_RESOURCE.config(),
				m -> "Layout Configuration pour Menu " + m.getName(), s -> (!s.isMenuCategory() && s.isBaseMenu()),
				x -> {
					presenter.showTemplateLayoutList(x);
				});

		// cellList.addIconColumn(m -> MBoaOnlineUI.APP_RESOURCE.content(),
		// m -> "Voir les Contenus du menu : " + m.getName(), x -> x.getParentMenuSo()
		// != null,
		// x -> presenter.showContentList(x));

		cellList.addEmptyColumn();

		add(cellList);
		cellList.setDatagridHeight("1000px");
		cellList.setMultiModus(false);
	}

	@Override
	public void iniIcons() {

		addButton = new ControlButton(IconType.ADD);
		deleteButton = new ControlButton(IconType.DELETE);
		buttons.add(addButton);
		buttons.add(deleteButton);
	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}
}
