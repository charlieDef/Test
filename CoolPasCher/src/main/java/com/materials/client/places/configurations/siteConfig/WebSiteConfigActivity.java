package com.materials.client.places.configurations.siteConfig;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.UserSO;
import com.materials.client.model.WebSiteSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.model.site.WebSiteDetailView;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.GeneralAction;
import com.materials.shared.action.GeneralCommand;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

public class WebSiteConfigActivity extends AbstractActivity implements WebSiteConfigView.Presenter {

	private AppClientFactory clientFactory;
	private WebSiteConfigPlace place;
	private WebSiteConfigView view;
	private WebSiteDetailPresenter webSiteDetailPresenter;

	public WebSiteConfigActivity(AppClientFactory clientFactory, WebSiteConfigPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;

		view = clientFactory.getSiteConfigView();
		webSiteDetailPresenter = new WebSiteDetailPresenter(view.getSiteDetailView());
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		UserSO userSO = clientFactory.getActualUserSO();
		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			panel.setWidget(view);
			webSiteDetailPresenter.initData();
		} else {
			MaterialPanel materialPanel = clientFactory.getEmptyStatePanel();
			panel.setWidget(materialPanel);
			ClientUtils.animeIn(materialPanel, Transition.FADEINLEFT);
		}
		clientFactory.scrollAppToTop(false);
	}

	class WebSiteDetailPresenter implements WebSiteDetailView.Presenter {

		private WebSiteDetailView detailView;

		public WebSiteDetailPresenter(WebSiteDetailView detailView) {
			this.detailView = detailView;
			this.detailView.setPresenter(this);
		}

		@Override
		public void save(WebSiteSO siteSO) {
			clientFactory.execute(new GeneralAction(DBAction.UPDATE, GeneralCommand.SAVE_SITE, siteSO), x -> {
				WebSiteSO saved = (WebSiteSO) x.getObject();
			});
		}

		private void initData() {

			WebSiteSO webSiteSO = clientFactory.getWebSiteSO();
			if (webSiteSO != null) {
				// webSiteSO.setImageUrl(Index.MODUL_BASE_FILEHELPER + "?siteID=" +
				// webSiteSO.getRandomId());

				detailView.setWebSite(webSiteSO);

				view.showWebsiteDetail();
			}

		}

	}

}
