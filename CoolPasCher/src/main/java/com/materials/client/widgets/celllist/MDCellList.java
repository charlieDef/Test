package com.materials.client.widgets.celllist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.DefaultSelectionEventManager.SelectAction;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;
import com.materials.client.widgets.celllist.page.RangeLabelPager;
import com.materials.client.widgets.celllist.page.ShowMorePagerPanel;
import com.materials.client.widgets.celllist.resources.CellListResources;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;

public class MDCellList<D> extends Composite {

	private boolean multiModus = false;
	private Set<D> selectedItems;
	private D selectedItem;
	private CellList<D> cellListSingle, cellListMulti;
	private CellListRender<D> cellListRender;
	private ListDataProvider<D> singleDataProvider, multiDataProvider;

	@UiField
	MaterialCard materalCardUi;

	@UiField
	MaterialCardTitle cardTitelUi;

	@UiField
	MaterialCardAction listPagerUi;

	@UiField
	RangeLabelPager singleRangeLabelPagerUi, multiRangeLabelPagerUi;

	@UiField
	ShowMorePagerPanel singlePagerPanelUi, multiPagerPanelUi;

	@UiField
	MaterialIcon backIconUi;

	@UiField
	MaterialRow toolsRowUi;

	@UiField
	MaterialSwitch selectionSwitchUi;

	public MDCellList(String titel, List<D> items, CellListRender<D> cellRender) {

		this.cellListRender = cellRender;
		initWidget(uiBinder.createAndBindUi(this));
		cardTitelUi.setText(titel);

		buildSingleCellListe(items);

		buildMultiCellListe(items);

	}

	void buildSingleCellListe(List<D> items) {
		MDCellKeyProvider keyProvider = new MDCellKeyProvider();
		singleDataProvider = new ListDataProvider<D>(items, keyProvider);

		// CellList custom UI resource
		CellList.Resources cellResources = GWT.create(CellListResources.class);

		// Create a CellList using the keyProvider.
		cellListSingle = new CellList<D>(cellListRender, cellResources, keyProvider);
		singleDataProvider.addDataDisplay(cellListSingle);
		cellListSingle.setEmptyListWidget(new MaterialLabel("Liste vide"));

		// Push data into the CellList.
		cellListSingle.setRowCount(singleDataProvider.getList().size(), true);
		cellListSingle.setRowData(0, singleDataProvider.getList());
		cellListSingle.setPageSize(10);
		cellListSingle.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		// cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		// // Add a selection model using the same keyProvider.

		SingleSelectionModel<D> singleSelectionModel = new SingleSelectionModel<D>(keyProvider);
		singleSelectionModel.addSelectionChangeHandler(e -> {
			selectedItem = singleSelectionModel.getSelectedObject();
		});
		cellListSingle.setSelectionModel(singleSelectionModel);

		singlePagerPanelUi.setDisplay(cellListSingle);
		singleRangeLabelPagerUi.setDisplay(cellListSingle);
	}

	void buildMultiCellListe(List<D> items) {
		MDCellKeyProvider keyProvider = new MDCellKeyProvider();
		multiDataProvider = new ListDataProvider<D>(items, keyProvider);

		// CellList custom UI resource
		CellList.Resources cellResources = GWT.create(CellListResources.class);

		// Create a CellList using the keyProvider.
		cellListMulti = new CellList<D>(cellListRender, cellResources, keyProvider);
		multiDataProvider.addDataDisplay(cellListMulti);
		cellListMulti.setEmptyListWidget(new MaterialLabel("Liste vide"));

		// Push data into the CellList.
		cellListMulti.setRowCount(singleDataProvider.getList().size(), true);
		cellListMulti.setRowData(0, singleDataProvider.getList());
		cellListMulti.setPageSize(10);
		cellListMulti.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		// cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		// // Add a selection model using the same keyProvider.

		MultiSelectionModel<D> multiSelectionModel = new MultiSelectionModel<D>();
		multiSelectionModel.addSelectionChangeHandler(e -> {
			selectedItems = multiSelectionModel.getSelectedSet();
			// if (presenter != null) {
			// // presenter.selectItem(selectedItem);
			// }
		});
		cellListMulti.setSelectionModel(multiSelectionModel,
				DefaultSelectionEventManager.<D>createCustomManager(new ToggleEventTranslator<D>()));

		multiPagerPanelUi.setDisplay(cellListMulti);
		multiRangeLabelPagerUi.setDisplay(cellListMulti);
	}

