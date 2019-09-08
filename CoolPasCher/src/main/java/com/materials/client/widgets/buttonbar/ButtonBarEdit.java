package com.materials.client.widgets.buttonbar;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.ui.MaterialIcon;

public class ButtonBarEdit extends Composite {

	private static ButtonBarEditUiBinder uiBinder = GWT.create(ButtonBarEditUiBinder.class);

	interface ButtonBarEditUiBinder extends UiBinder<HTMLPanel, ButtonBarEdit> {
	}

	private boolean editMode = false;

	private List<MaterialIcon> others = new ArrayList<>();
	private ButtonBarEditHandler barEditHandler;

	@UiField
	MaterialIcon editButton, cancelButton, saveButton;

	@UiField
	HTMLPanel divBase;

	public ButtonBarEdit() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("editButton")
	public void onEditClick(ClickEvent clickEvent) {
		setEditMode(true);
		editButton.setVisible(false);
		cancelButton.setVisible(true);
		saveButton.setVisible(true);

		others.forEach(btn -> {
			btn.setVisible(false);
		});

		if (barEditHandler != null) {

			barEditHandler.onEdit();
		}
	}

	public void setIconSize(IconSize iconSize) {
		editButton.setIconSize(iconSize);
		cancelButton.setIconSize(iconSize);
		saveButton.setIconSize(iconSize);

		others.forEach(btn -> {
			btn.setIconSize(iconSize);
		});

	}

	public void setIconColor(Color color) {
		editButton.setIconColor(color);
		cancelButton.setIconColor(color);
		saveButton.setIconColor(color);

		others.forEach(btn -> {
			btn.setIconColor(color);
		});

	}

	@UiHandler("cancelButton")
	public void onCancelClick(ClickEvent clickEvent) {
		setEditMode(false);
		editButton.setVisible(true);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
		if (others.size() > 0) {
			others.forEach(btn -> {
				btn.setVisible(true);
			});
		}

		if (barEditHandler != null) {
			barEditHandler.onCancel();
		}
	}

	@UiHandler("saveButton")
	public void onSaveClick(ClickEvent clickEvent) {
		setEditMode(false);
		editButton.setVisible(true);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
		others.forEach(btn -> {
			btn.setVisible(true);
		});
		if (barEditHandler != null) {
			barEditHandler.onSave();
		}
	}

	public void setBarEditHandler(ButtonBarEditHandler barEditHandler) {
		this.barEditHandler = barEditHandler;
	}

	@UiChild
	public void addButton(MaterialIcon iconButton) {
		iconButton.addStyleName("buttonDetail");
		divBase.add(iconButton);
		others.add(iconButton);
	}

	public void clickEdit() {
		onEditClick(null);
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void cancelEditMode() {
		setEditMode(false);
		onCancelClick(null);
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
}
