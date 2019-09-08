package com.materials.server.handler.comment;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.CommentSO;
import com.materials.server.model.Comment;
import com.materials.server.model.Content;
import com.materials.utils.APP_DB_Utils;

public class CommentUpdateHelper {

	public APPObjectSO update(APPObjectSO appObjectSO) {

		CommentSO commentSO = (CommentSO) appObjectSO;
		Comment comment = toComment(commentSO, false);
		if (comment != null) {
			APP_DB_Utils.saveObjectToDatabase(comment);
		}
		return commentSO;
	}

	public APPObjectSO save(APPObjectSO appObjectSO) {

		CommentSO commentSO = (CommentSO) appObjectSO;
		Comment newComment = toComment(commentSO, true);
		Content content = null;
		if (commentSO.getContentSO() != null) {
			content = APP_DB_Utils.findObject(commentSO.getContentSO().getId(), Content.class);
		}

		if (content != null) {
			content.addComment(newComment);
			APP_DB_Utils.saveObjectToDatabase(newComment);
			APP_DB_Utils.saveObjectToDatabase(content);
		} else {
			APP_DB_Utils.saveObjectToDatabase(newComment);
		}
		commentSO.setId(newComment.getId());
		return commentSO;
	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {

		appObjectSOs.forEach(appObjectSO -> {
			CommentSO commentSO = (CommentSO) appObjectSO;
			APP_DB_Utils.deleteObjectFromDatabase(commentSO.getId(), Comment.class);
		});
		return true;
	}

	private Comment toComment(CommentSO commentSO, boolean isNew) {
		Comment comment = isNew ? new Comment() : APP_DB_Utils.findObject(commentSO.getId(), Comment.class);
		if (comment != null) {
			comment.setActive(commentSO.isActive());
			comment.setLock(commentSO.isLock());
			comment.setCreationDate(commentSO.getCreationDate());
			comment.setPublisher(commentSO.getPublisherLastName());
			comment.setPublisherBlog(commentSO.getBlogName());
			comment.setPublisherEmail(commentSO.getPublisherEmail());
			comment.setText(commentSO.getText());
			comment.setPublisherRandomId(commentSO.getPublisherRandomId());
			comment.setRating(commentSO.getRating());
			comment.setCommentTitel(commentSO.getCommentTitel());
		}

		return comment;
	}

}
