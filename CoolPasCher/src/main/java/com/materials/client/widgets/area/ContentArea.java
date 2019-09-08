package com.materials.client.widgets.area;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.CAreaSO;
import com.materials.client.widgets.area.editable.HAreaEditable;
import com.materials.client.widgets.area.editable.VAreaEditable;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.display.DisplayWidget;

public class ContentArea extends BaseDetails2 implements ContentAreaView {

	private Presenter presenter;
	private HAreaEditable hAreaEditable;
	private VAreaEditable vAreaEditable;

	private static ContentAreaUiBinder uiBinder = GWT.create(ContentAreaUiBinder.class);

	interface ContentAreaUiBinder extends UiBinder<Widget, ContentArea> {
	}

	@UiField
	DisplayWidget contentAreaUi;

	public ContentArea(boolean readOnly) {

		super(true);

		add(uiBinder.createAndBindUi(this));
		if (readOnly) {
			getElement().getStyle().setOverflowY(Overflow.AUTO);
		}
	}

	@Override
	public void setContentArea(CAreaSO areaSO) {

		if (areaSO.getAreaType().equals(CAreaSO.TYPE_HOR)) {
			hAreaEditable = new HAreaEditable();
			hAreaEditable.setBackCallback(x -> presenter.backToContentAreaList());
			hAreaEditable.setSaveCallback(object -> presenter.saveContentArea(object));
			hAreaEditable.setModel(areaSO);
			contentAreaUi.setContent(hAreaEditable);

		} else if (areaSO.getAreaType().equals(CAreaSO.TYPE_VER)) {
			vAreaEditable = new VAreaEditable();
			vAreaEditable.setBackCallback(x -> presenter.backToContentAreaList());
			vAreaEditable.setSaveCallBack(object -> presenter.saveContentArea(object));
			vAreaEditable.setModel(areaSO);
			contentAreaUi.setContent(vAreaEditable);
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setContentAreas(List<CAreaSO> areaSOs) {
		contentAreaUi.clear();

	}

	@Override
	public void setMDFormPanel() {
		// TODO Auto-generated method stub

	}

}
