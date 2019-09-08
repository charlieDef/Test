package com.materials.client.widgets.model.content.flex.readOnly;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.model.CommentSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.comments.collapsible.CollapsibleComment;
import com.materials.client.widgets.form.FlexFormPanel;
import com.materials.client.widgets.model.content.flex.owner.OwnerPanel;
import com.materials.client.widgets.slider.jssor.HTMLFlexImageGallery;
import com.materials.client.widgets.utils.WidgetUtils;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class FlexContentDetailsReadOnly extends Composite implements FlexContentDetailsReadOnlyView {

	private static FlexContentDetailsReadOnlyUiBinder uiBinder = GWT.create(FlexContentDetailsReadOnlyUiBinder.class);

	interface FlexContentDetailsReadOnlyUiBinder extends UiBinder<MaterialCard, FlexContentDetailsReadOnly> {
	}

	private HTMLFlexImageGallery htmlFlexImageGallery;
	private CollapsibleComment collapsibleComment;
	private OwnerPanel ownerPanel;
	private FlexFormPanel flexFormPanel;

	private Presenter presenter;
	private ContentSO contentSO;

	@UiField
	MaterialPanel panelCommentUi, panelOwnerUi;

	@UiField
	MaterialRow materialRow1Ui;

	@UiField
	MaterialColumn columnBottomLeft, columnBottomRight, materialColumnSliderLeftUi, materialColumnSliderRightUi;

	@UiField
	MaterialLabel labelUi;

	public FlexContentDetailsReadOnly() {
		initWidget(uiBinder.createAndBindUi(this));

		WidgetUtils.addToViewPort(labelUi);

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;

		materialColumnSliderLeftUi.clear();
		// materialColumnSliderRightUi.clear();

		labelUi.setValue(contentSO.getTitel());
		htmlFlexImageGallery = new HTMLFlexImageGallery();
		htmlFlexImageGallery.getElement().getStyle().setMarginBottom(40, Unit.PX);

		materialColumnSliderLeftUi.add(htmlFlexImageGallery);

		MaterialButton share = new MaterialButton("", IconType.SHARE, ButtonType.FLOATING);
		share.addClickHandler(x -> {
			// StringSelection stringSelection = new StringSelection("Hello Deffo!!");
			// Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			// clipboard.setContents(stringSelection, null);

			htmlFlexImageGallery.test();
		});

		share.setMarginTop(-182);
		materialColumnSliderLeftUi.add(share);

		flexFormPanel = new FlexFormPanel();
		materialColumnSliderLeftUi.add(flexFormPanel);

		collapsibleComment = new CollapsibleComment();
		for (CommentSO commentSO : contentSO.getComments()) {
			collapsibleComment.addComment(commentSO);
		}

		ownerPanel = new OwnerPanel();

		panelOwnerUi.add(ownerPanel);

		panelCommentUi.add(collapsibleComment);

	}

}
