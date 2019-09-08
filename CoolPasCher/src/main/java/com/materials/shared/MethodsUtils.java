package com.materials.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.MenuSO;

public class MethodsUtils {

	public static String js_DoRun_Command(String targetName) {
		return "javascript:dorun('" + targetName + "')";
	}

	public static <T> T castList(List<APPObjectSO> pbObjects, final Class class1) {
		List<T> list = new ArrayList<>();
		if (pbObjects != null && pbObjects.size() > 0) {
			for (APPObjectSO pbObject : pbObjects) {

				T t = (T) pbObject;
				list.add(t);
			}
		}
		return (T) list;
	}

	public static String getRecursiveMenu(MenuSO menuSO) {

		String titelName = "";
		if (menuSO != null) {
			titelName = menuSO.getName();
			if (menuSO.getParentMenuSo() != null) {
				titelName = getRecursiveMenu(menuSO.getParentMenuSo()) + "::" + titelName;
			}
		}

		return titelName;
	}

	public static String getRecursiveMenu(ContentSO contentSO) {
		String titelName = (contentSO.getTitel() != null && !contentSO.getTitel().isEmpty()) ? contentSO.getTitel()
				: "";
		if (contentSO.getMenuSO() != null) {
			titelName = getRecursiveMenu(contentSO.getMenuSO()) + "::" + titelName;
		}
		return titelName;
	}

	public static Map<String, List<ContentSO>> getSortedMap(List<ContentSO> contentSOs) {
		Map<String, List<ContentSO>> map = new HashMap<String, List<ContentSO>>();
		for (ContentSO contentSO : contentSOs) {
			if (contentSO.isActive()) {
				if (map.get(contentSO.getCategory()) != null) {
					map.get(contentSO.getCategory()).add(contentSO);
				} else {
					List<ContentSO> list = new ArrayList<>();
					list.add(contentSO);
					map.put(contentSO.getCategory(), list);
				}
			}
		}
		return map;
	}

	public static Map<String, List<ContentSO>> getSortedMap(List<ContentSO> contentSOs, String contentType) {

		Map<String, List<ContentSO>> map = new HashMap<String, List<ContentSO>>();
		for (ContentSO contentSO : contentSOs) {
			if (contentSO.getType().contains(contentType)) {
				if (contentSO.isActive()) {
					if (map.get(contentSO.getCategory()) != null) {
						map.get(contentSO.getCategory()).add(contentSO);
					} else {
						List<ContentSO> list = new ArrayList<>();
						list.add(contentSO);
						map.put(contentSO.getCategory(), list);
					}
				}
			}

		}
		return map;
	}

	public static List<MenuSO> filterMenuList(List<MenuSO> list, boolean isMember) {
		List<MenuSO> newList = new ArrayList<MenuSO>();

		for (MenuSO menuSO : list) {
			if (menuSO.isMemberOnly() && !isMember) {
				continue;
			}
			newList.add(menuSO);
			List<MenuSO> newSubMenus = new ArrayList<MenuSO>();
			for (MenuSO subMenu : menuSO.getSubMenuSos()) {
				if (subMenu.isMemberOnly() && !isMember) {
					continue;
				}
				newSubMenus.add(subMenu);
			}
			menuSO.setSubMenuSos(newSubMenus);
		}

		return newList;
	}

	public static boolean isStringOK(String string) {
		boolean ok = false;
		if (string != null && !string.trim().isEmpty()) {
			ok = true;
		}

		return ok;
	}

}
