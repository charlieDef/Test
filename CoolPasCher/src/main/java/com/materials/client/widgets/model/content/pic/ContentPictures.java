package com.materials.client.widgets.model.content.pic;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.buttonbar.FloatingButtonBar;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.slider.RSliderWidget;
import com.materials.client.widgets.slider.jssor.JssorFlexImgGallery;
import com.materials.client.widgets.upload.image.SimpleUploader;

import gwt.material.design.client.ui.MaterialLabel;

public class ContentPictures extends BaseDetails2 implements ContentPicturesView {

	private ContentSO contentSO;
	private List<CAreaSO> cAreaSOs = new ArrayList<CAreaSO>();
	private Presenter presenter;

	private RSliderWidget sliderWidget;

	@UiField
	SimpleUploader fileUploadUi;

	@UiField
	FlowPanel sliderUi;

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiHandler("backButtonUi")
	void onBackToPhotosList(ClickEvent clickEvent) {
		presenter.backToAlbumPhotoList();

		ClientUtils.addTimer(x -> {
			sliderUi.clear();
		}, 1200);

	}

	public ContentPictures(boolean configure) {
		super(configure);
		add(uiBinder.createAndBindUi(this));
		sliderWidget = new RSliderWidget();
		sliderUi.add(sliderWidget);

		mdFormUi.setFormHandler(new BBarHandler());

		fileUploadUi.setVisible(false);
		fileUploadUi.addValueChangeHandler(x -> {
			// String fileName = array[1];
			String urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + fileUploadUi.getRandomID();
			CAreaSO areaSO = new CAreaSO();
			areaSO.setId(-10);
			areaSO.setTitel(fileUploadUi.getFileName());
			areaSO.setRandomId(fileUploadUi.getRandomID());
			// areaSO.setImageUrl(urlImg);
			areaSO.setContentSO(this.contentSO);
			areaSO.setAreaType("");
			areaSO.setIndex(cAreaSOs.size() + 1);

			cAreaSOs.add(areaSO);
			setCAreaSOs(cAreaSOs);
		});

		// fileUploadUi.setDeleteLast(false);
		// fileUploadUi.setUploadCompletListener((tempUploaID, fileType) -> {
		//
		// // String fileName = array[1];
		// String urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" +
		// tempUploaID;
		// CAreaSO areaSO = new CAreaSO();
		// areaSO.setId(-10);
		// areaSO.setTitel(fileUploadUi.getFileName());
		// areaSO.setRandomId(tempUploaID);
		// // areaSO.setImageUrl(urlImg);
		// areaSO.setContentSO(this.contentSO);
		// areaSO.setAreaType("");
		//
		// cAreaSOs.add(areaSO);
		// setCAreaSOs(cAreaSOs);
		// });

		handleEditEnable();

	}

	private static ContentPicturesUiBinder uiBinder = GWT.create(ContentPicturesUiBinder.class);

	interface ContentPicturesUiBinder extends UiBinder<Widget, ContentPictures> {
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			fileUploadUi.setVisible(true);
		}

		@Override
		public void onSave() {
			fileUploadUi.setVisible(false);
			contentSO.setcAreaSOs(cAreaSOs);
			presenter.saveContentSO(contentSO);
		}

		@Override
		public void onCancel() {
			cAreaSOs.clear();
			fileUploadUi.setVisible(false);
			// setCAreaSOs(contentSO.getcAreaSOs());
			if (cAreaSOs == null || cAreaSOs.isEmpty()) {
				presenter.backToAlbumPhotoList();
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

	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;
		this.cAreaSOs = contentSO.getcAreaSOs();
		textHeaderLabelUi.setText(contentSO.getTitel());
		sliderUi.clear();
		// sliderUi.add(new RSliderWidget(cAreaSOs));

		if (!cAreaSOs.isEmpty()) {
			sliderUi.add(new JssorFlexImgGallery(cAreaSOs));
		}

	}

	private void setCAreaSOs(List<CAreaSO> cAreaSOs2) {
		sliderUi.clear();
		// sliderUi.add(new RSliderWidget(cAreaSOs2));
		sliderUi.add(new JssorFlexImgGallery(cAreaSOs2));
	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}

	public FloatingButtonBar getButtonBar() {
		return mdFormUi.getButtonbar();
	}
}
