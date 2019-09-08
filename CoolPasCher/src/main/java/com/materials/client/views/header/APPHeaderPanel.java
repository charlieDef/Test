package com.materials.client.views.header;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuSO;
import com.materials.client.model.MenuTemplateSO;
import com.materials.client.model.UserSO;
import com.materials.client.model.WebSiteSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.widgets.menu.HeaderMenu;
import com.materials.client.widgets.menu.MenuLink;
import com.materials.client.widgets.menu.column1.MenuColumn_1;
import com.materials.client.widgets.menu.column1.MenuColumn_2;
import com.materials.client.widgets.menu.column1.MenuColumn_3;
import com.materials.client.widgets.menu.column1.MenuColumn_4;
import com.materials.shared.MethodsUtils;

import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialNavSection;
import gwt.material.design.client.ui.MaterialPanel;

public class APPHeaderPanel extends Composite implements APPHeaderView {

	private static APPHeaderPanelUiBinder uiBinder = GWT.create(APPHeaderPanelUiBinder.class);

	interface APPHeaderPanelUiBinder extends UiBinder<MaterialHeader, APPHeaderPanel> {
	}

	boolean statusNewAnnonce = false;

	@UiField
	Style style;

	private Presenter presenter;

	@UiField
	MaterialLabel logoLabelUi;

	// @UiField
	// ExpandableInlineSearch expandableInline;

	@UiField
	MaterialNavBar navBar;

	@UiField
	MaterialNavSection navSectionUi;

	// @UiField
	// MaterialPanel menu3Ui;

	@UiField
	HTMLPanel menuUi;

	@UiField
	MaterialPanel menu2Ui;

	@UiField
	MaterialHeader header;

	@UiField
	MaterialNavBrand materialNavBrandUi;

	@UiField
	MaterialImage logoImageUi;

	// @UiField
	// MboaButton buttonCreation;

	// @UiHandler("searchIconUi")
	// void onClick(ClickEvent clickEvent) {
	// expandableInline.open();
	//
	// }

	public APPHeaderPanel() {

		initWidget(uiBinder.createAndBindUi(this));
		// expandableInline.setBackgroundColor(Color.WHITE);
		// expandableInline.open();
		// expandableInline.getIconClose().setVisible(false);
		// expandableInline.getIconSearch().setIconColor(Color.GREY_DARKEN_1);
		// expandableInline.setFocus(false);

		logoLabelUi.addClickHandler(x -> {
			CoolPasCherUI.CLIENT_FACTORY.setScrollIndex(0);
			MenuPlace menuPlace = new MenuPlace().handleMenu("start", MenuSO.ACCEUIL);
			menuPlace.setHome(true);
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);
			CoolPasCherUI.CLIENT_FACTORY.getStartView().getAppNavBarPanel().deselectSelected();
			CoolPasCherUI.CLIENT_FACTORY.getStartView().getAppNavBarPanel().clearSearch();
			clearSearchBox();
		});

		// ViewPort.when(Resolution.TABLET, Resolution.LAPTOP).then(viewPortChange -> {
		// expandableInline.setWidth("25%");
		// });
		//
		// ViewPort.when(Resolution.TABLET, Resolution.LAPTOP_LARGE).then(viewPortChange
		// -> {
		// expandableInline.setWidth("22%");
		// });
		// // //
		// ViewPort.when(Resolution.LAPTOP_4K).then(viewPortChange -> {
		// expandableInline.setWidth("20%");
		// });
		//
		//
		// MyKeyDown keyDown = new MyKeyDown();
		//
		// expandableInline.addKeyDownHandler(keyDown);
		// expandableInline.getIconSearch().addClickHandler(keyDown);

