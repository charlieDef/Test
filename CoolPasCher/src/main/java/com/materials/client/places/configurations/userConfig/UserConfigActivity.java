package com.materials.client.places.configurations.userConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.APPObjectSO;
import com.materials.client.model.CommentSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonceView;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.user.UserDetailsView;
import com.materials.client.widgets.model.user.cellist.UserListView;
import com.materials.client.widgets.model.user.comments.celllist.CommentListView;
import com.materials.client.widgets.model.user.comments.details.CommentDetailView;
import com.materials.client.widgets.tab.MDTab;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.comment.CommentAction;
import com.materials.shared.action.comment.CommentCommand;
import com.materials.shared.action.content.ContentAction;
import com.materials.shared.action.content.ContentCommand;
import com.materials.shared.action.user.UserAction;
import com.materials.shared.action.user.UserCommand;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

/**
 * 
 * For Configuration Activity
 * 
 * @author Charles Mouafo Deffo
 *
 */
public class UserConfigActivity extends AbstractActivity implements UserConfigView.Presenter {

	private AppClientFactory clientFactory;
	private UserConfigPlace place;
	private UserConfigView view;

	private final UserListPresenter userListPresenter;
	private final UserDetailPresenter userDetailPresenter;

	private final CommentDetailPresenter commentDetailPresenter;
	private final CommentListPresenter commentListPresenter;

	private final ContentListPresenter contentListPresenter;
	private final EditAbleAnnoncePresenter editAbleAnnoncePresenter;

	public UserConfigActivity(AppClientFactory clientFactory, UserConfigPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;
		view = clientFactory.getUserConfigView();
		userListPresenter = new UserListPresenter(view.getUserListView());
		userDetailPresenter = new UserDetailPresenter(view.getUserDetailsView());
		commentDetailPresenter = new CommentDetailPresenter(view.getCommentDetailView());
		commentListPresenter = new CommentListPresenter(view.getCommentListView());
		editAbleAnnoncePresenter = new EditAbleAnnoncePresenter(view.getEditAbleAnnonceView());
		contentListPresenter = new ContentListPresenter(view.getContentListView());
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		UserSO userSO = clientFactory.getActualUserSO();

		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			panel.setWidget(view);
			userListPresenter.initData();
		} else {
			MaterialPanel materialPanel = clientFactory.getEmptyStatePanel();
			panel.setWidget(materialPanel);
			ClientUtils.animeIn(materialPanel, Transition.FADEINLEFT);
		}

