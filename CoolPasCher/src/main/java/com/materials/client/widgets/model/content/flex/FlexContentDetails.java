package com.materials.client.widgets.model.content.flex;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.ContentSO;

import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

public class FlexContentDetails extends Composite implements FlexContentDetailsView {

	private static FlexContentDetailsUiBinder uiBinder = GWT.create(FlexContentDetailsUiBinder.class);

	interface FlexContentDetailsUiBinder extends UiBinder<MaterialCard, FlexContentDetails> {
	}

	@UiField
	MaterialRow materialRowSliderUi, materialRow1Ui, materialRow2Ui, materialRow4Ui;
	@UiField
	MaterialColumn columnTopLeft, columnBottomLeft, columnBottomRight;
	@UiField
	MaterialPanel columnTopRight;

	private ContentSO contentSO;
	private Presenter presenter;

	public FlexContentDetails() {
		initWidget(uiBinder.createAndBindUi(this));

		// super.setOpacity(1);
		// materialRowSliderUi.add(new HTMLFlexImageGallery());

		ViewPort.when(Resolution.ALL_MOBILE, Resolution.TABLET).then(viewPortChange -> {
			columnBottomRight.getElement().getStyle().setBorderWidth(0, Unit.PX);
		});

		ViewPort.when(Resolution.ALL_LAPTOP).then(viewPortChange -> {
			columnBottomRight.getElement().getStyle().setBorderWidth(1, Unit.PX);
		});

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;

	}

	public void addToTop1(Widget widget) {
		materialRow1Ui.add(widget);
	}

	public void addToTop1Left(Widget widget) {
		columnTopLeft.add(widget);
	}

	public void addToTop1Right(Widget widget) {
		columnTopRight.add(widget);
	}

	public void addToTop2(Widget widget) {
		materialRow2Ui.add(widget);
	}

	public void addToMiddle(Widget widget) {
		materialRowSliderUi.add(widget);
	}

	public void addToBottomLeft(Widget widget) {
		columnBottomLeft.add(widget);
	}

	public void addToBottomRight(Widget widget) {
		columnBottomRight.add(widget);
	}

}
