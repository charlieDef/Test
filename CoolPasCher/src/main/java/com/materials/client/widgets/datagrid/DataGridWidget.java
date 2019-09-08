package com.materials.client.widgets.datagrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.DefaultSelectionEventManager.SelectAction;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;

public class DataGridWidget<D> extends Composite {
	private static final GridTemplates TEMPLATES = GWT.create(GridTemplates.class);
	private final ListHandler<D> columnSortHandler = new ListHandler<>(Collections.emptyList());
	private ListDataProvider<D> singleDataProvider, multiDataProvider;
	private boolean multiModus = false;
	private Set<D> selectedItems;
	private D selectedItem;
	private SimplePager simplePager;
	private DataGrid<D> datagrid, multiDatagrid;
	private Consumer<D> selectionConsumer;

	@UiField
	FlowPanel singlePanelUi, multiPanelUi;

	@UiField
	MaterialCardAction cardFooterUi;

	@UiField
	MaterialRow toolsRowUi;

	@UiField
	MaterialSwitch selectionSwitchUi;

	@UiField
	MaterialCardTitle cardTitelUi;

	@UiField
	MaterialCardContent cardContentUi;

	public DataGridWidget(String titel, Function<D, Object> keyProvider) {
		initWidget(uiBinder.createAndBindUi(this));
		DatagridResources.INSTANCE.dataGridStyle().ensureInjected();

		cardTitelUi.setText(titel);
		simplePager = new SimplePager();

		buildSingle(keyProvider);

		buildMulti(keyProvider);

		cardFooterUi.add(simplePager);
	}

	public void setDatagridHeight(String height) {
		datagrid.setHeight(height);
	}

	private void buildSingle(Function<D, Object> keyProvider) {

		ProvidesKey<D> providesKey = new ProvidesKey<D>() {
			@Override
			public Object getKey(D item) {
				return keyProvider.apply(item);
			}
		};

		datagrid = new DataGrid<>(50, DatagridResources.INSTANCE);
		datagrid.setHeight("1000px");
		singlePanelUi.add(datagrid);

		List<D> items = new ArrayList<>();
		singleDataProvider = new ListDataProvider<D>(items, providesKey);
		singleDataProvider.addDataDisplay(datagrid);
		datagrid.setEmptyTableWidget(new MaterialLabel("Liste vide"));

		// Push data into the CellList.
		datagrid.setRowCount(singleDataProvider.getList().size(), true);
		datagrid.setRowData(0, singleDataProvider.getList());
		// datagrid.setPageSize(10);
		// datagrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		// cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		// // Add a selection model using the same keyProvider.

		SingleSelectionModel<D> singleSelectionModel = new SingleSelectionModel<D>(providesKey);
		singleSelectionModel.addSelectionChangeHandler(e -> {
			selectedItem = singleSelectionModel.getSelectedObject();
			if (selectionConsumer != null) {
				selectionConsumer.accept(selectedItem);
			}
		});
		datagrid.setSelectionModel(singleSelectionModel);
		datagrid.addColumnSortHandler(columnSortHandler);
		simplePager.setDisplay(datagrid);
	}

	private void buildMulti(Function<D, Object> keyProvider) {
		ProvidesKey<D> providesKey = new ProvidesKey<D>() {
			@Override
			public Object getKey(D item) {
				return keyProvider.apply(item);
			}
		};

		multiDatagrid = new DataGrid<>(30, DatagridResources.INSTANCE);
		multiDatagrid.setHeight("700px");
		multiPanelUi.add(multiDatagrid);

		List<D> items = new ArrayList<>();
		multiDataProvider = new ListDataProvider<D>(items, providesKey);
		multiDataProvider.addDataDisplay(multiDatagrid);
		datagrid.setEmptyTableWidget(new MaterialLabel("Liste vide"));

		// Push data into the CellList.
		multiDatagrid.setRowCount(multiDataProvider.getList().size(), true);
		multiDatagrid.setRowData(0, multiDataProvider.getList());
		// multiDatagrid.setPageSize(10);
		multiDatagrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		// cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		// // Add a selection model using the same keyProvider.

		MultiSelectionModel<D> multiSelectionModel = new MultiSelectionModel<D>();
		multiSelectionModel.addSelectionChangeHandler(e -> {
			selectedItems = multiSelectionModel.getSelectedSet();
		});
		multiDatagrid.setSelectionModel(multiSelectionModel,
				DefaultSelectionEventManager.<D>createCustomManager(new ToggleEventTranslator<D>()));

	}

