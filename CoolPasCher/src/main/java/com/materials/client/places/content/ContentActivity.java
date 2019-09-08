package com.materials.client.places.content;

import java.util.Date;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.CommentSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.client.widgets.articles.annonce.simple.SimpleViewAbleAnnonceDetails;
import com.materials.client.widgets.articles.annonce.simple.SimpleViewAbleAnnonceDetailsView;
import com.materials.client.widgets.notallowed.EmptyStatePanel;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.GeneralAction;
import com.materials.shared.action.GeneralCommand;
import com.materials.shared.action.comment.CommentAction;
import com.materials.shared.action.user.UserAction;

/**
 * 
 * For Article Activity
 * 
 * @author Charles Mouafo Deffo
 *
 */
public class ContentActivity extends AbstractActivity implements ContentView.Presenter {

	private AppClientFactory clientFactory;
	private ContentPlace place;
	private ContentView view;
	private final AnnonceDetailsPresenter annonceDetailsPresenter;

	public ContentActivity(AppClientFactory clientFactory, ContentPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;
		view = clientFactory.getContentView();
		view.setPresenter(this);
		annonceDetailsPresenter = new AnnonceDetailsPresenter();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		// panel.setWidget(materialPanel);

		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();

		clientFactory.getContentForArticle(place.getArticleClicked(), contentSO -> {

			if (contentSO.isIntern()) {
				if (userSO != null && userSO.isPrebaalMember()) {
					loadContent(panel, contentSO);
				} else {
					panel.setWidget(new EmptyStatePanel());
				}

			} else {
				loadContent(panel, contentSO);
			}

		});

		CoolPasCherUI.CLIENT_FACTORY.getScrollHelper().scrollTo(0);
	}

	private void loadContent(AcceptsOneWidget panel, ContentSO contentSO) {
		clientFactory.getAppMetaData().updateMetaForArticle(contentSO);

		switch (contentSO.getType()) {
		case ContentSO.TYPE_IMAGE:
			view.setContentImages(contentSO);
			break;
		case ContentSO.TYPE_DOCUMENT:
			Window.alert("download document");
			break;
		case ContentSO.TYPE_VIDEO:
			Window.alert("shows video");
			break;
		case ContentSO.TYPE_ANNONCE:
			annonceDetailsPresenter.showAnnonce(contentSO);

			break;

		default: {// article
			view.showContentAreas(contentSO);
			updateViewCount(contentSO);
		}
			break;
		}
		panel.setWidget(view);
	}

	public void updateViewCount(ContentSO contentSO) {
		clientFactory.execute(new GeneralAction(null, GeneralCommand.INCREMENT_CONTENT_VIEW, contentSO), result -> {
			ContentSO saved = (ContentSO) result.getObject();
			contentSO.setViewed(saved.getViewed());
		});
	}

	// @Override
	// public void saveNewAnnonce(ContentSO contentSO) {
	// DBAction dbAction = contentSO.getId() == -10 ? DBAction.SAVE_NEW :
	// DBAction.UPDATE;
	// clientFactory.execute(new ContentAction(dbAction, contentSO), x -> {
	// ContentSO saved = (ContentSO) x.getObject();
	// clientFactory.addNewArticle(saved);
	//
	// clientFactory.getPlaceController().goTo(new MenuPlace().handleMenu("start",
	// MenuSO.ACCEUIL));
	// });
	//
	// }

	public class AnnonceDetailsPresenter implements SimpleViewAbleAnnonceDetailsView.Presenter {

		private ContentSO contentSO;
		private SimpleViewAbleAnnonceDetailsView annonceDetailsView;

		public AnnonceDetailsPresenter() {
		}

		@Override
		public void saveNewQuestion(String firstName, String question) {

			CommentSO commentSO = new CommentSO();
			commentSO.setCreationDate(new Date());
			commentSO.setPublisherLastName(firstName);
			commentSO.setCommentTitel(question);
			commentSO.setText("------- Réponse en attente ------");
			commentSO.setActive(true);
			commentSO.setContentSO(contentSO);
			commentSO.setLock(false);
			commentSO.setTitelContent(contentSO.getTitel());
			commentSO.setRating(1);

			clientFactory.execute(new CommentAction(DBAction.SAVE_NEW, commentSO), result -> {
				CommentSO saved = (CommentSO) result.getObject();
				annonceDetailsView.getQuestionReponseView().addNewQuestion(saved);
			});
		}

		@Override
		public void sendMail(String email, String message) {
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().setWidgetContent("Message envoyé !!",
					new HTML("Votre Message à été envoyé avec succèss à l'annonceur. <br>Ce dernier vous contactera"));
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show();
		}

		@Override
		public void respondToQuestion(Long questionId, String response) {

		}

		private void showAnnonce(ContentSO contentSO) {
			this.contentSO = contentSO;
			annonceDetailsView = new SimpleViewAbleAnnonceDetails();
			annonceDetailsView.setContentSO(contentSO);
			// annonceDetailsView.loadSlider();
			annonceDetailsView.setPresenter(this);
			view.showAnnonceDetails(annonceDetailsView);
		}

		// public void setContentSO(ContentSO contentSO) {
		// this.contentSO = contentSO;
		// annonceDetailsView.setContentSO(contentSO);
		// }

		@Override
		public void addToFavorits(Long annonceId) {
			clientFactory.getActualUserSO().addHashFragmentProperty(UserSO.FAVORITS, String.valueOf(annonceId));
			clientFactory.execute(new UserAction(DBAction.UPDATE, clientFactory.getActualUserSO()), x -> {
				UserSO userSO = (UserSO) x.getObject();
				clientFactory.setUserSO(userSO);
				clientFactory.getStartView().getAppHeaderPanel().refreshHeader();
				clientFactory.getStartView().getAppNavBarPanel().refreshNavbar();
				clientFactory.getStartView().getAppFooterPanel().refreshFooter();
			});
		}

		@Override
		public void removeToFavorits(Long annonceId) {
			clientFactory.getActualUserSO().removeHashFragmentProperty(UserSO.FAVORITS, String.valueOf(annonceId));
			clientFactory.execute(new UserAction(DBAction.UPDATE, clientFactory.getActualUserSO()), x -> {
				UserSO userSO = (UserSO) x.getObject();
				clientFactory.setUserSO(userSO);
				clientFactory.getStartView().getAppHeaderPanel().refreshHeader();
				clientFactory.getStartView().getAppNavBarPanel().refreshNavbar();
				clientFactory.getStartView().getAppFooterPanel().refreshFooter();
			});
		}

		@Override
		public void saveResponse(CommentSO commentSO) {
			clientFactory.execute(new CommentAction(DBAction.UPDATE, commentSO), result -> {
				CommentSO saved = (CommentSO) result.getObject();
				annonceDetailsView.getQuestionReponseView().addResponse(saved.getText());
			});

		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	// @Override
	// public void onStop() {
	// super.onStop();
	//
	// // view.getAnnonceDetailsView().clearSliderContainer();
	//
	// Place toPlace = clientFactory.getPlaceController().getWhere();
	// // if (toPlace instanceof MenuPlace) {
	// // MenuPlace menuPlace = (MenuPlace) toPlace;
	// // if (menuPlace.getMenuClicked().equals("home")) {
	// // final int scroll = menuPlace.isHome() ? 0 : place.getSrollValue();
	// // clientFactory.scrollToPosition(scroll);
	// // }
	// // }
	// }
}
