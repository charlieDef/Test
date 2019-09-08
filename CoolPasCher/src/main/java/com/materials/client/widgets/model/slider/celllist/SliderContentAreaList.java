package com.materials.client.widgets.model.slider.celllist;

import java.util.ArrayList;
import java.util.Arrays;
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

public class SliderContentAreaList extends BaseList implements SliderContentAreaListView {

	private Presenter presenter;
	private MDCellList<CAreaSO> cellList;
	private static Consumer<Boolean> detailCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addHButton, addVButton, deleteButton;

	public SliderContentAreaList() {
		super();

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		detailCallBack = x -> presenter.swipeToSliderContentAreaDetail(cellList.getSelectedItem());
		cellList.setBackButtonVisible(x -> presenter.swipeBackToSliderList());

		addHButton.addClickHandler(e -> presenter.newSliderContentArea(CAreaSO.TYPE_HOR));
		addVButton.addClickHandler(e -> presenter.newSliderContentArea(CAreaSO.TYPE_VER));

		confirmationWidgetDelete = new ConfirmationWidget("Vraiment supprimer ?", confirm -> {
			if (confirm) {
				presenter.deleteSliderContentAreas(getSelectedContents());
			}
		});
		confirmationWidgetDelete.getElement().getStyle().setProperty("maxWidth", "400px");
		add(confirmationWidgetDelete);

		deleteButton.addClickHandler(c -> confirmationWidgetDelete.show());
	}

	@Override
	public void setData(ContentSO contentSOs) {
		cellList.setData(contentSOs.getcAreaSOs());

		if (contentSOs != null && contentSOs.getMenuSO() != null) {
			cellList.setMDCellListTitel("SliderContentAreas::" + MethodsUtils.getRecursiveMenu(contentSOs));
		}

		setAddAble(contentSOs.getType().equals(ContentSO.TYPE_ARTICLE)
				|| contentSOs.getType().equals(ContentSO.TYPE_MENU));

	}

	private CellListRender<CAreaSO> getRender() {
		CellListRender<CAreaSO> render = new CellListRender<CAreaSO>("sliderContentAreaDetailClick()");
		render.setCellProperties(new CellPropertie<CAreaSO>() {

			@Override
			public String getTitel(CAreaSO d) {
				return d.getAreaType() + "::" + d.getTitel();
			}

			@Override
			public String getImageUrl(CAreaSO d) {
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

	private static void sliderContentAreaDetailClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static native void exportCellListFunction()/*-{
		$wnd.contentAreaDetailClick = @com.materials.client.widgets.model.slider.celllist.SliderContentAreaList::sliderContentAreaDetailClick();
	}-*/;

	@Override
	protected void buildTable() {
		exportCellListFunction();
		cellList = new MDCellList<CAreaSO>("SliderContentAreas", new ArrayList<>(), getRender());
		add(cellList);

	}

	@Override
	public void iniIcons() {

		addHButton = new ControlButton(IconType.SWAP_HORIZ);
		addVButton = new ControlButton(IconType.SWAP_VERT);
		deleteButton = new ControlButton(IconType.DELETE);

		addHButton.setTitle("Nouveau contenu horizontal");
		addVButton.setTitle("Nouveau contenu vertical");
		deleteButton.setTitle("Supprimer le contenu");

		buttons.add(addHButton);
		buttons.add(addVButton);
		buttons.add(deleteButton);
	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}

}