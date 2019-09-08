package com.materials.client.places.configurations.userConfig;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonceView;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.user.UserDetailsView;
import com.materials.client.widgets.model.user.cellist.UserListView;
import com.materials.client.widgets.model.user.comments.celllist.CommentListView;
import com.materials.client.widgets.model.user.comments.details.CommentDetailView;

import gwt.material.design.client.base.MaterialWidget;

public interface UserConfigView extends IsWidget {

	CommentListView getCommentListView();

	CommentDetailView getCommentDetailView();

	UserListView getUserListView();

	UserDetailsView getUserDetailsView();

	ContentListView getContentListView();

	EditAbleAnnonceView getEditAbleAnnonceView();

	void showUserList();

	void showUserDetail();

	void showCommentList();

	void showCommentDetail();

	void goBackToCommentList(MaterialWidget widgetActual);

	void goBackToUserList(MaterialWidget widgetActual);

	interface Presenter {

	}

	void showUserAnnonceDetail();

	void showUserAnnonceList();

	void goBackToUserAnnonceList();

}
