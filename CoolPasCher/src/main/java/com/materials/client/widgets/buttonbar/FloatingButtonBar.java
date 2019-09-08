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
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.widgets.button.ControlButton;

import gwt.material.design.client.ui.MaterialPanel;

public class FloatingButtonBar extends Composite {

	private static FloatingButtonBarUiBinder uiBinder = GWT.create(FloatingButtonBarUiBinder.class);

	interface FloatingButtonBarUiBinder extends UiBinder<Widget, FloatingButtonBar> {
	}

	private boolean editMode = false;
	private List<ControlButton> others = new ArrayList<>();
	private ButtonBarEditHandler barEditHandler;
	@UiField
	MaterialPanel divBase;

	@UiField
	ControlButton cancelButtonUi, saveButtonUi, editButtonUi;

	public FloatingButtonBar() {
		initWidget(uiBinder.createAndBindUi(this));
		saveButtonUi.setVisible(false);
		cancelButtonUi.setVisible(false);
	}

	@UiHandler("editButtonUi")
	public void onEditClick(ClickEvent clickEvent) {
		setEditMode(true);
		editButtonUi.setVisible(false);
		cancelButtonUi.setVisible(true);
		saveButtonUi.setVisible(true);

		others.forEach(btn -> {
			btn.setVisible(false);
		});

		if (barEditHandler != null) {
			barEditHandler.onEdit();
		}
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	@UiHandler("cancelButtonUi")
	public void onCancelClick(ClickEvent clickEvent) {
		setEditMode(false);
		editButtonUi.setVisible(true);
		cancelButtonUi.setVisible(false);
		saveButtonUi.setVisible(false);
		if (others.size() > 0) {
			others.forEach(btn -> {
				btn.setVisible(true);
			});
		}

		if (barEditHandler != null) {
			barEditHandler.onCancel();
		}
	}

	@UiHandler("saveButtonUi")
	public void onSaveClick(ClickEvent clickEvent) {
		setEditMode(false);
		editButtonUi.setVisible(true);
		cancelButtonUi.setVisible(false);
		saveButtonUi.setVisible(false);
		others.forEach(btn -> {
			btn.setVisible(true);
		});
		if (barEditHandler != null) {
			barEditHandler.onSave();
		}
	}

	@UiChild
	public void addButton(ControlButton iconButton) {
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

	public void setButtonBarEditHandler(ButtonBarEditHandler barEditHandler) {
		this.barEditHandler = barEditHandler;
	}

	public void setFloatingEnabled(boolean enable) {
		editButtonUi.setEnabled(enable);
		cancelButtonUi.setEnabled(enable);
		saveButtonUi.setEnabled(enable);
		others.forEach(btn -> {
			btn.setEnabled(enable);
		});
	}

}
