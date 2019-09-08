package com.materials.client.widgets.model.content.flex.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FlexContentDetailsEdit extends Composite {

	private static FlexContentDetailsEditUiBinder uiBinder = GWT.create(FlexContentDetailsEditUiBinder.class);

	interface FlexContentDetailsEditUiBinder extends UiBinder<Widget, FlexContentDetailsEdit> {
	}

	public FlexContentDetailsEdit() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
