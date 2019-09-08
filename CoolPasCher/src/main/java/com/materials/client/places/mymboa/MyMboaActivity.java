package com.materials.client.places.mymboa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.APPObjectSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonceView;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.content.ContentAction;
import com.materials.shared.action.content.ContentCommand;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

public class MyMboaActivity extends AbstractActivity implements MyMboaView.Presenter {

	private AppClientFactory clientFactory;
	private MyMboaPlace place;
	private MyMboaView view;

	private final ContentListPresenter2 contentListPresenter;
	private final EditAbleAnnoncePresenter2 editAbleAnnoncePresenter;

	public MyMboaActivity(AppClientFactory clientFactory, MyMboaPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;
		view = clientFactory.getMyMboaView();
		editAbleAnnoncePresenter = new EditAbleAnnoncePresenter2(view.getEditAbleAnnonceView());
		contentListPresenter = new ContentListPresenter2(view.getContentListView());
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		UserSO userSO = clientFactory.getActualUserSO();

		if (userSO != null) {
			panel.setWidget(view);
			contentListPresenter.initData();
		} else {

			MaterialPanel materialPanel = clientFactory.getEmptyStatePanel();
			panel.setWidget(materialPanel);
			ClientUtils.animeIn(materialPanel, Transition.FADEINLEFT);
		}

		clientFactory.scrollAppToTop(false);

	}

	class ContentListPresenter2 implements ContentListView.Presenter {

		private ContentListView contentListView;

		public ContentListPresenter2(ContentListView contentListView) {
			this.contentListView = contentListView;
			contentListView.setPresenter(this, null);

		}

		@Override
		public void swipeToContentDetail(ContentSO contentSO, boolean isNew) {

			view.getEditAbleAnnonceView().setContentSO(contentSO);
			view.showContentDetail();

		}

		@Override
		public void newContent() {
		}

		@Override
		public void deleteContents(List<ContentSO> contentSOs) {

			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new ContentAction(DBAction.DELETE, new ArrayList<APPObjectSO>(contentSOs)),
							x -> {
								if (x.getBooleanValue()) {
									contentListView.getCellList().removeSelected();
									UserSO userSO = clientFactory.getActualUserSO();
									for (ContentSO contentSO : contentSOs) {
										userSO.removeHashFragmentProperty(UserSO.ANNONCES, contentSO.getId() + "");
									}
									clientFactory.getStartView().getAppHeaderPanel().refreshHeader();
									clientFactory.getStartView().getAppNavBarPanel().refreshNavbar();
									clientFactory.getStartView().getAppFooterPanel().refreshFooter();
								}
							});
				}
			});
		}

		private void initData() {
			UserSO userSO = clientFactory.getActualUserSO();
			clientFactory.execute(new ContentAction(DBAction.READ, ContentCommand.ALL_ANNONCES_FOR_USER,
					String.valueOf(userSO.getId())), result -> {
						List<ContentSO> list = MethodsUtils.castList(result.getObjects(), ContentSO.class);

						if (list != null && !list.isEmpty()) {
							list = list.stream().filter(c -> show(c)).collect(Collectors.toList());
						}
						contentListView.setData(list);
						view.showContentList();
					});
		}

		private boolean show(ContentSO c) {
			return (c.getType().equals(ContentSO.TYPE_ARTICLE) || c.getType().equals(ContentSO.TYPE_H_ADVERTISEMENT)
					|| c.getType().equals(ContentSO.TYPE_V_ADVERTISEMENT)
					|| c.getType().equals(ContentSO.TYPE_ANNONCE));
		}

		@Override
		public void showContentTools(ContentSO contentSO) {

		}

		@Override
		public void backToMenuList() {

		}

		@Override
		public void showContentAreaList(ContentSO contentSO) {
		}

	}

	class EditAbleAnnoncePresenter2 implements EditAbleAnnonceView.Presenter {

		private EditAbleAnnonceView editAbleAnnonceView;

		public EditAbleAnnoncePresenter2(EditAbleAnnonceView editAbleAnnonceView) {
			this.editAbleAnnonceView = editAbleAnnonceView;
			this.editAbleAnnonceView.setPresenter(this);
			this.editAbleAnnonceView.setEdit(false);
		}

		@Override
		public void saveContentSO(ContentSO contentSO) {
			DBAction dbAction = contentSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;
			clientFactory.execute(new ContentAction(dbAction, contentSO), x -> {
				ContentSO saved = (ContentSO) x.getObject();
				// editAbleAnnonceView.setContentSO(saved);
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					contentSO.setId(saved.getId());
					view.getContentListView().getCellList().addItem(saved);
				}
				view.getContentListView().getCellList().refresh();
			});
		}

		@Override
		public void cancel() {
		}

		@Override
		public void goBack() {
			view.backToContentList();
			// ClientUtils.addTimer(x -> {
			// editAbleAnnonceView.getNewAnnonceView().setContent(new ContentSO());
			// editAbleAnnonceView.selectTab(MDTab.TAB_1);
			// editAbleAnnonceView.setEdit(false);
			// }, 500);
		}
	}

}
