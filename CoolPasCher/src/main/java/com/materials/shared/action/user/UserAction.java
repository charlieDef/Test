package com.materials.shared.action.user;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class UserAction extends BaseAction {

	private UserCommand command;

	/** For serialization only. */
	protected UserAction() {
		super();
	}

	public UserAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public UserAction(DBAction dbAction, List<APPObjectSO> contentSOs) {
		super(dbAction, contentSOs);
	}

	public UserAction(DBAction dbAction, UserCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}

	public UserCommand getCommand() {
		return command;
	}

}
