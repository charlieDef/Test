package com.materials.shared.action.mtemplate;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class MenuTemplateAction extends BaseAction {

	private MenuTemplateCommand command;

	/** For serialization only. */
	protected MenuTemplateAction() {
		super();
	}

	public MenuTemplateAction(DBAction dbAction, APPObjectSO templateSO) {
		super(dbAction, templateSO);
	}

	public MenuTemplateAction(DBAction dbAction, List<APPObjectSO> templateSOs) {
		super(dbAction, templateSOs);
	}

	public MenuTemplateAction(DBAction dbAction, MenuTemplateCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}

	public MenuTemplateCommand getCommand() {
		return command;
	}
}
