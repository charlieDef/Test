package com.materials.client.places.configurations.userConfig;

import com.materials.client.places.configurations.AbstractControlPanel;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonce;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonceView;
import com.materials.client.widgets.model.content.celllist.ContentList;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.user.UserDetails;
import com.materials.client.widgets.model.user.UserDetailsView;
import com.materials.client.widgets.model.user.cellist.UserList;
import com.materials.client.widgets.model.user.cellist.UserListView;
import com.materials.client.widgets.model.user.comments.celllist.CommentList;
import com.materials.client.widgets.model.user.comments.celllist.CommentListView;
import com.materials.client.widgets.model.user.comments.details.CommentDetail;
import com.materials.client.widgets.model.user.comments.details.CommentDetailView;

import gwt.material.design.client.base.MaterialWidget;

public class UserConfigViewImpl extends AbstractControlPanel implements UserConfigView {

	private UserList userList;
	private UserDetails userDetails;
	private CommentList commentList;
	private CommentDetail commentDetail;
	private ContentList contentList;
	private EditAbleAnnonce editAbleAnnonce;

	public UserConfigViewImpl() {

		super(true);

		userList = new UserList();
		userDetails = new UserDetails(false);
		commentList = new CommentList();
		commentDetail = new CommentDetail(false);
		contentList = new ContentList();
		editAbleAnnonce = new EditAbleAnnonce(false);

		swipperWidget.addSwipeItem("userList", userList, true);
		swipperWidget.addSwipeItem("userDetails", userDetails, false);
		swipperWidget.addSwipeItem("commentList", commentList, true);
		swipperWidget.addSwipeItem("commentDetail", commentDetail, false);
		swipperWidget.addSwipeItem("contentList", contentList, true);
		swipperWidget.addSwipeItem("editAbleAnnonce", editAbleAnnonce, false);

	}

	@Override
	public void showUserList() {
		swipperWidget.show("userList");
		configControlPanel.showControll(userList.getButtons());
	}

	@Override
	public void showUserDetail() {// L1
		swipperWidget.swipeTo("userDetails");
		configControlPanel.showControll(userDetails.getButtonBar());

	}

	@Override
	public void showCommentList() {// L1
		swipperWidget.swipeTo("commentList");
		configControlPanel.showControll(commentList.getButtons());
	}

	@Override
	public void showCommentDetail() {// L2
		swipperWidget.swipeTo("commentDetail");
		configControlPanel.showControll(commentDetail.getButtonBar());
	}

	@Override
	public void showUserAnnonceList() {// L1
		swipperWidget.swipeTo("contentList");
		configControlPanel.showControll(contentList.getButtons());
	}

	@Override
	public void showUserAnnonceDetail() {// L1
		swipperWidget.swipeTo("editAbleAnnonce");
		// configControlPanel.showControll(editAbleAnnonce.getButtonBar());
	}

	@Override
	public void goBackToCommentList(MaterialWidget materialWidget) {
		swipperWidget.swipeBackTo("commentList");
		configControlPanel.showControll(commentList.getButtons());
	}

	@Override
	public void goBackToUserAnnonceList() {
		swipperWidget.swipeBackTo("contentList");
		configControlPanel.showControll(contentList.getButtons());
	}

	@Override
	public void goBackToUserList(MaterialWidget widgetActual) {
		swipperWidget.swipeBackTo("userList");
		configControlPanel.showControll(userList.getButtons());
	}

	@Override
	public UserDetailsView getUserDetailsView() {
		return userDetails;
	}

	@Override
	public CommentListView getCommentListView() {
		return commentList;
	}

	@Override
	public CommentDetailView getCommentDetailView() {
		return commentDetail;
	}

	@Override
	public UserListView getUserListView() {
		return userList;
	}

	@Override
	public ContentListView getContentListView() {
		return contentList;
	}

	@Override
	public EditAbleAnnonceView getEditAbleAnnonceView() {
		return editAbleAnnonce;
	}

}
