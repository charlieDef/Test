package com.materials.server.handler.mtemplate;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.MenuTemplateSO;
import com.materials.server.model.template.MenuTemplate;
import com.materials.shared.action.mtemplate.MenuTemplateCommand;
import com.materials.utils.APP_DB_Utils;

public class MenuTemplateReadHelper {

	public List<MenuTemplateSO> read(MenuTemplateCommand command, String key) {

		List<MenuTemplateSO> list = null;

		switch (command) {

		case BY_MENU_NAME: {
			list = getMenuTemplates(
					"SELECT t FROM MenuTemplate t, Menu m WHERE t.menu.id = m.id AND m.name='" + key + "'");
		}
			break;
		case BY_ID: {
			list = getMenuTemplates("SELECT t FROM MenuTemplate t WHERE t.id='" + key + "'");
		}
			break;
		}
		return list;

	}

	private List<MenuTemplateSO> getMenuTemplates(String query) {
		List<MenuTemplateSO> list = new ArrayList<>();
		List<MenuTemplate> menuTemplates = APP_DB_Utils.queryListObjects(query, null, MenuTemplate.class);
		if (menuTemplates != null) {
			menuTemplates.forEach(x -> list.add(new MenuTemplateSO(x)));
		}
		return list;
	}

}
