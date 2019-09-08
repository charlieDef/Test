package com.materials.client.widgets.model.content.celllist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.shared.MethodsUtils;

import gwt.material.design.client.constants.IconType;

public class ContentList extends BaseList implements ContentListView {

	private Presenter presenter;
	private MDCellList<ContentSO> cellList;
	private static Consumer<Boolean> detailCallBack, contentToolsCallBack, contentAreasCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addButton, deleteButton;
	private boolean tools = false;
	private CellListRender<ContentSO> render;

	public ContentList() {
		super();
	}

	@Override
	public void setPresenter(Presenter presenter, Consumer<Boolean> backCallBack) {
		this.presenter = presenter;
		detailCallBack = x -> presenter.swipeToContentDetail(cellList.getSelectedItem(), false);
		contentAreasCallBack = x -> presenter.showContentAreaList(cellList.getSelectedItem());
		contentToolsCallBack = x -> presenter.showContentTools(cellList.getSelectedItem());

		if (backCallBack != null) {
			cellList.setBackButtonVisible(backCallBack);
		}

		// addIcon.addClickHandler(e -> presenter.newContent());
		// deleteIcon.addClickHandler(c ->
		// presenter.deleteContents(getSelectedContents()));
		addButton.addClickHandler(e -> presenter.newContent());
		deleteButton.addClickHandler(c -> presenter.deleteContents(getSelectedContents()));
	}

	@Override
	public void setData(List<ContentSO> contentSOs) {
		cellList.setData(contentSOs);
		if (contentSOs != null && !contentSOs.isEmpty()) {
			cellList.setMDCellListTitel("Contenus  " + MethodsUtils.getRecursiveMenu(contentSOs.get(0).getMenuSO()));
		}
	}

	private CellListRender<ContentSO> getRender() {

		this.render = new CellListRender<ContentSO>("contentDetailClick()", "contentAreasClick()");
		render.setCellProperties(new CellPropertie<ContentSO>() {

			@Override
			public String getTitel(ContentSO c) {
				String titel = c.getTitel();
				if (c.getMenuSO() != null) {
					titel = "::" + titel;
				}

				return titel;
			}

			@Override
			public String getImageUrl(ContentSO c) {
				String urlImage = c.getTitelImageUrl() != null ? c.getTitelImageUrl()
						: CoolPasCherUI.APP_RESOURCE.menuContent().getSafeUri().asString();
				return urlImage;
			}

			@Override
			public Long getId(ContentSO c) {
				return c.getId();
			}

			@Override
			public String getDescription(ContentSO d) {
				return d.getDescription();
			}

			@Override
			public String getDetailIconTooltip(ContentSO d) {
				return "Voir les images du contenu [" + d.getcAreaSOs().size() + "]";
			}

			@Override
			public boolean addToolsClick(ContentSO d) {
				return true;
			}
		});
		return render;
	}

	@Override
	public MDCellList<ContentSO> getCellList() {
		return cellList;
	}

	private static void contentDetailClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static void contentToolsClick() {
		new Timer() {
			@Override
			public void run() {
				contentToolsCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static void contentAreasClick() {
		new Timer() {
			@Override
			public void run() {
				contentAreasCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	public void setBackButtonVisible(Consumer<Boolean> consumer) {
		cellList.setBackButtonVisible(consumer);
	}

	private List<ContentSO> getSelectedContents() {
		if (cellList.isMultiModus()) {
			return new ArrayList<ContentSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static native void exportCellListFunction()/*-{
		$wnd.contentDetailClick = @com.materials.client.widgets.model.content.celllist.ContentList::contentDetailClick();
		$wnd.contentAreasClick = @com.materials.client.widgets.model.content.celllist.ContentList::contentAreasClick();
		$wnd.contentToolsClick = @com.materials.client.widgets.model.content.celllist.ContentList::contentToolsClick();
	}-*/;

	@Override
	public void setNewContentAble(boolean multiContents) {
		addButton.setVisible(multiContents);
		deleteButton.setVisible(multiContents);
		cellList.setMulti(multiContents);
	}

	@Override
	protected void buildTable() {
		exportCellListFunction();
		getElement().getStyle().setMarginRight(10, Unit.PX);
		cellList = new MDCellList<ContentSO>("Contents", new ArrayList<>(), getRender());
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
