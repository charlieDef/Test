package com.materials.client.widgets.model.user;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.APPObjectSO;
import com.materials.client.model.UserSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.buttonbar.FloatingButtonBar;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.image.SimpleUploader;
import com.materials.shared.APPConstants;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class UserDetails extends BaseDetails2 implements UserDetailsView {

	private UserSO userSO;
	private Presenter presenter;

	@UiField
	MaterialImage itemImageUi;

	@UiField
	MaterialCardTitle itemEmailUi;

	@UiField
	MaterialLabel textHeaderLabelUi, itemLastnameUi, itemNameUi;

	@UiField
	MaterialDatePicker creationUi;

	@UiField
	MaterialIntegerBox phoneUi;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialTextBox emailUi, lastnameUi, nameUi, cityUi, annonceUi, messageUi;

	@UiField
	MaterialCheckBox lockUi, activeUi;

	@UiField
	MaterialComboBox<String> livingCountryUi, typeUtilisateurUi, roleUi;

	@UiHandler({ "emailUi" })
	void onEmailUiChanged(ValueChangeEvent<String> event) {
		itemEmailUi.setText(emailUi.getValue());
	}

	@UiHandler({ "lastnameUi" })
	void onLastnameUiChanged(ValueChangeEvent<String> event) {
		itemLastnameUi.setText(lastnameUi.getValue());
	}

	@UiHandler({ "itemNameUi" })
	void onTitelChanged(ValueChangeEvent<String> event) {
		itemNameUi.setText(itemNameUi.getValue());
	}

	@UiHandler("backButtonUi")
	void onBackToContentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToUserList();

		ClientUtils.addTimer(x -> itemImageUi.setUrl(""), 500);
	}

	public UserDetails(boolean configure) {

		super(configure);

		add(uiBinder.createAndBindUi(this));

		mdFormUi.setFormHandler(new BBarHandler());

		livingCountryUi.setAcceptableValues(Arrays.asList(APPObjectSO.PREBAAL_PAYS));
		typeUtilisateurUi.setAcceptableValues(Arrays.asList(APPObjectSO.TYPE_DANNONCEURS));
		roleUi.setAcceptableValues(Arrays.asList(APPObjectSO.MBOA_ROLES));

		roleUi.setAllowBlank(true);
		livingCountryUi.setAllowBlank(true);
		typeUtilisateurUi.setAllowBlank(true);
		// statusUi.setAllowBlank(true);

		fileUploadUi.setVisible(false);
		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.addValueChangeHandler(x -> {
			itemImageUi.setUrl(fileUploadUi.getValue());
			userSO.setRandomId(fileUploadUi.getRandomID());
		});

		handleEditEnable();

	}

	public void setHeaderTitel(String titel) {
		textHeaderLabelUi.setText(titel);

	}

	@Override
	public void setUserSO(UserSO userSO) {
		this.userSO = userSO;
		String imageUrl = userSO.getUserImageUrl() != null ? userSO.getUserImageUrl() : "";
		Date creation = userSO.getCreation() != null ? userSO.getCreation() : new Date();
		DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

		// item
		itemImageUi.setUrl(imageUrl);
		itemEmailUi.setText(userSO.getEmail());
		itemLastnameUi.setText(userSO.getLastName());
		itemNameUi.setValue(userSO.getName());
		messageUi.setValue(userSO.getTextInfo());

		// boxes
		creationUi.setDate(creation);
		emailUi.setValue(userSO.getEmail());
		lastnameUi.setValue(userSO.getLastName());
		nameUi.setValue(userSO.getName());
		cityUi.setValue(userSO.getCity());
		lockUi.setValue(userSO.isLock());
		phoneUi.setValue(userSO.getTel());
		typeUtilisateurUi.setSingleValue(userSO.getVillageArea() != null ? userSO.getVillageArea() : "---");
		roleUi.setSingleValue(userSO.getRole() != null ? userSO.getRole() : "---");
		// statusUi.setSingleValue(userSO.getStatus() != null ? userSO.getStatus() :
		// "---");
		livingCountryUi.setSingleValue(userSO.getCountry() != null ? userSO.getCountry() : "---");

		String inscription = userSO.getUserProperties().get(APPObjectSO.PREBAAL_INSCRIPTION);
		String fCaisse = userSO.getUserProperties().get(APPObjectSO.PREBAAL_FOND_CAISSE);
		String epargne = userSO.getUserProperties().get(APPObjectSO.PREBAAL_EPARGNE);

		// inscriptionUi.setValue(inscription != null ? inscription : "");
		// fondCaisseUi.setValue(fCaisse != null ? fCaisse : "");
		// epargneUi.setValue(epargne != null ? epargne : "");

		textHeaderLabelUi.setText("User::" + userSO.getLastName());

	}

	private static UserDetailsUiBinder uiBinder = GWT.create(UserDetailsUiBinder.class);

	interface UserDetailsUiBinder extends UiBinder<Widget, UserDetails> {
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			fileUploadUi.setVisible(true);
		}

		@Override
		public void onSave() {
			fileUploadUi.setVisible(false);
			userSO.setActive(activeUi.getValue());
			userSO.setEmail(emailUi.getValue());
			userSO.setLastName(lastnameUi.getValue());
			userSO.setName(nameUi.getValue());
			userSO.setLock(lockUi.getValue());
			userSO.setTel(phoneUi.getValue() == null ? 00 : phoneUi.getValue());
			userSO.setCity(cityUi.getValue());
			userSO.setCountry(livingCountryUi.getSingleValue());
			userSO.setTextInfo(messageUi.getValue());
			// userSO.setFunction(functionUi.getSingleValue());
			userSO.setRole(roleUi.getSingleValue());
			// userSO.setStatus(statusUi.getSingleValue());
			userSO.setVillageArea(typeUtilisateurUi.getSingleValue());
			// userSO.setProfession(professionUi.getValue());

			// String inscription = inscriptionUi.getValue();
			// String fCaisse = fondCaisseUi.getValue();
			// String epargne = epargneUi.getValue();
			// if (inscription != null && !inscription.isEmpty()) {
			// userSO.getUserProperties().put(APPObjectSO.PREBAAL_INSCRIPTION, inscription);
			// }
			// if (fCaisse != null && !fCaisse.isEmpty()) {
			// userSO.getUserProperties().put(APPObjectSO.PREBAAL_FOND_CAISSE, fCaisse);
			// }
			// if (epargne != null && !epargne.isEmpty()) {
			// userSO.getUserProperties().put(APPObjectSO.PREBAAL_EPARGNE, epargne);
			// }

			presenter.saveUser(userSO);
		}

		@Override
		public void onCancel() {
			fileUploadUi.setVisible(false);
			if (userSO.getId() == -10) {
				presenter.backToUserList();
			}

		}
	}

	@Override
	public void setEdit() {
		mdFormUi.edit();

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;
	}

	@Override
	public FloatingButtonBar getButtonBar() {
		return mdFormPanel.getButtonbar();
	}

}
