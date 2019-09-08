package com.materials.shared.action.stats;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class StatisticAction extends BaseAction {

	private StatisticCommand command;

	protected StatisticAction() {
		super();
	}

	public StatisticAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public StatisticAction(DBAction dbAction, List<APPObjectSO> contentSOs) {
		super(dbAction, contentSOs);
	}

	public StatisticAction(DBAction dbAction, StatisticCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}
	
	public StatisticAction(DBAction dbAction, StatisticCommand command, String key, String key2) {
		super(dbAction, key, key2);
		this.command = command;
	}

	public StatisticCommand getCommand() {
		return command;
	}

}
