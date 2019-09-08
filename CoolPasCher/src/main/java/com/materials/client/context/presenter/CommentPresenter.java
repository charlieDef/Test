package com.materials.client.context.presenter;

import java.util.Date;

import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CommentSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.client.widgets.comments.CommentAreaView;
import com.materials.client.widgets.comments.item.CommentItemView;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.comment.CommentAction;

public class CommentPresenter implements CommentItemView.Presenter {

	private CommentAreaView commentAreaView;

	public CommentPresenter(CommentAreaView commentAreaView) {
		this.commentAreaView = commentAreaView;
	}

	@Override
	public void postNewComment(ContentSO contentSO, String commentText) {

		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();

		CommentSO commentSO = new CommentSO();
		commentSO.setId(-10);
		commentSO.setCreationDate(new Date());
		commentSO.setActive(true);
		commentSO.setLock(false);
		commentSO.setText(commentText);
		commentSO.setPublisherLastName(userSO.getName());
		commentSO.setPublisherEmail(userSO.getEmail());
		commentSO.setPublisherRandomId(userSO.getRandomId());
		commentSO.setContentSO(contentSO);

		CoolPasCherUI.CLIENT_FACTORY.execute(new CommentAction(DBAction.SAVE_NEW, commentSO), x -> {

			CommentSO saved = (CommentSO) x.getObject();
			contentSO.getComments().add(saved);
			commentAreaView.insertCommentToView(saved);

		});
	}

}
