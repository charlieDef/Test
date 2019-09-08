package com.materials.shared.action.comment;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class CommentAction extends BaseAction {

	private CommentCommand command;

	protected CommentAction() {
		super();
	}

	public CommentAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public CommentAction(DBAction dbAction, List<APPObjectSO> contentSOs) {
		super(dbAction, contentSOs);
	}

	public CommentAction(DBAction dbAction, CommentCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}

	public CommentCommand getCommand() {
		return command;
	}

}
