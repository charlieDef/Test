package com.materials.shared.action.carea;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class CAreaAction extends BaseAction {

	private CAreaCommand command;

	/** For serialization only. */
	protected CAreaAction() {
		super();
	}

	public CAreaAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public CAreaAction(DBAction dbAction, List<APPObjectSO> contentSOs) {
		super(dbAction, contentSOs);
	}

	public CAreaAction(DBAction dbAction, CAreaCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}

	public CAreaCommand getCommand() {
		return command;
	}

}
