package com.materials.client.widgets.comments;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CommentSO;

public interface CommentAreaView extends IsWidget {

	void insertCommentToView(CommentSO commentSO);

	interface Presenter {

	}

}
