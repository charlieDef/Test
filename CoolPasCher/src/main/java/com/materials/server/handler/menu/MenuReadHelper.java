package com.materials.server.handler.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.materials.client.model.MenuSO;
import com.materials.server.model.Menu;
import com.materials.shared.action.menu.MenuCommand;
import com.materials.utils.APP_DB_Utils;

public class MenuReadHelper {

	private HttpServletRequest servletRequest;

	public List<MenuSO> read(MenuCommand command, String key) {

		List<MenuSO> list = null;
		Map<String, Object> map = new HashMap<String, Object>();

		switch (command) {

		case SUBMENUS_BY_MENU_NAME: {
			map.put("mName", key);
			list = getMenus("SELECT m FROM Menu m WHERE m.parentMenu.name = :mName", map);
		}
			break;
		case ALL_BASE_MENU: {
			list = getMenus("SELECT m FROM Menu m WHERE m.parentMenu is null");
		}
			break;
		case ALL_BASE_MENU_FOR_HEADER: {

			Object object = servletRequest != null ? servletRequest.getSession().getAttribute("appTicket") : null;
			list = getMenus("SELECT m FROM Menu m WHERE m.parentMenu is null");
			list = list.stream().filter(m -> showMenu(m, object != null)).collect(Collectors.toList());
		}
			break;
		case ALL_MENUS: {
			list = getMenus("SELECT m FROM Menu m");
		}
			break;
		}

		// list.sort((MenuSO o1, MenuSO o2) ->
		// Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId())));

		return list;

	}

	private List<MenuSO> getMenus(String query) {
		List<MenuSO> list = new ArrayList<>();
		List<Menu> menus = APP_DB_Utils.queryListObjects(query, null, Menu.class);
		if (menus != null) {
			menus.forEach(x -> list.add(new MenuSO(x)));
		}

		return list;
	}

	private List<MenuSO> getMenus(String query, Map<String, Object> parameter) {
		List<MenuSO> list = new ArrayList<>();
		List<Menu> menus = APP_DB_Utils.queryListObjects(query, parameter, Menu.class);
		if (menus != null) {
			menus.forEach(x -> list.add(new MenuSO(x)));
		}

		return list;
	}

	public void setHttpServlerRequest(HttpServletRequest threadLocalRequest) {
		this.servletRequest = threadLocalRequest;
	}

	private boolean showMenu(MenuSO m, boolean islogged) {

		if (m.getTextInfo() != null && !m.getTextInfo().equals("OK")) {
			if (islogged) {
				return !m.getTextInfo().equals("NOT_LOGGED");
			} else {
				return !m.getTextInfo().equals("LOGGED");
			}
		} else
			return true;

	}
}
