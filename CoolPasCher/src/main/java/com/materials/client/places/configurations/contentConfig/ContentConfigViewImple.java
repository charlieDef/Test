package com.materials.client.places.configurations.contentConfig;

import com.google.gwt.user.client.Window;
import com.materials.client.CoolPasCherUI;
import com.materials.client.places.configurations.AbstractControlPanel;
import com.materials.client.widgets.articles.annonce.simple.SimpleAnnonceDetailView;
import com.materials.client.widgets.articles.annonce.simple.SimpleAnnonceDetails;
import com.materials.client.widgets.model.content.celllist.ContentAreaList;
import com.materials.client.widgets.model.content.celllist.ContentAreaListView;
import com.materials.client.widgets.model.content.celllist.ContentList;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.content.pic.ContentPictures;
import com.materials.client.widgets.model.content.pic.ContentPicturesView;

public class ContentConfigViewImple extends AbstractControlPanel implements ContentConfigView {

	private ContentList contentList;
	private SimpleAnnonceDetails simpleAnnonceDetails;
	private ContentAreaList contentAreaList;
	private ContentPictures contentPictures;
	private int actual;

	public ContentConfigViewImple() {
		super(true);

		contentList = new ContentList();
		simpleAnnonceDetails = new SimpleAnnonceDetails(false);
		contentAreaList = new ContentAreaList();
		contentPictures = new ContentPictures(false);
		swipperWidget.addSwipeItem("contentList", contentList, true);
		swipperWidget.addSwipeItem("simpleAnnonceDetails", simpleAnnonceDetails, false);
		swipperWidget.addSwipeItem("contentAreaList", contentAreaList, true);
		swipperWidget.addSwipeItem("contentPictures", contentPictures, false);

	}

	@Override
	public void showContentPictures() {
		swipperWidget.swipeTo("contentPictures");
		configControlPanel.showControll(contentPictures.getButtonBar());
		scrollToTop();
	}

	@Override
	public void showContentAreaList() {
		swipperWidget.swipeTo("contentAreaList");
		configControlPanel.showControll(contentAreaList.getButtons());
		scrollToTop();
	}

	@Override
	public void showContentList() {
		swipperWidget.show("contentList");
		configControlPanel.showControll(contentList.getButtons());
		scrollToTop();
	}

	@Override
	public void showContentDetails(boolean isNew) {// LEVEL-1 :OK
		CoolPasCherUI.CLIENT_FACTORY.setScrollIndex(Window.getScrollTop());
		
		swipperWidget.swipeTo("simpleAnnonceDetails", x -> {
			scrollToTop();
		});
		configControlPanel.showControll(simpleAnnonceDetails.getButtonBar());
		
	}

	@Override
	public void backToContentList() {// OK
		swipperWidget.swipeBackTo("contentList", x -> {
			CoolPasCherUI.CLIENT_FACTORY.getScrollHelper().scrollTo(CoolPasCherUI.CLIENT_FACTORY.getScrollIndex());	
		});
		configControlPanel.showControll(contentList.getButtons());
		

	
	}

	@Override
	public void backToContentListFromAreaList() {// OK
		swipperWidget.swipeBackTo("contentList");
		configControlPanel.showControll(contentList.getButtons());
	}

	@Override
	public void backToContentArealist() {// OK
		swipperWidget.swipeBackTo("contentAreaList");
		configControlPanel.showControll(contentAreaList.getButtons());
	}

	@Override
	public ContentListView getContentListView() {
		return contentList;
	}

	@Override
	public ContentAreaListView getContentAreaListView() {
		return contentAreaList;
	}

	@Override
	public ContentPicturesView getContentPicturesView() {
		return contentPictures;
	}

	@Override
	public SimpleAnnonceDetailView getSimpleAnnonceDetailView() {
		return simpleAnnonceDetails;
	}
	
	

}
