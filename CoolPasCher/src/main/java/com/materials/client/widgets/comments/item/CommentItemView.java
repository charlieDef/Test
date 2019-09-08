package com.materials.client.widgets.comments.item;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ContentSO;

public interface CommentItemView extends IsWidget {

	void setPresenter(Presenter presenter);

	interface Presenter {

		void postNewComment(ContentSO contentSO, String commentText);

	}
}
