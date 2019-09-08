package com.materials.client.widgets.articles.annonce.simple;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.model.content.flex.owner.PrettyOwnerPanel2;
import com.materials.client.widgets.model.content.flex.owner.PrettyOwnerView2;
import com.materials.client.widgets.question.QuestionReponseView;
import com.materials.client.widgets.slider.jssor.JssorImgGallery;
import com.materials.client.widgets.utils.WidgetUtils;
import com.materials.shared.APPConstants;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextBox;

public class SimpleViewAbleAnnonceDetails extends MaterialPanel implements SimpleViewAbleAnnonceDetailsView {

	private static AnnonceDetailsUiBinder uiBinder = GWT.create(AnnonceDetailsUiBinder.class);

	interface AnnonceDetailsUiBinder extends UiBinder<MaterialPanel, SimpleViewAbleAnnonceDetails> {
	}

	// interface Driver extends SimpleBeanEditorDriver<ContentSO,
	// AnnonceDetails> {
	// }
	//
	// private static Driver driver = GWT.create(Driver.class);

	private QuestionReponseView collapsibleComment;
	private PrettyOwnerPanel2 ownerPanel;

	private Presenter presenter;
	private ContentSO contentSO;
	private MaterialCard materialCard;

	@UiField
	MaterialPanel panelCommentUi, panelOwnerUi, sliderUi, materialPanelUi;

	@UiField
	MaterialCard contentUi;

	@UiField
	MaterialRow materialRow1Ui;

	@UiField
	MaterialTextBox prixlabelEditor;

	@UiField
	MaterialColumn columnBottomLeft, columnBottomRight, materialColumnSliderLeftUi, materialColumnSliderRightUi;

	// @UiField
	// MaterialTextBox categorieUi;

	@UiField
	HTMLPanel longDescriptionUi;

	@UiField
	MaterialLabel labelUi;

	@UiField
	MaterialSwitch favoritsUi;

	@UiField
	MaterialLabel favoritsLabelUi;

	@UiField
	MaterialIcon shareUi;

	public SimpleViewAbleAnnonceDetails() {
		add(uiBinder.createAndBindUi(this));

		WidgetUtils.addToViewPort(labelUi);
		contentUi.getElement().getStyle().setProperty("borderRadius", "5px");
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@UiHandler("favoritsUi")
	public void onSwitch(ValueChangeEvent<Boolean> changeEvent) {

		if (favoritsUi.getValue()) {
			presenter.addToFavorits(contentSO.getId());
		} else {
			presenter.removeToFavorits(contentSO.getId());
		}
		favoritsUi.setTitle(favoritsUi.getValue() ? "Retirer des favorits" : "Ajouter comme favorit");
	}

	@Override
	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;

		// driver.edit(contentSO);

		// MaterialLoader.loading(true, sliderUi);

		panelOwnerUi.clear();
		panelCommentUi.clear();

		labelUi.setValue(contentSO.getCategory() + " || " + contentSO.getTitel());

		Date creation = contentSO.getCreationDate() != null ? contentSO.getCreationDate() : new Date();
		DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

		// categorieUi.setValue(contentSO.getCategory());
		// nummerUi.setValue("" + contentSO.getId());
		// viewsUi.setValue("" + contentSO.getViewed());
		prixlabelEditor.setValue(contentSO.getPrix());

		prixlabelEditor.getLabel().getElement().getStyle().setProperty("fontSize", "20px");

		longDescriptionUi.clear();
		longDescriptionUi.add(new HTML(contentSO.getLongDescription()));

		// collapsibleComment = new QuestionReponsePanel();
		// collapsibleComment.setPresenter(presenter);
		// collapsibleComment.loadQuestions(contentSO);
		//
		ownerPanel = new PrettyOwnerPanel2();
		// ownerPanel.setPresenter(presenter);
		panelOwnerUi.add(ownerPanel);

		ownerPanel.setOwnerInfo(contentSO.getContentProperties());
		//
		// panelCommentUi.add(collapsibleComment);

		if (CoolPasCherUI.checkLoggedMember()) {
			favoritsUi.setVisible(true);
			favoritsLabelUi.setVisible(true);
			boolean isFavorit = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO().annonceIsFavorit(contentSO.getId());
			favoritsUi.setValue(isFavorit);
			favoritsUi.setTitle(isFavorit ? "Retirer des favorits" : "Ajouter comme favorit");
		}

		if (contentSO.getcAreaSOs() != null && contentSO.getcAreaSOs().size() > 0) {
			sliderUi.add(new JssorImgGallery(contentSO.getcAreaSOs()));
		}

	}

	@Override
	public PrettyOwnerView2 getPrettyOwnerView() {
		return ownerPanel;
	}

	@Override
	public QuestionReponseView getQuestionReponseView() {
		return collapsibleComment;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		MaterialWidget button = CoolPasCherUI.CLIENT_FACTORY.getBackButton();
		materialPanelUi.add(button);
		CoolPasCherUI.CLIENT_FACTORY.addAnimation(button, null, 500);

	}

}
