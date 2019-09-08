package com.materials.client.views.footer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.WebSiteSO;

import gwt.material.design.client.ui.MaterialFooter;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

public class APPFooterPanel extends Composite implements APPFooterView {

	private Presenter presenter;

	private static APPFooterPanelUiBinder uiBinder = GWT.create(APPFooterPanelUiBinder.class);

	interface APPFooterPanelUiBinder extends UiBinder<MaterialFooter, APPFooterPanel> {
	}

	@UiField
	MaterialFooter footerUi;

	@UiField
	MaterialLink loginLinkUi;

	@UiField
	MaterialLink cguUi;

	@UiField
	MaterialLabel siteLongNameUi, siteShortNameUi, siteCopyRightTextUi;

	public APPFooterPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		WebSiteSO siteSO = CoolPasCherUI.CLIENT_FACTORY.getWebSiteSO();
		if (siteSO != null) {
			siteLongNameUi.setText(siteSO.getLongName());
			siteShortNameUi.setText(siteSO.getName());
			siteCopyRightTextUi.setText(siteSO.getCopyRightText());
			cguUi.setHref(siteSO.getCGUUrl());
			cguUi.setTarget("_blank");
			cguUi.setTitle("Conditions Genarales d'utilisations");
		}

		// ViewPort.when(Resolution.TABLET, Resolution.ALL_MOBILE,
		// Resolution.LAPTOP).then(viewPortChange -> {
		// footerUi.setMarginBottom(80);
		// });
		// ViewPort.when(Resolution.LAPTOP_LARGE,
		// Resolution.LAPTOP_4K).then(viewPortChange -> {
		// footerUi.setMarginBottom(0);
		// });

		if (CoolPasCherUI.CLIENT_FACTORY.getActualUserSO() != null) {
			loginLinkUi.setText("Logout");
		} else {
			loginLinkUi.setText("Login");
		}

		loginLinkUi.addClickHandler(x -> {
			if (loginLinkUi.getText().equals("Login")) {
				CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().show();
			} else {
				CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().getPresenter().performLogout();
			}
		});

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	public void refreshFooter() {
		if (CoolPasCherUI.CLIENT_FACTORY.getActualUserSO() != null) {
			loginLinkUi.setText("Logout");
		} else {
			loginLinkUi.setText("Login");
		}

	}

}
