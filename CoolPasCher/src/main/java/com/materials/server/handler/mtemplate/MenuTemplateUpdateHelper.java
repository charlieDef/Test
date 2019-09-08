package com.materials.server.handler.mtemplate;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.MenuTemplateSO;
import com.materials.server.model.Menu;
import com.materials.server.model.TempFile;
import com.materials.server.model.template.MenuTemplate;
import com.materials.utils.APP_DB_Utils;

public class MenuTemplateUpdateHelper {

	public APPObjectSO update(APPObjectSO appObjectSO) {

		MenuTemplateSO menuTemplateSO = (MenuTemplateSO) appObjectSO;
		MenuTemplate menuTemplate = toMenuTemplate(menuTemplateSO, false);
		APP_DB_Utils.saveObjectToDatabase(menuTemplate);
		return menuTemplateSO;
	}

	public APPObjectSO save(APPObjectSO appObjectSO) {

		MenuTemplateSO menuTemplateSO = (MenuTemplateSO) appObjectSO;
		MenuTemplate newMenuTemplate = toMenuTemplate(menuTemplateSO, true);
		APP_DB_Utils.saveObjectToDatabase(newMenuTemplate);
		menuTemplateSO.setId(newMenuTemplate.getId());

		return menuTemplateSO;
	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {

		appObjectSOs.forEach(appObjectSO -> {
			MenuTemplateSO menuTemplateSO = (MenuTemplateSO) appObjectSO;
			APP_DB_Utils.deleteObjectFromDatabase(menuTemplateSO.getId(), MenuTemplate.class);
		});
		return true;
	}

	private MenuTemplate toMenuTemplate(MenuTemplateSO menuTemplateSO, boolean isNew) {
		MenuTemplate menuTemplate = isNew ? new MenuTemplate()
				: APP_DB_Utils.findObject(menuTemplateSO.getId(), MenuTemplate.class);
		if (menuTemplate != null) {
			menuTemplate.setActive(menuTemplateSO.isActive());
			menuTemplate.setLock(menuTemplateSO.isLock());
			menuTemplate.setCategory(menuTemplateSO.getCategory());
			menuTemplate.setColNr(menuTemplateSO.getColNr());
			menuTemplate.setIndex(menuTemplateSO.getIndex());
			menuTemplate.setLeftAlign(menuTemplateSO.isLeftAlign());
			menuTemplate.setButtonStyle(menuTemplateSO.isButtonStyle());
			menuTemplate.setRandomId(menuTemplateSO.getRandomId());
			menuTemplate.setName(menuTemplateSO.getName());
			menuTemplate.setTemplateLinks(menuTemplateSO.getTemplateLinks());

			updateMenuSO(menuTemplateSO, menuTemplate);

			// TODO be carefull here --> Image set
			if (menuTemplateSO.getRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(menuTemplateSO.getRandomId());
				if (tempFile != null) {
					menuTemplate.setRandomId(menuTemplateSO.getRandomId());
					menuTemplate.setImageData(tempFile.getTempData());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}
		}

		return menuTemplate;
	}

	private void updateMenuSO(MenuTemplateSO menuTemplateSO, MenuTemplate menuTemplate) {
		if (menuTemplateSO.getMenuSO() != null) {
			Menu parentMenu = APP_DB_Utils.getObjectByAttribute("name", menuTemplateSO.getMenuSO().getName(),
					Menu.class);
			if ((menuTemplate.getMenu() == null) || (parentMenu != null && menuTemplate.getMenu() != null
					&& !parentMenu.getName().equals(menuTemplate.getMenu().getName()))) {
				menuTemplate.setMenu(parentMenu);
			}
		}
	}

}
