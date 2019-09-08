package com.materials.client.widgets.comments;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.context.presenter.CommentPresenter;
import com.materials.client.model.CommentSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.comments.item.CommentItem;
import com.materials.shared.APPConstants;

import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.ImageType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextArea.ResizeRule;

public class CommentArea extends Composite implements CommentAreaView {

	private ContentSO contentSO;

	private DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

	private static CommentAreaUiBinder uiBinder = GWT.create(CommentAreaUiBinder.class);

	interface CommentAreaUiBinder extends UiBinder<MaterialCollapsible, CommentArea> {
	}

	@UiField
	MaterialCollection commentPanelUi;

	private CommentItem commentItem;

	@UiField
	MaterialRow commentRowUi;

	@UiField
	MaterialLink linkTitleUi;

	public CommentArea() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public CommentArea(ContentSO contentSO) {
		this.contentSO = contentSO;

		initWidget(uiBinder.createAndBindUi(this));

		commentItem = new CommentItem(contentSO);
		commentItem.setPresenter(new CommentPresenter(this));
		commentRowUi.add(commentItem);

		List<CommentSO> commentaires = contentSO.getComments();
		commentaires.sort(new Comparator<CommentSO>() {
			@Override
			public int compare(CommentSO o1, CommentSO o2) {
				return Long.valueOf(o2.getId()).compareTo(Long.valueOf(o1.getId()));
			}
		});

		for (CommentSO commentSO : commentaires) {

			MaterialCollectionItem collectionItem = getCollectionItem(commentSO);
			commentPanelUi.add(collectionItem);
		}

		linkTitleUi.setText("Commentaires [ " + commentaires.size() + " ]");

	}

	private MaterialCollectionItem getCollectionItem(CommentSO commentSO) {

		MaterialCollectionItem item = new MaterialCollectionItem();
		item.setType(CollectionType.AVATAR);
		item.getElement().getStyle().setMarginBottom(10, Unit.PX);

		MaterialImage image = new MaterialImage(commentSO.getPublisherImageUrl());
		image.setTitle(commentSO.getPublisherLastName());
		image.setType(ImageType.CIRCLE);
		item.add(image);

		MaterialLabel title = new MaterialLabel(commentSO.getPublisherLastName());
		title.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		title.getElement().getStyle().setFontSize(16, Unit.PX);
		item.add(title);

		MaterialLabel date = new MaterialLabel(ttipFormat.format(commentSO.getCreationDate()));

		date.setTextColor(Color.GREY_LIGHTEN_1);
		item.add(date);

		MaterialTextArea text = new MaterialTextArea();
		text.setResizeRule(ResizeRule.FOCUS);

		text.setClass("myTextArea");

		text.setValue(commentSO.getText());

		item.add(text);

		// MaterialCollectionSecondary secondarydate = new
		// MaterialCollectionSecondary();
		// text.getElement().getStyle().setMarginTop(10, Unit.PX);
		// MaterialIcon icon = new MaterialIcon(IconType.LIBRARY_ADD);
		// secondarydate.add(icon);
		//
		// item.add(secondarydate);

		return item;

	}

	@Override
	public void insertCommentToView(CommentSO commentSO) {

		MaterialCollectionItem child = getCollectionItem(commentSO);

		commentPanelUi.insert(child, 0);
		linkTitleUi.setText("Commentaires [ " + contentSO.getComments().size() + " ]");
		commentItem.clearTextArea();

	}

}
