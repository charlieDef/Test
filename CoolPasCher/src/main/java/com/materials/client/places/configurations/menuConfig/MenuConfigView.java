package com.materials.client.places.configurations.menuConfig;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.area.ContentAreaView;
import com.materials.client.widgets.model.content.ContentDetailsView;
import com.materials.client.widgets.model.content.celllist.ContentAreaListView;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.content.doc.ContentDocumentView;
import com.materials.client.widgets.model.content.pic.ContentPicturesView;
import com.materials.client.widgets.model.menu.MenuDetailsView;
import com.materials.client.widgets.model.menu.celllist.MenuListView;
import com.materials.client.widgets.model.mtemplate.celllist.MenuTemplateListView;
import com.materials.client.widgets.model.mtemplate.details.single.SingleTemplateDetailView;

import gwt.material.design.client.base.MaterialWidget;

public interface MenuConfigView extends IsWidget {

	ContentDocumentView getContentDocumentView();

	ContentListView getContentListView();

	ContentPicturesView getContentPicturesView();

	ContentAreaListView getContentAreaListView();

	ContentAreaView getContentAreaView();

	ContentDetailsView getContentDetailsView();

	MenuListView getMenuListView();

	MenuTemplateListView getMenuTemplateListView();

	SingleTemplateDetailView getSingleTemplateDetailView();

	MenuDetailsView getMenuDetailsView();

	// SubMenuListView getSubMenuListView();
	//
	// SubMenuDetailsView getSubMenuDetailsView();

	void showContentDocument();

	void showContentPictures();

	void showContentAreaDetail();

	void showContentAreaList();

	void showContentList();

	void showContentDetails();

	void showMenuList();

	void showMenuTemplateList();

	void showSingleTemplateDetail();

	void showMenuDetail();

	// void showSubMenuList();
	//
	// void showSubMenuDetail();

	interface Presenter {

	}

	void backToContentList(MaterialWidget materialWidget);

	void backToMenuList(MaterialWidget materialWidget);

	void backToContentAreaList(MaterialWidget materialWidget);

	void backToTemplateList(MaterialWidget materialWidget);

}
