package com.materials.client.widgets.model.rsocial.details;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.materials.client.model.RSocialSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class RSociauxDetail extends BaseDetails2 implements RSociauxDetailView {

	private static RSociauxDetailUiBinder uiBinder = GWT.create(RSociauxDetailUiBinder.class);

	interface RSociauxDetailUiBinder extends UiBinder<MDFormPanel, RSociauxDetail> {
	}

	public RSociauxDetail(boolean configure) {

		super(configure);
		add(uiBinder.createAndBindUi(this));

		mdFormUi.setFormHandler(new BBarHandler());

		handleEditEnable();
	}

	private RSocialSO rSocialSO;
	private Presenter presenter;

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	MaterialImage itemImageUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialTextBox nameUi, tooltipUi, targetUrlUi;

	@UiField
	MaterialCheckBox activeUi, lockUi;

	@UiHandler("backButtonUi")
	void onBackToContentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToRSocialList();
	}

	@UiHandler("nameUi")
	void onTextChanged(ValueChangeEvent<String> event) {
		itemImageUi.setUrl("img/rsociaux/64/" + nameUi.getValue() + ".png");
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {

		}

		@Override
		public void onSave() {

			rSocialSO.setActif(activeUi.getValue());
			rSocialSO.setName(nameUi.getValue());
			rSocialSO.setLock(lockUi.getValue());
			rSocialSO.setTargetUrl(targetUrlUi.getValue());
			rSocialSO.setActif(activeUi.getValue());
			rSocialSO.setTooltip(tooltipUi.getValue());
			rSocialSO.setHtmlImg(itemImageUi.getValue());

			presenter.saveRSocial(rSocialSO);
		}

		@Override
		public void onCancel() {
			if (rSocialSO.getId() == -10) {
				presenter.backToRSocialList();
			}
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setRSocial(RSocialSO rSocialSO) {
		this.rSocialSO = rSocialSO;

		activeUi.setValue(rSocialSO.isActif());
		nameUi.setValue(rSocialSO.getName());
		lockUi.setValue(rSocialSO.isActif());
		itemImageUi.setUrl(rSocialSO.getHtmlImg());
		targetUrlUi.setValue(rSocialSO.getTargetUrl());
		tooltipUi.setValue(rSocialSO.getTooltip());

		textHeaderLabelUi.setValue("Details::" + rSocialSO.getName());
	}

	@Override
	public void setEdit() {
		mdFormUi.edit();
	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}

}
