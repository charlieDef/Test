package com.materials.client.context;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.materials.client.model.APPTicket;
import com.materials.client.model.ContentSO;
import com.materials.client.model.MenuSO;
import com.materials.client.model.UserSO;
import com.materials.client.model.WebSiteSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.places.configurations.contentConfig.ContentConfigView;
import com.materials.client.places.configurations.menuConfig.MenuConfigView;
import com.materials.client.places.configurations.rsociaux.RSociauxConfigView;
import com.materials.client.places.configurations.siteConfig.WebSiteConfigView;
import com.materials.client.places.configurations.sliderConfig.SliderConfigView;
import com.materials.client.places.configurations.stat.StatisticConfigView;
import com.materials.client.places.configurations.userConfig.UserConfigView;
import com.materials.client.places.content.ContentView;
import com.materials.client.places.menu.MenuView;
import com.materials.client.places.mymboa.MyMboaView;
import com.materials.client.places.search.SearchView;
import com.materials.client.views.content.StartView;
import com.materials.client.widgets.articles.recentes.ArticleRecentes;
import com.materials.client.widgets.articles.sortable.SortableArticle;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.client.widgets.login.LoginWidgetView2;
import com.materials.client.widgets.model.content.annonce.AnnonceDetailsView;
import com.materials.client.widgets.register.RegisterWidgetView;
import com.materials.client.widgets.slider.jssor.master.MasterSlider;
import com.materials.shared.APPMetaData;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.helper.ScrollHelper;
import gwt.material.design.client.ui.MaterialPanel;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Charles Mouafo Deffo
 *
 */
public interface AppClientFactory {

	void getAllBaseMenus(Consumer<List<MenuSO>> consumer);

	EventBus getEventBus();

	PlaceController getPlaceController();

	ScrollHelper getScrollHelper();

	void buildWidgetsForWelcome(Consumer<Widget[]> consumer);

	void getContentsForMenu(String menuName, Consumer<List<ContentSO>> consumer);

	void getContentForArticle(String contentTitel, Consumer<ContentSO> consumer);

	void getContentsByPage(String pageNr, Consumer<List<ContentSO>> consumer);

	MenuView getMenuView();

	MyMboaView getMyMboaView();

	StartView getStartView();

	SearchView getSearchView();

	ContentView getContentView();

	ContentConfigView getContentConfigView();

	MenuConfigView getMenuConfigView();

	UserConfigView getUserConfigView();

	SliderConfigView getSliderConfigView();

	RSociauxConfigView getRSociauxConfigView();

	WebSiteConfigView getSiteConfigView();

	StatisticConfigView getStatisticConfigView();

	AnnonceDetailsView getAnnonceDetailsView();

	// for slider
	void startJESSORSlider(String sliderId, boolean preview);

	void startRSlider();

	void startJESSORImageGallery();

	void startJESSORFlexImageGallery();

	// -------------Widget--------------------
	LoginWidgetView2 getLoginWidget();

	RegisterWidgetView getRegisterWidget();

	ConfirmationWidget getConfirmationWidget();

	void checkTicket(Consumer<APPTicket> consumer);

	// -------------Tables--------------------
	void addAnimation(final ComplexPanel complexPanel1, final ComplexPanel complexPanel2, int milli);

	// Help
	void deleteTempFile(String randomId);

	<R extends Result> void execute(Action<R> action, Consumer<R> successCallback);

	void handleException(Throwable caught);

	void scrollAppTo(int maxTop);

	void addAnimation(Element element, int milli);

	void scrollAppToTop(boolean animate);

	void scrollToTop();

	WebSiteSO getWebSiteSO();

	UserSO getActualUserSO();

	void setUserSO(UserSO userSO);

	void handleCurrentHistory();

	void setCurrentHistoryConsumer(Consumer<Boolean> consumer);

	APPMetaData getAppMetaData();

	MenuSO getMenuByTitel(String titel);

	List<StatisticSO> getStatisticSOs();

	Map<String, SortableArticle> getSearchArticle();

	Map<Long, String> getAnnoncesCategories();

	public void setAnnoncesCategories(Map<Long, String> cMap);

	void setStartView(StartView startView);

	boolean isSessionNew();

	boolean reloadContent();

	void setReloadContent(boolean reload);

	MenuSO getMenuSO();

	ContentSO getContentSO();

	void addNewArticle(ContentSO saved);

	void setScrollIndex(double index);

	double getScrollIndex();

	void setPageIndex(int index);

	int getPageIndex();

	MaterialWidget getBackButton();

	MaterialPanel getEmptyStatePanel();

	MaterialPanel getStartBasePanel();

	void setStartBasePanel(MaterialPanel materialPanel);

	MasterSlider getMasterSlider();

	ArticleRecentes getArticleRecentes();

	void updateCache(List<ContentSO> contentSOs);

}
