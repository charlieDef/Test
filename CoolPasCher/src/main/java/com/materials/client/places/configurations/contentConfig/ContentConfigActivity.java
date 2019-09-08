package com.materials.client.places.configurations.contentConfig;

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
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.articles.annonce.simple.SimpleAnnonceDetailView;
import com.materials.client.widgets.model.content.celllist.ContentAreaListView;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.content.pic.ContentPicturesView;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.carea.CAreaAction;
import com.materials.shared.action.content.ContentAction;
import com.materials.shared.action.content.ContentCommand;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

/**
 * 
 * For Configuration Activity
 * 
 * @author Charles Mouafo Deffo
 *
 */
public class ContentConfigActivity extends AbstractActivity implements ContentConfigView.Presenter {

	private AppClientFactory clientFactory;
	private ContentConfigPlace place;
	private ContentConfigView view;

	private final ContentListPresenter contentListPresenter;
	private final SimpleAnnoncePresenter simpleAnnoncePresenter;
	private final ContentAreaListPresenter contentAreaListPresenter;
	private final CPicturesPresenter picturesPresenter;

	public ContentConfigActivity(AppClientFactory clientFactory, ContentConfigPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;
		view = clientFactory.getContentConfigView();
		simpleAnnoncePresenter = new SimpleAnnoncePresenter(view.getSimpleAnnonceDetailView());
		contentListPresenter = new ContentListPresenter(view.getContentListView());
		contentAreaListPresenter = new ContentAreaListPresenter(view.getContentAreaListView());
		picturesPresenter = new CPicturesPresenter(view.getContentPicturesView());
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		UserSO userSO = clientFactory.getActualUserSO();

		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			panel.setWidget(view);
			contentListPresenter.initData();
		} else {

			MaterialPanel materialPanel = clientFactory.getEmptyStatePanel();
			panel.setWidget(materialPanel);
			ClientUtils.animeIn(materialPanel, Transition.FADEINLEFT);
		}

		clientFactory.scrollAppToTop(false);

	}

	class ContentListPresenter implements ContentListView.Presenter {

		private ContentListView contentListView;

		public ContentListPresenter(ContentListView contentListView) {
			this.contentListView = contentListView;
			contentListView.setPresenter(this, null);

		}

		@Override
		public void swipeToContentDetail(ContentSO contentSO, boolean isNew) {

			view.getSimpleAnnonceDetailView().setContentSO(contentSO);

			view.showContentDetails(isNew);

		}

		@Override
		public void newContent() {
			ContentSO ctent = new ContentSO("Non Classé", "[ Titre ici ]", "[ Description du contenu ici ]",
					"img/africaGirl.jpg");
			ctent.setId(-10);
			ctent.setCreationDate(new Date());
			ctent.setActive(false);
			ctent.setLock(false);
			ctent.setCreator(CoolPasCherUI.CLIENT_FACTORY.getActualUserSO().getEmail());
			ctent.setDescription2("Des");
			ctent.setLongDescription("Longue description");
			ctent.setCategory("");
			ctent.setPrix("");
			ctent.setProvince("");
			ctent.setLocalite("");
			ctent.setViewAble(1);
			ctent.setLabel("");
			ctent.setQuartier("");
			ctent.setShowToHome(true);
			ctent.setType(ContentSO.TYPE_ANNONCE);
			ctent.setVille("");

			swipeToContentDetail(ctent, true);
			view.getSimpleAnnonceDetailView().setEdit();
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

		private void initData() {
			clientFactory.execute(new ContentAction(DBAction.READ, ContentCommand.ALL_ARTICLE_CONTENTS, null),
					result -> {
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
			view.getContentPicturesView().setContentSO(contentSO);
			;
			view.showContentPictures();

		}

		@Override
		public void backToMenuList() {

		}

		@Override
		public void showContentAreaList(ContentSO contentSO) {
			contentAreaListPresenter.initData(contentSO);
			view.showContentAreaList();
		}

	}

	class SimpleAnnoncePresenter implements SimpleAnnonceDetailView.Presenter {

		private SimpleAnnonceDetailView simpleAnnonceDetailView;

		public SimpleAnnoncePresenter(SimpleAnnonceDetailView simpleAnnonceDetailView) {
			this.simpleAnnonceDetailView = simpleAnnonceDetailView;
			this.simpleAnnonceDetailView.setPresenter(this);
			// this.simpleAnnonceDetailView.setEdit(false);
		}

		@Override
		public void saveContent(ContentSO contentSO) {
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
		public void backToContentList() {
			view.backToContentList();
		}

	}

	@Override
	public void onStop() {
		super.onStop();
	}

	class CPicturesPresenter implements ContentPicturesView.Presenter {
		private ContentPicturesView contentPicturesView;
		private ContentSO contentSO;

		public CPicturesPresenter(ContentPicturesView contentPicturesView) {
			this.contentPicturesView = contentPicturesView;
			contentPicturesView.setPresenter(this);
		}

		@Override
		public void saveContentSO(ContentSO contentSO) {
			DBAction dbAction = contentSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;
			clientFactory.execute(new ContentAction(dbAction, contentSO), result -> {
				ContentSO saved = (ContentSO) result.getObject();
				contentSO.setId(saved.getId());
				contentSO.setcAreaSOs(saved.getcAreaSOs());

				contentPicturesView.setContentSO(contentSO);
			});

		}

		@Override
		public void backToAlbumPhotoList() {
			view.backToContentList();

		}

		public void setContentSO(ContentSO contentSO) {
			this.contentSO = contentSO;
			contentPicturesView.setContentSO(contentSO);
		}
	}

	class ContentAreaListPresenter implements ContentAreaListView.Presenter {
		private ContentSO contentSO;
		private ContentAreaListView contentAreaListView;

		public ContentAreaListPresenter(ContentAreaListView contentAreaListView) {
			this.contentAreaListView = contentAreaListView;
			contentAreaListView.setPresenter(this);
		}

		@Override
		public void swipeToContentAreaDetail(CAreaSO cAreaSO) {

			// view.getContentAreaView().setContentArea(cAreaSO);
			// view.showContentAreaDetail();

		}

		@Override
		public void newContentArea(String type) {
			CAreaSO areaSO = new CAreaSO();
			areaSO.setId(-10);
			areaSO.setAreaType(type);
			areaSO.setCreationDate(new Date());
			areaSO.setLock(true);
			areaSO.setContentSO(contentSO);
			areaSO.setCreator("UNKNOW");

			// areaSO.setImageUrl(type.equals("H") ? "img/slider1.jpg" : "img/african.jpg");
			areaSO.setTextContent("Votre text ici");
			areaSO.setTitel("Votre Titre");
			areaSO.setIndex(0);
			areaSO.setRandomId(null);
			swipeToContentAreaDetail(areaSO);
		}

		private void initData(ContentSO contentSO) {
			this.contentSO = contentSO;
			contentAreaListView.setData(contentSO);
		}

		@Override
		public void deleteContentAreas(List<CAreaSO> cAreaSOs) {

			clientFactory.execute(new CAreaAction(DBAction.DELETE, new ArrayList<APPObjectSO>(cAreaSOs)), x -> {
				if (x.getBooleanValue()) {
					contentAreaListView.getCellList().removeSelected();
				}
			});

		}

		@Override
		public void swipeBackToContentList() {
			view.backToContentListFromAreaList();

		}
	}

}
