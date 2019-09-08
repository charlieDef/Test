package com.materials.client.widgets.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.widgets.button.MboaButton;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.shared.validator.EmailValidator;

import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;

public class LoginWidget extends Composite implements LoginWidgetView {

	private Presenter presenter;

	private static LoginWidgetUiBinder uiBinder = GWT.create(LoginWidgetUiBinder.class);

	interface LoginWidgetUiBinder extends UiBinder<Widget, LoginWidget> {
	}

	private EmailValidator validator = new EmailValidator();

	@UiField
	MaterialTextBox pwdLabelUI, emailUI;

	@UiField
	MaterialDialog materialModalUi;
	@UiField
	MaterialLabel loginFailUi;
	@UiField
	MaterialImage userImageUi;
	@UiField
	MaterialLink linkButtonUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MboaButton connecButtontUi, resetButtontUi;

	public LoginWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		pwdLabelUI.setType(InputType.PASSWORD);
		emailUI.addValidator(validator);

		pwdLabelUI.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {

				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doLogin();
				}

			}
		});

	}

	@UiHandler("connecButtontUi")
	void onOk(ClickEvent clickEvent) {

		doLogin();
	}

	@UiHandler("resetButtontUi")
	void onReset(ClickEvent clickEvent) {
		String email = emailUI.getValue();
		if ((email != null && !email.trim().isEmpty())) {
			presenter.resetPwd(email);
		}
	}

	@UiHandler("linkButtonUi")
	void passOublier(ClickEvent clickEvent) {
		pwdLabelUI.setVisible(false);
		linkButtonUi.setVisible(false);
		connecButtontUi.setVisible(false);
		resetButtontUi.setVisible(true);

		loginFailUi.setValue("");
	}

	@UiHandler({ "emailUI" })
	void onFocus(FocusEvent clickEvent) {
		loginFailUi.setValue("");
		loginFailUi.setVisible(false);

	}

	@UiHandler({ "emailUI" })
	void onValueChanged(ValueChangeEvent<String> clickEvent) {
		loginFailUi.setValue("");
		userImageUi.setUrl("img/a_user.jpg");
		String email = emailUI.getValue();
		if ((email != null && !email.trim().isEmpty())) {
			presenter.fetchUser(emailUI.getValue().trim(), x -> {
				if (x != null && !x.isEmpty()) {
					userImageUi.setUrl(x);
				}
			});
		}
	}

	@UiHandler({ "pwdLabelUI" })
	void onPWDValueChanged(ValueChangeEvent<String> clickEvent) {
		loginFailUi.setValue("");
		loginFailUi.setVisible(false);
	}

	@Override
	public void show() {
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

	@Override
	public void close() {
		materialModalUi.close();
		reset();

	}

	private void reset() {
		pwdLabelUI.setVisible(true);
		pwdLabelUI.setValue("");
		linkButtonUi.setVisible(true);
		connecButtontUi.setVisible(true);
		resetButtontUi.setVisible(false);
		emailUI.setValue("");
		loginFailUi.setValue("");

	}

	@Override
	public void handleWrongPwd() {
		connecButtontUi.setEnabled(true);
		loginFailUi.setVisible(true);
		loginFailUi.setValue("Mot de pass incorrect!!");
	}

	private void doLogin() {
		String email = emailUI.getValue().trim();
		String pwd = pwdLabelUI.getValue().trim();
		if ((email != null && !email.trim().isEmpty()) && (pwd != null && !pwd.trim().isEmpty())) {
			presenter.checkLogin(email, pwd);
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
		ConfirmationWidget confirmationWidget = CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget();
		confirmationWidget.setBoxTitle("Succes");
		confirmationWidget.setLabelContent("Nouveau mot de passe généré et envoyé à l'adresse: " + emailUI.getValue());
		confirmationWidget.show(x -> CoolPasCherUI.CLIENT_FACTORY.handleCurrentHistory());
	}

}
