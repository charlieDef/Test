package com.materials.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.materials.client.context.AppActivityMapper;
import com.materials.client.context.AppClientFactory;
import com.materials.client.context.AppHistoryMapper;
import com.materials.client.context.presenter.LoginPresenter;
import com.materials.client.model.MenuSO;
import com.materials.client.model.UserSO;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.resources.AppResource;
import com.materials.client.utils.ClientUtils;
import com.materials.client.views.content.StartView;
import com.materials.client.views.content.StartViewPanel;
import com.materials.client.widgets.login.LoginWidgetView2;

import gwt.material.design.client.constants.OffsetPosition;
import gwt.material.design.client.ui.MaterialLoader;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CoolPasCherUI implements EntryPoint {

	public static final AppResource APP_RESOURCE = GWT.create(AppResource.class);
	public static final AppClientFactory CLIENT_FACTORY = GWT.create(AppClientFactory.class);
	public static final String MODUL_BASE_FILEHELPER = GWT.getModuleBaseURL() + "FileHelper";
	public static final String MODUL_BASE_FILEUPLOAD = GWT.getModuleBaseURL() + "UploadServlet";
	public static boolean ARTICLE_STARTET = false;

	@Override
	public void onModuleLoad() {

		ActivityMapper activityMapper = new AppActivityMapper(CLIENT_FACTORY);
		ActivityManager activityManager = new ActivityManager(activityMapper, CLIENT_FACTORY.getEventBus());

		Place defaultPlace = new MenuPlace().handleMenu("start", MenuSO.ACCEUIL);
		PlaceController placeController = CLIENT_FACTORY.getPlaceController();

		PlaceHistoryMapper historyMapper = GWT.create(AppHistoryMapper.class);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, CLIENT_FACTORY.getEventBus(), defaultPlace);

		MaterialLoader.progress(true, RootPanel.get());

		CLIENT_FACTORY.buildWidgetsForWelcome(wigets -> {

			StartView startView = new StartViewPanel();
			startView.asWidget().getElement().getStyle().setOpacity(0);

			CLIENT_FACTORY.setStartView(startView);
			RootPanel.get().add(startView);
			activityManager.setDisplay(startView.getContentUi());

			CLIENT_FACTORY.getScrollHelper().setDuration(2);
			CLIENT_FACTORY.getScrollHelper().setOffsetPosition(OffsetPosition.TOP);

			// Login
			LoginWidgetView2 loginWidget = CLIENT_FACTORY.getLoginWidget();
			loginWidget.setPresenter(new LoginPresenter(loginWidget, startView));
			startView.addWidget(loginWidget);

			startView.addWidget(CLIENT_FACTORY.getConfirmationWidget());

			CLIENT_FACTORY.setCurrentHistoryConsumer(x -> historyHandler.handleCurrentHistory());
			historyHandler.handleCurrentHistory();

			// PwaManager pwaManager = PwaManager.getInstance();
			// pwaManager.setServiceWorker(GWT.getHostPageBaseURL() + "service-worker.js");
			// pwaManager.setWebManifest(GWT.getHostPageBaseURL() + "manifest.json");
			//
			// pwaManager.load();

			ClientUtils.addTimer(x -> {
				// RootPanel.get().remove(materialRow);
				CoolPasCherUI.CLIENT_FACTORY.addAnimation(startView.asWidget().getElement(), 1700);

				MaterialLoader.progress(false);
			}, 2000);

		});

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				GWT.log(e.getMessage());
				Window.alert(e.getMessage());
			}
		});

		// CLIENT_FACTORY.addAnimation(flowPanel, null, 2000);
	}

	public static boolean checkLoggedMember() {
		boolean member = false;
		UserSO userSO = CLIENT_FACTORY.getActualUserSO();
		if (userSO != null) {
			member = true;
		}
		return member;
	}

	public static boolean checkAdminMember() {
		boolean member = false;
		UserSO userSO = CLIENT_FACTORY.getActualUserSO();
		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			member = true;
		}
		return member;
	}

	// public static void showBackButton(boolean show) {
	// if (show) {
	//
	// RootPanel.get().add(CLIENT_FACTORY.getBackButton());
	// } else {
	// RootPanel.get().remove(CLIENT_FACTORY.getBackButton());
	// }
	// }

}