	@UiHandler("selectionSwitchUi")
	void onClick(ClickEvent event) {
		multiModus = selectionSwitchUi.getValue();
		// addIconUi.setVisible(!multiModus);

		if (!multiModus) {
			showMultiCell();
			multiDataProvider.setList(singleDataProvider.getList());
			SingleSelectionModel<D> selectionModel = (SingleSelectionModel<D>) getSingleCellList().getSelectionModel();
			selectionModel.clear();

		} else {
			showSingleCell();
			singleDataProvider.setList(multiDataProvider.getList());
			MultiSelectionModel<D> selectionModel = (MultiSelectionModel<D>) getMultiCellList().getSelectionModel();
			selectionModel.clear();
		}
	}

	public void removeItem(D d) {
		List<D> list = new ArrayList<D>(singleDataProvider.getList());
		list.remove(d);
		singleDataProvider.setList(list);
		cellListSingle.redraw();
	}

	public void removeSelected() {
		if (multiModus) {
			removeItems(selectedItems);
		} else {
			removeItem(selectedItem);

		}
	}

	public void removeItems(Set<D> list) {

		List<D> listData = new ArrayList<D>(multiDataProvider.getList());
		list.forEach(d -> listData.remove(d));

		multiDataProvider.setList(listData);
		cellListMulti.redraw();
	}

	public void addItem(D d) {
		singleDataProvider.getList().add(d);
	}

	public void setData(List<D> list) {
		getDataProvider().setList(list);
	}

	public ListDataProvider<D> getDataProvider() {
		if (multiModus) {
			return multiDataProvider;
		} else {
			return singleDataProvider;
		}
	}

	private static MDCellListUiBinder uiBinder = GWT.create(MDCellListUiBinder.class);

	interface MDCellListUiBinder extends UiBinder<Widget, MDCellList> {
	}

	class MDCellKeyProvider implements ProvidesKey<D> {
		@Override
		public Object getKey(D item) {
			// Always do a null check.
			if (cellListRender != null && cellListRender.getCellProperties() != null) {
				return (cellListRender == null) ? null : cellListRender.getCellProperties().getId(item);
			}
			return null;
		}
	}

	public void setMDCellListTitel(String titel) {
		cardTitelUi.setText(titel);
	}

	public void addWidgetToFooter(Widget widget) {
		listPagerUi.add(widget);
	}

	class ToggleEventTranslator<T> implements DefaultSelectionEventManager.EventTranslator<T> {
		@Override
		public boolean clearCurrentSelection(final CellPreviewEvent<T> event) {
			return false;
		}

		@Override
		public SelectAction translateSelectionEvent(final CellPreviewEvent<T> event) {
			return SelectAction.TOGGLE;
		}

	}

	private CellList<D> getSingleCellList() {
		return cellListSingle;
	}

	private CellList<D> getMultiCellList() {
		return cellListMulti;
	}

	void showMultiCell() {

		singlePagerPanelUi.setVisible(false);
		singleRangeLabelPagerUi.setVisible(false);
		multiPagerPanelUi.setVisible(true);
		multiRangeLabelPagerUi.setVisible(true);

	}

	void showSingleCell() {

		singlePagerPanelUi.setVisible(true);
		singleRangeLabelPagerUi.setVisible(true);
		multiPagerPanelUi.setVisible(false);
		multiRangeLabelPagerUi.setVisible(false);
	}

	public void refresh() {
		getDataProvider().refresh();
	}

	public List<D> getData() {
		return getDataProvider().getList();
	}

	public void setBackButtonVisible(Consumer<Boolean> consumer) {
		backIconUi.setVisible(true);
		backIconUi.addClickHandler(x -> consumer.accept(Boolean.TRUE));
	}

	public D getSelectedItem() {
		return selectedItem;
	}

	public Set<D> getSelectedItems() {
		return selectedItems;
	}

	public void setMulti(boolean multi) {
		selectionSwitchUi.setEnabled(multi);
	}

	public void addIconButton(MaterialIcon materialIcon) {

		MaterialColumn materialColumn = new MaterialColumn();
		materialIcon.setWaves(WavesType.LIGHT);
		materialIcon.setIconFontSize(2.3, Unit.EM);
		materialIcon.setIconColor(Color.AMBER_DARKEN_1);
		materialIcon.setMarginTop(-10);
		materialIcon.setFloat(Float.RIGHT);
		materialColumn.add(materialIcon);

		toolsRowUi.add(materialColumn);

	}

	public boolean isMultiModus() {
		return multiModus;
	}

	public void setListHeight(String height) {

	}

}
