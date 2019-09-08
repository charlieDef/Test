package com.materials.server.handler.content;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.server.handler.carea.CAreaUpdateHelper;
import com.materials.server.model.CArea;
import com.materials.server.model.Content;
import com.materials.server.model.Menu;
import com.materials.server.model.TempFile;
import com.materials.server.model.User;
import com.materials.utils.APP_DB_Utils;

public class ContentUpdateHelper {

	private List<String> tempFileToDelete = new ArrayList<String>();

	public APPObjectSO update(APPObjectSO appObjectSO) {

		ContentSO contentSO = (ContentSO) appObjectSO;
		Content content = toContent(contentSO, false);
		if (content != null) {

			// check CArea to delete
			if (!contentSO.getcAreaSOToDelete().isEmpty()) {
				List<CArea> areas = new ArrayList<CArea>(content.getContentAreas());
				for (String areaSO : contentSO.getcAreaSOToDelete()) {
					for (CArea area : content.getContentAreas()) {
						if (area.getRandomId().equals(areaSO)) {
							areas.remove(area);
							APP_DB_Utils.deleteObjectFromDatabase(area.getId(), CArea.class);
							break;
						}
					}
				}
				content.setContentAreas(areas);
				contentSO.clearDeletedCAreas();
			}

			// check CArea
			if (!contentSO.getcAreaSOs().isEmpty()) {
				for (CAreaSO areaSO : contentSO.getcAreaSOs()) {
					boolean isNew = areaSO.getId() == -10;
					CArea cArea = CAreaUpdateHelper.toCArea(areaSO, isNew);
					if (isNew) {
						APP_DB_Utils.saveObjectToDatabase(cArea);
						areaSO.setId(cArea.getId());
						content.addContentArea(cArea);
					}
				}
			}
			APP_DB_Utils.saveObjectToDatabase(content);
		}

		return contentSO;
	}

	public APPObjectSO save(APPObjectSO appObjectSO) {

		ContentSO contentSO = (ContentSO) appObjectSO;
		Content newContent = toContent(contentSO, true);
		APP_DB_Utils.saveObjectToDatabase(newContent);
		contentSO.setId(newContent.getId());

		// check CArea
		if (!contentSO.getcAreaSOs().isEmpty()) {
			for (CAreaSO areaSO : contentSO.getcAreaSOs()) {
				CArea cArea = CAreaUpdateHelper.toCArea(areaSO, true);
				newContent.addContentArea(cArea);
				APP_DB_Utils.saveObjectToDatabase(cArea);
				APP_DB_Utils.saveObjectToDatabase(newContent);
				areaSO.setId(cArea.getId());
			}
		}

		// User user = APP_DB_Utils.findObject(Long.valueOf(contentSO.getCreator()),
		// User.class);
		User user = APP_DB_Utils.getObjectByAttribute("email", contentSO.getCreator(), User.class);
		if (user != null) {
			fillProperties(contentSO, user);
			user.addHashFragmentProperty(UserSO.ANNONCES, String.valueOf(newContent.getId()));
			APP_DB_Utils.saveObjectToDatabase(user);
		}

		return contentSO;
	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {

		appObjectSOs.forEach(appObjectSO -> {
			ContentSO contentSO = (ContentSO) appObjectSO;

			APP_DB_Utils.deleteObjectFromDatabase(contentSO.getId(), Content.class);

			// User user = APP_DB_Utils.findObject(Long.valueOf(contentSO.getCreator()),
			// User.class);
			User userCreator = APP_DB_Utils.getObjectByAttribute("email", contentSO.getCreator(), User.class);
			if (userCreator != null) {
				userCreator.removeHashFragmentProperty(UserSO.ANNONCES, "" + contentSO.getId());
				APP_DB_Utils.saveObjectToDatabase(userCreator);

			}
		});
		return true;
	}

	private Content toContent(ContentSO contentSO, boolean isNew) {
		Content content = isNew ? new Content() : APP_DB_Utils.findObject(contentSO.getId(), Content.class);
		if (content != null) {
			content.setActive(contentSO.isActive());
			content.setLock(contentSO.isLock());
			content.setShowToHome(contentSO.isShowToHome());
			content.setViewAble(contentSO.getViewAble());
			content.setViewed(contentSO.getViewed());
			content.setCreationDate(contentSO.getCreationDate());
			content.setCategory(contentSO.getCategory());
			content.setDescription(contentSO.getDescription());
			content.setDescription2(contentSO.getDescription2());
			content.setTitel(contentSO.getTitel());
			content.setType(contentSO.getType());
			content.setCreator(contentSO.getCreator());
			content.setIntern(contentSO.isIntern());
			content.setTextInfo(contentSO.getTextInfo());

			content.setLongDescription(contentSO.getLongDescription());
			content.setProvince(contentSO.getProvince());
			content.setVille(contentSO.getVille());
			content.setQuartier(contentSO.getQuartier());
			content.setLocalite(contentSO.getLocalite());
			content.setVip(contentSO.isVip());
			content.setLabel(contentSO.getLabel());
			content.setPrix(contentSO.getPrix());

			if (!contentSO.getContentProperties().keySet().isEmpty()) {
				for (String key : contentSO.getContentProperties().keySet()) {
					content.getContentProperties().put(key, contentSO.getContentProperties().get(key));
				}

			}

			updateMenuSO(contentSO, content);

			// TODO be carefull here --> Image set
			if (contentSO.getRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(contentSO.getRandomId());
				if (tempFile != null) {
					content.setRandomId(contentSO.getRandomId());
					content.setImageData(tempFile.getTempData());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}
		}

		return content;
	}

	private void updateMenuSO(ContentSO contentSo, Content content) {
		if (contentSo.getMenuSO() != null) {
			Menu parentMenu = APP_DB_Utils.getObjectByAttribute("name", contentSo.getMenuSO().getName(), Menu.class);
			if ((content.getMenu() == null) || (parentMenu != null && content.getMenu() != null
					&& !parentMenu.getName().equals(content.getMenu().getName()))) {
				content.setMenu(parentMenu);
			}
		}
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

}
