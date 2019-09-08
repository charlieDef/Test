package com.materials.client.places.configurations.menuConfig;

import com.materials.client.places.configurations.AbstractControlPanel;
import com.materials.client.widgets.area.ContentArea;
import com.materials.client.widgets.area.ContentAreaView;
import com.materials.client.widgets.model.content.ContentDetails;
import com.materials.client.widgets.model.content.ContentDetailsView;
import com.materials.client.widgets.model.content.celllist.ContentAreaList;
import com.materials.client.widgets.model.content.celllist.ContentAreaListView;
import com.materials.client.widgets.model.content.celllist.ContentList;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.content.doc.ContentDocument;
import com.materials.client.widgets.model.content.doc.ContentDocumentView;
import com.materials.client.widgets.model.content.pic.ContentPictures;
import com.materials.client.widgets.model.content.pic.ContentPicturesView;
import com.materials.client.widgets.model.menu.MenuDetails;
import com.materials.client.widgets.model.menu.MenuDetailsView;
import com.materials.client.widgets.model.menu.celllist.MenuList;
import com.materials.client.widgets.model.menu.celllist.MenuListView;
import com.materials.client.widgets.model.mtemplate.celllist.MenuTemplateList;
import com.materials.client.widgets.model.mtemplate.celllist.MenuTemplateListView;
import com.materials.client.widgets.model.mtemplate.details.single.SingleTemplateDetail;
import com.materials.client.widgets.model.mtemplate.details.single.SingleTemplateDetailView;

import gwt.material.design.client.base.MaterialWidget;

public class MenuConfigViewImpl extends AbstractControlPanel implements MenuConfigView {

	private final MenuList menuList;
	private final MenuDetails menuDetails;
	private final ContentList contentList;
	private final ContentDetails contentDetails;
	private final ContentArea contentArea;
	private final ContentAreaList contentAreaList;
	private final ContentPictures contentPictures;
	private final ContentDocument contentDocument;

	private final MenuTemplateList templateList;
	private final SingleTemplateDetail singleTemplateDetail;

	public MenuConfigViewImpl() {

		super(true);

		menuList = new MenuList();
		menuDetails = new MenuDetails(false);
		contentList = new ContentList();
		templateList = new MenuTemplateList();
		singleTemplateDetail = new SingleTemplateDetail(false);
		contentDetails = new ContentDetails(false);
		contentAreaList = new ContentAreaList();
		contentArea = new ContentArea(false);
		contentPictures = new ContentPictures(false);
		contentDocument = new ContentDocument(false);

		swipperWidget.addSwipeItem("menuList", menuList, true);
		swipperWidget.addSwipeItem("menuDetails", menuDetails, false);
		swipperWidget.addSwipeItem("contentList", contentList, true);
		swipperWidget.addSwipeItem("templateList", templateList, true);
		swipperWidget.addSwipeItem("singleTemplateDetail", singleTemplateDetail, false);
		swipperWidget.addSwipeItem("contentDetails", contentDetails, false);
		swipperWidget.addSwipeItem("contentAreaList", contentAreaList, true);
		swipperWidget.addSwipeItem("contentArea", contentArea, false);
		swipperWidget.addSwipeItem("contentPictures", contentPictures, false);
		swipperWidget.addSwipeItem("contentDocument", contentDocument, false);

	}

	@Override
	public void backToContentAreaList(MaterialWidget materialWidget) {// OK
		swipperWidget.swipeBackTo("contentAreaList");
		configControlPanel.showControll(contentAreaList.getButtons());
	}

	@Override
	public void backToMenuList(MaterialWidget materialWidget) {// OK
		swipperWidget.swipeBackTo("menuList");
		configControlPanel.showControll(menuList.getButtons());
	}

	@Override
	public void backToContentList(MaterialWidget materialWidget) {// OK
		swipperWidget.swipeBackTo("contentList");
		configControlPanel.showControll(contentList.getButtons());
	}

	@Override
	public void backToTemplateList(MaterialWidget materialWidget) {// OK
		swipperWidget.swipeBackTo("templateList");
		configControlPanel.showControll(templateList.getButtons());
	}

	@Override
	public void showContentAreaList() {// LEVEL-2 :OK
		swipperWidget.swipeTo("contentAreaList");
		configControlPanel.showControll(contentAreaList.getButtons());
	}

	@Override
	public void showContentAreaDetail() {// LEVEL-3 :OK
		swipperWidget.swipeTo("contentArea");
		configControlPanel.showControll(contentArea.getButtonBar());
	}

	@Override
	public void showMenuList() {
		swipperWidget.show("menuList");
		configControlPanel.showControll(menuList.getButtons());
	}

	@Override
	public void showMenuDetail() {// LEVEL-1 :OK
		swipperWidget.swipeTo("menuDetails");
		configControlPanel.showControll(menuDetails.getButtonBar());
	}

	@Override
	public void showContentDocument() {// LEVEL-2 :OK
		swipperWidget.swipeTo("contentDocument");
		configControlPanel.showControll(contentDocument.getButtonBar());
	}

	@Override
	public void showContentPictures() {// LEVEL-2 :OK
		swipperWidget.swipeTo("contentPictures");
		configControlPanel.showControll(contentPictures.getButtonBar());
	}

	@Override
	public void showContentList() {// LEVEL-1 :OK
		swipperWidget.swipeTo("contentList");
		configControlPanel.showControll(contentList.getButtons());
	}

	@Override
	public void showMenuTemplateList() {// LEVEL-1 :OK
		swipperWidget.swipeTo("templateList");
		configControlPanel.showControll(templateList.getButtons());
	}

	@Override
	public void showSingleTemplateDetail() {// LEVEL-2 :OK

		swipperWidget.swipeTo("singleTemplateDetail");
		configControlPanel.showControll(singleTemplateDetail.getButtonBar());
	}

	@Override
	public void showContentDetails() {// LEVEL-2 :OK
		swipperWidget.swipeTo("contentDetails");
		configControlPanel.showControll(singleTemplateDetail.getButtonBar());
	}

	@Override
	public ContentAreaListView getContentAreaListView() {
		return contentAreaList;
	}

	@Override
	public ContentAreaView getContentAreaView() {
		return contentArea;
	}

	@Override
	public MenuListView getMenuListView() {
		return menuList;
	}

	@Override
	public MenuDetailsView getMenuDetailsView() {
		return menuDetails;
	}

	@Override
	public ContentDocumentView getContentDocumentView() {
		return contentDocument;
	}

	@Override
	public ContentPicturesView getContentPicturesView() {
		return contentPictures;
	}

	@Override
	public ContentDetailsView getContentDetailsView() {
		return contentDetails;
	}

	@Override
	public ContentListView getContentListView() {
		return contentList;
	}

	@Override
	public MenuTemplateListView getMenuTemplateListView() {
		return templateList;
	}

	@Override
	public SingleTemplateDetailView getSingleTemplateDetailView() {
		return singleTemplateDetail;
	}

}
