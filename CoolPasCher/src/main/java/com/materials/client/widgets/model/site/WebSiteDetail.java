package com.materials.client.widgets.model.site;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.materials.client.model.WebSiteSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.image.SimpleUploader;

import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class WebSiteDetail extends BaseDetails2 implements WebSiteDetailView {

	private static WebSiteDetailUiBinder uiBinder = GWT.create(WebSiteDetailUiBinder.class);

	interface WebSiteDetailUiBinder extends UiBinder<MaterialPanel, WebSiteDetail> {
	}

	private Presenter presenter;
	private WebSiteSO webSiteSO;

	@UiField
	SimpleUploader uploadCguUi, fileUploadUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialTextBox nameUi, shortNameUi, longNameUi, descriptionUi, emailUi, telUi, copyRightUi, emailAdminUi,
			emailPresidentUi, pwd1Ui, pwd2Ui;

	@UiField
	MaterialImage itemImageUi;

	@UiField
	MaterialTextArea adresseUi, mailSendSuccessTextUi;

	public WebSiteDetail() {
		super(false);

		add(uiBinder.createAndBindUi(this));

		pwd1Ui.setType(InputType.PASSWORD);
		pwd2Ui.setType(InputType.PASSWORD);

		mdFormUi.setFormHandler(new BBarHandler());

		fileUploadUi.setVisible(false);
		fileUploadUi.addValueChangeHandler(x -> {
			itemImageUi.setUrl(fileUploadUi.getValue());
			webSiteSO.setRandomId(fileUploadUi.getRandomID());
		});

		uploadCguUi.setVisible(false);
		uploadCguUi.setUploadTitel("Attacher le document CGU");
		uploadCguUi.addValueChangeHandler(x -> {
			webSiteSO.setCguRandomId(uploadCguUi.getRandomID());
		});

		handleEditEnable();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setWebSite(WebSiteSO webSiteSO) {
		this.webSiteSO = webSiteSO;
		nameUi.setValue(webSiteSO.getName());
		shortNameUi.setValue(webSiteSO.getShortName());
		longNameUi.setValue(webSiteSO.getLongName());
		descriptionUi.setValue(webSiteSO.getDescription());
		emailUi.setValue(webSiteSO.getEmail());
		telUi.setValue(webSiteSO.getTel());
		copyRightUi.setValue(webSiteSO.getCopyRightText());
		emailAdminUi.setValue(webSiteSO.getEmailAdmin());
		emailPresidentUi.setValue(webSiteSO.getEmailPresident());
		adresseUi.setValue(webSiteSO.getAdresse());
		mailSendSuccessTextUi.setValue(webSiteSO.getMailSendSuccessText());

		itemImageUi.setUrl(webSiteSO.getImageUrl());
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			fileUploadUi.setVisible(true);
			uploadCguUi.setVisible(true);
		}

		@Override
		public void onSave() {
			fileUploadUi.setVisible(false);
			uploadCguUi.setVisible(false);

			webSiteSO.setAdresse(adresseUi.getValue());
			webSiteSO.setCopyRightText(copyRightUi.getValue());
			webSiteSO.setDescription(descriptionUi.getValue());
			webSiteSO.setMailSendSuccessText(mailSendSuccessTextUi.getValue());
			webSiteSO.setEmail(emailUi.getValue());
			webSiteSO.setEmailAdmin(emailAdminUi.getValue());
			webSiteSO.setName(nameUi.getValue());
			webSiteSO.setEmailPresident(emailPresidentUi.getValue());
			// setEzuw(ezuw);
			webSiteSO.setLongName(longNameUi.getValue());
			webSiteSO.setShortName(shortNameUi.getValue());
			webSiteSO.setTel(telUi.getValue());
			if (pwdOk()) {
				presenter.save(webSiteSO);
			} else {
				Window.alert("Les mots de pass ne sont pas identiques!!");
			}
		}

		@Override
		public void onCancel() {
			fileUploadUi.setVisible(false);
			uploadCguUi.setVisible(false);
		}
	}

	private boolean pwdOk() {
		boolean ok = true;
		// if ((pwd1Ui.getValue() != null) && (pwd2Ui.getValue() != null)) {
		// if (!pwd1Ui.getValue().equals(pwd2Ui.getValue())) {
		// ok = false;
		// }
		// }
		return ok;
	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}
}
