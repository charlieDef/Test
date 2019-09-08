package com.materials.client.widgets.base;

import java.util.LinkedList;

import com.materials.client.CoolPasCherUI;
import com.materials.client.model.UserSO;
import com.materials.client.widgets.button.ControlButton;
import com.materials.client.widgets.confirm.ConfirmationWidget;

import gwt.material.design.client.ui.MaterialPanel;

public abstract class BaseList extends MaterialPanel {

	protected LinkedList<ControlButton> buttons = new LinkedList<>();
	private ConfirmationWidget confirmationWidgetDelete;

	public BaseList() {

		// setLayoutPosition(Position.ABSOLUTE);
		setLeft(0);
		setRight(0);
		setOpacity(0);

		buildTable();

		iniIcons();

		enableIcon();

	}

	public BaseList(boolean configure) {

		if (configure) {
			// setLayoutPosition(Position.ABSOLUTE);
			setLeft(0);
			setRight(0);
			setOpacity(0);
		}

		buildTable();

		iniIcons();

		enableIcon();

	}

	protected abstract void buildTable();

	public abstract void iniIcons();

	private void enableIcon() {
		boolean istAdminMaster = false;
		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
		if (userSO != null) {
			istAdminMaster = userSO.isAdminMaster();
		}
		for (ControlButton icon : buttons) {
			icon.setVisible(istAdminMaster);
		}

	}

	public ConfirmationWidget getConfirmationWidgetDelete() {
		return confirmationWidgetDelete;
	}

	public LinkedList<ControlButton> getButtons() {
		return buttons;
	}
}
