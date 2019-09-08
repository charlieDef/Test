package com.materials.server.handler.menu;

import java.util.Date;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.MenuSO;
import com.materials.server.model.Content;
import com.materials.server.model.Menu;
import com.materials.utils.APP_DB_Utils;

public class MenuUpdateHelper {

	public APPObjectSO update(APPObjectSO appObjectSO) {

		MenuSO menuSO = (MenuSO) appObjectSO;
		Menu menu = toMenu(menuSO, false);
		if (menu != null) {
			APP_DB_Utils.saveObjectToDatabase(menu);
		}
		return menuSO;
	}

	public APPObjectSO save(APPObjectSO appObjectSO, String parentMenuName) {

		MenuSO menuSO = (MenuSO) appObjectSO;
		Menu newMenu = toMenu(menuSO, true);
		Menu parentMenu = newMenu.getParentMenu();

		if (parentMenu != null) {
			APP_DB_Utils.saveObjectToDatabase(parentMenu);
		}
		APP_DB_Utils.saveObjectToDatabase(newMenu);

		menuSO.setId(newMenu.getId());

		handleMenuSONewContent(menuSO, newMenu);

		return menuSO;
	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {

		appObjectSOs.forEach(appObjectSO -> {
			MenuSO menuSO = (MenuSO) appObjectSO;
			APP_DB_Utils.deleteObjectFromDatabase(menuSO.getId(), Menu.class);
		});
		return true;
	}

	private Menu toMenu(MenuSO menuSO, boolean isNew) {
		Menu menu = isNew ? new Menu() : APP_DB_Utils.findObject(menuSO.getId(), Menu.class);
		if (menu != null) {
			menu.setActive(menuSO.isActive());
			menu.setLock(menuSO.isLock());
			menu.setCategory(menuSO.getCategory());
			menu.setDescription(menuSO.getDescription());
			menu.setTextInfo(menuSO.getTextInfo());
			menu.setName(menuSO.getName());
			menu.setHasContents(menuSO.isHasContents());
			menu.setMemberOnly(menuSO.isMemberOnly());
			menu.setIndex(menuSO.getIndex());
			menu.setType(menuSO.getType());
			menu.setMenuCategory(menuSO.isMenuCategory());
			menu.setDropDownAlignRight(menuSO.isDropDownAlignRight());

			updateParentMenuSO(menuSO, menu);
		}

		return menu;
	}

	private void updateParentMenuSO(MenuSO menuSO, Menu menu) {
		if (menuSO.getParentMenuSo() != null) {
			Menu actualparentMenu = APP_DB_Utils.findObject(menuSO.getParentMenuSo().getId(), Menu.class);;
			
			
			Menu oldparentMenu = menu.getParentMenu();
			if (actualparentMenu != null) {

				if (oldparentMenu != null) {
					oldparentMenu.removeSubMenu(menu);
					APP_DB_Utils.saveObjectToDatabase(oldparentMenu);
				}

				actualparentMenu.addSubMenu(menu);
			}
		}
	}

	private void handleMenuSONewContent(MenuSO menuSO, Menu menu) {
		if (!menuSO.isMultiContents()) {
			String titelDescription = "Contenu pour le Menu " + menuSO.getName();
			Content content = new Content(titelDescription);
			content.setActive(true);
			content.setCategory("MenuContent");
			content.setCreationDate(new Date());
			content.setDescription(titelDescription);
			content.setDescription2(titelDescription);
			content.setType("MENU_CONTENT");
			content.setViewAble(0);
			menu.addContent(content);
			APP_DB_Utils.saveObjectToDatabase(content);
			APP_DB_Utils.saveObjectToDatabase(menu);
			menuSO.addContent(new ContentSO(content));
		}
	}
}
