package com.materials.server.handler.user;

import java.util.Date;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.UserSO;
import com.materials.server.model.TempFile;
import com.materials.server.model.User;
import com.materials.utils.APPUtils;
import com.materials.utils.APP_DB_Utils;

public class UserUpdateHelper {

	public APPObjectSO update(APPObjectSO appObjectSO) {

		UserSO userSO = (UserSO) appObjectSO;
		User user = toUser(userSO, false);

		if (user != null) {
			if (userSO.getZuwu() != null && !userSO.getZuwu().isEmpty()) {

				String array[] = userSO.getZuwu().split("#");

				if (array != null && array.length == 2) {
					String old = array[0];
					String actual1 = array[1];
					String actualDecripted = APPUtils.getDecriptedPwd(user.getZuwu());
					if (actualDecripted.equals(old)) {
						user.setZuwu(APPUtils.getEncriptedPwd(actual1));
						userSO.setTextInfo("Modifications enregistrées!!");
					} else {
						userSO.setTextInfo("ERREUR: ANCIEN MOT DE PASS INCORRECT");
					}
				}

			}
			APP_DB_Utils.saveObjectToDatabase(user);
		}

		return userSO;
	}

	public APPObjectSO save(APPObjectSO appObjectSO) {
		UserSO userSO = (UserSO) appObjectSO;
		userSO.setCreation(new Date());
		User newUser = toUser(userSO, true);
		// newUser.setZuwu(APPUtils.getEncriptedPwd("BalengForever"));

		if (userSO.getZuwu() != null && !userSO.getZuwu().isEmpty()) {
			newUser.setZuwu(APPUtils.getEncriptedPwd(userSO.getZuwu()));
		} else {
			newUser.setZuwu(APPUtils.getEncriptedPwd("BalengForever"));
		}

		APP_DB_Utils.saveObjectToDatabase(newUser);
		userSO.setId(newUser.getId());
		return userSO;
	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {
		appObjectSOs.forEach(appObjectSO -> {
			UserSO commentSO = (UserSO) appObjectSO;
			APP_DB_Utils.deleteObjectFromDatabase(commentSO.getId(), User.class);
		});
		return true;
	}

	private User toUser(UserSO userSO, boolean isNew) {
		User user = isNew ? new User() : APP_DB_Utils.findObject(userSO.getId(), User.class);
		if (user != null) {
			user.setActive(userSO.isActive());
			user.setLock(userSO.isLock());
			user.setCreationDate(userSO.getCreation());
			user.setName(userSO.getName());
			user.setLastName(userSO.getLastName());
			user.setEmail(userSO.getEmail());
			user.setCity(userSO.getCity());
			user.setCountry(userSO.getCountry());
			user.setFunction(userSO.getFunction());
			user.setRandomId(userSO.getRandomId());
			user.setRole(userSO.getRole());
			user.setStatus(userSO.getStatus());
			user.setTel(userSO.getTel());
			user.setProfession(userSO.getProfession());
			user.setVillageArea(userSO.getVillageArea());
			user.setTextInfo(userSO.getTextInfo());
			user.setPwdChangeCode(userSO.getPwdChangeCode());
			user.setPwdChangeRequired(userSO.isPwdChangeRequired());
			user.setPwdChangeCodeValidity(userSO.getPwdChangeCodeValidity());

			if (!userSO.getUserProperties().keySet().isEmpty()) {
				for (String key : userSO.getUserProperties().keySet()) {
					user.getUserProperties().put(key, userSO.getUserProperties().get(key));
				}

			}

			// TODO be carefull here --> Image set
			if (userSO.getRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(userSO.getRandomId());
				if (tempFile != null) {
					user.setImageData(tempFile.getTempData());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}
		}

		return user;
	}

}
