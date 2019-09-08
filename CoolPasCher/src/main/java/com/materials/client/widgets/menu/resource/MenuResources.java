package com.materials.client.widgets.menu.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;

public interface MenuResources extends ClientBundle {
	public final static MenuResources INSTANCE = GWT.create(MenuResources.class);

	public interface MenuStyle extends CssResource {
		String drop();

		String dropdown_1column();

		String dropdown_2columns();

		String dropdown_3columns();

		String dropdown_4columns();

		String dropdown_5columns();

		String col_1();

		String col_2();

		String col_3();

		String col_4();

		String col_5();

		String imgshadow();

		String img_left();

		String black_box();

		String align_right();

		String strong();

		String italic();

		String greybox();

		String menu_right();

		String menuImage();

		String appNoHover();

		String linkText();

		String fCaisse();
	}

	@Source("menu.css")
	MenuStyle menuStyles();

	@Source("defmenu.css")
	TextResource menubarCss();
}