package com.materials.client.widgets.connect;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.client.ui.MaterialFAB;

public class ConnectionPanel extends Composite {

	private static ConnectionPanelUiBinder uiBinder = GWT.create(ConnectionPanelUiBinder.class);

	interface ConnectionPanelUiBinder extends UiBinder<MaterialFAB, ConnectionPanel> {
	}

	public ConnectionPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
