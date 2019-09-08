package com.materials.client.widgets.model.content.celllist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.user.client.Timer;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.shared.MethodsUtils;

import gwt.material.design.client.constants.IconType;

public class ContentAreaList extends BaseList implements ContentAreaListView {

	private Presenter presenter;
	private MDCellList<CAreaSO> cellList;
	private static Consumer<Boolean> detailCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addHButton, addVButton, deleteButton;

	public ContentAreaList() {
		super();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		detailCallBack = x -> presenter.swipeToContentAreaDetail(cellList.getSelectedItem());
		cellList.setBackButtonVisible(x -> presenter.swipeBackToContentList());

		// addHButton.addClickHandler(e -> presenter.newContentArea(CAreaSO.TYPE_HOR));
		// addVButton.addClickHandler(e -> presenter.newContentArea(CAreaSO.TYPE_VER));
		deleteButton.addClickHandler(c -> presenter.deleteContentAreas(getSelectedContents()));
	}

	@Override
	public void setData(ContentSO contentSOs) {

		List<CAreaSO> areaSOs = contentSOs.getcAreaSOs();
		areaSOs.sort(new Comparator<CAreaSO>() {
			@Override
			public int compare(CAreaSO o1, CAreaSO o2) {
				return Integer.valueOf(o1.getIndex()).compareTo(Integer.valueOf(o2.getIndex()));
			}
		});

		cellList.setData(areaSOs);

		if (contentSOs != null && contentSOs.getMenuSO() != null) {
			cellList.setMDCellListTitel("ContentAreas::" + MethodsUtils.getRecursiveMenu(contentSOs));
		}

		// setAddAble(contentSOs.getType().equals(ContentSO.TYPE_ARTICLE)
		// || contentSOs.getType().equals(ContentSO.TYPE_MENU));

	}

	private CellListRender<CAreaSO> getRender() {
		CellListRender<CAreaSO> render = new CellListRender<CAreaSO>("contentAreaDetailClick()");
		render.setCellProperties(new CellPropertie<CAreaSO>() {

			@Override
			public String getTitel(CAreaSO d) {
				return d.getAreaType() + "::" + d.getTitel();
			}

			@Override
			public String getImageUrl(CAreaSO d) {
				// d.setImageUrl((Index.MODUL_BASE_FILEHELPER + "?caRandomID=" +
				// d.getRandomId()));
				return d.getImageUrl();
			}

			@Override
			public Long getId(CAreaSO d) {
				return d.getId();
			}

			@Override
			public String getDescription(CAreaSO d) {
				return String.valueOf(d.getIndex());
			}
		});
		return render;
	}

	private void setAddAble(boolean addAble) {
		addHButton.setVisible(addAble);
		addVButton.setVisible(addAble);

	}

	@Override
	public MDCellList<CAreaSO> getCellList() {
		return cellList;
	}

	private List<CAreaSO> getSelectedContents() {
		if (cellList.isMultiModus()) {
			return new ArrayList<CAreaSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static void contentAreaDetailClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static native void exportCellListFunction()/*-{
		$wnd.contentAreaDetailClick = @com.materials.client.widgets.model.content.celllist.ContentAreaList::contentAreaDetailClick();
	}-*/;

	@Override
	protected void buildTable() {
		exportCellListFunction();
		cellList = new MDCellList<CAreaSO>("ContentAreas", new ArrayList<>(), getRender());
		add(cellList);

	}

	@Override
	public void iniIcons() {

		addHButton = new ControlButton(IconType.SWAP_HORIZ);
		addVButton = new ControlButton(IconType.SWAP_VERT);
		deleteButton = new ControlButton(IconType.DELETE);
		// buttons.add(addHButton);
		// buttons.add(addVButton);
		buttons.add(deleteButton);

	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}
}