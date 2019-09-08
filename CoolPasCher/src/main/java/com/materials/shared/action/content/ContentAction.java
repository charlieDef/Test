package com.materials.shared.action.content;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class ContentAction extends BaseAction {

	private ContentCommand command;

	/** For serialization only. */
	protected ContentAction() {
		super();
	}

	public ContentAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public ContentAction(DBAction dbAction, List<APPObjectSO> contentSOs) {
		super(dbAction, contentSOs);
	}

	public ContentAction(DBAction dbAction, ContentCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}

	public ContentCommand getCommand() {
		return command;
	}
}
