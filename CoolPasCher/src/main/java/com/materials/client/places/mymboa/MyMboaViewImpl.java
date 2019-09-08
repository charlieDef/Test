package com.materials.client.places.mymboa;

import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonce;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonceView;
import com.materials.client.widgets.model.content.celllist.ContentList;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.swipe.SwipperWidget;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

public class MyMboaViewImpl extends MaterialPanel implements MyMboaView {

	private Presenter presenter;

	private SwipperWidget swipperWidget;
	private ContentList contentList;
	private EditAbleAnnonce editAbleAnnonce;

	public MyMboaViewImpl() {

		swipperWidget = new SwipperWidget();
		add(swipperWidget);

		contentList = new ContentList();
		editAbleAnnonce = new EditAbleAnnonce(false);

		swipperWidget.addSwipeItem("contentList", contentList, true);
		swipperWidget.addSwipeItem("editAbleAnnonce", editAbleAnnonce, false);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void showContentList() {
		// swipperWidget.show("contentList");
		swipperWidget.show("contentList", Transition.FADE_IN_IMAGE, 1200);
	}

	@Override
	public void showContentDetail() {// LEVEL-1 :OK
		swipperWidget.swipeTo("editAbleAnnonce");
	}

	@Override
	public void backToContentList() {// OK
		swipperWidget.swipeBackTo("contentList");
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
