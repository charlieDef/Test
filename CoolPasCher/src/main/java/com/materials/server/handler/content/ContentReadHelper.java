package com.materials.server.handler.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.materials.client.model.ContentSO;
import com.materials.server.model.Content;
import com.materials.server.model.User;
import com.materials.shared.action.content.ContentCommand;
import com.materials.utils.APP_DB_Utils;

public class ContentReadHelper {

	public List<ContentSO> read(ContentCommand command, String key) {

		List<ContentSO> list = null;
		Map<String, Object> map = new HashMap<String, Object>();

		switch (command) {

		case ANNONCES_BY_PAGE_NR: {
			list = new ArrayList<ContentSO>();
			List<ContentSO> listForPage = getContents(
					"SELECT c FROM Content c WHERE c.showToHome = '1' AND c.active = '1' AND c.type  in ('ANNONCE_CONTENT')");

			Collections.sort(listForPage, Comparator.comparing(ContentSO::getCreationDate).reversed());

			int initIndex = getInitIndex(key);
			int endIndex = getEndIndex(key);

			for (int i = initIndex; i < endIndex; i++) {

				if (i < listForPage.size()) {
					ContentSO contentSO = listForPage.get(i);
					list.add(contentSO);
				}

			}

		}
			break;

		case FAVORITS: {
			list = new ArrayList<>();
			List<ContentSO> listnew = getContents(
					"SELECT c FROM Content c WHERE c.showToHome = '1' AND c.active = '1' AND c.type  in ('ANNONCE_CONTENT')");
			String[] ids = key.split("#");
			for (String id : ids) {
				for (ContentSO contentSO : listnew) {
					if (contentSO.getId() == Long.valueOf(id) && !list.contains(contentSO)) {
						list.add(contentSO);
						break;
					}
				}
			}
		}
			break;

		case BY_MENU_NAME: {
			map.put("cName", key);
			list = getContents("SELECT c FROM Content c, Menu m WHERE c.menu.id = m.id AND m.name = :cName", map);
		}
			break;
		case BY_SLIDER_ID: {
			list = getContents(
					"SELECT c FROM Content c, SliderItem s WHERE c.id = s.content.id AND s.id='" + key + "'");
		}
			break;
		case BY_TITEL: {

			map.put("ctitel", key);
			list = getContents("SELECT c FROM Content c WHERE c.titel = :ctitel", map);
		}
			break;
		case FOR_HOME_ACTIVE: {
			list = getContents(
					"SELECT c FROM Content c WHERE c.showToHome = '1' AND c.active = '1' AND c.type  in ('ANNONCE_CONTENT','ARTICLE_CONTENT','H_ADVERTISEMENT_CONTENT','V_ADVERTISEMENT_CONTENT')");
		}
			break;
		case ALL_ARTICLE_CONTENTS: {
			list = getContents(
					"SELECT c FROM Content c WHERE c.type in ('ANNONCE_CONTENT','ARTICLE_CONTENT','V_ADVERTISEMENT_CONTENT','H_ADVERTISEMENT_CONTENT','PHOTO_CONTENT')");
		}
			break;

		case ALL_ANNONCES_FOR_USER: {
			map.put("cName", key);
			list = new ArrayList<>();
			list = getContents(
					"SELECT c FROM Content c WHERE c.showToHome = '1' AND c.active = '1' AND c.type  in ('ANNONCE_CONTENT') AND c.creator='"
							+ key + "'");
		}
			break;
		case ALL_ANNONCES_BY_CATEGORY: {
			list = getContents(
					"SELECT c FROM Content c WHERE c.type in ('ANNONCE_CONTENT') AND c.active = '1' AND c.category like'%"
							+ key + "%'");
		}
			break;
		case ALL_CONTENTS_LABEL: {
			list = new ArrayList<>();
			list = getContents("SELECT c FROM Content c WHERE (c.label like'%" + key + "%' OR c.category like '%" + key
					+ "%' OR c.titel like '%" + key + "%') AND c.type in ('ANNONCE_CONTENT') AND c.showToHome = '1' ");
		}
			break;
		}
		return list;

	}

	private List<ContentSO> getContents(String query) {
		List<ContentSO> list = new ArrayList<>();
		List<Content> contents = APP_DB_Utils.queryListObjects(query, null, Content.class);
		if (contents != null) {
			contents.forEach(x -> list.add(getIt(x)));
		}
		return list;
	}

	private List<ContentSO> getContents(String query, Map<String, Object> parameter) {
		List<ContentSO> list = new ArrayList<>();
		List<Content> contents = APP_DB_Utils.queryListObjects(query, parameter, Content.class);
		if (contents != null) {
			contents.forEach(x -> list.add(getIt(x)));
		}
		return list;
	}

	private ContentSO getIt(Content content) {
		ContentSO contentSO = new ContentSO(content);
		if (content.getCreator() != null && NumberUtils.isNumber(content.getCreator())) {
			User user = APP_DB_Utils.findObject(Long.valueOf(content.getCreator()), User.class);
			if (user != null) {
				fillProperties(contentSO, user);
			}
		}
		return contentSO;
	}

	private void fillProperties(ContentSO contentSO, User user) {
		contentSO.addProperty(ContentSO.PP_CREATOR_NAME, user.getName());
		contentSO.addProperty(ContentSO.PP_CREATOR_LASTNAME, user.getLastName());
		contentSO.addProperty(ContentSO.PP_CREATOR_COUNTRY, user.getCountry());
		contentSO.addProperty(ContentSO.PP_CREATOR_CITY, user.getCity());
		contentSO.addProperty(ContentSO.PP_CREATOR_TYPE, user.getVillageArea());
		contentSO.addProperty(ContentSO.PP_CREATOR_EMAIL, user.getEmail());
		contentSO.addProperty(ContentSO.PP_CREATOR_TEL, String.valueOf(user.getTel()));
		contentSO.addProperty(ContentSO.PP_CREATOR_TEXT, user.getTextInfo());
		contentSO.addProperty(ContentSO.PP_CREATOR_RANDOM_ID, user.getRandomId());
	}

	private int getInitIndex(String pageNr) {
		if (pageNr.equals("0")) {
			return 0;
		} else if (pageNr.equals("1")) {
			return 8;
		} else {
			return 16;
		}
	}

	private int getEndIndex(String pageNr) {
		if (pageNr.equals("0")) {
			return 8;
		} else if (pageNr.equals("1")) {
			return 16;
		} else {
			return 24;
		}
	}
}
