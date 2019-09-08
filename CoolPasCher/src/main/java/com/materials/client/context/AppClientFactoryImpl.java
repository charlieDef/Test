package com.materials.client.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.materials.client.model.APPTicket;
import com.materials.client.model.ContentSO;
import com.materials.client.model.MenuSO;
import com.materials.client.model.RSocialSO;
import com.materials.client.model.SliderSO;
import com.materials.client.model.UserSO;
import com.materials.client.model.WebSiteSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.places.configurations.contentConfig.ContentConfigView;
import com.materials.client.places.configurations.contentConfig.ContentConfigViewImple;
import com.materials.client.places.configurations.menuConfig.MenuConfigView;
import com.materials.client.places.configurations.menuConfig.MenuConfigViewImpl;
import com.materials.client.places.configurations.rsociaux.RSociauxConfigView;
import com.materials.client.places.configurations.rsociaux.RSociauxConfigViewImpl;
import com.materials.client.places.configurations.siteConfig.WebSiteConfigView;
import com.materials.client.places.configurations.siteConfig.WebSiteConfigViewImpl;
import com.materials.client.places.configurations.sliderConfig.SliderConfigView;
import com.materials.client.places.configurations.sliderConfig.SliderConfigViewImpl;
import com.materials.client.places.configurations.stat.StatisticConfigView;
import com.materials.client.places.configurations.stat.StatisticConfigViewImpl;
import com.materials.client.places.configurations.userConfig.UserConfigView;
import com.materials.client.places.configurations.userConfig.UserConfigViewImpl;
import com.materials.client.places.content.ContentView;
import com.materials.client.places.content.ContentViewImpl;
import com.materials.client.places.menu.MenuView;
import com.materials.client.places.menu.MenuViewImpl;
import com.materials.client.places.mymboa.MyMboaView;
import com.materials.client.places.mymboa.MyMboaViewImpl;
import com.materials.client.places.search.SearchView;
import com.materials.client.places.search.SearchViewImpl;
import com.materials.client.utils.KeyValue;
import com.materials.client.views.content.StartView;
import com.materials.client.widgets.articles.recentes.ArticleRecentes;
import com.materials.client.widgets.articles.sortable.SortableArticle;
import com.materials.client.widgets.ckeditor.CKeditor;
import com.materials.client.widgets.confirm.ConfirmationWidget;
import com.materials.client.widgets.login.LoginWidget2;
import com.materials.client.widgets.model.content.annonce.AnnonceDetails;
import com.materials.client.widgets.model.content.annonce.AnnonceDetailsView;
import com.materials.client.widgets.notallowed.EmptyStatePanel;
import com.materials.client.widgets.register.RegisterWidget;
import com.materials.client.widgets.register.RegisterWidgetView;
import com.materials.client.widgets.slider.jssor.master.MasterSlider;
import com.materials.shared.APPMetaData;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.GeneralAction;
import com.materials.shared.action.GeneralCommand;
import com.materials.shared.action.content.ContentAction;
import com.materials.shared.action.content.ContentCommand;
import com.materials.shared.action.menu.MenuAction;
import com.materials.shared.action.menu.MenuCommand;

import gwt.material.design.client.base.helper.ScrollHelper;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialPanel;
import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

public class AppClientFactoryImpl implements AppClientFactory {

	private DispatchAsync dispatch = new StandardDispatchAsync(new DefaultExceptionHandler());
	private EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final ScrollHelper scrollHelper = new ScrollHelper();

	private boolean galleryStartet = false;
	private MasterSlider masterSlider;
	private ArticleRecentes articleRecentes;

	private double scrollIndex = 0;
	private int pageIndex = 0;
	private MaterialButton backButton;
	private MaterialPanel startBasePanelPanel;
	private boolean sliderStartet = false, sessionNew = false, reloadMenuContent = false, sliderPreviewStartet = false,
			modeNewAnnonce = false;
	private WebSiteSO webSiteSO;
	private UserSO actualUserSO;
	private List<StatisticSO> statisticSOs = new ArrayList<StatisticSO>();
	private Map<Long, String> annoncesCategories = new HashMap<Long, String>();

