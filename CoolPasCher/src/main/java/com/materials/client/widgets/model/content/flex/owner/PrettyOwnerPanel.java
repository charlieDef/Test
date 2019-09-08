package com.materials.client.widgets.model.content.flex.owner;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.articles.annonce.simple.SimpleViewAbleAnnonceDetailsView;
import com.materials.client.widgets.utils.WidgetUtils;

import gwt.material.design.addins.client.pathanimator.MaterialPathAnimator;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.FieldType;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class PrettyOwnerPanel extends Composite implements PrettyOwnerView {

	private static OwnerPanelUiBinder uiBinder = GWT.create(OwnerPanelUiBinder.class);

	interface OwnerPanelUiBinder extends UiBinder<Widget, PrettyOwnerPanel> {
	}

	private SimpleViewAbleAnnonceDetailsView.Presenter presenter;

	private MaterialPathAnimator animator = new MaterialPathAnimator();

	MaterialLabel label = new MaterialLabel("---");

	@UiField
	MaterialLabel labelBigUi, labelSmallUi, annonceurUi;

	@UiField
	MaterialImage imageUi;

	@UiField
	MaterialButton btnTelSource, btnMailSource, btnQuestionSource;

	@UiField
	MaterialRow contactRowUi;

	@UiField
	MaterialTextBox labelCityDistrictUi, labelCountryUi;

	private MaterialTextBox email, prenom;
	private MaterialTextArea message, question;

	@UiField
	MaterialCard cardUi;

	@UiField
	MaterialPanel pathAnimatorUi;

	public PrettyOwnerPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		WidgetUtils.addToViewPort(labelBigUi);
		WidgetUtils.addToViewPort(labelSmallUi);

		cardUi.getElement().getStyle().setProperty("borderRadius", "5px");

		btnTelSource.getElement().getStyle().setProperty("backgroundColor", "#960018");
		btnTelSource.getElement().getStyle().setProperty("color", "#ffb74d");
		btnTelSource.setType(ButtonType.OUTLINED);
		btnTelSource.getElement().getStyle().setProperty("border", "1px solid rgb(150, 0, 24)");

		btnMailSource.getElement().getStyle().setProperty("backgroundColor", "#960018");
		btnMailSource.getElement().getStyle().setProperty("color", "#ffb74d");
		btnMailSource.setType(ButtonType.OUTLINED);
		btnMailSource.getElement().getStyle().setProperty("border", "1px solid rgb(150, 0, 24)");

		btnQuestionSource.getElement().getStyle().setProperty("backgroundColor", "#960018");
		btnQuestionSource.getElement().getStyle().setProperty("color", "#ffb74d");
		btnQuestionSource.setType(ButtonType.OUTLINED);
		btnQuestionSource.getElement().getStyle().setProperty("border", "1px solid rgb(150, 0, 24)");
	}

	@UiHandler("btnTelSource")
	void onTelSource(ClickEvent e) {
		contactRowUi.clear();
		contactRowUi.setHeight("100%");

		MaterialCard cardTarget = getTelCard();
		contactRowUi.add(cardTarget);

		animator.setSourceElement(btnTelSource.getElement());
		animator.setTargetElement(cardTarget.getElement());
		animator.setShadow(5);
		animator.setBackgroundColor(Color.RED_DARKEN_4);

		animator.animate();
		btnTelSource.setEnabled(false);
		btnMailSource.setEnabled(true);
		btnQuestionSource.setEnabled(true);
	}

	@UiHandler("btnMailSource")
	void onMailSource(ClickEvent e) {
		contactRowUi.clear();
		contactRowUi.setHeight("100%");

		MaterialCard cardTarget = getMailCard();
		contactRowUi.add(cardTarget);

		animator.setSourceElement(btnMailSource.getElement());
		animator.setTargetElement(cardTarget.getElement());
		animator.setShadow(5);
		animator.setBackgroundColor(Color.RED_DARKEN_4);

		animator.animate();
		btnMailSource.setEnabled(false);
		btnTelSource.setEnabled(true);
		btnQuestionSource.setEnabled(true);
	}

	@UiHandler("btnQuestionSource")
	void onQuestionSource(ClickEvent e) {
		contactRowUi.clear();
		contactRowUi.setHeight("100%");

		MaterialCard cardTarget = getQuestionCard();
		contactRowUi.add(cardTarget);

		animator.setSourceElement(btnQuestionSource.getElement());
		animator.setTargetElement(cardTarget.getElement());
		animator.setShadow(5);
		animator.setBackgroundColor(Color.RED_DARKEN_4);

		animator.animate();
		btnQuestionSource.setEnabled(false);
		btnMailSource.setEnabled(true);
		btnTelSource.setEnabled(true);

	}

	private MaterialCard getTelCard() {

		MaterialCard card = new MaterialCard();
		card.getElement().getStyle().setProperty("borderTop", "1px solid #ddd");
		card.getElement().getStyle().setProperty("borderBottom", "1px solid #ddd");
		card.setMargin(0);
		card.setBackgroundColor(Color.WHITE);
		card.setOpacity(0);
		card.setShadow(0);

		MaterialCardContent cardContent = new MaterialCardContent();
		cardContent.setTextColor(Color.BLACK);
		card.add(cardContent);

		MaterialCardTitle cardTitle = new MaterialCardTitle();
		cardTitle.setText("Contacts de l'annonceur");
		cardTitle.setIconType(IconType.HIGHLIGHT_OFF);
		cardTitle.setIconPosition(IconPosition.RIGHT);
		cardContent.add(cardTitle);

		cardContent.add(label);

		cardTitle.getIcon().setTitle("Fermer");
		cardTitle.getIcon().addClickHandler(c -> {
			animator.reverseAnimate();
			contactRowUi.setHeight("0");
			btnTelSource.setEnabled(true);
		});

		return card;
	}

	private MaterialCard getMailCard() {

		MaterialCard card = new MaterialCard();
		card.getElement().getStyle().setProperty("borderTop", "1px solid #ddd");
		card.getElement().getStyle().setProperty("borderBottom", "1px solid #ddd");
		card.setMargin(0);
		card.setBackgroundColor(Color.WHITE);
		card.setOpacity(0);
		card.setShadow(0);

		MaterialCardContent cardContent = new MaterialCardContent();
		cardContent.setTextColor(Color.BLACK);
		card.add(cardContent);

		MaterialCardTitle cardTitle = new MaterialCardTitle();
		cardTitle.setText("Nouveau message à l'annonceur ");
		cardTitle.setFontSize("13px");
		cardTitle.getElement().getStyle().setColor("#bbb");
		cardTitle.setIconType(IconType.HIGHLIGHT_OFF);
		cardTitle.setIconPosition(IconPosition.RIGHT);
		cardContent.add(cardTitle);

		email = new MaterialTextBox();
		email.setLabel("Votre email");
		email.setFieldType(FieldType.OUTLINED);
		message = new MaterialTextArea();
		message.setLabel("Message");
		message.setFieldType(FieldType.OUTLINED);
		cardContent.add(email);
		cardContent.add(message);

		MaterialButton send = new MaterialButton("Envoyer", IconType.SEND);
		send.getElement().getStyle().setProperty("backgroundColor", " #960018");
		send.getElement().getStyle().setProperty("color", " #ffb74d");
		send.setIconPosition(IconPosition.RIGHT);
		send.setShadow(0);
		cardContent.add(send);

		cardTitle.getIcon().setTitle("Fermer");
		cardTitle.getIcon().getElement().getStyle().setColor("black");
		cardTitle.getIcon().addClickHandler(c -> {
			animator.reverseAnimate();
			contactRowUi.setHeight("0");
			btnMailSource.setEnabled(true);
		});

		send.addClickHandler(c -> {

			if (!email.getValue().isEmpty() && !message.getValue().isEmpty()) {
				animator.reverseAnimate();
				contactRowUi.setHeight("0");
				btnMailSource.setEnabled(true);
				presenter.sendMail(email.getValue(), message.getValue());
			}

		});

		return card;
	}

	private MaterialCard getQuestionCard() {

		MaterialCard card = new MaterialCard();
		card.getElement().getStyle().setProperty("borderTop", "1px solid #ddd");
		card.getElement().getStyle().setProperty("borderBottom", "1px solid #ddd");
		card.setMargin(0);
		card.setBackgroundColor(Color.WHITE);
		card.setOpacity(0);
		card.setShadow(0);

		MaterialCardContent cardContent = new MaterialCardContent();
		cardContent.setTextColor(Color.BLACK);
		card.add(cardContent);

		MaterialCardTitle cardTitle = new MaterialCardTitle();
		cardTitle.setText("Réponse très bientot en dessous ( Questions / Réponses ) ");
		cardTitle.setFontSize("13px");
		cardTitle.getElement().getStyle().setColor("#bbb");
		cardTitle.setIconType(IconType.HIGHLIGHT_OFF);
		cardTitle.setIconPosition(IconPosition.RIGHT);
		cardContent.add(cardTitle);

		prenom = new MaterialTextBox();
		prenom.setLabel("Votre prenom");
		prenom.setFieldType(FieldType.OUTLINED);
		cardContent.add(prenom);

		question = new MaterialTextArea();
		question.setLabel("Votre question");
		question.setFieldType(FieldType.OUTLINED);
		cardContent.add(question);

		MaterialButton send = new MaterialButton("Poster", IconType.SEND);
		send.getElement().getStyle().setProperty("backgroundColor", " #960018");
		send.getElement().getStyle().setProperty("color", " #ffb74d");
		send.setIconPosition(IconPosition.RIGHT);
		send.setShadow(0);
		cardContent.add(send);

		cardTitle.getIcon().setTitle("Fermer");
		cardTitle.getIcon().getElement().getStyle().setColor("black");
		cardTitle.getIcon().addClickHandler(c -> {
			animator.reverseAnimate();
			contactRowUi.setHeight("0");
			btnQuestionSource.setEnabled(true);
		});

		send.addClickHandler(c -> {

			presenter.saveNewQuestion(prenom.getValue(), question.getValue());
			animator.reverseAnimate();
			contactRowUi.setHeight("0");
			btnQuestionSource.setEnabled(true);

		});

		return card;
	}

	@Override
	public void setPresenter(SimpleViewAbleAnnonceDetailsView.Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setOwnerInfo(Map<String, String> map) {
		String imageUrl = map.get(ContentSO.PP_CREATOR_RANDOM_ID);
		String creator = map.get(ContentSO.PP_CREATOR_NAME) + " " + map.get(ContentSO.PP_CREATOR_LASTNAME);

		String city = getMapValue(map.get(ContentSO.PP_CREATOR_CITY));
		String type = getMapValue(map.get(ContentSO.PP_CREATOR_TYPE));

		imageUi.setUrl(getUserImageUrl(imageUrl));
		labelBigUi.setValue(creator);
		labelSmallUi.setValue(map.get(ContentSO.PP_CREATOR_TEXT));
		labelCityDistrictUi.setValue(city);
		labelCountryUi.setValue(getMapValue(map.get(ContentSO.PP_CREATOR_COUNTRY)));
		label.setValue(getMapValue(map.get(ContentSO.PP_CREATOR_TEL)));

		annonceurUi.setValue("Annonceur [ " + type + " ]");

	}

	public String getUserImageUrl(String randomId) {
		String urlImg = "img/a_user.jpg";
		if (randomId != null && !randomId.isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?userID=" + randomId;
		}
		return urlImg;
	}

	private String getMapValue(String value) {

		if (value != null) {
			return value;
		} else {
			return "---";
		}

	}

}