	public void showLoader(boolean show) {
		MaterialLoader.loading(show, cardContentUi);
		if (show) {
			datagrid.setEmptyTableWidget(new MaterialLabel("Chargement en cour..."));
		} else if (!show && getDataProvider().getList().size() == 0) {
			datagrid.setEmptyTableWidget(new MaterialLabel("Liste vide"));
		}
	}

	public void setEmptyDataText(String text) {
		datagrid.setEmptyTableWidget(new MaterialLabel(text));
	}

	public final void addTextColumn(String header, final Function<D, String> valueExtractor, Comparator<D> comparator) {
		final Column<D, String> column = new Column<D, String>(new TextCell()) {
			@Override
			public String getValue(D object) {
				return valueExtractor.apply(object);
			}

			@Override
			public String getCellStyleNames(Context context, D object) {
				return super.getCellStyleNames(context, object) + " "
						+ DatagridResources.INSTANCE.dataGridStyle().dataGridTextBoxCell();
			}
		};
		if (comparator != null) {
			column.setSortable(true);
			columnSortHandler.setComparator(column, comparator);
		}
		datagrid.addColumn(column, header);
		multiDatagrid.addColumn(column, header);
	}

	public final void addIconColumn(Function<D, ImageResource> imageExtractor, Function<D, String> tooltipExtractor,
			Predicate<D> isVisible, Consumer<D> callback) {

		final Column<D, UIObjectHelper> column = new Column<D, UIObjectHelper>(new IconCell()) {
			@Override
			public UIObjectHelper getValue(D object) {
				ImageResource icon = imageExtractor.apply(object);
				String tooltip = tooltipExtractor.apply(object);
				return isVisible.test(object) ? new UIObjectHelper(tooltip, icon.getSafeUri().asString()) : null;
			}
		};
		datagrid.addColumn(column, "");
		multiDatagrid.addColumn(column, "");
		column.setFieldUpdater((index, object, value) -> callback.accept(object));
		column.setCellStyleNames(DatagridResources.INSTANCE.dataGridStyle().dataGridIconCell());
	}

	public final void addSubTopColumn(String header, final Function<D, String> valueExtractor, Comparator<D> comparator,
			Predicate<D> isTop, Function<D, Integer> level) {

		final Column<D, String> column = new Column<D, String>(new TextCell()) {
			@Override
			public String getValue(D object) {
				int menuLevel = level.apply(object);
				String textTitel = valueExtractor.apply(object);

				switch (menuLevel) {
				case 1:
					textTitel = "» " + textTitel;
					break;
				case 2:
					textTitel = "- " + textTitel;
					break;
				case 3:
					textTitel = "- " + textTitel;
					break;
				default:
					textTitel = ":: " + textTitel;
					break;
				}

				return textTitel;
			}

			@Override
			public String getCellStyleNames(Context context, D object) {
				int menuLevel = level.apply(object);
				String style = "";

				String TopStyle = DatagridResources.INSTANCE.dataGridStyle().dataGridTopCell();
				String subStyle = DatagridResources.INSTANCE.dataGridStyle().dataGridSubCell();

				// String style = isTop.test(object) ? TopStyle : subStyle;

				switch (menuLevel) {
				case 1:
					style = DatagridResources.INSTANCE.dataGridStyle().dataGridSubCell();
					break;
				case 2:
					style = DatagridResources.INSTANCE.dataGridStyle().dataGridSubCell2();
					break;
				case 3:
					style = DatagridResources.INSTANCE.dataGridStyle().dataGridSubCell3();
					break;
				default:
					style = DatagridResources.INSTANCE.dataGridStyle().dataGridTopCell();
					break;
				}

				return super.getCellStyleNames(context, object) + " " + style;
			}
		};
		if (comparator != null) {
			columnSortHandler.setComparator(column, comparator);
		}
		datagrid.addColumn(column, header);
		multiDatagrid.addColumn(column, header);
	}