	private Map<String, SortableArticle> searchArticle = new HashMap<String, SortableArticle>();

	private MenuSO menuSO;
	private ContentSO contentSO;

	private MenuView menuView = new MenuViewImpl();
	private ContentView contentView = new ContentViewImpl();
	private AnnonceDetailsView annonceDetailsView = new AnnonceDetails();
	private SearchView searchView = new SearchViewImpl();
	private StartView startView;
	private Consumer<Boolean> currentHistory;

	private LoginWidget2 loginWidget;
	private RegisterWidget registerWidget;
	private ConfirmationWidget confirmationWidget;

	// 0 --> Slider | 1 --> Citation | 2 --> Content
	private Widget[] widgetsForWelcome = new Widget[3];
	private List<MenuSO> allMenus = new ArrayList<MenuSO>();
	private List<MenuSO> settingsMenus = new ArrayList<MenuSO>();
	private List<ContentSO> allArticlesContents = new ArrayList<ContentSO>();
	private List<RSocialSO> allRSociaux = new ArrayList<RSocialSO>();
	private List<KeyValue<ContentSO>> contents = new ArrayList<KeyValue<ContentSO>>();

	private CKeditor basicCKeditor = new CKeditor("54365", "150px");
	private APPMetaData metaData = new APPMetaData();

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public <R extends Result> void execute(Action<R> action, Consumer<R> successCallback) {
		dispatch.execute(action, new AppAsyncCallback<>(successCallback));
	}

	@Override
	public void handleException(Throwable caught) {
		String message = caught.getMessage() == null ? "Server communication failed!" : caught.getMessage();
		GWT.log(message, caught);
	}

	private class AppAsyncCallback<T> implements AsyncCallback<T> {

		private final Consumer<T> successCallback;

		private AppAsyncCallback(Consumer<T> successCallback) {
			this.successCallback = successCallback;
		}

		@Override
		public final void onFailure(Throwable caught) {
			AppClientFactoryImpl.this.handleException(caught);
		}

		@Override
		public final void onSuccess(T result) {
			this.successCallback.accept(result);
		}
	}

	@Override
	public MenuSO getMenuByTitel(String titel) {
		MenuSO menuSO = null;
		List<MenuSO> menuBases = allMenus.stream().filter(x -> x.getParentMenuSo() == null)
				.collect(Collectors.toList());
		for (MenuSO menuBase : menuBases) {
			for (MenuSO subMenu : menuBase.getSubMenuSos()) {
				if (subMenu.getName().equals(titel)) {
					return subMenu;
				}
			}
		}
		return menuSO;
	}

	@Override
	public void addAnimation(ComplexPanel complexPanel1, ComplexPanel complexPanel2, int milli) {
		if (complexPanel1 != null) {
			complexPanel1.getElement().getStyle().setOpacity(0);
			complexPanel1.getElement().getStyle().setProperty("opacity", "0");
		}
		if (complexPanel2 != null) {
			complexPanel2.getElement().getStyle().setOpacity(0);
			complexPanel1.getElement().getStyle().setProperty("opacity", "0");
		}
		new Animation() {
			@Override
			protected void onUpdate(double progress) {
				if (complexPanel1 != null) {
					complexPanel1.getElement().getStyle().setOpacity(progress);
					complexPanel1.getElement().getStyle().setProperty("opacity", String.valueOf(progress));
				}
				if (complexPanel2 != null) {
					complexPanel2.getElement().getStyle().setOpacity(progress);
					complexPanel1.getElement().getStyle().setProperty("opacity", String.valueOf(progress));
				}
			}
		}.run(milli);
	}

	@Override
	public void addAnimation(Element element, int milli) {
		new Animation() {
			@Override
			protected void onUpdate(double progress) {
				if (element != null) {
					element.getStyle().setOpacity(progress);
					element.getStyle().setProperty("opacity", String.valueOf(progress));
				}
			}
		}.run(milli);
	}

