package com.materials.client.widgets.base;

import com.google.gwt.dom.client.Style.Position;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.UserSO;
import com.materials.client.widgets.buttonbar.FloatingButtonBar;
import com.materials.client.widgets.form.MDFormPanel;

import gwt.material.design.client.ui.MaterialPanel;

public abstract class BaseDetails2 extends MaterialPanel {

	protected MDFormPanel mdFormPanel;

	public BaseDetails2(boolean adjustPosition) {

		if (adjustPosition) {
			setOpacity(0);
			setLayoutPosition(Position.ABSOLUTE);
			setLeft(0);
			setRight(0);
		}

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

	public FloatingButtonBar getButtonBar() {
		return mdFormPanel.getButtonbar();
	}
}