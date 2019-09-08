package com.materials.client.widgets.comments.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.shared.MethodsUtils;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextArea;

public class CommentItem extends Composite implements CommentItemView {

	private static CommentItemUiBinder uiBinder = GWT.create(CommentItemUiBinder.class);

	interface CommentItemUiBinder extends UiBinder<Widget, CommentItem> {
	}

	private ContentSO contentSO;

	private Presenter presenter;

	@UiField
	MaterialTextArea textAreaUi;

	@UiField
	MaterialLabel titelUi;

	@UiField
	MaterialImage imageUi;

	@UiField
	MaterialCollectionItem collectionItemUi;

	public CommentItem(ContentSO contentSO) {
		this.contentSO = contentSO;
		initWidget(uiBinder.createAndBindUi(this));

		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
		if (userSO != null) {
			imageUi.setUrl(userSO.getUserImageUrl());
			titelUi.setValue(userSO.getName());
			textAreaUi.setEnabled(true);
			collectionItemUi.add(getPostButton());

		} else {
			collectionItemUi.add(getLoginButton());
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	private MaterialButton getLoginButton() {
		MaterialButton button = new MaterialButton("Login");
		button.addClickHandler(x -> {
			CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().show();
		});

		return button;
	}

	private MaterialButton getPostButton() {
		MaterialButton button = new MaterialButton("Poster");
		button.addClickHandler(x -> {
			if (MethodsUtils.isStringOK(textAreaUi.getValue()) && textAreaUi.getValue().length() > 5) {
				presenter.postNewComment(contentSO, textAreaUi.getValue());
			}
		});

		return button;
	}

	public void clearTextArea() {
		textAreaUi.setValue("");
	}

}