	public final void addEmptyColumn() {

		final Column<D, String> column = new Column<D, String>(new TextCell()) {
			@Override
			public String getValue(D object) {
				return " ";
			}
		};
		datagrid.setColumnWidth(column, "25px");
		datagrid.addColumn(column, "");
		multiDatagrid.setColumnWidth(column, "25px");
		multiDatagrid.addColumn(column, "");
	}

	public final void addColumn(Column<D, ?> column, String header, Comparator<D> comparator, Integer widthInPx) {
		datagrid.addColumn(column, header);
		multiDatagrid.addColumn(column, header);
		if (widthInPx != null) {
			multiDatagrid.setColumnWidth(column, widthInPx.doubleValue(), Style.Unit.PX);
			datagrid.setColumnWidth(column, widthInPx.doubleValue(), Style.Unit.PX);
		}
	}

	public void setDataProvider(ListDataProvider<D> dataProvider) {
		dataProvider.addDataDisplay(datagrid);
		this.columnSortHandler.setList(dataProvider.getList());
	}

	private <T, V> Column<T, SafeHtml> getSafeHtmlColumn(final Function<T, V> valueExtractor,
			final SafeHtmlRenderer<V> renderer) {
		return new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(final T object) {
				return renderer.render(valueExtractor.apply(object));
			}
		};
	}

	interface GridTemplates extends SafeHtmlTemplates {
		@Template("<a href=\"javascript:;\">{0}</a>")
		SafeHtml toAnchor(String text);

		@Template("<img src='img/file-text.png'  title=\"{0}\"/>")
		SafeHtml toIconContent(String tooltip);

		@Template("<img src=\"{0}\"  title=\"{1}\"/>")
		SafeHtml toIcon(SafeUri safeUri, String tooltip);

	}

	private static class ActionCell<T> extends AbstractCell<T> {

		private final Function<T, SafeHtml> valueRenderer;

		private ActionCell(Function<T, SafeHtml> valueRenderer) {
			super(BrowserEvents.CLICK);
			this.valueRenderer = valueRenderer;
		}

		@Override
		public void render(final Context context, final T value, final SafeHtmlBuilder safeHtmlBuilder) {
			safeHtmlBuilder.append(value == null ? SafeHtmlUtils.EMPTY_SAFE_HTML : valueRenderer.apply(value));
		}

		@Override
		public void onBrowserEvent(final Context context, final Element parent, final T value, final NativeEvent event,
				final ValueUpdater<T> valueUpdater) {
			super.onBrowserEvent(context, parent, value, event, valueUpdater);

			if (value != null) {
				if (BrowserEvents.CLICK.equals(event.getType())) {
					EventTarget eventTarget = event.getEventTarget();
					if (!Element.is(eventTarget)) {
						return;
					}
					if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
						// Ignore clicks that occur outside of the main element.
						onEnterKeyDown(context, parent, value, event, valueUpdater);
					}
				}
			}
		}

		@Override
		protected void onEnterKeyDown(final Context context, final Element parent, final T value,
				final NativeEvent event, final ValueUpdater<T> valueUpdater) {
			if (value != null && valueUpdater != null) {
				valueUpdater.update(value);
			}
		}
	}

	private static class IconCell extends AbstractCell<UIObjectHelper> {

		private IconCell() {
			super(BrowserEvents.CLICK);

		}

		@Override
		public void render(final Context context, final UIObjectHelper value, final SafeHtmlBuilder safeHtmlBuilder) {
			safeHtmlBuilder.append(value == null ? SafeHtmlUtils.EMPTY_SAFE_HTML
					: TEMPLATES.toIcon(UriUtils.fromSafeConstant(value.getIconUrl()), value.getTitel()));
		}

		@Override
		public void onBrowserEvent(final Context context, final Element parent, final UIObjectHelper value,
				final NativeEvent event, final ValueUpdater<UIObjectHelper> valueUpdater) {
			super.onBrowserEvent(context, parent, value, event, valueUpdater);

			if (value != null) {
				if (BrowserEvents.CLICK.equals(event.getType())) {
					EventTarget eventTarget = event.getEventTarget();
					if (!Element.is(eventTarget)) {
						return;
					}
					if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
						// Ignore clicks that occur outside of the main element.
						onEnterKeyDown(context, parent, value, event, valueUpdater);
					}
				}
			}
		}

		@Override
		protected void onEnterKeyDown(final Context context, final Element parent, final UIObjectHelper value,
				final NativeEvent event, final ValueUpdater<UIObjectHelper> valueUpdater) {
			if (value != null && valueUpdater != null) {
				valueUpdater.update(value);
			}
		}

	}

	public void addItem(D d) {
		singleDataProvider.getList().add(d);
	}

	public void removeItem(D d) {
		List<D> list = new ArrayList<D>(singleDataProvider.getList());
		list.remove(d);
		singleDataProvider.setList(list);
		datagrid.redraw();
	}

	public void removeItems(Set<D> list) {

		List<D> listData = new ArrayList<D>(multiDataProvider.getList());
		list.forEach(d -> listData.remove(d));

		multiDataProvider.setList(listData);
		multiDatagrid.redraw();
	}

	public void removeSelected() {
		if (multiModus) {
			removeItems(selectedItems);
		} else {
			removeItem(selectedItem);

		}
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

	void showMultiGrid() {
		singlePanelUi.setVisible(false);
		multiPanelUi.setVisible(true);
		simplePager.setDisplay(multiDatagrid);
	}

	void showSingleGrid() {
		singlePanelUi.setVisible(true);
		multiPanelUi.setVisible(false);
		simplePager.setDisplay(datagrid);
	}

	public D getSelectedItem() {
		return selectedItem;
	}

	public Set<D> getSelectedItems() {
		return selectedItems;
	}

	public void selectItem(D d) {
		datagrid.getSelectionModel().setSelected(d, true);
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

	public void addIconButton(MaterialIcon materialIcon) {

		MaterialColumn materialColumn = new MaterialColumn();
		materialIcon.setWaves(WavesType.LIGHT);
		materialIcon.setIconFontSize(2.3, Unit.EM);
		materialIcon.setIconColor(Color.AMBER_DARKEN_1);
		// materialIcon.setMarginTop(-10);
		materialIcon.setFloat(Float.RIGHT);
		materialColumn.add(materialIcon);

		toolsRowUi.add(materialColumn);

	}

	@UiHandler("selectionSwitchUi")
	void onClick(ClickEvent event) {
		multiModus = selectionSwitchUi.getValue();
		// addIconUi.setVisible(!multiModus);

		if (!isMultiModus()) {
			showMultiGrid();
			multiDataProvider.setList(singleDataProvider.getList());
			SingleSelectionModel<D> selectionModel = (SingleSelectionModel<D>) datagrid.getSelectionModel();
			selectionModel.clear();

		} else {
			showSingleGrid();
			singleDataProvider.setList(multiDataProvider.getList());
			MultiSelectionModel<D> selectionModel = (MultiSelectionModel<D>) multiDatagrid.getSelectionModel();
			selectionModel.clear();
		}
	}

	public boolean listContains(D d) {
		return getData().contains(d);
	}

	public boolean isMultiModus() {
		return multiModus;
	}

	public void refresh() {
		getDataProvider().refresh();
	}

	public List<D> getData() {
		return getDataProvider().getList();
	}

	public void setSelectionConsumer(Consumer<D> selectionConsumer) {
		this.selectionConsumer = selectionConsumer;
	}

	private static DataGridWidgetUiBinder uiBinder = GWT.create(DataGridWidgetUiBinder.class);

	interface DataGridWidgetUiBinder extends UiBinder<MaterialCard, DataGridWidget> {
	}

	public void setMultiModus(boolean multiModus) {
		this.multiModus = multiModus;
		selectionSwitchUi.setEnabled(multiModus);
	}

}
