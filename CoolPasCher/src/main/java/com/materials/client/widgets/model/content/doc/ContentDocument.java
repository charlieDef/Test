package com.materials.client.widgets.model.content.doc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.upload.MDFileUploader;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class ContentDocument extends BaseDetails2 implements ContentDocumentView {

	private CAreaSO cAreaSO;
	private Presenter presenter;

	@UiField
	MDFileUploader fileUploadUi;

	@UiField
	MaterialLabel textHeaderLabelUi, imageUrlUi;

	@UiField
	MaterialTextBox fileNameUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiHandler("backButtonUi")
	void onBackToContentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.goBackToContentList();
	}

	public ContentDocument(boolean configure) {

		super(configure);

		add(uiBinder.createAndBindUi(this));

		mdFormUi.setFormHandler(new BBarHandler());

		fileUploadUi.setUploadCompletListener((tempUploaID, fileType) -> {

			// String fileName = array[1];
			String urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + tempUploaID;

			cAreaSO.setRandomId(tempUploaID);
			cAreaSO.setTitel(fileUploadUi.getFileName());
			fileNameUi.setText(fileUploadUi.getFileName());
			cAreaSO.setAreaType(fileType);
			imageUrlUi.setText(urlImg);
		});

		handleEditEnable();

	}

	public void setHeaderTitel(String titel) {
		textHeaderLabelUi.setText(titel);
	}

	@Override
	public void setCAreaSO(CAreaSO cAreaSO) {

		if (cAreaSO != null) {
			String urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?caRandomID=" + cAreaSO.getRandomId();
			setHeaderTitel("Document:: " + cAreaSO.getContentSO().getTitel());
			this.cAreaSO = cAreaSO;
			fileNameUi.setText(cAreaSO.getTitel());
			imageUrlUi.setText(urlImg);
		} else {
			fileNameUi.setText("");
			imageUrlUi.setText("");
		}

	}

	private static ContentDocumentUiBinder uiBinder = GWT.create(ContentDocumentUiBinder.class);

	interface ContentDocumentUiBinder extends UiBinder<Widget, ContentDocument> {
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			fileNameUi.setEnabled(false);
		}

		@Override
		public void onSave() {
			presenter.saveCArea(cAreaSO);
			imageUrlUi.setText(CoolPasCherUI.MODUL_BASE_FILEHELPER + "?caRandomID=" + cAreaSO.getRandomId());
		}

		@Override
		public void onCancel() {

			if (cAreaSO.getId() == -10) {
				presenter.goBackToContentList();
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
}
