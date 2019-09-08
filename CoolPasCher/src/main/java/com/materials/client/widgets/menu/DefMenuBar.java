package com.materials.client.widgets.menu;

import static gwt.material.design.jquery.client.api.JQuery.$;

import com.google.gwt.dom.client.Document;
import com.materials.client.widgets.menu.resource.MenuResources;

import gwt.material.design.addins.client.base.constants.AddinsCssName;
import gwt.material.design.client.MaterialDesignBase;
import gwt.material.design.client.base.MaterialWidget;

public class DefMenuBar extends MaterialWidget {

	static {

		MaterialDesignBase.injectCss(MenuResources.INSTANCE.menubarCss());

	}

	private String minHeight;

	public DefMenuBar() {
		super(Document.get().createDivElement(), AddinsCssName.MENU_BAR);
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		$(getElement()).find(".dropdown-content li").css("minHeight", minHeight);
		$(getElement()).find(".dropdown-content li").css("lineHeight", minHeight);
		$(getElement()).find(".dropdown-content li").css("maxHeight", minHeight);
	}

	public void setItemHeight(String minHeight) {
		this.minHeight = minHeight;
	}

}