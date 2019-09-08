package com.materials.shared.action.slider;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.BaseAction;
import com.materials.shared.action.DBAction;

public class SliderAction extends BaseAction {

	private SliderCommand command;

	/** For serialization only. */
	protected SliderAction() {
		super();
	}

	public SliderAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public SliderAction(DBAction dbAction, List<APPObjectSO> contentSOs) {
		super(dbAction, contentSOs);
	}

	public SliderAction(DBAction dbAction, SliderCommand command, String key) {
		super(dbAction, key);
		this.command = command;
	}

	public SliderCommand getCommand() {
		return command;
	}

}
