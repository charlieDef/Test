package com.materials.client.widgets.model.content.annonce.pic;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.materials.client.model.CAreaSO;
import com.materials.client.widgets.slider.jssor.JssorImgGallery;

import gwt.material.design.client.ui.MaterialPanel;

public class AnnoncePicture extends Composite {

	private List<CAreaSO> areaSOs;
	private MaterialPanel materialPanel;

	public AnnoncePicture(List<CAreaSO> areaSOs) {
		this.areaSOs = areaSOs;

		this.materialPanel = new MaterialPanel();
		materialPanel.setHeight("480px");
		initWidget(materialPanel);

	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();

		JssorImgGallery htmlFlexImageGallery = new JssorImgGallery(areaSOs);
		materialPanel.add(htmlFlexImageGallery);
	}

}
