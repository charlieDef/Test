package com.materials.client.widgets.ckeditor;

import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.client.ui.MaterialPanel;

public class MDEditor extends Composite {

	private MaterialPanel materialPanel = new MaterialPanel();
	private CKeditor cKeditor;

	public MDEditor() {
		initWidget(materialPanel);
		cKeditor = new CKeditor("54365", "150px");
		cKeditor.setEditorBasic();
		materialPanel.add(cKeditor);
	}

	public MDEditor(String id, String height) {
		initWidget(materialPanel);
		cKeditor = new CKeditor(id, height);
		cKeditor.setEditorBasic();
		materialPanel.add(cKeditor);
	}

	public void setEditorValue(String value) {
		cKeditor.setValue(value);
	}

	public String getEditorValue() {
		return cKeditor.getValue();
	}

}
