package com.materials.client.widgets.model.user.comments.details;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.materials.client.model.CommentSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.shared.APPConstants;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class CommentDetail extends BaseDetails2 implements CommentDetailView {

	private static CommentDetailUiBinder uiBinder = GWT.create(CommentDetailUiBinder.class);

	interface CommentDetailUiBinder extends UiBinder<MDFormPanel, CommentDetail> {
	}

	private CommentSO commentSO;
	private Presenter presenter;

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialDatePicker creation2Ui;

	@UiField
	MaterialTextArea textContentUi;

	@UiField
	MaterialTextBox blogNameUi, commentTitelUi;

	@UiField
	MaterialIntegerBox ratingUi;

	@UiField
	MaterialCheckBox activeUi, lockUi;

	@UiHandler("backButtonUi")
	void onBackToCommentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToCommentList();
	}

	public CommentDetail(boolean configure) {
		super(configure);
		add(uiBinder.createAndBindUi(this));
		mdFormUi.setFormHandler(new BBarHandler());
		handleEditEnable();
	}

	public void setHeaderTitel(String titel) {
		textHeaderLabelUi.setText(titel);
	}

	@Override
	public void setCommentSO(CommentSO commentSO) {
		this.commentSO = commentSO;
		DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);

		// boxes
		textContentUi.setValue(commentSO.getText());
		blogNameUi.setValue(commentSO.getBlogName());
		creation2Ui.setValue(commentSO.getCreationDate());
		activeUi.setValue(commentSO.isActive());
		lockUi.setValue(commentSO.isLock());
		commentTitelUi.setValue(commentSO.getCommentTitel());
		ratingUi.setValue(commentSO.getRating());
		// textHeaderLabelUi.setText("Comment_Contenu:: " +
		// commentSO.getContentSO().getTitel());
		textHeaderLabelUi.setText("Comment:: ");
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
		}

		@Override
		public void onSave() {
			commentSO.setActive(activeUi.getValue());
			commentSO.setLock(lockUi.getValue());
			commentSO.setText(textContentUi.getValue());
			commentSO.setBlogName(blogNameUi.getValue());
			commentSO.setCommentTitel(commentTitelUi.getValue());
			commentSO.setRating(ratingUi.getValue());
			presenter.saveComment(commentSO);
		}

		@Override
		public void onCancel() {
			if (commentSO.getId() == -10) {
				presenter.backToCommentList();
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
