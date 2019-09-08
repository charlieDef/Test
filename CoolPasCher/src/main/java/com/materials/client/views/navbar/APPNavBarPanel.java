package com.materials.client.views.navbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.place.shared.Place;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuSO;
import com.materials.client.model.UserSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.places.configurations.contentConfig.ContentConfigPlace;
import com.materials.client.places.configurations.menuConfig.MenuConfigPlace;
import com.materials.client.places.configurations.rsociaux.RSociauxConfigPlace;
import com.materials.client.places.configurations.siteConfig.WebSiteConfigPlace;
import com.materials.client.places.configurations.sliderConfig.SliderConfigPlace;
import com.materials.client.places.configurations.userConfig.UserConfigPlace;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.places.mymboa.MyMboaPlace;
import com.materials.client.places.search.SearchPlace;
import com.materials.client.widgets.boxSearch.BoxSearch;
import com.materials.client.widgets.category.CategoryCO;
import com.materials.client.widgets.category.PrettyCategoryPanel;

import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialDivider;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialSideNavPush;

public class APPNavBarPanel extends Composite implements AppNavBarView {

	private static APPNavBarPanelUiBinder uiBinder = GWT.create(APPNavBarPanelUiBinder.class);

	interface APPNavBarPanelUiBinder extends UiBinder<MaterialSideNavPush, APPNavBarPanel> {
	}

	private Presenter presenter;
	// private PrettyCategoryPanel categoryPanel;

	@UiField
	MaterialSideNavPush sideNav;

	private List<MenuSO> allMenus = new ArrayList<>();

	@UiField
	MaterialNavBrand materialNavBrandUi;

	@UiField
	BoxSearch boxSearchUi;

	@UiField
	MaterialIcon homeIconUi;

	@UiField
	MaterialCollapsible collapsibleUi;

	@UiField
	MaterialPanel materialPanelMorphUi;

	private boolean stoped = true;

	private MaterialCollectionItem itemSelected;

	@UiField
	Style style;

	public APPNavBarPanel() {

		initWidget(uiBinder.createAndBindUi(this));
		sideNav.setBackgroundColor(Color.WHITE);

		// sideNav.addOpeningHandler(x -> {
		// if (Window.getClientWidth() < 993) {
		// sideNav.setAllowBodyScroll(false);
		// }
		// });
		//
		// sideNav.addClosingHandler(x -> {
		// sideNav.setAllowBodyScroll(true);
		// });

		// if (Window.getClientWidth() < 993) {
		// sideNav.addOpeningHandler(x -> {
		// sideNav.setAllowBodyScroll(false);
		// });
		//
		// sideNav.addClosingHandler(x -> {
		// sideNav.setAllowBodyScroll(true);
		// });
		// }

		// if(Navigator.getPlatform().equals("iPhone")) {
		//
		// }

		// ViewPort.when(Resolution.ALL_MOBILE, Resolution.TABLET).then(viewPortChange
		// -> {
		// sideNav.setAllowBodyScroll(false);
		//
		// });

		// ViewPort.when(Resolution.ALL_LAPTOP).then(viewPortChange -> {
		//
		// sideNav.setAllowBodyScroll(true);
		// });

		homeIconUi.setFontSize("35px");
		boxSearchUi.setPlaceHolder("Que cherchez vous?");

		CoolPasCherUI.CLIENT_FACTORY.getAllBaseMenus(menus ->

		{
			Collections.sort(menus, Comparator.comparing(MenuSO::getName));
			this.allMenus = menus;
			loadCategories();
			buildMenu(menus);
		});

		homeIconUi.addClickHandler(x -> {
			CoolPasCherUI.CLIENT_FACTORY.setScrollIndex(0);
			MenuPlace menuPlace = new MenuPlace().handleMenu("start", MenuSO.ACCEUIL);
			menuPlace.setHome(true);
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);
			CoolPasCherUI.CLIENT_FACTORY.getStartView().getAppNavBarPanel().deselectSelected();
			CoolPasCherUI.CLIENT_FACTORY.getStartView().getAppHeaderPanel().clearSearchBox();
			clearSearch();
		});

