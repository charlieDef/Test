package com.materials.client.widgets.model.user.comments.celllist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.materials.client.model.CommentSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.shared.APPConstants;

import gwt.material.design.client.constants.IconType;

public class CommentList extends BaseList implements CommentListView {

	private Presenter presenter;
	private MDCellList<CommentSO> cellList;
	private static Consumer<Boolean> detailCallBack, commentContentCallBack;
	private ConfirmationWidget confirmationWidgetDelete;
	private ControlButton addIcon, deleteIcon;

	public CommentList() {
		super();

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		cellList.setBackButtonVisible(x -> presenter.swipeBackToUserList());
		detailCallBack = x -> presenter.swipeToCommentDetail(cellList.getSelectedItem());
		addIcon.addClickHandler(e -> presenter.newComment());

		deleteIcon.addClickHandler(c -> presenter.deleteComments(getSelectedComments()));

	}

	@Override
	public void setData(List<CommentSO> commentSOs) {

		commentSOs.sort(new Comparator<CommentSO>() {
			@Override
			public int compare(CommentSO o1, CommentSO o2) {
				return Long.valueOf(o2.getId()).compareTo(Long.valueOf(o1.getId()));
			}
		});

		cellList.setData(commentSOs);
	}

	private CellListRender<CommentSO> getRender() {
		CellListRender<CommentSO> render = new CellListRender<CommentSO>("commentDetailsClick()", null);
		render.setCellProperties(new CellPropertie<CommentSO>() {

			@Override
			public String getTitel(CommentSO c) {
				String titel = "::NO CONTENT";
				if (c.getCommentTitel() != null) {
					titel = "::" + c.getCommentTitel();
				}
				return titel;
			}

			@Override
			public String getImageUrl(CommentSO c) {
				return "img/Comments.png";
			}

			@Override
			public Long getId(CommentSO c) {
				return c.getId();
			}

			@Override
			public String getDescription(CommentSO c) {
				DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);
				return ttipFormat.format(c.getCreationDate());
			}
		});
		return render;
	}

	@Override
	public MDCellList<CommentSO> getCellList() {
		return cellList;
	}

	private static void commentDetailsClick() {
		new Timer() {
			@Override
			public void run() {
				detailCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static void commentContentsClick() {
		new Timer() {
			@Override
			public void run() {
				commentContentCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private List<CommentSO> getSelectedComments() {
		if (cellList.isMultiModus()) {
			return new ArrayList<CommentSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static native void exportCellListFunction()/*-{
		$wnd.commentDetailsClick = @com.materials.client.widgets.model.user.comments.celllist.CommentList::commentDetailsClick();
		$wnd.commentContentsClick = @com.materials.client.widgets.model.user.comments.celllist.CommentList::commentContentsClick();
	}-*/;

	@Override
	public void setReadOnly(boolean readOnly) {
		deleteIcon.setVisible(!readOnly);
		addIcon.setVisible(!readOnly);
	}

	@Override
	protected void buildTable() {
		exportCellListFunction();
		getElement().getStyle().setMarginRight(10, Unit.PX);
		cellList = new MDCellList<CommentSO>("Comments", new ArrayList<>(), getRender());
		add(cellList);

	}

	@Override
	public void iniIcons() {

		addIcon = new ControlButton(IconType.ADD);
		deleteIcon = new ControlButton(IconType.DELETE);
		buttons.add(addIcon);
		buttons.add(deleteIcon);
	}

	@Override
	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}
}
