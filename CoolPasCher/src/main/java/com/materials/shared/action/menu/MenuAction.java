package com.materials.shared.action.menu;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class MenuAction extends BaseAction {

	private MenuCommand command;

	/** For serialization only. */
	protected MenuAction() {
		super();
	}

	public MenuAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public MenuAction(DBAction dbAction, List<APPObjectSO> contentSOs) {
		super(dbAction, contentSOs);
	}

	public MenuAction(DBAction dbAction, MenuCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}

	public MenuCommand getCommand() {
		return command;
	}

}
