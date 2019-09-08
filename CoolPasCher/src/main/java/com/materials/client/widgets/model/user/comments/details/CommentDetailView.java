package com.materials.client.widgets.model.user.comments.details;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CommentSO;

public interface CommentDetailView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setCommentSO(CommentSO commentSO);

	void setEdit();

	interface Presenter {

		void backToCommentList();

		void saveComment(CommentSO commentSO);
	}

}
