package com.materials.client.places.configurations.siteConfig;

import com.materials.client.places.configurations.AbstractControlPanel;
import com.materials.client.widgets.model.site.WebSiteDetail;
import com.materials.client.widgets.model.site.WebSiteDetailView;

import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.jquery.client.api.Functions.Func;

public class WebSiteConfigViewImpl extends AbstractControlPanel implements WebSiteConfigView {

	private WebSiteDetail webSiteDetail;

	public WebSiteConfigViewImpl() {

		super(false);
		getElement().getStyle().setProperty("minHeight", "1300px");

		webSiteDetail = new WebSiteDetail();
		add(webSiteDetail);

	}

	@Override
	public WebSiteDetailView getSiteDetailView() {
		return webSiteDetail;
	}

	@Override
	public void showWebsiteDetail() {
		new MaterialAnimation().transition(Transition.FADEINLEFT).animate(webSiteDetail, new Func() {
			@Override
			public void call() {
				webSiteDetail.setOpacity(1);
			}
		});
		configControlPanel.showControll(webSiteDetail.getButtonBar());

	}

}
