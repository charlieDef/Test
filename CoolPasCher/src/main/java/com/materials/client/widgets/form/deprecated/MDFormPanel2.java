/**
 * 
 */
package com.materials.client.widgets.form.deprecated;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.widgets.buttonbar.ButtonBarEdit;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.upload.MDFileUploader;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

/**
 * @author Mouafo Deffo Charles
 *
 */
public class MDFormPanel2 extends Composite {

	private Map<String, Widget> map = new HashMap<>();

	private static MDFormPanelUiBinder uiBinder = GWT.create(MDFormPanelUiBinder.class);

	interface MDFormPanelUiBinder extends UiBinder<MaterialCard, MDFormPanel2> {
	}

	private ButtonBarEditHandler formHandler;

	@UiField
	ButtonBarEdit buttonbar;
	@UiField
	MaterialRow formContent, topDiv, bottomDiv;
	@UiField
	MaterialColumn headerAreaUi1, headerAreaUi2;
	@UiField
	MaterialRow materialRow1Ui;

	@UiField
	MaterialCard materialCardUi;

	public MDFormPanel2() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonbar.setBarEditHandler(new BBarHandler());

		buttonbar.setIconColor(Color.AMBER_DARKEN_3);
	}

	@UiChild
	public void addFormButton(MaterialIcon iconButton) {
		buttonbar.addButton(iconButton);
	}

	@UiChild
	public void addChild(Widget widget, String bindName) {
		formContent.add(widget);
		if (bindName != null && !bindName.isEmpty()) {
			map.put(bindName, widget);
		}
	}

	@UiChild
	public void addChildHeader1(Widget widget) {
		headerAreaUi1.add(widget);
	}

	@UiChild
	public void addChildHeader2(Widget widget) {
		headerAreaUi2.add(widget);
	}

	@UiChild
	public void addChildTop(Widget widget) {
		topDiv.add(widget);
	}

	@UiChild
	public void addChildBottom(Widget widget) {
		bottomDiv.add(widget);
	}

	public void edit() {
		buttonbar.clickEdit();
	}

	class BBarHandler implements ButtonBarEditHandler {
		@Override
		public void onEdit() {
			setEnabled(true);
			if (formHandler != null) {
				formHandler.onEdit();
			}
		}

		@Override
		public void onSave() {
			setEnabled(false);
			if (formHandler != null) { // TODO Presenter here
				formHandler.onSave();
			}
		}

		@Override
		public void onCancel() {
			setEnabled(false);
			if (formHandler != null) { // TODO Presenter here
				formHandler.onCancel();
			}
		}
	}

	public void setEnabled(boolean readOnly) {
		map.forEach((attr, widget) -> {
			if (widget instanceof MaterialTextBox) {
				MaterialTextBox input = (MaterialTextBox) widget;
				input.setEnabled((readOnly));
			} else if (widget instanceof MaterialCheckBox) {
				MaterialCheckBox check = (MaterialCheckBox) widget;
				check.setEnabled(readOnly);
			} else if (widget instanceof MaterialComboBox) {
				MaterialComboBox<String> mcombobox = (MaterialComboBox<String>) widget;
				mcombobox.setEnabled(readOnly);
			} else if (widget instanceof MaterialTextArea) {
				MaterialTextArea mtextarea = (MaterialTextArea) widget;
				mtextarea.setEnabled(readOnly);
			} else if (widget instanceof MDFileUploader) {
				MDFileUploader mfileUpload = (MDFileUploader) widget;
				mfileUpload.setVisible(readOnly);
			} else if (widget instanceof MaterialSwitch) {
				MaterialSwitch mswitch = (MaterialSwitch) widget;
				mswitch.setEnabled(readOnly);
			} else if (widget instanceof MaterialDatePicker) {
				MaterialDatePicker dpicker = (MaterialDatePicker) widget;
				dpicker.setEnabled(readOnly);
			} else if (widget instanceof MaterialIntegerBox) {
				MaterialIntegerBox ibox = (MaterialIntegerBox) widget;
				ibox.setEnabled(readOnly);
			}
		});
	}

	public void setFormHandler(ButtonBarEditHandler formHandler) {
		this.formHandler = formHandler;
	}

	public void resetMode() {
		if (buttonbar.isEditMode()) {
			buttonbar.cancelEditMode();
		}
	}

	public void enableEdit(boolean enable) {
		buttonbar.setVisible(enable);
	}
}
