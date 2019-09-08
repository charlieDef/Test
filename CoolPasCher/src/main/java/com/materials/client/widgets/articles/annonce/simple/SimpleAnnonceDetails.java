package com.materials.client.widgets.articles.annonce.simple;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.buttonbar.FloatingButtonBar;
import com.materials.client.widgets.ckeditor.MDEditor;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.image.SimpleUploader;
import com.materials.shared.APPConstants;
import com.materials.shared.MethodsUtils;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;

public class SimpleAnnonceDetails extends BaseDetails2 implements SimpleAnnonceDetailView {

	private static SimpleAnnonceDetailsUiBinder uiBinder = GWT.create(SimpleAnnonceDetailsUiBinder.class);

	interface SimpleAnnonceDetailsUiBinder extends UiBinder<Widget, SimpleAnnonceDetails> {
	}

	private ContentSO contentSO;
	private Presenter presenter;

	private MDEditor mdEditor;

	@UiField
	MaterialImage itemImageUi;

	@UiField
	MaterialLabel itemTitelUi;

	@UiField
	MaterialComboBox<String> categorieUi;

	@UiField
	MaterialLabel textHeaderLabelUi, itemDescriptionUi, itemDescription2Ui;

	@UiField
	MaterialDatePicker creationUi;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	MaterialPanel ckEditorUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialTextBox titelUi, prixUi, creatorUi, descriptionUi;

	@UiField
	MaterialCheckBox actifUi, lockUi;

	public SimpleAnnonceDetails(boolean configure) {
		super(configure);

		add(uiBinder.createAndBindUi(this));

		mdFormUi.setFormHandler(new BBarHandler());

		Collection<String> values = CoolPasCherUI.CLIENT_FACTORY.getAnnoncesCategories().values();
		List<String> menusCategories = values.stream().filter(x -> x.contains("||")).collect(Collectors.toList());
		categorieUi.setAcceptableValues(menusCategories);

		fileUploadUi.setVisible(false);
		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.setVisible(false);
		fileUploadUi.addValueChangeHandler(x -> {
			itemImageUi.setUrl(fileUploadUi.getValue());
			contentSO.setRandomId(fileUploadUi.getRandomID());
		});

	}

	@UiHandler({ "descriptionUi" })
	void onDescriptionChanged(ValueChangeEvent<String> event) {
		itemDescriptionUi.setText(descriptionUi.getValue());
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

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setContentSO(ContentSO contentSO) {
		ckEditorUi.clear();
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

		// boxes||
		creationUi.setDate(creation);
		creatorUi.setValue(contentSO.getCreator());
		titelUi.setValue(contentSO.getTitel());
		descriptionUi.setValue(contentSO.getDescription());
		actifUi.setValue(contentSO.isActive());
		lockUi.setValue(contentSO.isLock());
		prixUi.setValue(contentSO.getPrix());

		textHeaderLabelUi.setText("Contenu::" + MethodsUtils.getRecursiveMenu(contentSO));
		ckEditorUi.add(new HTML(contentSO.getLongDescription()));
		categorieUi.setSingleValue(contentSO.getCategory(), false);

		// if (contentSO.getCategory() != null && !contentSO.getCategory().isEmpty()) {
		// String[] sousCAtegory = contentSO.getCategory().split("_");
		// if (sousCAtegory != null) {
		// categorieUi.setSingleValue(sousCAtegory[0], true);
		// }
		//
		// }

	}

	@Override
	public void setMDFormPanel() {
		this.mdFormPanel = mdFormUi;

	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			fileUploadUi.setVisible(true);
			ckEditorUi.clear();

			String lDescription = contentSO != null ? contentSO.getLongDescription() : "";
			mdEditor = new MDEditor();
			ckEditorUi.add(mdEditor);
			mdEditor.setEditorValue(lDescription);
		}

		@Override
		public void onSave() {
			fileUploadUi.setVisible(false);
			contentSO.setPrix("");
			contentSO.setActive(actifUi.getValue());
			contentSO.setDescription(descriptionUi.getValue());
			contentSO.setTitel(titelUi.getValue());
			contentSO.setLock(lockUi.getValue());
			contentSO.setPrix(prixUi.getValue());

			contentSO.setCategory(categorieUi.getSingleValue());
			contentSO.setLongDescription(mdEditor.getEditorValue());
			contentSO.setCreator(CoolPasCherUI.CLIENT_FACTORY.getActualUserSO().getEmail());

			presenter.saveContent(contentSO);
			ckEditorUi.clear();
			ckEditorUi.add(new HTML(contentSO.getLongDescription()));

		}

		@Override
		public void onCancel() {
			fileUploadUi.setVisible(false);
			mdEditor.setEditorValue("");
			ckEditorUi.clear();
			ckEditorUi.add(new HTML(contentSO.getLongDescription()));
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
	public FloatingButtonBar getButtonBar() {
		return mdFormUi.getButtonbar();
	}

}
