package com.materials.client.widgets.contact;

import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.WebSiteSO;
import com.materials.client.widgets.button.MboaButton;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.shared.MethodsUtils;
import com.materials.shared.validator.EmailValidator;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class ContactWidget extends Composite implements ContactWidgetView {

	private static ContactWidgetUiBinder uiBinder = GWT.create(ContactWidgetUiBinder.class);

	interface ContactWidgetUiBinder extends UiBinder<MaterialRow, ContactWidget> {
	}

	private EmailValidator emailValidator;
	private ConfirmationWidget confirmationWidget;
	private Consumer<Boolean> confirmationConsumer = x -> {
	};
	private Presenter presenter;

	@UiField
	MaterialRow validatorUi;

	@UiField
	MaterialCard materialCardUI;

	@UiField
	MaterialButton telUi, telUi2;

	@UiField
	MboaButton sendUi;

	@UiField
	MaterialTextBox emailUi, nameUi, prenomUi;

	@UiField
	MaterialTextArea textAreaUi;

	public ContactWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		materialCardUI.getElement().getStyle().setProperty("borderRadius", "5px");

		WebSiteSO siteSO = CoolPasCherUI.CLIENT_FACTORY.getWebSiteSO();

		emailValidator = new EmailValidator();

		emailUi.addValidator(emailValidator);

		confirmationWidget = new ConfirmationWidget(siteSO.getMailSendSuccessText(), confirmationConsumer);
		confirmationWidget.setBoxTitle("Message envoyé");
		confirmationWidget.desactiveNoButton(true);

		confirmationWidget.getElement().getStyle().setProperty("maxWidth", "500px");
		materialCardUI.add(confirmationWidget);

		sendUi.getElement().getStyle().setProperty("backgroundColor", "#960018");
		sendUi.getElement().getStyle().setProperty("color", "#ffb74d");
		sendUi.getIcon().setIconColor(Color.AMBER_DARKEN_1);

		telUi.setText(siteSO.getTel());
		telUi2.setText("00237 691901930");

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("sendUi")
	void onSend(ClickEvent clickEvent) {

		String email = emailUi.getValue();
		String name = nameUi.getValue();
		String prenom = prenomUi.getValue();
		String text = textAreaUi.getValue();

		if (validatorUi.validate()) {
			if (MethodsUtils.isStringOK(email) && MethodsUtils.isStringOK(name) && MethodsUtils.isStringOK(prenom)
					&& MethodsUtils.isStringOK(text) && emailValidator.isValid(email)) {
				presenter.sendMessage(name, prenom, email, text);
			}
		}
	}

	@Override
	public void showConfirmationLighBox(String headerLabel, String contentLabel, Consumer<Boolean> consumer) {
		confirmationWidget.setLabelContent(contentLabel);
		confirmationWidget.setBoxTitle(headerLabel);
		confirmationWidget.show(consumer);

	}

	@Override
	protected void onLoad() {
		super.onLoad();
		CoolPasCherUI.CLIENT_FACTORY.addAnimation(materialCardUI, null, 800);
	}

}
