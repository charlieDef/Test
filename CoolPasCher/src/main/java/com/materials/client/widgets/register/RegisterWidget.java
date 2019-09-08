package com.materials.client.widgets.register;

import java.util.Arrays;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.APPObjectSO;
import com.materials.client.model.UserSO;
import com.materials.client.model.WebSiteSO;
import com.materials.client.widgets.button.MboaButton;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.client.widgets.upload.image.SimpleUploader;
import com.materials.shared.MethodsUtils;
import com.materials.shared.validator.EmailValidator;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class RegisterWidget extends Composite implements RegisterWidgetView {

	private ConfirmationWidget confirmationWidget;

	private static RegisterWidgetUiBinder uiBinder = GWT.create(RegisterWidgetUiBinder.class);

	interface RegisterWidgetUiBinder extends UiBinder<MaterialRow, RegisterWidget> {
	}

	private boolean isEdit = false;

	private EmailValidator validator = new EmailValidator();
	private BlankValidator<String> blankValidator = new BlankValidator<String>("Entrez une valeur");

	private Presenter presenter;
	private UserSO userSO;
	private Consumer<Boolean> confirmationConsumer = x -> {
	};

	@UiField
	MaterialRow materialRowUi;

	@UiField
	MaterialTextBox pwdLabelUI, pwdLabelUI2, oldPwdLabelUI, emailLabelUI, lastnameUi, nameUi, cityUi, professionUi,
			messageUi;

	@UiField
	MaterialIntegerBox phoneUi;

	@UiField
	MaterialLabel loginFailUi;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	MaterialCardTitle cardTitelUi;

	@UiField
	MaterialImage userImageUi;

	@UiField
	MaterialComboBox<String> livingCountryUi, balengAreaUi;

	@UiField
	MboaButton sauverButtontUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MaterialCheckBox conditionsDutilisationsUi;

	@UiField
	MaterialRow validatorUi;

	@UiField
	MaterialLink cguUi;

	public RegisterWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		materialCardUi.getElement().getStyle().setProperty("borderRadius", "5px");

		WebSiteSO siteSO = CoolPasCherUI.CLIENT_FACTORY.getWebSiteSO();
		if (siteSO != null) {
			cguUi.setHref(siteSO.getCGUUrl());
			cguUi.setTarget("_blank");
			cguUi.setTitle("Conditions Genarales d'utilisations");

		}
		oldPwdLabelUI.setType(InputType.PASSWORD);
		pwdLabelUI.setType(InputType.PASSWORD);
		pwdLabelUI2.setType(InputType.PASSWORD);
		emailLabelUI.addValidator(validator);

		livingCountryUi.setAcceptableValues(Arrays.asList(APPObjectSO.PREBAAL_PAYS));
		balengAreaUi.setAcceptableValues(Arrays.asList(APPObjectSO.TYPE_DANNONCEURS));

		livingCountryUi.setAllowBlank(true);
		balengAreaUi.setAllowBlank(true);

		fileUploadUi.setWidth("70px");
		fileUploadUi.setIconSize(IconSize.MEDIUM);
		fileUploadUi.setIconColor(Color.AMBER_DARKEN_3);
		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.addValueChangeHandler(x -> {

			userImageUi.setUrl(fileUploadUi.getValue());
			userSO.setRandomId(fileUploadUi.getRandomID());
		});

		nameUi.addValidator(blankValidator);
		lastnameUi.addValidator(blankValidator);
		nameUi.addValidator(blankValidator);
		cityUi.addValidator(blankValidator);
		professionUi.addValidator(blankValidator);

		confirmationWidget = new ConfirmationWidget("Compte crée avec success!!", confirmationConsumer);
		confirmationWidget.setBoxTitle("Account Actif");
		confirmationWidget.desactiveNoButton(true);

		confirmationWidget.getElement().getStyle().setProperty("maxWidth", "500px");
		materialCardUi.add(confirmationWidget);

		if (CoolPasCherUI.checkLoggedMember()) {
			conditionsDutilisationsUi.setVisible(false);
			cguUi.setVisible(false);
			sauverButtontUi.setText("Sauver les changements");

			Timer timer = new Timer() {
				@Override
				public void run() {
					sauverButtontUi.setEnabled(true);
				}
			};
			timer.schedule(500);

		} else {
			sauverButtontUi.setText("Creer le compte");
			pwdLabelUI.addValidator(blankValidator);
			pwdLabelUI2.addValidator(blankValidator);
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@UiHandler("conditionsDutilisationsUi")
	public void onCheck(ValueChangeEvent<Boolean> event) {

		sauverButtontUi.setEnabled(conditionsDutilisationsUi.getValue());

	}

	@Override
	public void showConfirmationLighBox(String headerLabel, String contentLabel,
			Consumer<Boolean> confirmationConsumer) {

		confirmationWidget.setLabelContent(contentLabel);
		confirmationWidget.setBoxTitle(headerLabel);
		confirmationWidget.show(confirmationConsumer);
	}

	@Override
	public void reset() {
		pwdLabelUI.setValue("");
		pwdLabelUI2.setValue("");
		emailLabelUI.setValue("");
		userImageUi.setValue("img/a_user.jpg");
		lastnameUi.setValue("");
		nameUi.setValue("");
		cityUi.setValue("");
		professionUi.setValue("");
		phoneUi.setValue(00);
		isEdit = false;
		pwdLabelUI.setLabel("Mot de pass");
		pwdLabelUI2.setLabel("Mot de pass");
		oldPwdLabelUI.setVisible(false);
		cardTitelUi.setText("S'enregistrer");
		messageUi.setValue("");
	}

	@Override
	public void editMode(boolean editMode) {
		if (editMode) {
			oldPwdLabelUI.setVisible(editMode);
			pwdLabelUI.setLabel("Nouveau Mot de pass");
			pwdLabelUI2.setLabel("Nouveau Mot de pass");
			cardTitelUi.setText("Modifications");
		}
		this.isEdit = editMode;
	}

	@Override
	public void setUserSO(UserSO userSO) {
		this.userSO = userSO;

		emailLabelUI.setValue(userSO.getEmail());
		lastnameUi.setValue(userSO.getLastName());
		nameUi.setValue(userSO.getName());
		cityUi.setValue(userSO.getCity());
		professionUi.setValue(userSO.getProfession());
		messageUi.setValue(userSO.getTextInfo());

		userImageUi.setValue(userSO.getUserImageUrl());
		phoneUi.setValue(userSO.getTel());

		professionUi.setValue(userSO.getProfession());
		balengAreaUi.setSingleValue(userSO.getVillageArea() != null ? userSO.getVillageArea() : "---");
		livingCountryUi.setSingleValue(userSO.getCountry() != null ? userSO.getCountry() : "---");
	}

	// @UiHandler("imageEditableUi")
	// void onImageChange(ValueChangeEvent<String> event) {
	// userSO.setUserImageUrl(event.getValue());
	// userSO.setRandomId(imageEditableUi.getRandomID());
	//
	// }

	@UiHandler("pwdLabelUI")
	void onFocus(ValueChangeEvent<String> event) {
		loginFailUi.setVisible(false);
		loginFailUi.setText("");
	}

	@UiHandler("pwdLabelUI2")
	void onFocus2(ValueChangeEvent<String> event) {
		loginFailUi.setVisible(false);
		loginFailUi.setText("");
	}

	@UiHandler("sauverButtontUi")
	void onSave(ClickEvent clickEvent) {

		if (validatorUi.validate()) {
			if (isEdit) {
				handleEditUser();
			} else {
				handleNewUser();
			}
		}

	}

	private void handleEditUser() {
		String email = emailLabelUI.getValue();
		String pwd1 = pwdLabelUI.getValue();
		String pwd2 = pwdLabelUI2.getValue();
		String oldPwd = oldPwdLabelUI.getValue();

		if (MethodsUtils.isStringOK(pwd1) && MethodsUtils.isStringOK(pwd2) && MethodsUtils.isStringOK(oldPwd)) {

			if (!pwd1.equals(pwd2)) {
				loginFailUi.setVisible(true);
				loginFailUi.setText("Mots de pass pas identiques!!!");
				return;
			}
			userSO.setZuwu(oldPwd + "#" + pwd1);
		}

		if (MethodsUtils.isStringOK(email)) {
			userSO.setEmail(email);
		} else {
			loginFailUi.setVisible(true);
			loginFailUi.setText("Verifier l'email svp");
			return;
		}

		userSO.setLastName(lastnameUi.getValue());
		userSO.setName(nameUi.getValue());
		userSO.setProfession(professionUi.getValue());
		userSO.setTextInfo(messageUi.getValue());
		userSO.setTel(phoneUi.getValue());
		userSO.setCity(cityUi.getValue());
		userSO.setCountry(livingCountryUi.getSingleValue());
		// userSO.setFunction(APPObjectSO.F_SIMPLE_MEMBRE);
		// userSO.setRole(APPObjectSO.R_PSIMPLE_USER);
		// userSO.setStatus(APPObjectSO.STATUS_ETUDIANT);
		userSO.setVillageArea(balengAreaUi.getSingleValue());
		userSO.setProfession(professionUi.getValue());
		presenter.saveMember(userSO);

	}

	private void handleNewUser() {
		String email = emailLabelUI.getValue();
		String pwd1 = pwdLabelUI.getValue();
		String pwd2 = pwdLabelUI2.getValue();
		if (MethodsUtils.isStringOK(pwd1) && MethodsUtils.isStringOK(pwd2)) {

			if (!pwd1.equals(pwd2)) {
				loginFailUi.setVisible(true);
				loginFailUi.setText("Mots de pass pas identiques!!!");
				return;
			}

			if (MethodsUtils.isStringOK(email)) {
				userSO.setEmail(email);
			} else {
				loginFailUi.setVisible(true);
				loginFailUi.setText("Verifier l'email svp");
				return;
			}

			userSO.setLastName(lastnameUi.getValue());
			userSO.setName(nameUi.getValue());
			userSO.setProfession(professionUi.getValue());
			userSO.setEmail(email);
			userSO.setTel(phoneUi.getValue());
			userSO.setCity(cityUi.getValue());
			userSO.setCountry(livingCountryUi.getSingleValue());
			userSO.setFunction(APPObjectSO.F_SIMPLE_MEMBRE);
			userSO.setRole(APPObjectSO.R_MEMBRE);
			userSO.setZuwu(pwd1);
			userSO.setStatus(APPObjectSO.STATUS_ETUDIANT);
			userSO.setVillageArea(balengAreaUi.getSingleValue());
			userSO.setProfession(professionUi.getValue());
			userSO.setId(-10);
			userSO.setPwdChangeCode("");
			userSO.setPwdChangeCodeValidity(null);
			userSO.setPwdChangeRequired(false);
			presenter.saveMember(userSO);
		}

	}

	@Override
	public Presenter getPresenter() {
		return presenter;
	}

	@Override
	public void showFailText(String failText) {

		loginFailUi.setVisible(true);
		loginFailUi.setText(failText);

	}

	@Override
	protected void onLoad() {
		super.onLoad();
		CoolPasCherUI.CLIENT_FACTORY.addAnimation(materialCardUi, null, 800);
	}

}
