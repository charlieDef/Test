package com.materials.client.widgets.model.user.cellist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.UserSO;
import com.materials.client.widgets.base.BaseList;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.celllist.CellListRender;
import com.materials.client.widgets.celllist.CellPropertie;
import com.materials.client.widgets.celllist.MDCellList;

import gwt.material.design.client.constants.IconType;

public class UserList extends BaseList implements UserListView {

	private Presenter presenter;
	private MDCellList<UserSO> cellList;
	private static Consumer<Boolean> detailCallBack, userCommentCallBack, userAnnoncesCallBack;

	private ControlButton addIcon, deleteIcon;

	public UserList() {
		super();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		detailCallBack = x -> presenter.swipeToUserDetail(cellList.getSelectedItem());
		userCommentCallBack = x -> presenter.showUserComments(cellList.getSelectedItem());
		userAnnoncesCallBack = x -> presenter.showUserAnnonces(cellList.getSelectedItem());
		addIcon.addClickHandler(e -> presenter.newUser());

		deleteIcon.addClickHandler(c -> presenter.deleteUsers(getSelectedUsers()));
	}

	@Override
	public void setData(List<UserSO> userSOs) {

		userSOs.sort(new Comparator<UserSO>() {
			@Override
			public int compare(UserSO o1, UserSO o2) {
				return o2.getLastName().compareTo(o1.getLastName());
			}
		});

		cellList.setData(userSOs);
	}

	private CellListRender<UserSO> getRender() {
		CellListRender<UserSO> render = new CellListRender<UserSO>("userDetailsClick()", "userAnnoncesClick()");
		render.setCellProperties(new CellPropertie<UserSO>() {

			@Override
			public String getTitel(UserSO d) {
				return d.getLastName() + ", " + d.getName();
			}

			@Override
			public String getImageUrl(UserSO d) {
				return d.getUserImageUrl();
			}

			@Override
			public Long getId(UserSO d) {
				return d.getId();
			}

			@Override
			public String getDescription(UserSO d) {
				return "::" + d.getEmail();
			}

			@Override
			public String getDetailIconTooltip(UserSO d) {
				return "Voir les " + d.getAnnoncesNumber() + " annonces de " + d.getName();
			}

			@Override
			public String getToolsIcon(UserSO d) {
				return CoolPasCherUI.APP_RESOURCE.comments().getSafeUri().asString();
			}

			@Override
			public String getToolsIconTooltip(UserSO d) {
				return "Voir les commentaires de " + d.getName();
			}

			@Override
			public boolean addToolsClick(UserSO d) {
				return true;
			}

			@Override
			public String getImageBorderColor(UserSO d) {
				String fCaise = null;
				String fondDeCaisse = d.getUserProperties().get(UserSO.PREBAAL_FOND_CAISSE);
				if (fondDeCaisse != null && !fondDeCaisse.isEmpty()) {
					boolean isTravailleur = d.getStatus().equals(UserSO.STATUS_TRAVAILLEUR);
					Integer value = Integer.valueOf(fondDeCaisse);
					Integer endvalue1 = isTravailleur ? 80 : 60;
					Integer endvalue2 = isTravailleur ? 50 : 30;

					if (value >= endvalue1) {
						fCaise = "#50C878";// green
					} else if (value >= endvalue2) {
						fCaise = "#FBEC5D";// yellow
					} else if (value <= endvalue2) {
						fCaise = "#C41E3A";// red
					}
				}
				return fCaise;
			}
		});
		return render;
	}

	@Override
	public MDCellList<UserSO> getCellList() {
		return cellList;
	}

	private static void userAnnoncesClick() {
		new Timer() {
			@Override
			public void run() {
				userAnnoncesCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private static void userDetailsClick() {
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
				userCommentCallBack.accept(Boolean.TRUE);
			}
		}.schedule(50);
	}

	private List<UserSO> getSelectedUsers() {
		if (cellList.isMultiModus()) {
			return new ArrayList<UserSO>(cellList.getSelectedItems());
		} else {
			return Arrays.asList(cellList.getSelectedItem());
		}
	}

	private static native void exportCellListFunction()/*-{
		$wnd.userDetailsClick = @com.materials.client.widgets.model.user.cellist.UserList::userDetailsClick();
		$wnd.userAnnoncesClick = @com.materials.client.widgets.model.user.cellist.UserList::userAnnoncesClick();
		$wnd.contentToolsClick = @com.materials.client.widgets.model.user.cellist.UserList::contentToolsClick();
	}-*/;

	@Override
	protected void buildTable() {
		exportCellListFunction();
		getElement().getStyle().setMarginRight(10, Unit.PX);
		// getElement().getStyle().setMarginTop(-8, Unit.PX);
		cellList = new MDCellList<UserSO>("Users", new ArrayList<>(), getRender());
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
