package com.materials.shared.action.rsocial;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class RSocialAction extends BaseAction {

	private RSocialCommand command;

	/** For serialization only. */
	protected RSocialAction() {
		super();
	}

	public RSocialAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public RSocialAction(DBAction dbAction, List<APPObjectSO> contentSOs) {
		super(dbAction, contentSOs);
	}

	public RSocialAction(DBAction dbAction, RSocialCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}

	public RSocialCommand getCommand() {
		return command;
	}

}
