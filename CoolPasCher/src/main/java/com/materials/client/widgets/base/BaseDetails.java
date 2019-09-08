package com.materials.client.widgets.base;

import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.UserSO;
import com.materials.client.widgets.form.MDFormPanel;

public abstract class BaseDetails extends Composite {

	protected MDFormPanel mdFormPanel;

	public BaseDetails() {

	}

	public abstract void setMDFormPanel();

	protected void handleEditEnable() {
		setMDFormPanel();
		boolean istAdminMaster = false;
		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
		if (userSO != null) {
			istAdminMaster = userSO.isAdminMaster();
		}

		mdFormPanel.enableEdit(istAdminMaster);
	}

}
