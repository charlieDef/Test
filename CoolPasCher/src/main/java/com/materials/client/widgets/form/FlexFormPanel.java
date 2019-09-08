package com.materials.client.widgets.form;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FlexFormPanel extends Composite {

	private static FlexFormPanelUiBinder uiBinder = GWT.create(FlexFormPanelUiBinder.class);

	interface FlexFormPanelUiBinder extends UiBinder<Widget, FlexFormPanel> {
	}

	public FlexFormPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
