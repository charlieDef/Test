package com.materials.server.handler.comment;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.CommentSO;
import com.materials.server.model.Comment;
import com.materials.shared.action.comment.CommentCommand;
import com.materials.utils.APP_DB_Utils;

public class CommentReadHelper {

	public List<APPObjectSO> read(CommentCommand command, String key) {

		List<APPObjectSO> list = null;

		switch (command) {

		case BY_CONTENT: {
			list = getComments("SELECT c FROM Comment c WHERE c.content.id = " + key);
		}
			break;

		case BY_USER_NAME: {
			list = getComments("SELECT c FROM Comment c WHERE c.publisher = '" + key + "'");
		}
			break;

		default: {// BY_USER_EMAIL
			list = getComments("SELECT c FROM Comment c WHERE c.publisherEmail = '" + key + "'");
		}
			break;
		}
		return list;

	}

	private List<APPObjectSO> getComments(String query) {
		List<APPObjectSO> list = new ArrayList<>();
		List<Comment> comments = APP_DB_Utils.queryListObjects(query, null, Comment.class);
		if (comments != null) {
			comments.forEach(x -> list.add(new CommentSO(x)));
		}
		return list;
	}

}
