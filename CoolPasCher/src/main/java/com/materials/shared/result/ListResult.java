package com.materials.shared.result;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;

import net.customware.gwt.dispatch.shared.Result;

public class ListResult implements Result {

	private List<APPObjectSO> objects = new ArrayList<>();

	// for serialization only
	public ListResult() {
	}

	public ListResult(List<APPObjectSO> objects) {
		this.objects = objects;
	}

	public List<APPObjectSO> getObjects() {
		return objects;
	}

	public void addAll(List<APPObjectSO> datas) {
		objects.addAll(datas);
	}
}
