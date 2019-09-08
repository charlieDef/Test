package com.materials.shared.action;

import java.util.HashMap;
import java.util.Map;

import com.materials.client.model.APPObjectSO;

public class GeneralAction extends BaseAction {

	private GeneralCommand command;
	private Map<String, String> arguments = new HashMap<String, String>();
	private String[] array;

	public GeneralAction() {
		super();
	}

	public GeneralAction(DBAction dbAction, APPObjectSO contentSO) {
		super(dbAction, contentSO);
	}

	public GeneralAction(DBAction dbAction, GeneralCommand command, APPObjectSO contentSO) {
		super(dbAction, contentSO);
		this.command = command;
	}

	public GeneralAction(GeneralCommand command, Map<String, String> arguments, String key) {
		super(null, key);
		this.arguments = arguments;
		this.command = command;
	}

	public GeneralAction(GeneralCommand command, String[] array) {
		super();
		this.command = command;
		this.array = array;
	}

	public Map<String, String> getArguments() {
		return arguments;
	}

	public GeneralCommand getCommand() {
		return command;
	}

	public String[] getArray() {
		return array;
	}
}
