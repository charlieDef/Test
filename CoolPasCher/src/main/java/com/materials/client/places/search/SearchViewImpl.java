package com.materials.client.places.search;

import com.google.gwt.dom.client.Style.Unit;
import com.materials.client.CoolPasCherUI;
import com.materials.client.utils.ClientUtils;
import com.materials.client.utils.ConstantsUtils;
import com.materials.client.widgets.articles.sortable.SortableArticle;
import com.materials.client.widgets.swipe.SwipperWidget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.LoaderType;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

public class SearchViewImpl extends MaterialPanel implements SearchView {

	private Presenter presenter;
	private SwipperWidget swipperWidget;
	private MaterialPanel materialPanel = new MaterialPanel();
	private String lastViewName;
	private int duration = 1000;

	public SearchViewImpl() {
		swipperWidget = new SwipperWidget("800px");
		add(swipperWidget);
		swipperWidget.addSwipeItem("materialPanel", materialPanel, false);
	}

	@Override
	public void showSearchResult(SortableArticle article, String searchTerm, boolean isLast) {

		materialPanel.clear();
		MaterialLabel label = new MaterialLabel(article.getSize() + "  Annones(s) pour [ " + searchTerm + " ]");
		label.getElement().getStyle().setProperty("fontFamily", ConstantsUtils.MBOA_FONT_FAMILY);
		label.getElement().getStyle().setFontSize(20, Unit.PX);
		label.getElement().getStyle().setMarginTop(20, Unit.PX);
		label.getElement().getStyle().setMarginBottom(15, Unit.PX);
		label.getElement().getStyle().setMarginLeft(20, Unit.PX);
		materialPanel.add(label);
		if (article.getSize() > 0) {
			materialPanel.add(article);
		}
		this.lastViewName = searchTerm;
		swipperWidget.show("materialPanel", isLast ? Transition.FADEIN : Transition.FADE_IN_IMAGE,duration);
	}

	@Override
	public void showLast() {
		swipperWidget.show("materialPanel", Transition.FADEIN, duration);
	}

	@Override
	public String getLastSearchName() {
		return lastViewName;

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void showLoading() {
		materialPanel.clear();
		MaterialPanel panel = new MaterialPanel();
		panel.setHeight("800px");
		panel.setMarginLeft(25);
		panel.setMarginRight(25);
		MaterialLoader loader = new MaterialLoader();
		loader.getProgress().setColor(Color.RED_DARKEN_2);
		loader.setContainer(panel);
		loader.setType(LoaderType.PROGRESS);
		loader.show();

		materialPanel.add(panel);
		swipperWidget.show("materialPanel", Transition.FADE_IN_IMAGE, 1200);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		ClientUtils.addTimer(x -> CoolPasCherUI.CLIENT_FACTORY.getScrollHelper()
				.scrollTo(CoolPasCherUI.CLIENT_FACTORY.getScrollIndex()), 50);
	}

}
