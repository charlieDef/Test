package com.materials.client.places.configurations.rsociaux;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.APPObjectSO;
import com.materials.client.model.RSocialSO;
import com.materials.client.model.UserSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.model.rsocial.celllist.RSociauxListView;
import com.materials.client.widgets.model.rsocial.details.RSociauxDetailView;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.rsocial.RSocialAction;
import com.materials.shared.action.rsocial.RSocialCommand;

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
public class RSociauxConfigActivity extends AbstractActivity implements RSociauxConfigView.Presenter {

	private AppClientFactory clientFactory;
	private RSociauxConfigPlace place;
	private RSociauxConfigView view;

	private final RSociauxDetailPresenter detailPresenter;
	private RSocialListPresenter listPresenter;
	//

	public RSociauxConfigActivity(AppClientFactory clientFactory, RSociauxConfigPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;

		view = clientFactory.getRSociauxConfigView();
		detailPresenter = new RSociauxDetailPresenter(view.getRSociauxDetail());
		listPresenter = new RSocialListPresenter(view.getRSociauxListView());
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		UserSO userSO = clientFactory.getActualUserSO();
		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			panel.setWidget(view);
			listPresenter.initData();
		} else {
			MaterialPanel materialPanel = clientFactory.getEmptyStatePanel();
			panel.setWidget(materialPanel);
			ClientUtils.animeIn(materialPanel, Transition.FADEINLEFT);
		}

		clientFactory.scrollAppToTop(false);
	}

	class RSociauxDetailPresenter implements RSociauxDetailView.Presenter {

		private RSociauxDetailView rSociauxDetailView;
		private RSocialSO rSocialSO;

		public RSociauxDetailPresenter(RSociauxDetailView rSociauxDetailView) {
			this.rSociauxDetailView = rSociauxDetailView;
			rSociauxDetailView.setPresenter(this);
		}

		@Override
		public void backToRSocialList() {
			view.goBackToRSocialList((MaterialWidget) rSociauxDetailView);
		}

		@Override
		public void saveRSocial(RSocialSO rSocialSO) {
			this.rSocialSO = rSocialSO;
			DBAction dbAction = rSocialSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;
			clientFactory.execute(new RSocialAction(dbAction, rSocialSO), x -> {
				RSocialSO saved = (RSocialSO) x.getObject();
				this.rSocialSO = saved;
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					view.getRSociauxListView().getCellList().addItem(saved);
				}
				view.getRSociauxListView().getCellList().refresh();
			});

		}
	}

	class RSocialListPresenter implements RSociauxListView.Presenter {

		private RSociauxListView rSociauxListView;

		public RSocialListPresenter(RSociauxListView rSociauxListView) {
			this.rSociauxListView = rSociauxListView;
			this.rSociauxListView.setPresenter(this);
		}

		@Override
		public void showRSocialList() {
			view.showRSocialList();
		}

		@Override
		public void swipeToRSocialDetail(RSocialSO rSocialSO) {
			view.getRSociauxDetail().setRSocial(rSocialSO);
			view.showRSocialDetail();
		}

		@Override
		public void newRSocial() {
			RSocialSO rSocialSO = new RSocialSO();
			rSocialSO.setId(-10);
			rSocialSO.setActif(false);
			rSocialSO.setLock(false);
			rSocialSO.setName("Nom du Reseaux social");
			rSocialSO.setTargetUrl("http://...");
			rSocialSO.setTooltip("Tooltip ici");
			rSocialSO.setHtmlImg("<i class=\"fab fa-linkedin\"></i>");

			swipeToRSocialDetail(rSocialSO);
			view.getRSociauxDetail().setEdit();
		}

		@Override
		public void deleteRSocial(List<RSocialSO> rSocialSOs) {

			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new RSocialAction(DBAction.DELETE, new ArrayList<APPObjectSO>(rSocialSOs)),
							x -> {
								if (x.getBooleanValue()) {
									rSociauxListView.getCellList().removeSelected();
								}
							});
				}
			});

		}

		private void initData() {
			clientFactory.execute(new RSocialAction(DBAction.READ, RSocialCommand.ALL_RSOCIAUX, null), result -> {
				List<RSocialSO> list = MethodsUtils.castList(result.getObjects(), RSocialSO.class);
				rSociauxListView.setData(list);
				view.showRSocialList();
			});
		}

	}

}
