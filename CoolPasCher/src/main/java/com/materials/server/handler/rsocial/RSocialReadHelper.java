package com.materials.server.handler.rsocial;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.RSocialSO;
import com.materials.server.model.RSocial;
import com.materials.shared.action.rsocial.RSocialCommand;
import com.materials.utils.APP_DB_Utils;

public class RSocialReadHelper {

	public List<RSocialSO> read(RSocialCommand command, String key) {

		List<RSocialSO> list = null;

		switch (command) {

		case ACTIVE_RSOCIAUX: {
			list = getRSocials("SELECT s FROM RSocial s WHERE s.active = '1'");
		}
			break;

		case ALL_RSOCIAUX: {
			list = getRSocials("SELECT s FROM RSocial s");
		}
			break;
		}
		return list;

	}

	private List<RSocialSO> getRSocials(String query) {
		List<RSocialSO> list = new ArrayList<>();
		List<RSocial> rSocials = APP_DB_Utils.queryListObjects(query, null, RSocial.class);
		if (rSocials != null) {
			rSocials.forEach(x -> list.add(new RSocialSO(x)));
		}
		return list;
	}

}
