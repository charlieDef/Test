package com.materials.client.widgets.document;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialRow;

public class FileItem extends Composite {

	private MaterialRow materialRow = new MaterialRow();

	public FileItem(CAreaSO cAreaSO) {

		initWidget(materialRow);

		MaterialColumn imageColumn = new MaterialColumn();
		MaterialColumn textColumn = new MaterialColumn();
		textColumn.getElement().getStyle().setPaddingTop(12, Unit.PX);

		materialRow.add(imageColumn);
		materialRow.add(textColumn);

		materialRow.setVerticalAlign(VerticalAlign.BOTTOM);

		Anchor anchorButton = new Anchor(cAreaSO.getContentSO().getTitel());
		anchorButton.setTarget("_blank");
		String url = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?caRandomID=" + cAreaSO.getRandomId() + "&dType="
				+ cAreaSO.getAreaType() + "&fName=" + cAreaSO.getTitel();
		anchorButton.setHref(url);

		anchorButton.addStyleName("pbAnchor");
		anchorButton.setTitle(cAreaSO.getContentSO().getDescription());

		MaterialImage image = new MaterialImage(getImage(cAreaSO));
		image.setTitle(cAreaSO.getContentSO().getDescription());

		imageColumn.add(image);
		textColumn.add(anchorButton);

	}

	private ImageResource getImage(CAreaSO cAreaSO) {
		ImageResource imageResource = null;
		String titel = cAreaSO.getTitel();
		if (titel.contains("pdf")) {
			imageResource = CoolPasCherUI.APP_RESOURCE.pdfDoc();
		} else if (titel.contains("xls")) {
			imageResource = CoolPasCherUI.APP_RESOURCE.xlsDoc();
		} else if (titel.contains("doc")) {
			imageResource = CoolPasCherUI.APP_RESOURCE.worldDoc();
		} else if (titel.contains("zip")) {
			imageResource = CoolPasCherUI.APP_RESOURCE.zip();
		}
		return imageResource;
	}
}
