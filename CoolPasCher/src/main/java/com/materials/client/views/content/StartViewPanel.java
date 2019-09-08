package com.materials.client.views.content;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.presenter.RegisterPresenter;
import com.materials.client.model.MenuSO;
import com.materials.client.model.UserSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.views.footer.APPFooterPanel;
import com.materials.client.views.header.APPHeaderPanel;
import com.materials.client.views.navbar.APPNavBarPanel;
import com.materials.client.widgets.config.ConfigurationWidget;
import com.materials.client.widgets.register.RegisterWidget;
import com.materials.client.widgets.register.RegisterWidgetView;

import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

public class StartViewPanel extends Composite implements StartView {

	private static StartViewPanelUiBinder uiBinder = GWT.create(StartViewPanelUiBinder.class);

	interface StartViewPanelUiBinder extends UiBinder<HTMLPanel, StartViewPanel> {
	}

	private List<MenuSO> list;
	private ConfigurationWidget configurationWidget;
	private Presenter presenter;

	@UiField
	HTMLPanel htmlPanel;

	@UiField
	SimplePanel contentUi;

	// @UiField
	// ExpandableInlineSearch expandableInline;
	//
	// @UiField
	// MaterialIcon searchUi;
	//
	// @UiField
	// MboaButton buttonCreation;

	@UiField
	APPHeaderPanel headerPanelUi;

	@UiField
	APPNavBarPanel navBarPanelUi;

	// @UiField
	// MaterialColumn panelBottom;

	private APPFooterPanel aPPFooterPanel;

	@UiField
	MaterialPanel materialPanelUi;

	@UiField
	MaterialContainer mainContainer;

	public StartViewPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		configurationWidget = new ConfigurationWidget();


		new Timer() {
			@Override
			public void run() {
				aPPFooterPanel = new APPFooterPanel();
				materialPanelUi.add(aPPFooterPanel);
				UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();

				// if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
				// htmlPanel.add(configurationWidget);
				// }
			}
		}.schedule(700);

		// ViewPort.when(Resolution.LAPTOP).then(viewPortChange -> {
		// mainContainer.setMarginLeft(35);
		// mainContainer.setMarginRight(35);
		// });

		ViewPort.when(Resolution.LAPTOP_LARGE).then(viewPortChange -> {
			mainContainer.setMarginLeft(40);
			mainContainer.setMarginRight(40);
		});

		ViewPort.when(Resolution.LAPTOP_4K).then(viewPortChange -> {
			mainContainer.setMarginLeft(90);
			mainContainer.setMarginRight(90);
		});

		ViewPort.when(Resolution.ALL_MOBILE, Resolution.TABLET).then(viewPortChange -> {
			mainContainer.setMarginLeft(15);
			mainContainer.setMarginRight(15);
		});

		// ---------------------------------------------------------------------------------
		// panelBottom.setHideOn(HideOn.HIDE_ON_MED_UP);
		// panelBottom.setShowOn(ShowOn.SHOW_ON_MED_DOWN);
		// buttonCreation.setType(ButtonType.OUTLINED);
		// buttonCreation.setIconType(IconType.ADD_CIRCLE);
		// buttonCreation.setIconFontSize(20, Unit.PX);
		// buttonCreation.setWaves(WavesType.LIGHT);
		// WidgetUtils.addToViewPortButton(new Widget[] { buttonCreation });
		
	
	}

	public SimplePanel getContentUi() {
		return contentUi;
	}

	// @Override
	// public void rebuildHeader(List<MenuSO> list) {
	// this.list = list;
	// headerPanelUi.build(list);
	// }
	//
	// @Override
	// public void rebuildNavbar(List<MenuSO> list) {
	// this.list = list;
	// navBarPanelUi.build(list);
	// }
	//
	// @Override
	// public void refreshNavbar() {
	// navBarPanelUi.rebuildNavbar();
	// }
	//
	// @Override
	// public void refreshHeaders() {
	// headerPanelUi.rebuildHeader();
	// }

	@Override
	public void showConfiguration(boolean show) {
		if (show) {
			htmlPanel.add(configurationWidget);
		} else if (htmlPanel.getWidgetIndex(configurationWidget) != -1) {
			htmlPanel.remove(configurationWidget);
		}
	}

	@Override
	public void showRegisterPanel(boolean edit) {
		RegisterWidgetView registerWidgetView = new RegisterWidget();
		registerWidgetView.setPresenter(new RegisterPresenter(registerWidgetView, this));
		registerWidgetView.reset();
		registerWidgetView.editMode(edit);
		registerWidgetView.setUserSO(edit ? CoolPasCherUI.CLIENT_FACTORY.getActualUserSO() : new UserSO(-10));

		MaterialPanel materialPanel = new MaterialPanel();
		materialPanel.setHeight("1200px");
		materialPanel.add(registerWidgetView);
		materialPanel.setOpacity(0);

		contentUi.clear();
		contentUi.add(materialPanel);

		ClientUtils.animeIn(materialPanel, Transition.FADEIN, 1000);

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public Presenter getPresenter() {
		return presenter;
	}

	@Override
	public void addWidget(IsWidget widget) {
		materialPanelUi.add(widget);

	}
	


	// @Override
	// public void deselectMenuSelected() {
	// navBarPanelUi.deselectSlected();
	//
	// }

	@Override
	public APPHeaderPanel getAppHeaderPanel() {
		return headerPanelUi;
	}

	@Override
	public APPNavBarPanel getAppNavBarPanel() {
		return navBarPanelUi;
	}

	@Override
	public APPFooterPanel getAppFooterPanel() {
		return aPPFooterPanel;
	}

}