	@Override
	public void buildWidgetsForWelcome(Consumer<Widget[]> consumer) {

		if (widgetsForWelcome[0] == null) {
			execute(new GeneralAction(GeneralCommand.READ_MENUS_SLIDERS_ARTICLES_RSOCIAUX_WEBSITE, null, null),
					result -> {

						actualUserSO = result.getAPPTicket().getUserLogged();
						this.menuSO = null;

						List<SliderSO> sliders = new ArrayList<>();

						// Sliders
						result.getObjects().stream().filter(obj -> obj instanceof SliderSO).collect(Collectors.toList())
								.forEach(s -> sliders.add((SliderSO) s));

						// Menus
						result.getObjects().stream().filter(obj -> obj instanceof MenuSO).collect(Collectors.toList())
								.forEach(menu -> allMenus.add((MenuSO) menu));

						// Contents
						result.getObjects().stream().filter(obj -> obj instanceof ContentSO)
								.collect(Collectors.toList())
								.forEach(content -> allArticlesContents.add((ContentSO) content));

						// // RSocialSO
						// result.getObjects().stream().filter(obj -> obj instanceof RSocialSO)
						// .collect(Collectors.toList()).forEach(rs -> allRSociaux.add((RSocialSO) rs));

						// WebSiteSO
						result.getObjects().stream().filter(obj -> obj instanceof WebSiteSO)
								.collect(Collectors.toList()).forEach(ws -> webSiteSO = (WebSiteSO) ws);

						// StatisticSO
						result.getObjects().stream().filter(obj -> obj instanceof StatisticSO)
								.collect(Collectors.toList()).forEach(stat -> statisticSOs.add((StatisticSO) stat));

						List<ContentSO> listFiltered = allArticlesContents.stream().filter(ContentSO::isShowToHome)
								.collect(Collectors.toList());

						widgetsForWelcome[0] = new MasterSlider(sliders);
						widgetsForWelcome[1] = new ArticleRecentes(listFiltered);
						widgetsForWelcome[2] = new SortableArticle(listFiltered, 1000, 4000);
						consumer.accept(widgetsForWelcome);

						sessionNew = result.getBooleanValue();
					});
		} else {
			updateAnnonceButton();
			consumer.accept(widgetsForWelcome);
		}
	}

	private void updateAnnonceButton() {
		// chec
		// Timer timer = new Timer() {
		// @Override
		// public void run() {
		// ((SearchPanel3) widgetsForWelcome[2]).showSource();
		// }
		// };
		// timer.schedule(500);

	}

	@Override
	public void scrollToTop() {

		Timer timer = new Timer() {
			@Override
			public void run() {
				scrollTop();
			}
		};
		timer.schedule(300);
	}

	@Override
	public void startJESSORSlider(String sliderId, boolean preview) {
		if (preview) {
			runIt(sliderId);
		} else if (!sliderStartet) {
			runIt(sliderId);
			sliderStartet = true;
		}

	}

	private void runIt(String sliderId) {
		Timer timer = new Timer() {
			@Override
			public void run() {
				loadJESSORSlider(sliderId);
			}
		};
		timer.schedule(1000);
	}

	@Override
	public void startJESSORImageGallery() {

		Timer timer = new Timer() {
			@Override
			public void run() {
				loadJESSORImageGallery();
			}
		};
		timer.schedule(1);

	}

	@Override
	public void startJESSORFlexImageGallery() {

		Timer timer = new Timer() {
			@Override
			public void run() {
				loadJESSORFlexImageGallery();
			}
		};
		timer.schedule(1000);

	}

	@Override
	public void startRSlider() {
		Timer timer = new Timer() {
			@Override
			public void run() {
				loadRS();
			}
		};
		timer.schedule(1);
	}

	public static native void loadRS() /*-{
		$wnd.startRSlider();
	}-*/;

	public static native void scrollTop() /*-{
		$wnd.scrollToTop();
	}-*/;

	public static native void loadJESSORSlider(String sliderId) /*-{
		$wnd.startJssorSlider(sliderId);
	}-*/;

	public static native void loadJESSORImageGallery() /*-{
		$wnd.startJssorImageGallery();
	}-*/;

	public static native void loadJESSORFlexImageGallery() /*-{
		$wnd.loadJESSORFlexImageGallery();
	}-*/;

