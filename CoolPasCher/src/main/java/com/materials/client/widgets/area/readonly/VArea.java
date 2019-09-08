package com.materials.client.widgets.area.readonly;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;

import gwt.material.design.client.ui.MaterialImage;

public class VArea extends Composite {

	private CAreaSO areaSO;

	@UiField
	HTML textTitelUi, textContentUi;

	@UiField
	MaterialImage imageUi;

	public VArea(CAreaSO areaSO) {
		this.areaSO = areaSO;
		initWidget(uiBinder.createAndBindUi(this));
		loadModel();
	}

	void loadModel() {

		textTitelUi.setHTML(areaSO.getTitel());
		textContentUi.setHTML(areaSO.getTextContent());
		if (areaSO.getRandomId() == null || areaSO.getRandomId().isEmpty()) {
			imageUi.removeFromParent();
		} else {
			String urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?caRandomID=" + areaSO.getRandomId();
			imageUi.setUrl(urlImg);

			if (areaSO.getAreaType().equals(CAreaSO.TYPE_TEXT)) {
				imageUi.removeFromParent();
			}
		}

	}

	interface VAreaUiBinder extends UiBinder<Widget, VArea> {
	}

	private static VAreaUiBinder uiBinder = GWT.create(VAreaUiBinder.class);
}
