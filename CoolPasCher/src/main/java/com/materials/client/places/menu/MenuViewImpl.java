package com.materials.client.places.menu;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.area.readonly.HArea;
import com.materials.client.widgets.area.readonly.VArea;
import com.materials.client.widgets.articles.SArticlesWidget;
import com.materials.client.widgets.articles.sortable.SortableArticle;
import com.materials.client.widgets.display.DisplayWidget;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonce;
import com.materials.client.widgets.swipe.SwipperWidget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

public class MenuViewImpl extends MaterialPanel implements MenuView {

	private Presenter presenter;
	private String lastCategory;
	private MaterialPanel basePanel = new MaterialPanel();
	private MaterialPanel panelForNewAnnonce = new MaterialPanel();
	private MaterialPanel panelForCategories = new MaterialPanel();
	private MaterialPanel panelForOthers = new MaterialPanel();
	
	private int duration = 1000;

	private DisplayWidget displayWidget;
	private SwipperWidget swipperWidget;

	public MenuViewImpl() {

		swipperWidget = new SwipperWidget("1000px");
		add(swipperWidget);

		swipperWidget.addSwipeItem("basePanel", basePanel, false);
		swipperWidget.addSwipeItem("panelForNewAnnonce", panelForNewAnnonce, false);
		swipperWidget.addSwipeItem("panelForOthers", panelForOthers, false);
		swipperWidget.addSwipeItem("panelForCategories", panelForCategories, false);
	}

	@Override
	public void swippeToSearchResult() {
		swipperWidget.show("panelForSearchResult", Transition.FADEIN,duration);
	}

	@Override
	public void swippeToCategory(SortableArticle article, String categoryName) {

		this.lastCategory = categoryName;
		panelForCategories.clear();
		panelForCategories.add(article);
		swipperWidget.show("panelForCategories", Transition.FADEIN,duration);
	}

	@Override
	public void swippeToCategory() {
		swipperWidget.show("panelForCategories", Transition.FADEIN,duration);

	}

	@Override
	public void swippeToAcceuil() {
		swipperWidget.swipeTo("basePanel");
	}

	@Override
	public void startAcceuil(boolean isHome) {

		MaterialPanel startBasePanel = CoolPasCherUI.CLIENT_FACTORY.getStartBasePanel();
		if (startBasePanel == null) {

			// slider & annonces
			MaterialColumn column2 = new MaterialColumn(12, 12, 12);
			MaterialColumn column3 = new MaterialColumn(12, 12, 12);

			column2.add(CoolPasCherUI.CLIENT_FACTORY.getMasterSlider());
			column3.add(CoolPasCherUI.CLIENT_FACTORY.getArticleRecentes());

			basePanel.add(column2);
			basePanel.add(column3);
			CoolPasCherUI.CLIENT_FACTORY.setStartBasePanel(basePanel);
		}
		swipperWidget.show("basePanel", Transition.FADEIN, duration);

	}

	@Override
	public void showNewAnnoncePanel(EditAbleAnnonce editAbleAnnonce) {
		editAbleAnnonce.enableSave(false);
		panelForNewAnnonce.clear();
		panelForNewAnnonce.add(editAbleAnnonce);
		swipperWidget.show("panelForNewAnnonce");
	}

	@Override
	public void showStartPanel() {
		swipperWidget.show("basePanel",Transition.FADEIN, duration);
	}

	@Override
	public void swipeBack(boolean isNewAnnonce) {
		swipperWidget.swipeBackTo("basePanel", Transition.FADEOUTLEFT);
		CoolPasCherUI.CLIENT_FACTORY.getArticleRecentes().reload();
	}

	@Override
	public void setContentAreas(List<CAreaSO> areaSOs) {
		panelForOthers.clear();
		areaSOs.sort(new Comparator<CAreaSO>() {
			@Override
			public int compare(CAreaSO o1, CAreaSO o2) {
				return Integer.valueOf(o1.getIndex()).compareTo(Integer.valueOf(o2.getIndex()));
			}
		});

		displayWidget = new DisplayWidget();
		panelForOthers.add(displayWidget);
		FlowPanel flowPanel = new FlowPanel();
		for (CAreaSO areaSO : areaSOs) {
			if (areaSO.getAreaType().equals(CAreaSO.TYPE_HOR)) {
				flowPanel.add(new HArea(areaSO));
			} else {
				flowPanel.add(new VArea(areaSO));
			}
		}
		displayWidget.add(flowPanel);
		// swipperWidget.swipeTo("basePanel");
		swipperWidget.swipeTo("panelForOthers", Transition.FADEIN, duration);
	}

	@Override
	public void loadFavorits(List<ContentSO> contentSOs) {
		panelForOthers.clear();
		SortableArticle sortableArticle = new SortableArticle(contentSOs, 1, 3000);
		if (contentSOs.size() < 3) {
			sortableArticle.setHeight("1200px");
		}
		panelForOthers.add(sortableArticle);
		swipperWidget.show("panelForOthers", Transition.FADE_IN_IMAGE,duration);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void onAttach() {
		super.onAttach();
		CoolPasCherUI.CLIENT_FACTORY.getAppMetaData().updateStandardMeta();
	}

	@Override
	public void setContent(Widget widget) {
		panelForOthers.clear();
		panelForOthers.add(widget);
		swipperWidget.show("panelForOthers", Transition.FADEIN, duration);
	}

	@Override
	public MaterialPanel getContent() {
		basePanel.clear();
		basePanel.setVisible(true);
		return basePanel;
	}

	@Override
	public void setContents(List<ContentSO> contentSOs) {
		basePanel.clear();
		SArticlesWidget articlesWidget = new SArticlesWidget(contentSOs);
		basePanel.add(articlesWidget);
		swipperWidget.show("basePanel", Transition.FADEIN, duration);
	}

	@Override
	public void clearContent() {
		basePanel.setVisible(false);
		basePanel.clear();
		panelForNewAnnonce.clear();
	}

	@Override
	public void clearAnnoncePanel() {
		panelForNewAnnonce.clear();
	}

	public MaterialPanel getPanelForSearchResult() {
		return basePanel;
	}
	
	@Override
	public String getLastCategory() {
		return lastCategory;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		basePanel.setVisible(false);
		panelForOthers.setVisible(false);
		panelForNewAnnonce.setVisible(false);
		panelForCategories.setVisible(false);

		basePanel.setOpacity(0);
		ClientUtils.addTimer(x -> CoolPasCherUI.CLIENT_FACTORY.getScrollHelper()
				.scrollTo(CoolPasCherUI.CLIENT_FACTORY.getScrollIndex()), 50);
	}

}