		CoolPasCherUI.CLIENT_FACTORY.getAllBaseMenus(menus -> {
			build(menus);
		});
	}

	private void addStatistic() {
		menu2Ui.clear();
		List<StatisticSO> statistics = CoolPasCherUI.CLIENT_FACTORY.getStatisticSOs();
		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
		for (StatisticSO st : statistics) {
			if (st.isIntern() && userSO != null) {
				MaterialLink link = new MaterialLink(st.getLabel());
				link.addStyleName(style.stat());
				link.setMarginTop(33);
				link.addClickHandler(x -> {
					CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new MenuPlace().handleStatistic(st));
				});
				menu2Ui.add(link);
			}
		}
	}

	private void handleLogoSetting() {
		WebSiteSO siteSO = CoolPasCherUI.CLIENT_FACTORY.getWebSiteSO();
		logoLabelUi.setText(siteSO.getName());
		// logoImageUi.setUrl(siteSO.getImageUrl());

		// // Logo image Animation
		// MaterialImage materialImage = new MaterialImage(siteSO.getImageUrl());
		// materialImage.setWaves(WavesType.LIGHT);
		// // materialImage.getElement().getStyle().setOpacity(0);
		// materialImage.addStyleName("materialImage");
		// materialNavBrandUi.add(materialImage);
		// MyClicHandler clicHandler = new MyClicHandler();
		//
		// logoLabelUi.addClickHandler(clicHandler);
		// logoImageUi.addClickHandler(clicHandler);
	}

	class MyClicHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			CoolPasCherUI.CLIENT_FACTORY.setScrollIndex(0);

			MenuPlace menuPlace = new MenuPlace().handleMenu("start", MenuSO.ACCEUIL);
			menuPlace.setHome(true);
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);
			CoolPasCherUI.CLIENT_FACTORY.getStartView().getAppNavBarPanel().deselectSelected();
			CoolPasCherUI.CLIENT_FACTORY.getStartView().getAppHeaderPanel().clearSearchBox();
		}
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		// ClientUtils.animeIn(header, Transition.FADEIN);

	}

	public void build(List<MenuSO> list) {

		addStatistic();

		if (CoolPasCherUI.checkLoggedMember()) {
			// expandableInline.setMarginRight(340);
		}

		menuUi.clear();

		HeaderMenu appMenu = new HeaderMenu();
		List<MenuSO> listfilterd = list.stream().filter(x -> !x.isMenuCategory()).collect(Collectors.toList());

		for (MenuSO menuSO : listfilterd) {
			if (!menuSO.isActive() || (menuSO.isMemberOnly() && !CoolPasCherUI.checkLoggedMember())) {
				continue;
			}

			int colNr = menuSO.getTemplateColNr();
			int templateNr = menuSO.getMenuTemplates().size();

			switch (colNr) {
			case 0: {
				handleMenuForCol_0(menuSO, appMenu);
			}
			case 1: {
				handleMenuForCol_1(templateNr, menuSO, appMenu);
			}
				break;
			case 2:

				handleMenuForCol_2(menuSO, appMenu);
				break;
			}
		}

		menuUi.add(appMenu);
		// menuUi.setVisible(true);

		handleLogoSetting();
	}

	private void handleMenuForCol_0(MenuSO menuSO, HeaderMenu appMenu) {
		MenuColumn_1 column_1 = new MenuColumn_1(menuSO.getName(), false,
				MethodsUtils.js_DoRun_Command(menuSO.getName()));
		appMenu.addMenu(column_1);
	}

	private void handleMenuForCol_2(MenuSO menuSO, HeaderMenu appMenu) {

		MenuColumn_2 column_2 = new MenuColumn_2(menuSO.getName());

		Map<String, List<MenuTemplateSO>> map = getTemplateByCategory(menuSO);

		for (String category : map.keySet()) {
			column_2.addTitleArea(category, 2);
			for (MenuTemplateSO mso : map.get(category)) {
				if (mso.getRandomId() != null) {
					String imgUrl = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?mtRandomID=" + mso.getRandomId();
					String name = mso.getName();
					column_2.addImageAndLinks(imgUrl, getSortedMapLinks(mso), mso.isLeftAlign(), mso.isButtonStyle());
				}
			}
		}
		appMenu.addMenu(column_2);
		if (menuSO.isDropDownAlignRight()) {
			column_2.setAlignRight();
		}

	}

	private void handleMenuForCol_1(int templateNr, MenuSO menuSO, HeaderMenu appMenu) {
		Map<String, List<MenuTemplateSO>> map = getTemplateByCategory(menuSO);

		int mcNr = getColNrFromCategoryMap(map);

		switch (mcNr) {
		case 1: {
			MenuColumn_1 column_1 = new MenuColumn_1(menuSO.getName(), true, null);
			MenuTemplateSO menuTemplateSO = menuSO.getMenuTemplates().get(0);
			Map<Integer, String> mapLinks = getSortedMap(menuTemplateSO);

			String imgUrl = null;
			String titleArea = menuTemplateSO.getCategory();
			String name = menuTemplateSO.getName();
			String jsMethode = MethodsUtils.js_DoRun_Command(name);

			UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
			if (userSO != null && menuSO.getName().equals(MenuSO.SETTINGS)) {
				imgUrl = userSO.getUserImageUrl();
				titleArea = userSO.getName();
				name = MenuSO.SETTINGS;
				jsMethode = null;

			} else if (menuTemplateSO.getRandomId() != null) {
				imgUrl = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?mtRandomID=" + menuTemplateSO.getRandomId();
			}
			column_1.addTitleArea(titleArea, 2);

			column_1.addImage(imgUrl, (name != null && !name.isEmpty()) ? jsMethode : null);

			// column_1.addText(epargne);
			// column_1.addText(fCaisse);

			for (String link : mapLinks.values()) {

				String newLink = link;

				if (userSO != null && link.contains("Favorits")) {
					// newLink = link + " [ " + userSO.getFavoritsNumber() + " ]";
					newLink = link;

				} else if (userSO != null && link.contains("Annonces")) {
					// newLink = link + " [ " + userSO.getAnnoncesNumber() + " ]";
					newLink = link;
				}

				column_1.addMenuLink(newLink, MethodsUtils.js_DoRun_Command(link), menuTemplateSO.isButtonStyle());
			}
			appMenu.addMenu(column_1);
		}
			break;
		case 2: {
			MenuColumn_2 column_2 = new MenuColumn_2(menuSO.getName());

			for (String category : map.keySet()) {
				column_2.addTitleArea(category, 2);
				for (MenuTemplateSO mso : map.get(category)) {
					if (mso.getRandomId() != null) {
						String imgUrl = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?mtRandomID=" + mso.getRandomId();
						String name = mso.getName();
						column_2.addImage(imgUrl,
								(name != null && !name.isEmpty()) ? MethodsUtils.js_DoRun_Command(name) : null,
								mso.getName(), getSortedMapLinks(mso), mso.isButtonStyle());
					}
				}
			}
			appMenu.addMenu(column_2);
			if (menuSO.isDropDownAlignRight()) {
				column_2.setAlignRight();
			}
		}

			break;
		case 3: {
			MenuColumn_3 column_3 = new MenuColumn_3(menuSO.getName());
			for (String category : map.keySet()) {
				column_3.addTitleArea(category, 2);
				for (MenuTemplateSO mso : map.get(category)) {
					if (mso.getRandomId() != null) {
						String imgUrl = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?mtRandomID=" + mso.getRandomId();
						String name = mso.getName();
						column_3.addImage(imgUrl,
								(name != null && !name.isEmpty()) ? MethodsUtils.js_DoRun_Command(name) : null,
								mso.getName(), getSortedMapLinks(mso), mso.isButtonStyle());
					}
				}
			}
			appMenu.addMenu(column_3);
			if (menuSO.isDropDownAlignRight()) {
				column_3.setAlignRight();
			}
		}

			break;
		case 4: {
			MenuColumn_4 column_4 = new MenuColumn_4(menuSO.getName());

			for (String category : map.keySet()) {
				column_4.addTitleArea(category, 2);
				for (MenuTemplateSO mso : map.get(category)) {
					if (mso.getRandomId() != null) {
						String imgUrl = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?mtRandomID=" + mso.getRandomId();
						String name = mso.getName();
						column_4.addImage(imgUrl,
								(name != null && !name.isEmpty()) ? MethodsUtils.js_DoRun_Command(name) : null,
								mso.getName(), getSortedMapLinks(mso), mso.isButtonStyle());
					}
				}
			}
			appMenu.addMenu(column_4);
			if (menuSO.isDropDownAlignRight()) {
				column_4.setAlignRight();
			}
		}
			break;
		}
	}

	private Map<Integer, String> getSortedMap(MenuTemplateSO menuTemplateSO) {
		Map<Integer, String> treeMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		treeMap.putAll(menuTemplateSO.getTemplateLinks());
		return treeMap;
	}

	private MenuLink[] getSortedMapLinks(MenuTemplateSO menuTemplateSO) {
		Map<Integer, String> treeMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		treeMap.putAll(menuTemplateSO.getTemplateLinks());

		MenuLink[] menuLinks = new MenuLink[treeMap.size()];
		int i = 0;
		for (String string : treeMap.values()) {
			menuLinks[i++] = new MenuLink(string, MethodsUtils.js_DoRun_Command(string));
		}
		return menuLinks;
	}

	private Map<String, List<MenuTemplateSO>> getTemplateByCategory(MenuSO menuSO) {
		Map<String, List<MenuTemplateSO>> treeMap = new HashMap<String, List<MenuTemplateSO>>();

		for (MenuTemplateSO menuTemplateSO : menuSO.getMenuTemplates()) {
			if (treeMap.get(menuTemplateSO.getCategory()) == null) {
				List<MenuTemplateSO> mList = new ArrayList<MenuTemplateSO>();
				treeMap.put(menuTemplateSO.getCategory(), mList);
			}
			treeMap.get(menuTemplateSO.getCategory()).add(menuTemplateSO);
		}
		return treeMap;
	}

	private int getColNrFromCategoryMap(Map<String, List<MenuTemplateSO>> map) {
		int result = 0;
		for (List<MenuTemplateSO> templateSOs : map.values()) {
			if (templateSOs.size() > result) {
				result = templateSOs.size();
			}
		}
		return result;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void rebuildHeader(List<MenuSO> list) {
		build(list);
	}

	@Override
	public void refreshHeader() {
		CoolPasCherUI.CLIENT_FACTORY.getAllBaseMenus(menus -> {
			build(menus);
		});
	}

	interface Style extends CssResource {
		String def();

		String myButton();

		String figcaption();

		String mLabel();

		String logoImage();

		String dropDown();

		String search();

		String searchIcon();

		String deffo();

		String stat();
	}

	// class MyKeyDown implements KeyDownHandler, ClickHandler {
	// @Override
	// public void onKeyDown(KeyDownEvent event) {
	// if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
	// // presenter.searchAnnonce(expandableInline.getValue(), "", "");
	//
	// SearchPlace place = new SearchPlace(expandableInline.getValue());
	// CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(place);
	//
	// }
	// }
	//
	// @Override
	// public void onClick(ClickEvent event) {
	// // presenter.searchAnnonce(expandableInline.getValue(), "", "");
	// SearchPlace place = new SearchPlace(expandableInline.getValue());
	// CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(place);
	// }
	// }

	@Override
	public void clearSearchBox() {
		// expandableInline.clear();

	}

}
