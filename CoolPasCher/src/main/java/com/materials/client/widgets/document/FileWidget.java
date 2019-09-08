package com.materials.client.widgets.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialRow;

public class FileWidget extends Composite {

	private Map<String, MaterialRow> categories = new HashMap<String, MaterialRow>();

	private static FileWidgetUiBinder uiBinder = GWT.create(FileWidgetUiBinder.class);

	interface FileWidgetUiBinder extends UiBinder<MaterialCard, FileWidget> {
	}

	@UiField
	MaterialCard materialCardUi;

	public FileWidget(List<ContentSO> list) {
		initWidget(uiBinder.createAndBindUi(this));

		for (ContentSO contentSO : list) {
			if (contentSO.isActive()) {
				if (contentSO.isIntern()) {
					if (CoolPasCherUI.checkLoggedMember()) {
						addFile(contentSO);
					}
				} else {
					addFile(contentSO);
				}
			}
		}
	}

	public void addFile(ContentSO contentSO) {
		// String nameCategory = contentSO.getMenuSO() != null ?
		// contentSO.getMenuSO().getName() : contentSO.getCategory();
		String nameCategory = contentSO.getCategory();

		MaterialRow materialRow = categories.get(nameCategory);
		if (materialRow == null) {
			materialRow = new MaterialRow();
			HTML html = new HTML(nameCategory);
			html.getElement().getStyle().setMarginLeft(10, Unit.PX);
			html.getElement().getStyle().setMarginRight(10, Unit.PX);
			html.addStyleName("rightContainerTitel");
			materialRow.add(html);
			categories.put(nameCategory, materialRow);
			materialCardUi.add(materialRow);
		}

		if (contentSO.getcAreaSOs() != null && !contentSO.getcAreaSOs().isEmpty()) {
			CAreaSO cAreaSO = contentSO.getcAreaSOs().get(0);
			FileItem fileItem = new FileItem(cAreaSO);
			materialRow.add(fileItem);
		}

	}
}
