package com.materials.client.widgets.comments.collapsible;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.model.CommentSO;
import com.materials.shared.APPConstants;

import gwt.material.design.addins.client.rating.MaterialRating;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class CollapsibleComment extends Composite {

	private static CollapsibleCommentUiBinder uiBinder = GWT.create(CollapsibleCommentUiBinder.class);

	interface CollapsibleCommentUiBinder extends UiBinder<MaterialPanel, CollapsibleComment> {
	}

	@UiField
	MaterialCollapsible collapsibleUi;

	@UiField
	CollapsStyle style;

	public CollapsibleComment() {

		initWidget(uiBinder.createAndBindUi(this));
		style.ensureInjected();
		// collapsibleUi.setHoverable(false);
	}

	public void addComment(CommentSO commentSO) {

		MaterialCollapsibleItem collapsibleItem = new MaterialCollapsibleItem();

		MaterialCollapsibleHeader collapsibleHeader = new MaterialCollapsibleHeader();
		collapsibleItem.add(collapsibleHeader);

		MaterialRow materialRow1 = new MaterialRow();
		materialRow1.setMarginBottom(0);
		materialRow1.setMarginTop(0);
		collapsibleHeader.add(materialRow1);

		DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);
		MaterialLabel commentDateLabel = new MaterialLabel(ttipFormat.format(commentSO.getCreationDate()));
		commentDateLabel.addStyleName(style.collapsDateLabel());
		commentDateLabel.setFloat(Float.RIGHT);
		materialRow1.add(commentDateLabel);

		MaterialLabel labelUserName = new MaterialLabel(commentSO.getPublisherLastName());
		labelUserName.addStyleName(style.collapsItemUserNameLabel());
		labelUserName.setFloat(Float.LEFT);
		materialRow1.add(labelUserName);

		// ----------------------------------------------
		MaterialRow materialRow = new MaterialRow();
		materialRow.setMarginBottom(15);
		collapsibleHeader.add(materialRow);

		MaterialImage userImage = new MaterialImage(commentSO.getPublisherImageUrl());
		userImage.setCircle(true);
		userImage.addStyleName(style.collapsItemUserImg());
		materialRow.add(userImage);

		MaterialLabel commentTitleLabel = new MaterialLabel(commentSO.getCommentTitel());
		commentTitleLabel.addStyleName(style.collapsItemTitleLabel());
		materialRow.add(commentTitleLabel);

		MaterialRating materialRating = new MaterialRating();

		materialRating.setEditable(false);
		materialRating.setTextColor(Color.AMBER);
		materialRating.setValue(commentSO.getRating());
		materialRating.addStyleName(style.collapsRating());
		materialRow.add(materialRating);
		// ------------------------------------------------

		// --------------------------------------------------
		MaterialCollapsibleBody body = new MaterialCollapsibleBody();
		body.addStyleName(style.collapsItemBody());
		collapsibleItem.add(body);

		MaterialLabel commentContent = new MaterialLabel(commentSO.getText());
		body.add(commentContent);

		collapsibleUi.add(collapsibleItem);

	}

	interface CollapsStyle extends CssResource {

		String collapsItemUserImg();

		String collapsItemUserNameLabel();

		String collapsItemBody();

		String collapsArea();

		String collapsItemTitleLabel();

		String collapsRating();

		String collapsDateLabel();

	}

}
