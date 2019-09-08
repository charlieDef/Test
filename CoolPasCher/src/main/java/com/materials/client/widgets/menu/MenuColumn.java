package com.materials.client.widgets.menu;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.LIElement;
import com.materials.client.widgets.menu.resource.MenuResources;
import com.materials.client.widgets.menu.resource.MenuResources.MenuStyle;

public abstract class MenuColumn {

	public MenuStyle style = MenuResources.INSTANCE.menuStyles();

	private String name;

	public abstract LIElement getCaptionElement();

	public abstract DivElement getDropdownColumn();

	public MenuColumn(String name) {
		this.name = name;
		style.ensureInjected();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
