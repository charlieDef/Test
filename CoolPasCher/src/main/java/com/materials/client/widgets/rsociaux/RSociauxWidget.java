package com.materials.client.widgets.rsociaux;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.model.RSocialSO;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialRow;

public class RSociauxWidget extends Composite {

	private static RSociauxWidgetUiBinder uiBinder = GWT.create(RSociauxWidgetUiBinder.class);

	interface RSociauxWidgetUiBinder extends UiBinder<MaterialRow, RSociauxWidget> {
	}

	@UiField
	MaterialRow materialRowUi;

	public RSociauxWidget(List<RSocialSO> list) {
		initWidget(uiBinder.createAndBindUi(this));

		for (RSocialSO socialSO : list) {
			MaterialImage image = new MaterialImage(socialSO.getHtmlImg().replaceAll("64", "32"));
			image.getElement().getStyle().setCursor(Cursor.POINTER);
			image.setTitle(socialSO.getTooltip());
			image.addClickHandler(e -> {
				Window.open(socialSO.getTargetUrl(), "_blank", "");
			});
			materialRowUi.add(image);
		}
	}

}
