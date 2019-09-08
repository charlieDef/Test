package com.materials.client.widgets.model.slider.celllist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.materials.client.model.SliderSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;

import gwt.material.design.client.constants.IconType;

public class SliderList extends BaseList implements SliderListView {

	private Presenter presenter;
	private MDCellList<SliderSO> cellList;
	private static Consumer<Boolean> detailCallBack, sliderContentCallBack, sliderPreviewCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addButton, addButtonPresentation, deleteButton;

	public SliderList() {
		super();

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

		detailCallBack = x -> presenter.swipeToSliderDetail(cellList.getSelectedItem());
		sliderContentCallBack = x -> presenter.showSliderContent(cellList.getSelectedItem());
		sliderPreviewCallBack = x -> presenter.showSliderPreview(cellList.getSelectedItem());
		addButton.addClickHandler(e -> presenter.newSlider(false));
		addButtonPresentation.addClickHandler(e -> presenter.newSlider(true));

		deleteButton.addClickHandler(c -> presenter.deleteSliders(getSelectedSliders()));
	}

	@Override
	public void setData(List<SliderSO> sliderSOs) {

		Collections.sort(sliderSOs, Comparator.comparing(SliderSO::getIndex));

		cellList.setData(sliderSOs);
	}

	private CellListRender<SliderSO> getRender() {
		CellListRender<SliderSO> render = new CellListRender<SliderSO>("sliderDetailsClick()", "sliderContentClick()");
		render.setCellProperties(new CellPropertie<SliderSO>() {

			@Override
			public String getTitel(SliderSO d) {
				String text = d.isPresentation() ? " [P]" : " [N]";
				return "::" + d.getTitel() + text;
			}

			@Override
			public String getImageUrl(SliderSO d) {
				return d.getSliderImageUrl();
			}

			@Override
			public Long getId(SliderSO d) {
				return d.getId();
			}

			@Override
			public String getDetailIconTooltip(SliderSO d) {
				return "Voir les images de la presentation";
			}

			@Override
			public String getDescription(SliderSO d) {
				return d.getTitel1() + ", " + d.getTitel2();
			}

			@Override
			public boolean addContentsClick(SliderSO d) {
				return d.isPresentation();
			}

			@Override
			public boolean addToolsClick(SliderSO d) {
				return true;
			}

			@Override
			public String getToolsIconTooltip(SliderSO d) {
				return "Apercu du slider";
			}

		});
		return render;
	}

	@Override
	public MDCellList<SliderSO> getCellList() {
		return cellList;
	}

	private static void sliderPreviewClick() {
		new Timer() {
			@Override
			public void run() {
				sliderPreviewCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static void sliderDetailsClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static void sliderContentClick() {
		new Timer() {
			@Override
			public void run() {
				sliderContentCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private List<SliderSO> getSelectedSliders() {
		if (cellList.isMultiModus()) {
			return new ArrayList<SliderSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static native void exportCellListFunction()/*-{
		$wnd.sliderDetailsClick = @com.materials.client.widgets.model.slider.celllist.SliderList::sliderDetailsClick();
		$wnd.sliderContentClick = @com.materials.client.widgets.model.slider.celllist.SliderList::sliderContentClick();
		$wnd.contentToolsClick = @com.materials.client.widgets.model.slider.celllist.SliderList::sliderPreviewClick();
	}-*/;

	@Override
	protected void buildTable() {
		exportCellListFunction();
		getElement().getStyle().setMarginRight(10, Unit.PX);
		cellList = new MDCellList<SliderSO>("Sliders", new ArrayList<>(), getRender());
		add(cellList);
		cellList.setMulti(false);
	}

	@Override
	public void iniIcons() {

		addButton = new ControlButton(IconType.NOTE_ADD);
		addButtonPresentation = new ControlButton(IconType.BURST_MODE);
		deleteButton = new ControlButton(IconType.DELETE);

		addButton.setTitle("Nouveau slider normal");
		addButtonPresentation.setTitle("Nouveau slider presentation");
		deleteButton.setTitle("Supprimer le slider");

		buttons.add(addButton);
		buttons.add(addButtonPresentation);
		buttons.add(deleteButton);
	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}

}
