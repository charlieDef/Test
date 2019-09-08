package com.materials.client.widgets.model.content.flex.owner;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.utils.WidgetUtils;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;

public class OwnerPanel extends Composite {

	private static OwnerPanelUiBinder uiBinder = GWT.create(OwnerPanelUiBinder.class);

	interface OwnerPanelUiBinder extends UiBinder<Widget, OwnerPanel> {
	}

	@UiField
	MaterialLabel labelBigUi, labelSmallUi;

	@UiField
	MaterialImage imageUi;

	public OwnerPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		WidgetUtils.addToViewPort(labelBigUi);
		WidgetUtils.addToViewPort(labelSmallUi);
	}

	public void setValues(Map<String, String> map) {
		String imageUrl = map.get(ContentSO.PP_CREATOR_RANDOM_ID);
		String creator = map.get(ContentSO.PP_CREATOR_NAME) + " " + map.get(ContentSO.PP_CREATOR_LASTNAME);
		imageUi.setUrl(getUserImageUrl(imageUrl));
		labelBigUi.setValue(creator);
		labelSmallUi.setValue(map.get(ContentSO.PP_CREATOR_TEXT));
	}

	public String getUserImageUrl(String randomId) {
		String urlImg = "img/a_user.jpg";
		if (randomId != null && !randomId.isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?userID=" + randomId;
		}
		return urlImg;
	}
}
