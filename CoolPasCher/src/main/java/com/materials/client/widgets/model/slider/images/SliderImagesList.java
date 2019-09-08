package com.materials.client.widgets.model.slider.images;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.user.client.Timer;
import com.materials.client.model.ByteDataSO;
import com.materials.client.model.SliderSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;

import gwt.material.design.client.constants.IconType;

public class SliderImagesList extends BaseList implements SliderImagesListView {

	private Presenter presenter;
	private MDCellList<ByteDataSO> cellList;
	private static Consumer<Boolean> detailCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addButton, deleteButton;

	public SliderImagesList() {
		super();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		detailCallBack = x -> presenter.swipeToSliderImageDetail(cellList.getSelectedItem());
		cellList.setBackButtonVisible(x -> presenter.swipeBackToSliderList());

		confirmationWidgetDelete = new ConfirmationWidget("Vraiment supprimer ?", confirm -> {
			if (confirm) {
				presenter.deleteSliderImages(getSelectedContents());
			}
		});
		confirmationWidgetDelete.getElement().getStyle().setProperty("maxWidth", "400px");
		add(confirmationWidgetDelete);

		deleteButton.addClickHandler(c -> confirmationWidgetDelete.show());
		addButton.addClickHandler(e -> presenter.newSliderImage());
	}

	@Override
	public void setData(SliderSO sliderSO) {
		cellList.setData(sliderSO.getByteDataSOs());
	}

	private CellListRender<ByteDataSO> getRender() {
		CellListRender<ByteDataSO> render = new CellListRender<ByteDataSO>("sliderImageDetailClick()");
		render.setCellProperties(new CellPropertie<ByteDataSO>() {

			@Override
			public String getTitel(ByteDataSO d) {
				return d.getRandomId();
			}

			@Override
			public String getImageUrl(ByteDataSO d) {
				return d.getImageUrl();
			}

			@Override
			public Long getId(ByteDataSO d) {
				return d.getId();
			}

			@Override
			public String getDescription(ByteDataSO d) {
				return String.valueOf(d.getIndex());
			}
		});
		return render;
	}

	@Override
	public MDCellList<ByteDataSO> getCellList() {
		return cellList;
	}

	private List<ByteDataSO> getSelectedContents() {
		if (cellList.isMultiModus()) {
			return new ArrayList<ByteDataSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static void sliderImageDetailClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static native void exportCellListFunction()/*-{
		$wnd.sliderImageDetailClick = @com.materials.client.widgets.model.slider.images.SliderImagesList::sliderImageDetailClick();
	}-*/;

	@Override
	protected void buildTable() {
		exportCellListFunction();
		cellList = new MDCellList<ByteDataSO>("SliderImages", new ArrayList<>(), getRender());
		add(cellList);
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