	@Override
	public void getContentsForMenu(final String menuClicked, Consumer<List<ContentSO>> consumer) {
		if (allMenus.isEmpty()) {
			execute(new MenuAction(DBAction.READ, MenuCommand.ALL_MENUS, null), result -> {
				result.getObjects().stream().filter(obj -> obj instanceof MenuSO).collect(Collectors.toList())
						.forEach(s -> {
							MenuSO menuSO = (MenuSO) s;
							allMenus.add(menuSO);
						});

				allMenus.forEach(menuSO -> {
					if (menuSO.getName().equals(menuClicked)) {
						this.menuSO = menuSO;
						consumer.accept(menuSO.getContents());
					}
				});

			});
		} else {
			allMenus.forEach(menuSO -> {
				if (menuSO.getName().equals(menuClicked)) {
					this.menuSO = menuSO;

					consumer.accept(menuSO.getContents());
				}
			});
		}
	}

	@Override
	public void getContentForArticle(final String contentTitel, Consumer<ContentSO> consumer) {
		this.menuSO = null;
		if (allArticlesContents.isEmpty() || !contain(contentTitel)) {
			execute(new ContentAction(DBAction.READ, ContentCommand.FOR_HOME_ACTIVE, null), result -> {
				result.getObjects().forEach(appObject -> {
					ContentSO contentSO = (ContentSO) appObject;
					allArticlesContents.add(contentSO);
				});

				allArticlesContents.forEach(contentSO -> {
					if (contentSO.getTitel().equals(contentTitel)) {
						this.contentSO = contentSO;
						consumer.accept(contentSO);
					}
				});

			});
		} else {
			allArticlesContents.forEach(contentSO -> {
				if (contentSO.getTitel().equals(contentTitel)) {
					this.contentSO = contentSO;
					consumer.accept(contentSO);
				}
			});
		}
	}

	private boolean contain(String name) {

		return allArticlesContents.stream().filter(x -> x.getTitel().equals(name)).count() > 0;
	}

	@Override
	public void getAllBaseMenus(Consumer<List<MenuSO>> consumer) {
		if (allMenus.isEmpty()) {
			execute(new MenuAction(DBAction.READ, MenuCommand.ALL_MENUS, null), result -> {
				result.getObjects().stream().filter(obj -> obj instanceof MenuSO).collect(Collectors.toList())
						.forEach(s -> {
							MenuSO menuSO = (MenuSO) s;
							allMenus.add(menuSO);
						});
				consumer.accept(retrieveBaseMenu());
			});
		} else {
			consumer.accept(retrieveBaseMenu());
		}
	}

	@Override
	public void scrollAppTo(int max) {
		Timer timer = new Timer() {
			// int init = 0;
			int init = Window.getScrollTop();

			@Override
			public void run() {
				if (init >= max) {
					cancel();
				} else {
					Window.scrollTo(0, init);
					init += 10;
				}
			}
		};
		timer.scheduleRepeating(1);
	}

	@Override
	public void scrollAppToTop(boolean animate) {

		new Timer() {
			@Override
			public void run() {

				if (animate) {
					getScrollHelper().setEasing("swing");
					getScrollHelper().setDuration(400);
				}
				setScrollIndex(0);
				getScrollHelper().scrollTo(0);
			}
		}.schedule(50);

		// Timer timer = new Timer() {
		// int init = Window.getScrollTop();
		//
		// @Override
		// public void run() {
		// if (init >= 0) {
		//
		// Window.scrollTo(0, init);
		// init -= 10;
		// } else {
		// cancel();
		// }
		// }
		// };
		// timer.scheduleRepeating(1);
	}

	private List<MenuSO> retrieveBaseMenu() {
		List<MenuSO> list = new ArrayList<MenuSO>();
		for (MenuSO menuSO : allMenus) {
			if (menuSO.isActive() && menuSO.getType().equals("BaseMenu")) {

				if (!showConnection(menuSO)) {
					continue;
				}
				if (!showSettings(menuSO)) {
					continue;
				}

				list.add(menuSO);
			}
		}
		list.sort((MenuSO o1, MenuSO o2) -> Long.valueOf(o1.getIndex()).compareTo(Long.valueOf(o2.getIndex())));
		return list;
	}

