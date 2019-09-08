package com.materials.shared.action;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.shared.Action;

public abstract class BaseAction implements Action<GeneralResult> {

	private DBAction dbAction;
	private List<APPObjectSO> objects;
	private APPObjectSO object;
	private String key;
	private String key2;

	/** For serialization only. */
	protected BaseAction() {
	}

	protected BaseAction(DBAction dbAction, String key) {
		this.dbAction = dbAction;
		this.key = key;
	}
	protected BaseAction(DBAction dbAction, String key,String key2) {
		this.dbAction = dbAction;
		this.key = key;
		this.key2 = key2;
	}

	protected BaseAction(DBAction dbAction, APPObjectSO object) {
		this.dbAction = dbAction;
		this.object = object;
	}

	protected BaseAction(DBAction dbAction, List<APPObjectSO> objects) {
		this.dbAction = dbAction;
		this.objects = objects;
	}

	public String getKey() {
		return key;
	}
	
	public String getKey2() {
		return key2;
	}

	public List<APPObjectSO> getObjects() {
		return objects;
	}

	public DBAction getDbAction() {
		return dbAction;
	}

	public APPObjectSO getObject() {
		return object;
	}

}
