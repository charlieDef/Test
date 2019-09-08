package com.materials.client.widgets.model.user.comments.celllist;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CommentSO;
import com.materials.client.widgets.celllist.MDCellList;

public interface CommentListView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setData(List<CommentSO> userSOs);

	MDCellList<CommentSO> getCellList();

	interface Presenter {

		void swipeBackToUserList();

		void swipeToCommentDetail(CommentSO commentSO);

		void newComment();

		void deleteComments(List<CommentSO> commentSOs);
	}

	void setReadOnly(boolean readOnly);

}
