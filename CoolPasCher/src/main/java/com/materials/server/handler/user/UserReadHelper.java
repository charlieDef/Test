package com.materials.server.handler.user;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.UserSO;
import com.materials.server.model.User;
import com.materials.shared.action.user.UserCommand;
import com.materials.utils.APP_DB_Utils;

public class UserReadHelper {

	public List<UserSO> read(UserCommand command, String key) {

		List<UserSO> list = new ArrayList<>();

		switch (command) {

		case BY_EMAIL: {
			list = getUsers("SELECT u FROM User u WHERE u.email = '" + key + "'");
		}
			break;
		case BY_LASTNAME: {
			list = getUsers("SELECT u FROM User u WHERE u.lastName like '%" + key + "%'");
		}
			break;
		case ALL_USERS: {
			list = getUsers("SELECT u FROM User u");
		}
			break;
		case BY_NAME: {
			list = getUsers("SELECT u FROM User u WHERE u.name like '%" + key + "%'");
		}
			break;
		}
		return list;

	}

	private List<UserSO> getUsers(String query) {
		List<UserSO> list = new ArrayList<>();
		List<User> users = APP_DB_Utils.queryListObjects(query, null, User.class);
		if (users != null) {
			users.forEach(x -> list.add(new UserSO(x)));
		}

		return list;
	}

}
