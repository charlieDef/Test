package com.materials.shared.result;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.APPTicket;

import net.customware.gwt.dispatch.shared.Result;

public class GeneralResult implements Result {

	private long id = 0;
	private String message = "";
	private boolean value = false;
	private APPTicket appTicket;
	private APPObjectSO object;
	private List<APPObjectSO> objects = new ArrayList<APPObjectSO>(0);

	protected GeneralResult() {
	}

	public GeneralResult(String message) {
		this.message = message;
	}

	public GeneralResult(APPTicket appTicket) {
		this.appTicket = appTicket;
	}

	public GeneralResult(APPTicket appTicket, List<APPObjectSO> objects) {
		this.appTicket = appTicket;
		this.objects = objects;
	}

	public GeneralResult(long id) {
		this.id = id;
	}

	public GeneralResult(boolean value) {
		this.value = value;
	}

	public GeneralResult(APPObjectSO object) {
		this.object = object;
	}

	public GeneralResult(List<APPObjectSO> objects) {
		this.objects = objects;
	}

	public long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public boolean getBooleanValue() {
		return value;
	}

	public APPTicket getAPPTicket() {
		return appTicket;
	}

	public List<APPObjectSO> getObjects() {
		return objects;
	}

	public APPObjectSO getObject() {
		return object;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

}
