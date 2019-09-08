package com.materials.client.widgets.model.content;

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
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.image.SimpleUploader;
import com.materials.shared.APPConstants;
import com.materials.shared.MethodsUtils;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class ContentDetails extends BaseDetails2 implements ContentDetailsView {

	private static ContentDetailsUiBinder uiBinder = GWT.create(ContentDetailsUiBinder.class);

	interface ContentDetailsUiBinder extends UiBinder<Widget, ContentDetails> {
	}

	private ContentSO contentSO;
	private Presenter presenter;

	@UiField
	MaterialImage itemImageUi;

	@UiField
	MaterialLabel itemTitelUi;

	@UiField
	MaterialLabel textHeaderLabelUi, itemDescriptionUi, itemDescription2Ui;

	@UiField
	MaterialDatePicker creationUi;

	@UiField
	MaterialIntegerBox viewAbleUi;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialTextBox titelUi, creatorUi, categoryUi, descriptionUi, description2Ui, menuTextBoxUi, linkUi;

	@UiField
	MaterialCheckBox actifUi, showToHomeUi, lockUi, internUi;

	@UiField
	MaterialComboBox<String> typeUi;

	@UiHandler({ "descriptionUi" })
	void onDescriptionChanged(ValueChangeEvent<String> event) {
		itemDescriptionUi.setText(descriptionUi.getValue());
	}

	@UiHandler({ "description2Ui" })
	void onDescription2Changed(ValueChangeEvent<String> event) {
		itemDescription2Ui.setText(description2Ui.getValue());
	}

	@UiHandler({ "titelUi" })
	void onTitelChanged(ValueChangeEvent<String> event) {
		itemTitelUi.setText(titelUi.getValue());
	}

	@UiHandler("backButtonUi")
	void onBackToContentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToContentList();
		ClientUtils.addTimer(x -> itemImageUi.setUrl(""), 500);
	}

	public ContentDetails(boolean configure) {
		super(configure);

		add(uiBinder.createAndBindUi(this));

		mdFormUi.setFormHandler(new BBarHandler());

		// categoryUi.setItems(Arrays.asList("", "Reunion", "Culture", "Sport",
		// "Formation", "Cuisine", "MenuContent",
		// "Mode", "Voyage", "Lifestyle"));

		typeUi.setItems(Arrays.asList("", ContentSO.TYPE_DOCUMENT, ContentSO.TYPE_VIDEO, ContentSO.TYPE_MENU,
				ContentSO.TYPE_IMAGE, ContentSO.TYPE_ARTICLE, ContentSO.TYPE_H_ADVERTISEMENT,
				ContentSO.TYPE_V_ADVERTISEMENT, ContentSO.TYPE_FLEX, ContentSO.TYPE_ANNONCE));

		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.setVisible(false);
		fileUploadUi.addValueChangeHandler(x -> {
			itemImageUi.setUrl(fileUploadUi.getValue());
			contentSO.setRandomId(fileUploadUi.getRandomID());
		});

		// fileUploadUi.setUploadCompletListener((tempUploaID, fileType) -> {
		//
		// // String fileName = array[1];
		// String urlImg = PrebaalUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" +
		// tempUploaID;
		// itemImageUi.setUrl(urlImg);
		// // contentSO.setTitelImageUrl(urlImg);
		// contentSO.setRandomId(tempUploaID);
		// });

		handleEditEnable();

	}

	public void setHeaderTitel(String titel) {
		textHeaderLabelUi.setText(titel);

	}

	@Override
	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;
		String detscription = contentSO.getDescription() != null ? contentSO.getDescription() : "";
		String titel = contentSO.getTitel() != null ? contentSO.getTitel() : "";
		String category = contentSO.getCategory() != null ? contentSO.getCategory() : "";
		String imageUrl = contentSO.getTitelImageUrl() != null ? contentSO.getTitelImageUrl() : "";
		Date creation = contentSO.getCreationDate() != null ? contentSO.getCreationDate() : new Date();
		DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

		// item
		itemImageUi.setUrl(imageUrl);
		itemDescriptionUi.setText(detscription);
		itemTitelUi.setText(titel);
		viewAbleUi.setValue(contentSO.getViewAble());
		linkUi.setValue(contentSO.getTextInfo());

		// boxes
		creationUi.setDate(creation);
		titelUi.setValue(contentSO.getTitel());
		descriptionUi.setValue(contentSO.getDescription());
		description2Ui.setValue(contentSO.getDescription2());
		actifUi.setValue(contentSO.isActive());
		lockUi.setValue(contentSO.isLock());
		internUi.setValue(contentSO.isIntern());
		typeUi.setSingleValue(contentSO.getType());
		showToHomeUi.setValue(contentSO.isShowToHome());
		// categoryUi.setSingleValue(contentSO.getCategory());
		categoryUi.setValue(contentSO.getCategory());
		creatorUi.setValue(contentSO.getCreator());

		if (contentSO.getMenuSO() != null) {
			menuTextBoxUi.setValue(contentSO.getMenuSO().getName());
		}
		textHeaderLabelUi.setText("Contenu::" + MethodsUtils.getRecursiveMenu(contentSO));

	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			fileUploadUi.setVisible(true);
		}

		@Override
		public void onSave() {
			fileUploadUi.setVisible(false);

			contentSO.setActive(actifUi.getValue());
			contentSO.setDescription(descriptionUi.getValue());
			contentSO.setDescription2(description2Ui.getValue());
			contentSO.setTitel(titelUi.getValue());
			contentSO.setCategory(categoryUi.getValue());
			contentSO.setIntern(internUi.getValue());
			contentSO.setLock(lockUi.getValue());
			contentSO.setShowToHome(showToHomeUi.getValue());
			contentSO.setViewAble(viewAbleUi.getValue());
			contentSO.setType(typeUi.getSingleValue());
			contentSO.setCreator(creatorUi.getValue());
			contentSO.setTextInfo(linkUi.getValue());

			checkType();

			presenter.saveContent(contentSO);
		}

		@Override
		public void onCancel() {
			fileUploadUi.setVisible(false);
			if (contentSO.getId() == -10) {
				presenter.backToContentList();
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

	private void checkType() {
		// on new --> ctype == document --> create new carea
		if (contentSO.getId() == -10 && contentSO.getType().equals(ContentSO.TYPE_DOCUMENT)) {
			Date date = new Date();
			CAreaSO areaSO = new CAreaSO();
			areaSO.setAreaType("DOC");
			areaSO.setCreationDate(date);
			areaSO.setId(-10);
			areaSO.setLock(false);
			areaSO.setTitel("CArea pour le contenu " + contentSO.getTitel() + " [" + date.getTime() + "]");
			contentSO.addCAreaSOs(areaSO);
		}
	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}

}
