package com.materials.client.widgets.model.content.simple;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.image.SimpleUploader;
import com.materials.shared.APPConstants;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.constants.FieldType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class NewAnnonceDetails extends Composite implements NewAnnonceDetailsView {

	private static NewAnnonceDetailsUiBinder uiBinder = GWT.create(NewAnnonceDetailsUiBinder.class);

	interface NewAnnonceDetailsUiBinder extends UiBinder<Widget, NewAnnonceDetails> {
	}

	private BlankValidator<String> blankValidator = new BlankValidator<String>("Entrez une valeur");

	private ContentSO contentSO;
	private Presenter presenter;

	private MaterialCheckBox actifUi, showToHomeUi, lockUi, vipUi;
	private MaterialComboBox<String> typeUi;
	private MaterialTextBox categoryUi, textInfoUi;
	private Consumer<Boolean> saveControllConsumer;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialImage itemImageUi;

	@UiField
	MaterialLabel itemDescriptionUi, itemTitelUi, itemDescription2Ui, revealTitelUi;

	@UiField
	MaterialDatePicker creationUi;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MaterialTextBox titelUi, descriptionUi, description2Ui;

	@UiHandler({ "descriptionUi" })
	void onDescriptionChanged(KeyUpEvent event) {
		itemDescriptionUi.setText(descriptionUi.getValue());
		contentSO.setDescription(descriptionUi.getValue());

		if (saveControllConsumer != null) {
			saveControllConsumer.accept(true);
		}
	}

	@UiHandler({ "description2Ui" })
	void onDescription2Changed(KeyUpEvent event) {
		revealTitelUi.setText(description2Ui.getValue());
		contentSO.setDescription2(description2Ui.getValue());
	}

	@UiHandler({ "titelUi" })
	void onTitelChanged(KeyUpEvent event) {
		itemTitelUi.setText(titelUi.getValue());
		contentSO.setTitel(titelUi.getValue());

		if (saveControllConsumer != null) {
			saveControllConsumer.accept(true);
		}

	}

	public NewAnnonceDetails() {
		initWidget(uiBinder.createAndBindUi(this));
		mdFormUi.enableEdit(false);
		// fileUploadUi.setIconColor(Color.AMBER_DARKEN_3);

		titelUi.addValidator(blankValidator);
		descriptionUi.addValidator(blankValidator);
		titelUi.setValidateOnBlur(true);
		descriptionUi.setValidateOnBlur(true);

		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.addValueChangeHandler(x -> {
			String randomId = fileUploadUi.getRandomID();
			itemImageUi.setUrl(fileUploadUi.getValue());
			contentSO.setRandomId(randomId);

			if (saveControllConsumer != null) {
				saveControllConsumer.accept(true);
			}

		});
		addAdminBox();
	}

	@Override
	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;
		String detscription = contentSO.getDescription() != null ? contentSO.getDescription() : "";
		String titel = contentSO.getTitel() != null ? contentSO.getTitel() : "";
		String imageUrl = contentSO.getTitelImageUrl() != null ? contentSO.getTitelImageUrl() : "";
		Date creation = contentSO.getCreationDate() != null ? contentSO.getCreationDate() : new Date();
		DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

		// item
		itemImageUi.setUrl(imageUrl);
		itemDescriptionUi.setText(detscription);
		itemTitelUi.setText(titel);
		revealTitelUi.setText(contentSO.getDescription2());

		// boxes
		creationUi.setDate(creation);
		titelUi.setValue(contentSO.getTitel());
		descriptionUi.setValue(contentSO.getDescription());
		description2Ui.setValue(contentSO.getDescription2());

		if (CoolPasCherUI.checkAdminMember()) {
			actifUi.setValue(contentSO.isActive());
			showToHomeUi.setValue(contentSO.isShowToHome());
			lockUi.setValue(contentSO.isLock());
			vipUi.setValue(contentSO.isVip());
			categoryUi.setValue(contentSO.getCategory());
			textInfoUi.setValue(contentSO.getTextInfo());
			typeUi.setSingleValue(contentSO.getType());
		}

	}

	@Override
	public void extractFromDetail(ContentSO contentSOToSave) {

		contentSOToSave.setTitel(contentSO.getTitel());
		contentSOToSave.setDescription(contentSO.getDescription());
		contentSOToSave.setDescription2(contentSO.getDescription2());
		contentSOToSave.setRandomId(contentSO.getRandomId());
		if (CoolPasCherUI.checkAdminMember()) {
			contentSOToSave.setActive(actifUi.getValue());
			contentSOToSave.setLock(lockUi.getValue());
			contentSOToSave.setVip(vipUi.getValue());
			contentSOToSave.setShowToHome(showToHomeUi.getValue());
			contentSOToSave.setType(typeUi.getSingleValue());
			contentSOToSave.setTextInfo(textInfoUi.getValue());
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public ContentSO getContentSO() {
		return this.contentSO;
	}

	@Override
	public void setEdit(boolean edit) {
		titelUi.setEnabled(edit);
		descriptionUi.setEnabled(edit);
		// categoryUi.setEnabled(edit);
		description2Ui.setEnabled(edit);
		creationUi.setEnabled(edit);
		fileUploadUi.setVisible(edit);

		if (CoolPasCherUI.checkAdminMember()) {
			actifUi.setEnabled(edit);
			showToHomeUi.setEnabled(edit);
			lockUi.setEnabled(edit);
			vipUi.setEnabled(edit);
			categoryUi.setEnabled(edit);
			textInfoUi.setEnabled(edit);
			typeUi.setEnabled(edit);
		}
	}

	private void addAdminBox() {

		if (CoolPasCherUI.checkAdminMember()) {
			actifUi = new MaterialCheckBox("Actif");
			showToHomeUi = new MaterialCheckBox("Page D'acceuil");
			lockUi = new MaterialCheckBox("Lock");
			categoryUi = new MaterialTextBox("Category");
			textInfoUi = new MaterialTextBox("Text Info");
			vipUi = new MaterialCheckBox("VIP");
			typeUi = new MaterialComboBox<String>();
			typeUi.setLabel("Typ");
			typeUi.setItems(Arrays.asList("", ContentSO.TYPE_DOCUMENT, ContentSO.TYPE_VIDEO, ContentSO.TYPE_MENU,
					ContentSO.TYPE_IMAGE, ContentSO.TYPE_ARTICLE, ContentSO.TYPE_H_ADVERTISEMENT,
					ContentSO.TYPE_V_ADVERTISEMENT, ContentSO.TYPE_FLEX, ContentSO.TYPE_ANNONCE));

			actifUi.setType(CheckBoxType.FILLED);
			vipUi.setType(CheckBoxType.FILLED);
			showToHomeUi.setType(CheckBoxType.FILLED);
			lockUi.setType(CheckBoxType.FILLED);
			categoryUi.setFieldType(FieldType.OUTLINED);
			textInfoUi.setFieldType(FieldType.OUTLINED);
			typeUi.setFieldType(FieldType.OUTLINED);

			mdFormUi.addChild(typeUi, "type");
			mdFormUi.addChild(categoryUi, "category");
			mdFormUi.addChild(textInfoUi, "textinfo");
			mdFormUi.addChild(actifUi, "active");
			mdFormUi.addChild(showToHomeUi, "showToHome");
			mdFormUi.addChild(lockUi, "lock");
			mdFormUi.addChild(vipUi, "vip");
		}
	}

	public void setSaveControllConsumer(Consumer<Boolean> saveControllConsumer) {
		this.saveControllConsumer = saveControllConsumer;
	}

	@Override
	public boolean enableSaveButton() {
		return titelUi.getValue() != null && !titelUi.getValue().isEmpty() && itemImageUi.getUrl() != null
				&& !itemImageUi.getUrl().isEmpty() && descriptionUi.getValue() != null
				&& !descriptionUi.getValue().isEmpty();
	}

}
