package com.materials.client.places.configurations.rsociaux;

import com.materials.client.places.configurations.AbstractControlPanel;
import com.materials.client.widgets.model.rsocial.celllist.RSocialList;
import com.materials.client.widgets.model.rsocial.celllist.RSociauxListView;
import com.materials.client.widgets.model.rsocial.details.RSociauxDetail;
import com.materials.client.widgets.model.rsocial.details.RSociauxDetailView;

import gwt.material.design.client.base.MaterialWidget;

public class RSociauxConfigViewImpl extends AbstractControlPanel implements RSociauxConfigView {

	private String titel = "";

	private RSocialList rSocialList;
	private RSociauxDetail rSociauxDetail;

	public RSociauxConfigViewImpl() {
		super(true);

		rSocialList = new RSocialList();
		rSociauxDetail = new RSociauxDetail(false);
		swipperWidget.addSwipeItem("rSocialList", rSocialList, true);
		swipperWidget.addSwipeItem("rSociauxDetail", rSociauxDetail, false);

	}

	@Override
	public void showRSocialList() {
		swipperWidget.show("rSocialList");
		configControlPanel.showControll(rSocialList.getButtons());
	}

	@Override
	public void goBackToRSocialList(MaterialWidget materialWidget) {
		swipperWidget.swipeBackTo("rSocialList");
		configControlPanel.showControll(rSocialList.getButtons());
	}

	@Override
	public void showRSocialDetail() {
		swipperWidget.swipeTo("rSociauxDetail");
		configControlPanel.showControll(rSociauxDetail.getButtonBar());
	}

	@Override
	public RSociauxListView getRSociauxListView() {
		return rSocialList;
	}

	@Override
	public RSociauxDetailView getRSociauxDetail() {
		return rSociauxDetail;
	}

}
