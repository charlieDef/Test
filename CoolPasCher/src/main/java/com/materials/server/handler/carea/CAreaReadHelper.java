package com.materials.server.handler.carea;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.CAreaSO;
import com.materials.server.model.CArea;
import com.materials.shared.action.carea.CAreaCommand;
import com.materials.utils.APP_DB_Utils;

public class CAreaReadHelper {

	public List<APPObjectSO> read(CAreaCommand command, String key) {

		List<APPObjectSO> list = null;

		switch (command) {

		case BY_CONTENT_ID: {
			list = getCAreas("SELECT c FROM CArea c WHERE c.content.id = " + key);
		}
			break;

		default: {// FOR_HOME
		}
			break;
		}
		return list;

	}

	private List<APPObjectSO> getCAreas(String query) {
		List<APPObjectSO> list = new ArrayList<>();
		List<CArea> cAreas = APP_DB_Utils.queryListObjects(query, null, CArea.class);
		if (cAreas != null) {
			cAreas.forEach(x -> list.add(new CAreaSO(x)));
		}
		return list;
	}

}