		clientFactory.scrollAppToTop(false);
	}

	class UserDetailPresenter implements UserDetailsView.Presenter {

		private UserDetailsView userDetailsView;

		public UserDetailPresenter(UserDetailsView userDetailsView) {
			this.userDetailsView = userDetailsView;
			this.userDetailsView.setPresenter(this);
		}

		@Override
		public void backToUserList() {
			view.goBackToUserList((MaterialWidget) userDetailsView);
		}

		@Override
		public void saveUser(UserSO userSO) {
			DBAction dbAction = userSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new UserAction(dbAction, userSO), x -> {

				UserSO saved = (UserSO) x.getObject();

				if (dbAction.equals(DBAction.SAVE_NEW)) {
					view.getUserListView().getCellList().addItem(saved);
				}
				view.getUserListView().getCellList().refresh();
			});
		}
	}

	class UserListPresenter implements UserListView.Presenter {

		private UserListView userListView;

		public UserListPresenter(UserListView userListView) {
			this.userListView = userListView;
			this.userListView.setPresenter(this);
		}

		@Override
		public void showUserComments(UserSO userSO) {
			commentListPresenter.initData(userSO);
			view.showCommentList();
		}

		@Override
		public void swipeToUserDetail(UserSO userSO) {
			view.getUserDetailsView().setUserSO(userSO);
			view.showUserDetail();
		}

		@Override
		public void newUser() {
			UserSO ctent = new UserSO();
			ctent.setId(-10);
			ctent.setCreation(new Date());
			ctent.setActive(false);
			ctent.setLock(false);
			// ctent.setUserImageUrl("img/newUser.jpg");

			swipeToUserDetail(ctent);
			view.getUserDetailsView().setEdit();
		}

		@Override
		public void deleteUsers(List<UserSO> userSOs) {

			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new UserAction(DBAction.DELETE, new ArrayList<APPObjectSO>(userSOs)), x -> {
						if (x.getBooleanValue()) {
							userListView.getCellList().removeSelected();
						}
					});
				}
			});
		}

		private void initData() {
			clientFactory.execute(new UserAction(DBAction.READ, UserCommand.ALL_USERS, null), result -> {
				List<UserSO> list = MethodsUtils.castList(result.getObjects(), UserSO.class);
				userListView.setData(list);
				view.showUserList();
			});
		}

		@Override
		public void showUserAnnonces(UserSO userSO) {
			contentListPresenter.initData(userSO);
			view.showUserAnnonceList();

		}

	}

	class CommentDetailPresenter implements CommentDetailView.Presenter {

		private CommentDetailView commentDetailView;

		public CommentDetailPresenter(CommentDetailView commentDetailView) {
			this.commentDetailView = commentDetailView;
			this.commentDetailView.setPresenter(this);
		}

		@Override
		public void backToCommentList() {
			view.goBackToCommentList((MaterialWidget) commentDetailView);
		}

		@Override
		public void saveComment(CommentSO commentSO) {

			DBAction dbAction = commentSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new CommentAction(dbAction, commentSO), x -> {

				CommentSO saved = (CommentSO) x.getObject();

				if (dbAction.equals(DBAction.SAVE_NEW)) {
					view.getCommentListView().getCellList().addItem(saved);
				}
				view.getCommentListView().getCellList().refresh();
			});

		}

	}

	class CommentListPresenter implements CommentListView.Presenter {

		private CommentListView commentListView;
		private UserSO userSO;

		public CommentListPresenter(CommentListView commentListView) {
			this.commentListView = commentListView;
			this.commentListView.setPresenter(this);
		}

		@Override
		public void swipeToCommentDetail(CommentSO commentSO) {
			view.getCommentDetailView().setCommentSO(commentSO);
			view.showCommentDetail();
		}

		@Override
		public void newComment() {
			CommentSO comment = new CommentSO();
			comment.setId(-10);
			comment.setCreationDate(new Date());
			comment.setActive(true);
			comment.setLock(false);
			comment.setPublisherLastName(userSO.getLastName());
			comment.setPublisherEmail(userSO.getEmail());
			comment.setPublisherRandomId(userSO.getRandomId());
			swipeToCommentDetail(comment);
			view.getCommentDetailView().setEdit();
		}

		@Override
		public void deleteComments(List<CommentSO> commentSOs) {
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new CommentAction(DBAction.DELETE, new ArrayList<APPObjectSO>(commentSOs)),
							x -> {
								if (x.getBooleanValue())
									commentListView.getCellList().removeSelected();
							});
				}
			});
		}

		private void initData(UserSO userSO) {
			this.userSO = userSO;
			clientFactory.execute(new CommentAction(DBAction.READ, CommentCommand.BY_USER_EMAIL, userSO.getEmail()),
					result -> {
						List<CommentSO> list = MethodsUtils.castList(result.getObjects(), CommentSO.class);
						commentListView.setData(list);
						commentListView.getCellList().setMDCellListTitel("[ Commentaires | " + userSO.getName() + " ]");
					});
		}

		@Override
		public void swipeBackToUserList() {
			view.goBackToUserList((MaterialWidget) commentListView);

		}

	}

	class EditAbleAnnoncePresenter implements EditAbleAnnonceView.Presenter {

		private EditAbleAnnonceView editAbleAnnonceView;

		public EditAbleAnnoncePresenter(EditAbleAnnonceView editAbleAnnonceView) {
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
			view.goBackToUserAnnonceList();
			ClientUtils.addTimer(x -> {
				editAbleAnnonceView.getNewAnnonceView().setContent(new ContentSO());
				editAbleAnnonceView.selectTab(MDTab.TAB_1);
				editAbleAnnonceView.setEdit(false);
			}, 500);
		}
	}

	class ContentListPresenter implements ContentListView.Presenter {

		private ContentListView contentListView;

		public ContentListPresenter(ContentListView contentListView) {
			this.contentListView = contentListView;
			contentListView.setPresenter(this, x -> view.goBackToUserList((MaterialWidget) contentListView));

		}

		@Override
		public void swipeToContentDetail(ContentSO contentSO, boolean isNew) {

			view.getEditAbleAnnonceView().setContentSO(contentSO);
			view.showUserAnnonceDetail();
		}

		@Override
		public void newContent() {

		}

		@Override
		public void deleteContents(List<ContentSO> contentSO) {

			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new ContentAction(DBAction.DELETE, new ArrayList<APPObjectSO>(contentSO)),
							x -> {
								if (x.getBooleanValue()) {
									contentListView.getCellList().removeSelected();
								}
							});
				}
			});
		}

		private void initData(UserSO userSo) {
			clientFactory.execute(new ContentAction(DBAction.READ, ContentCommand.ALL_ANNONCES_FOR_USER,
					String.valueOf(userSo.getId())), result -> {
						List<ContentSO> list = MethodsUtils.castList(result.getObjects(), ContentSO.class);

						if (list != null && !list.isEmpty()) {
							list = list.stream().filter(c -> show(c)).collect(Collectors.toList());
						}
						contentListView.setData(list);
						contentListView.getCellList().setMDCellListTitel("[ Annonces | " + userSo.getName() + " ]");
						view.showUserAnnonceList();
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
			// contentAreaListPresenter.initData(contentSO);
			// view.showContentAreaList();
		}

	}
}
