package com.materials.client.places.content;

import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.area.readonly.HArea;
import com.materials.client.widgets.area.readonly.VArea;
import com.materials.client.widgets.articles.annonce.simple.SimpleViewAbleAnnonceDetailsView;
import com.materials.client.widgets.comments.CommentArea;
import com.materials.client.widgets.display.DisplayWidget;
import com.materials.client.widgets.slider.jssor.JssorFlexImgGallery;
import com.materials.client.widgets.swipe.SwipperWidget;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.animate.Transition;

public class ContentViewImpl extends MaterialRow implements ContentView {

	private Presenter presenter;
	private MaterialPanel materialPanel = new MaterialPanel();
	private SwipperWidget swipperWidget;
	private SimpleViewAbleAnnonceDetailsView annonceDetailsView;

	public ContentViewImpl() {

		swipperWidget = new SwipperWidget();
		swipperWidget.addSwipeItem("materialPanel", materialPanel, true);
		add(swipperWidget);

	}

	@Override
	public void showAnnonceDetails(SimpleViewAbleAnnonceDetailsView annonceDetailsView) {
		this.annonceDetailsView = annonceDetailsView;

		materialPanel.clear();
		materialPanel.add(annonceDetailsView);
		swipperWidget.show("materialPanel", Transition.FADEIN);
	}

	@Override
	public void showContentAreas(ContentSO contentSO) {

		clear();

		DisplayWidget displayWidget = new DisplayWidget();
		MaterialPanel materialPanel = new MaterialPanel();
		materialPanel.getElement().getStyle().setProperty("minHeight", "800px");

		add(displayWidget);
		add(materialPanel);
		List<CAreaSO> areaSOs = contentSO.getcAreaSOs();
		FlowPanel flowPanel = new FlowPanel();
		for (CAreaSO areaSO : areaSOs) {
			if (areaSO.getAreaType().equals(CAreaSO.TYPE_HOR)) {
				flowPanel.add(new HArea(areaSO));
			} else {
				flowPanel.add(new VArea(areaSO));
			}
		}
		displayWidget.add(flowPanel);
		materialPanel.add(new CommentArea(contentSO));
		CoolPasCherUI.CLIENT_FACTORY.addAnimation(this, null, 500);
	}

	@Override
	public void setContentImages(ContentSO contentSO) {

		clear();
		DisplayWidget displayWidget = new DisplayWidget();

		add(displayWidget);
		MaterialPanel flowPanel = new MaterialPanel();
		MaterialLabel html = new MaterialLabel("::: " + contentSO.getTitel());
		html.addStyleName("rsliderTitel");
		flowPanel.add(html);
		flowPanel.add(new JssorFlexImgGallery(contentSO.getcAreaSOs()));
		displayWidget.add(flowPanel);
		CoolPasCherUI.CLIENT_FACTORY.addAnimation(this, null, 500);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setContent(Widget widget) {
		clear();
		setOpacity(0);
		add(widget);
		CoolPasCherUI.CLIENT_FACTORY.addAnimation(this, null, 800);
	}

}