	@Override
	public LoginWidget2 getLoginWidget() {
		if (loginWidget == null) {
			loginWidget = new LoginWidget2();
		}
		return loginWidget;
	}

	@Override
	public void deleteTempFile(String randomId) {
		execute(new GeneralAction(GeneralCommand.DELETE_TEMPFILE, null, randomId), result -> {
		});
	}

	@Override
	public MenuView getMenuView() {
		return menuView;
	}

	@Override
	public SearchView getSearchView() {
		return searchView;
	}

	@Override
	public MyMboaView getMyMboaView() {
		return new MyMboaViewImpl();
	}

	@Override
	public MenuConfigView getMenuConfigView() {
		return new MenuConfigViewImpl();
	}

	@Override
	public UserConfigView getUserConfigView() {
		return new UserConfigViewImpl();
	}

	@Override
	public SliderConfigView getSliderConfigView() {
		return new SliderConfigViewImpl();
	}

	@Override
	public ContentView getContentView() {
		return contentView;
	}

	@Override
	public ContentConfigView getContentConfigView() {
		return new ContentConfigViewImple();
	}

	@Override
	public RSociauxConfigView getRSociauxConfigView() {
		return new RSociauxConfigViewImpl();
	}

	@Override
	public WebSiteConfigView getSiteConfigView() {
		return new WebSiteConfigViewImpl();
	}

	@Override
	public StatisticConfigView getStatisticConfigView() {
		return new StatisticConfigViewImpl();
	}

	@Override
	public WebSiteSO getWebSiteSO() {
		return webSiteSO;
	}

	@Override
	public void checkTicket(Consumer<APPTicket> consumer) {
		execute(new GeneralAction(GeneralCommand.CHECK_LOGIN, null), result -> {
			consumer.accept(result.getAPPTicket());
		});
	}

	@Override
	public void setCurrentHistoryConsumer(Consumer<Boolean> consumer) {
		this.currentHistory = consumer;

	}

	@Override
	public StartView getStartView() {
		return startView;
	}

	@Override
	public void setStartView(StartView startView) {
		this.startView = startView;
	}

	@Override
	public void handleCurrentHistory() {
		currentHistory.accept(true);

	}

	@Override
	public UserSO getActualUserSO() {
		return actualUserSO;
	}

	@Override
	public void setUserSO(UserSO userSO) {
		this.actualUserSO = userSO;

	}

	@Override
	public RegisterWidgetView getRegisterWidget() {
		if (registerWidget == null) {
			registerWidget = new RegisterWidget();
		}
		return registerWidget;
	}

	private boolean showSettings(MenuSO menuSO) {
		boolean show = true;
		if (menuSO.getName().contains(MenuSO.SETTINGS) && actualUserSO == null) {
			show = false;
		}
		return show;

	}

	private boolean showConnection(MenuSO menuSO) {
		boolean show = true;
		if (menuSO.getName().contains(MenuSO.CONNECTION) && actualUserSO != null) {
			show = false;
		}
		return show;

	}

	@Override
	public APPMetaData getAppMetaData() {
		return metaData;
	}

	@Override
	public boolean isSessionNew() {
		return sessionNew;
	}

	@Override
	public ConfirmationWidget getConfirmationWidget() {
		if (confirmationWidget == null) {
			confirmationWidget = new ConfirmationWidget("", x -> {
			});
			confirmationWidget.getElement().getStyle().setProperty("maxWidth", "500px");
			confirmationWidget.desactiveNoButton(true);
		}
		return confirmationWidget;
	}

	@Override
	public MenuSO getMenuSO() {
		return menuSO;
	}

	@Override
	public ContentSO getContentSO() {
		return contentSO;
	}

	@Override
	public void addNewArticle(ContentSO saved) {
		// allArticlesContents.add(saved);
		SortableArticle aArticlesWidget = (SortableArticle) widgetsForWelcome[1];
		aArticlesWidget.addArticle(saved);

		allArticlesContents.add(saved);
	}

	@Override
	public ScrollHelper getScrollHelper() {
		scrollHelper.setDuration(2);
		return scrollHelper;
	}

	@Override
	public void setScrollIndex(double index) {
		this.scrollIndex = index;
	}

