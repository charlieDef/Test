package com.materials.client.widgets.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.button.MboaButton;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.shared.validator.EmailValidator;

import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.incubator.client.loadingstate.AppLoadingState;
import gwt.material.design.incubator.client.loadingstate.constants.State;

public class LoginWidget2 extends Composite implements LoginWidgetView2 {

	private Presenter presenter;

	private boolean isOpen = false;

	private static LoginWidget2UiBinder uiBinder = GWT.create(LoginWidget2UiBinder.class);

	interface LoginWidget2UiBinder extends UiBinder<Widget, LoginWidget2> {
	}

	private EmailValidator validator = new EmailValidator();

	@UiField
	MaterialTextBox pwdBoxUI, pwdBoxRepeatUI, emailUI, pwdResetCodeUI;

	@UiField
	MaterialDialog materialModalUi;
	@UiField
	MaterialImage userImageUi;
	@UiField
	MaterialLink linkButtonUi;

	@UiField
	MaterialPanel target;

	@UiField
	AppLoadingState appLoadingState;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MboaButton connecButtontUi, resetButtontUi;

	@UiField
	MaterialRow validatorUi, pwdResetCodeRowUI, pwdResetRowUI;

	public LoginWidget2() {
		initWidget(uiBinder.createAndBindUi(this));

		appLoadingState.setTarget(target);
		appLoadingState.addSuccessHandler(event -> appLoadingState.reset(target));
		appLoadingState.addErrorHandler(event -> appLoadingState.reset(target));

		materialModalUi.addCloseHandler(x -> {
			isOpen = false;
		});

		pwdBoxUI.setType(InputType.PASSWORD);
		emailUI.addValidator(validator);

		pwdBoxUI.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {

				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (validatorUi.validate()) {
						appLoadingState.setState(State.LOADING, "Authentification...", "Veuillez patienter svp.");
						new Timer() {
							@Override
							public void run() {
								doLogin();
							}
						}.schedule(600);
					}
				}
			}
		});
	}

	@UiHandler("connecButtontUi")
	void onOk(ClickEvent clickEvent) {

		if (connecButtontUi.getText().equals("Changer Pwd")) {

			appLoadingState.setState(State.LOADING, "Reinitialisation du pwd en cour...", "Veuillez patienter svp.");

			new Timer() {
				@Override
				public void run() {
					doResetPwd();
				}
			}.schedule(2000);

		} else if (validatorUi.validate()) {
			appLoadingState.setState(State.LOADING, "Authentification...", "Veuillez patienter svp.");
			new Timer() {
				@Override
				public void run() {
					doLogin();
				}
			}.schedule(1000);
		}
	}

	@UiHandler("resetButtontUi")
	void onReset(ClickEvent clickEvent) {
		String email = emailUI.getValue();
		if ((email != null && !email.trim().isEmpty())) {

			appLoadingState.setState(State.LOADING, "Mail en cour d'envoie...", "Veuillez patienter svp.");
			new Timer() {
				@Override
				public void run() {
					presenter.resetPwdCode(email);
				}
			}.schedule(2000);
		}
	}

	@UiHandler("linkButtonUi")
	void passOublier(ClickEvent clickEvent) {

		pwdBoxUI.setVisible(false);
		linkButtonUi.setVisible(false);
		connecButtontUi.setVisible(false);
		resetButtontUi.setVisible(true);

		// loginFailUi.setValue("");
	}

	@UiHandler({ "emailUI" })
	void onFocus(FocusEvent clickEvent) {
		// loginFailUi.setValue("");
		// loginFailUi.setVisible(false);

	}

	@UiHandler({ "emailUI" })
	void onValueChanged(ValueChangeEvent<String> clickEvent) {
		// loginFailUi.setValue("");
		userImageUi.setUrl("img/a_user.jpg");
		String email = emailUI.getValue();
		if ((email != null && !email.trim().isEmpty())) {
			presenter.fetchUser(emailUI.getValue().trim(), x -> {

				if (x != null && x.getUserImageUrl() != null && !x.getUserImageUrl().isEmpty()) {
					userImageUi.setUrl(x.getUserImageUrl());
				}

				pwdResetCodeRowUI.setVisible(x.isPwdChangeRequired());
				pwdResetRowUI.setVisible(x.isPwdChangeRequired());

				if (x.isPwdChangeRequired()) {
					connecButtontUi.setText("Changer Pwd");
					linkButtonUi.setVisible(false);
					ClientUtils.addTimer(w -> {
						pwdResetCodeUI.setFocus(true);
					}, 300);
				}

			});
		}
	}

	@UiHandler({ "pwdBoxUI" })
	void onPWDValueChanged(ValueChangeEvent<String> clickEvent) {
		// loginFailUi.setValue("");
		// loginFailUi.setVisible(false);
	}

	@Override
	public void show() {
		if (!isOpen) {
			isOpen = true;
			userImageUi.setUrl("img/a_user.jpg");
			reset();
			materialModalUi.open();
			new Timer() {
				@Override
				public void run() {
					emailUI.setFocus(true);
				}
			}.schedule(500);
		}
	}

	@Override
	public void close() {
		materialModalUi.close();
		reset();

	}

	private void reset() {
		pwdBoxUI.setVisible(true);
		pwdBoxUI.setValue("");
		linkButtonUi.setVisible(true);
		connecButtontUi.setVisible(true);
		resetButtontUi.setVisible(false);
		emailUI.setValue("");

		connecButtontUi.setText("Connection");

		pwdResetCodeRowUI.setVisible(false);
		pwdResetRowUI.setVisible(false);
		linkButtonUi.setVisible(true);

		// loginFailUi.setValue("");

	}

	@Override
	public void handleWrongPwd() {
		appLoadingState.setState(State.ERROR, "Echec de connexion", "Vérifiez svp vos donnés de connexion.");
		// new Timer() {
		// @Override
		// public void run() {
		// appLoadingState.setState(State.ERROR, "Echec de connexion", "Vérifiez svp vos
		// donnés de connexion.");
		// }
		// }.schedule(2000);
		// connecButtontUi.setEnabled(true);
		// loginFailUi.setVisible(true);
		// loginFailUi.setValue("Mot de pass incorrect!!");
	}

	@Override
	public void handleWrongPwd(String message) {
		appLoadingState.setState(State.ERROR, "Echec", message);
		// new Timer() {
		// @Override
		// public void run() {
		// appLoadingState.setState(State.ERROR, "Echec de connexion", "Vérifiez svp vos
		// donnés de connexion.");
		// }
		// }.schedule(2000);
		// connecButtontUi.setEnabled(true);
		// loginFailUi.setVisible(true);
		// loginFailUi.setValue("Mot de pass incorrect!!");
	}

	@Override
	public void handleSuccessPwd() {

		appLoadingState.setState(State.SUCCESS, "Success de connexion", "Redirection...");
		new Timer() {
			@Override
			public void run() {
				close();

				Place place = CoolPasCherUI.CLIENT_FACTORY.getPlaceController().getWhere();
				if (place instanceof MenuPlace) {
					MenuPlace menuPlace = (MenuPlace) place;
					CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
							.goTo(new MenuPlace().handleMenu("start", menuPlace.getMenuClicked(), true));
				} else {
					CoolPasCherUI.CLIENT_FACTORY.handleCurrentHistory();
				}

			}
		}.schedule(1000);

	}

	private void doLogin() {
		String email = emailUI.getValue().trim();
		String pwd = pwdBoxUI.getValue().trim();
		if ((email != null && !email.trim().isEmpty()) && (pwd != null && !pwd.trim().isEmpty())) {
			presenter.checkLogin(email, pwd);
		}
	}

	private void doResetPwd() {
		String email = emailUI.getValue().trim();
		String pwd = pwdBoxUI.getValue().trim();
		String resetPwdCde = pwdResetCodeUI.getValue().trim();
		String pwdRepeat = pwdBoxRepeatUI.getValue().trim();

		if ((email != null && !email.trim().isEmpty()) && (pwd != null && !pwd.trim().isEmpty())
				&& (pwdRepeat != null && !pwdRepeat.trim().isEmpty())
				&& (resetPwdCde != null && !resetPwdCde.trim().isEmpty())) {

			presenter.resetPwd(email, resetPwdCde, pwd, pwdRepeat);
		}
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
	public void showPwdResetSuccesMessage() {

		appLoadingState.setState(State.SUCCESS, "Mot de passe changé avec succès!!", "Redirection...");
		close();
		//
		// new Timer() {
		// @Override
		// public void run() {
		// close();
		// MBoaOnlineUI.CLIENT_FACTORY.handleCurrentHistory();
		// }
		// }.schedule(3000);

		ConfirmationWidget confirmationWidget = CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget();
		confirmationWidget.setBoxTitle("Succès");
		confirmationWidget.setLabelContent("Mot de passe changé avec succès!!");
		confirmationWidget.show(x -> CoolPasCherUI.CLIENT_FACTORY.handleCurrentHistory());
	}

	@Override
	public void showPwdResetCodeSuccesMessage() {
		// ConfirmationWidget confirmationWidget =
		// MBoaOnlineUI.CLIENT_FACTORY.getConfirmationWidget();
		// confirmationWidget.setBoxTitle("Verifiez votre mailbox svp");
		// confirmationWidget
		// .setLabelContent("Informations pour reinitialisation de pwd envoyé à
		// l'adresse: " + emailUI.getValue());
		// confirmationWidget.show(x ->
		// MBoaOnlineUI.CLIENT_FACTORY.handleCurrentHistory());

		appLoadingState.setState(State.SUCCESS, "Succès", "Redirection...");
		close();

		ConfirmationWidget confirmationWidget = CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget();
		confirmationWidget.setBoxTitle("Verifiez votre mailbox svp");
		confirmationWidget
				.setLabelContent("Informations pour reinitialisation de pwd envoyé à l'adresse: " + emailUI.getValue());
		confirmationWidget.show(x -> CoolPasCherUI.CLIENT_FACTORY.handleCurrentHistory());
	}

}
