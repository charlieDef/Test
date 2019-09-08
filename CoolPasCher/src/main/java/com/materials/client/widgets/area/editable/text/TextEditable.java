package com.materials.client.widgets.area.editable.text;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.widgets.ckeditor.CKeditor;
import com.materials.client.widgets.confirm.ConfirmationWidget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialRow;

public class TextEditable extends Composite implements HasValueChangeHandlers<String>, HasValue<String> {

	private static TextEditableUiBinder uiBinder = GWT.create(TextEditableUiBinder.class);

	interface TextEditableUiBinder extends UiBinder<Widget, TextEditable> {
	}

	private CKeditor cKeditor;
	private ConfirmationWidget confirmationWidget;
	private String editorHeight, editorTitel;

	@UiField
	HTML htmlUi;

	@UiField
	MaterialIcon editButtonUi;

	@UiField
	MaterialRow materialRowUi;

	@UiConstructor
	public TextEditable(String editorHeight, String editorTitel) {
		this.editorTitel = editorTitel;
		this.editorHeight = editorHeight;

		initWidget(uiBinder.createAndBindUi(this));

		confirmationWidget = new ConfirmationWidget(editorTitel, edited -> {
			if (edited) {
				setValue(cKeditor.getValue(), true);
			}
		});
		materialRowUi.add(confirmationWidget);
		cKeditor = new CKeditor(String.valueOf(new Date().getTime()), editorHeight);
		confirmationWidget.setWidgetContent(editorTitel, cKeditor);
		editButtonUi.setTitle(editorTitel);
	}

	@UiHandler("editButtonUi")
	public void onClick(ClickEvent event) {
		cKeditor.setValue(getValue());
		confirmationWidget.show();
	}

	@UiHandler("materialRowUi")
	public void onMouseOver(MouseOverEvent event) {
		editButtonUi.setVisible(true);
	}

	@UiHandler("materialRowUi")
	public void onMouseOut(MouseOutEvent event) {
		editButtonUi.setVisible(false);
	}

	public String getValue() {
		return htmlUi.getHTML();
	}

	public void setEditorHeight(String editorHeight) {
		this.editorHeight = editorHeight;
	}

	public String getEditorHeight() {
		return editorHeight;
	}

	public void setEditorTitel(String editorTitel) {
		this.editorTitel = editorTitel;
	}

	public String getEditorTitel() {
		return editorTitel;
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		htmlUi.setHTML(value);
		if (fireEvents) {
			ValueChangeEvent.fire(this, getValue());
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public void setValue(String value) {
		setValue(value, false);
	}

}