		boxSearchUi.setConsumer(textValue -> {
			SearchPlace place = new SearchPlace(textValue);
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(place);
		});

		// ViewPort.when(Resolution.LAPTOP_4K).then(viewPortChange -> {
		// sideNav.show();
		// });

	}

	private void loadCategories() {
		materialPanelMorphUi.clear();
		PrettyCategoryPanel categoryPanel = new PrettyCategoryPanel(allMenus);
		categoryPanel.setNavBarConsumer(x -> sideNav.hide());
		materialPanelMorphUi.add(categoryPanel);

		// List<CategoryCO> list = getCategoryCOs2();
		//
		// ZCategoriePanel categoriePanel = new ZCategoriePanel(list, "Categories",
		// IconType.VIEW_MODULE);
		// materialPanelMorphUi.add(categoriePanel);
		categoryPanel.swippeToBase();
	}

	public interface Style extends CssResource {

		String mLabelHeader();

		String materialImageNav();

		String materialSideNavDrawer();

		String mSideNavHeader();

		String mCollapsItemHeader();

		String mCollapsItemBody();

		String mCollapsItemBodyItem();

	}

	public void build(List<MenuSO> list) {

		if (list != null && !list.isEmpty()) {
			buildMenu(list);
		}
	}

	private void buildMenuConfigurations() {

		collapsibleUi.add(new MaterialDivider());

		MaterialCollapsibleItem collapsibleItem = new MaterialCollapsibleItem();
		collapsibleItem.setBackgroundColor(Color.GREY_LIGHTEN_4);

		collapsibleUi.add(collapsibleItem);

		// header
		MaterialCollapsibleHeader collapsibleHeader = new MaterialCollapsibleHeader();
		collapsibleHeader.setHeight("40px");
		// collapsibleHeader.setWaves(WavesType.DEFAULT);

		MaterialLink config = new MaterialLink(MenuSO.CONFIGURATIONS);
		config.setMarginTop(-10);
		config.getSpan().setMarginLeft(10);
		config.getIcon().setIconColor(Color.AMBER_DARKEN_3);
		config.setIconType(IconType.SETTINGS);
		config.setIconFontSize(25, Unit.PX);
		config.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		config.setTextColor(Color.AMBER_DARKEN_3);

		collapsibleHeader.add(config);
		collapsibleItem.add(collapsibleHeader);

		// body
		MaterialCollapsibleBody collapsibleBody = new MaterialCollapsibleBody();
		collapsibleBody.addStyleName(style.mCollapsItemBody());
		collapsibleItem.add(collapsibleBody);

		MaterialCollection materialCollection = new MaterialCollection();
		collapsibleBody.add(materialCollection);

		materialCollection.add(getCollapsibleItem("Users", IconType.PERSON, new UserConfigPlace()));
		materialCollection.add(getCollapsibleItem("Contents", IconType.DESCRIPTION, new ContentConfigPlace()));
		materialCollection.add(getCollapsibleItem("Sliders", IconType.PICTURE_IN_PICTURE, new SliderConfigPlace()));
		materialCollection.add(getCollapsibleItem("Menus", IconType.LIST, new MenuConfigPlace()));
		// materialCollection.add(getCollapsibleItem("Statistic", IconType.EQUALIZER,
		// new StatisticConfigPlace()));
		materialCollection.add(getCollapsibleItem("Reseau sociaux", IconType.STAR_HALF, new RSociauxConfigPlace()));
		materialCollection.add(getCollapsibleItem("Site", IconType.ACCOUNT_BALANCE, new WebSiteConfigPlace()));

		// ClientUtils.addTimer(x -> {
		// collapsibleItem.expand();
		// }, 500);

		// materialCollection.add(new MaterialDivider());

	}

	private MaterialCollapsibleItem getCollapsibleItem(String name, IconType iconType, Place placeSupplier) {
		MaterialCollapsibleItem collectionSiteItem = new MaterialCollapsibleItem();
		// collectionSiteItem.setWaves(WavesType.DEFAULT);
		MaterialLink link = new MaterialLink(name);
		link.addStyleName(style.mCollapsItemBodyItem());
		link.getSpan().setMarginLeft(-20);
		link.setIconType(iconType);
		link.setIconFontSize(20, Unit.PX);
		collectionSiteItem.add(link);

		link.addClickHandler(x -> {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(placeSupplier);
			// ClientUtils.addTimer(c -> sideNav.hide(), 500);

		});

		return collectionSiteItem;
	}

	private void buildMenu(List<MenuSO> list) {
		collapsibleUi.clear();
		sideNav.setAutoHideOnResize(true);

		// Configurations
		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			buildMenuConfigurations();
		}
		initGlobaleCategories(list);// SelectBox

		// Contact
		addContact();

		addStatistic();

		List<MenuSO> listfilterd = list.stream().filter(x -> !x.isMenuCategory()).collect(Collectors.toList());

		for (MenuSO menu : listfilterd) {
			if (!menu.isActive() || (menu.isMemberOnly() && !CoolPasCherUI.checkLoggedMember())) {
				continue;
			}
			MaterialCollapsibleItem collapsibleItem = new MaterialCollapsibleItem();

			collapsibleItem.setBackgroundColor(Color.GREY_LIGHTEN_4);

			collapsibleUi.add(collapsibleItem);

			// header
			MaterialCollapsibleHeader collapsibleHeader = new MaterialCollapsibleHeader();
			// collapsibleHeader.setWaves(WavesType.DEFAULT);
			collapsibleHeader.addStyleName(style.mCollapsItemHeader());
			MaterialLabel label = new MaterialLabel(menu.getName());
			label.setMarginLeft(15);
			collapsibleHeader.add(label);
			collapsibleItem.add(collapsibleHeader);

			// body
			MaterialCollapsibleBody collapsibleBody = new MaterialCollapsibleBody();
			collapsibleBody.addStyleName(style.mCollapsItemBody());

			// UnorderedList unorderedList = new UnorderedList();
			MaterialCollection materialCollection = new MaterialCollection();

			if (menu.getSubMenuSos().isEmpty()) {
				label.addClickHandler(c -> {
					CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
							.goTo(new MenuPlace().handleMenu("start", menu.getName()));
					sideNav.hide();
				});
			} else {

				List<MenuSO> submenus = new ArrayList<MenuSO>();
				if (menu.getSubMenuSos() != null && !menu.getSubMenuSos().isEmpty()) {
					submenus.addAll(menu.getSubMenuSos());
				}

				Collections.sort(submenus, Comparator.comparing(MenuSO::getIndex));

				for (MenuSO subMenu : submenus) {
					if (subMenu.isMemberOnly() && !CoolPasCherUI.checkLoggedMember()) {
						continue;
					}

					MaterialCollapsibleItem collapsibleItem2 = new MaterialCollapsibleItem();

					materialCollection.add(collapsibleItem2);
					MaterialLink link = new MaterialLink(subMenu.getName());

					// collapsibleItem2.setWaves(WavesType.DEFAULT);
					link.addStyleName(style.mCollapsItemBodyItem());
					collapsibleItem2.add(link);

					link.addClickHandler(c -> {

						if (subMenu.getName().equals(MenuSO.LOGIN)) {
							CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().show();
						} else if (subMenu.getName().equals(MenuSO.LOGOUT)) {
							CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().getPresenter().performLogout();
						} else if (subMenu.getName().equals(MenuSO.REGISTER)) {

							CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
									.goTo(new MenuPlace().handleMenu("start", MenuSO.REGISTER));
						} else if (subMenu.getName().equals(MenuSO.CONFIGURER)) {
							CoolPasCherUI.CLIENT_FACTORY.getStartView().showRegisterPanel(true);
						} else if (subMenu.getName().equals(MenuSO.CONTACT)) {
							CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
									.goTo(new MenuPlace().handleMenu("start", MenuSO.CONTACT));

						} else if (subMenu.getName().contains(MenuSO.FAVORITS)) {
							CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
									.goTo(new MenuPlace().handleMenu("start", MenuSO.FAVORITS));

						} else if (subMenu.getName().contains(MenuSO.MES_ANNONCES)) {
							CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
									.goTo(new MyMboaPlace(MenuSO.MES_ANNONCES));
						} else {
							CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
									.goTo(new MenuPlace().handleMenu("start", subMenu.getName()));
						}

						sideNav.hide();
					});
					collapsibleBody.add(materialCollection);
				}
			}

			collapsibleItem.add(collapsibleBody);

			// collapsibleItem.setHideOn(HideOn.HIDE_ON_MED_UP);
			// collapsibleItem.setShowOn(ShowOn.SHOW_ON_MED_DOWN);

			// boxSearchUi.getBoxPanel().setHideOn(HideOn.HIDE_ON_MED_UP);
			// boxSearchUi.getBoxPanel().setShowOn(ShowOn.SHOW_ON_MED_DOWN);

		}

	}

	@Override
	public void rebuildNavbar() {
		CoolPasCherUI.CLIENT_FACTORY.getAllBaseMenus(menus -> {
			build(menus);

		});
	}

	private void addViewPort() {
		ViewPort.when(Resolution.ALL_MOBILE).then(viewPortChange -> {
			sideNav.hide();
		});
	}

	@Override
	public void rebuildNavbar(List<MenuSO> list) {
		build(list);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		// MaterialAnimation animationMenu = new MaterialAnimation();
		// animationMenu.setTransition(Transition.FADEIN);
		// animationMenu.setDelay(1000);
		// animationMenu.setDuration(2000);
		// animationMenu.animate(sideNav);
		// new Timer() {
		// @Override
		// public void run() {
		// sideNav.setOpacity(1);
		// }
		// }.schedule(1000);
	}

	private Integer getFavNr() {
		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
		if (userSO != null) {
			return userSO.getFavoritsNumber();
		} else {
			return null;
		}
	}

	@Override
	public void deselectSelected() {
		// if (categoryPanel.getActualLevel() != 0) {
		// loadCategories();
		// }
	}

	@Override
	public void refreshNavbar() {
		rebuildNavbar();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void deselecAll() {
		// if (categoryPanel.getActualLevel() != 0) {
		// loadCategories();
		// }
	}

	@Override
	public void swipeBack() {
		deselecAll();

	}

	private void initGlobaleCategories(List<MenuSO> menus) {
		Map<Long, String> map = new HashMap<Long, String>();
		menus.stream().forEach(s -> {
			MenuSO menuSO = (MenuSO) s;
			String toAdd = menuSO.getName();
			if (menuSO.isMenuCategory()) {

				map.put(menuSO.getId(), toAdd);

				List<MenuSO> subMenus = getAllSubMenus(menuSO);
				for (MenuSO subMenu1 : subMenus) {// 1
					map.put(subMenu1.getId(), menuSO.getName() + " || " + subMenu1.getName());
					for (MenuSO subMenu2 : subMenu1.getSubMenuSos()) {// 2
						map.put(subMenu2.getId(),
								menuSO.getName() + " || " + subMenu1.getName() + "||" + subMenu2.getName());
						for (MenuSO subMenu3 : subMenu2.getSubMenuSos()) {// 3
							map.put(subMenu3.getId(), menuSO.getName() + "|| " + subMenu1.getName() + " || "
									+ subMenu2.getName() + " || " + subMenu3.getName());
							for (MenuSO subMenu4 : subMenu3.getSubMenuSos()) {// 4
								map.put(subMenu4.getId(),
										menuSO.getName() + " || " + subMenu1.getName() + " || " + subMenu2.getName()
												+ " || " + subMenu3.getName() + " || " + subMenu4.getName());
							}
						}
					}
				}
			}
		});

		CoolPasCherUI.CLIENT_FACTORY.setAnnoncesCategories(map);
	}

	private List<MenuSO> getAllSubMenus(MenuSO menuSo) {
		List<MenuSO> list = new ArrayList<>();
		if (menuSo.getSubMenuSos() != null && !menuSo.getSubMenuSos().isEmpty()) {
			for (MenuSO subMenu : menuSo.getSubMenuSos()) {
				list.add(subMenu);
			}
		}
		return list;
	}

	private void addContact() {
		collapsibleUi.add(new MaterialDivider());
		// Contact
		MaterialCollapsibleItem collapsibleItemContact = new MaterialCollapsibleItem();
		collapsibleItemContact.setBackgroundColor(Color.GREY_LIGHTEN_4);
		// collapsibleItemContact.setWaves(WavesType.DEFAULT);

		MaterialLink contact = new MaterialLink(MenuSO.CONTACT);

		contact.getSpan().setMarginLeft(-5);
		contact.setIconType(IconType.CONTACTS);
		// contact.setMarginTop(-10);
		contact.getIcon().setIconColor(Color.AMBER_DARKEN_3);
		contact.setIconFontSize(20, Unit.PX);
		contact.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		contact.setTextColor(Color.AMBER_DARKEN_3);
		collapsibleItemContact.add(contact);
		collapsibleUi.add(collapsibleItemContact);

		collapsibleUi.add(new MaterialDivider());

		contact.addClickHandler(c -> {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new MenuPlace().handleMenu("start", MenuSO.CONTACT));
		});

		// ClientUtils.addTimer(x -> {
		// ClientUtils.animeIn(collapsibleItemContact, Transition.FADEINLEFT);
		// }, 3000);

	}

	private List<CategoryCO> getCategoryCOs2() {
		List<CategoryCO> list = new ArrayList<>();
		for (MenuSO menuSO : allMenus) {
			if (menuSO.isMenuCategory() && menuSO.isBaseMenu()) {
				CategoryCO category = new CategoryCO(menuSO.getName(), menuSO.getDescription(), getIcon(menuSO));
				for (MenuSO subMenu : menuSO.getSubMenuSos()) {// 1
					CategoryCO sCategory1 = new CategoryCO(subMenu.getName(), subMenu.getDescription(),
							getIcon(subMenu));
					category.addChild(sCategory1);

					for (MenuSO subMenu2 : subMenu.getSubMenuSos()) {// 2
						CategoryCO sCategory2 = new CategoryCO(subMenu2.getName(), subMenu2.getDescription(),
								getIcon(subMenu2));
						sCategory1.addChild(sCategory2);

						for (MenuSO subMenu3 : subMenu2.getSubMenuSos()) {// 3
							CategoryCO sCategory3 = new CategoryCO(subMenu3.getName(), subMenu3.getDescription(),
									getIcon(subMenu3));
							sCategory2.addChild(sCategory3);

							for (MenuSO subMenu4 : subMenu3.getSubMenuSos()) {// 4
								CategoryCO sCategory4 = new CategoryCO(subMenu4.getName(), subMenu4.getDescription(),
										getIcon(subMenu4));
								sCategory3.addChild(sCategory4);
							}
						}
					}
				}
				list.add(category);
			}
		}
		return list;
	}

	private IconType getIcon(MenuSO menuSO) {
		return menuSO.getSubMenuSos().isEmpty() ? IconType.LENS : IconType.KEYBOARD_ARROW_RIGHT;
	}

	private void addStatistic() {

		List<StatisticSO> statistics = CoolPasCherUI.CLIENT_FACTORY.getStatisticSOs();
		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
		for (StatisticSO st : statistics) {
			if (st.isIntern() && userSO != null) {

				MaterialCollapsibleItem collapsibleItem = new MaterialCollapsibleItem();
				collapsibleItem.setBackgroundColor(Color.GREY_LIGHTEN_4);
				collapsibleUi.add(collapsibleItem);

				// header
				MaterialCollapsibleHeader collapsibleHeader = new MaterialCollapsibleHeader();
				// collapsibleHeader.setWaves(WavesType.DEFAULT);
				collapsibleHeader.addStyleName(style.mCollapsItemHeader());
				MaterialLabel label = new MaterialLabel(st.getLabel());
				label.setMarginLeft(5);
				collapsibleHeader.add(label);
				collapsibleItem.add(collapsibleHeader);

				// collapsibleItem.setHideOn(HideOn.HIDE_ON_MED_UP);
				// collapsibleItem.setShowOn(ShowOn.SHOW_ON_MED_DOWN);

				label.addClickHandler(x -> {
					CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new MenuPlace().handleStatistic(st));
					sideNav.hide();
				});
			}
		}
	}

	@Override
	public void clearSearch() {
		boxSearchUi.reset();
	}
}