	@Override
	public double getScrollIndex() {
		return scrollIndex;
	}

	public MaterialButton getBackButton() {
		if (backButton == null) {
			backButton = new MaterialButton("", IconType.KEYBOARD_BACKSPACE);
			backButton.setType(ButtonType.FLOATING);
			backButton.setSize(ButtonSize.LARGE);
			backButton.setTitle("Retourner");
			backButton.setLayoutPosition(Position.FIXED);
			backButton.setBottom(20);
			// backButton.setLeft(20);
			// backButton.setDepth(1000);
			backButton.setDepth(21);
			backButton.getElement().getStyle().setProperty("backgroundColor", "#960018");
			backButton.getElement().getStyle().setProperty("color", "#ffb74d");
			backButton.getIcon().setIconColor(Color.AMBER_DARKEN_1);

			backButton.addClickHandler(c -> goBack());
		}
		return backButton;

	}

//	public native void goBack() /*-{
//		$wnd.history.back();
//	}-*/;


	public void goBack() {
		History.back();
	}
	
	@Override
	public MaterialPanel getEmptyStatePanel() {
		MaterialPanel materialPanel = new MaterialPanel();
		materialPanel.add(new EmptyStatePanel());
		materialPanel.setOpacity(0);
		materialPanel.setHeight("1200px");
		return materialPanel;
	}

	@Override
	public List<StatisticSO> getStatisticSOs() {
		return statisticSOs;
	}

	@Override
	public boolean reloadContent() {
		return reloadMenuContent;
	}

	@Override
	public void setReloadContent(boolean reload) {
		this.reloadMenuContent = reload;
	}

	@Override
	public void setPageIndex(int index) {
		this.pageIndex = index;

	}

	@Override
	public int getPageIndex() {
		return pageIndex;
	}

	@Override
	public void getContentsByPage(String pageNr, Consumer<List<ContentSO>> consumer) {

		List<ContentSO> contentsToReturn = new ArrayList<ContentSO>();
		List<ContentSO> contentsForPage = contents.stream().filter(x -> x.getKey().equals(pageNr))
				.map(KeyValue::getObject).collect(Collectors.toList());

		if (contentsForPage != null && !contentsForPage.isEmpty()) {
			contentsToReturn.addAll(contentsForPage);
			consumer.accept(contentsToReturn);
		} else {
			execute(new ContentAction(DBAction.READ, ContentCommand.ANNONCES_BY_PAGE_NR, pageNr), result -> {

				List<ContentSO> list = MethodsUtils.castList(result.getObjects(), ContentSO.class);

				updateCache(list);

				result.getObjects().forEach(appObject -> {
					ContentSO contentSO = (ContentSO) appObject;
					contents.add(new KeyValue<ContentSO>(pageNr, contentSO));
					// allArticlesContents.add(contentSO);
					contentsToReturn.add(contentSO);
				});
				consumer.accept(contentsToReturn);
			});
		}
	}

	@Override
	public Map<Long, String> getAnnoncesCategories() {
		return annoncesCategories;
	}

	@Override
	public void setAnnoncesCategories(Map<Long, String> categories) {
		this.annoncesCategories = categories;
	}

	@Override
	public MasterSlider getMasterSlider() {
		return (MasterSlider) widgetsForWelcome[0];
	}

	@Override
	public ArticleRecentes getArticleRecentes() {
		return (ArticleRecentes) widgetsForWelcome[1];
	}

	@Override
	public MaterialPanel getStartBasePanel() {
		return startBasePanelPanel;
	}

	@Override
	public void setStartBasePanel(MaterialPanel materialPanel) {
		this.startBasePanelPanel = materialPanel;
	}

	public Map<String, SortableArticle> getSearchArticle() {
		return searchArticle;
	}

	@Override
	public AnnonceDetailsView getAnnonceDetailsView() {
		return new AnnonceDetails();
	}

	@Override
	public void updateCache(List<ContentSO> contentSOs) {
		contentSOs.forEach(contentSO -> {
			if (!contain(contentSO.getTitel())) {
				allArticlesContents.add(contentSO);
			}
		});
	}

}
