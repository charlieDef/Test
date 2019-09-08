package com.materials.client.widgets.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuSO;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.swipe.SwipperWidget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialDivider;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;

public class PrettyCategoryPanel extends MaterialPanel implements PrettyCategoryPanelView {

	private Presenter presenter;
	private MaterialCollection baseCategories;
	private Map<Integer, MaterialCollection> collections;
	private Map<String, List<MenuSO>> menus;
	private SwipperWidget swipperWidget;
	private boolean swippingDone = true;

	private int actualLevel = 0;
	private Consumer<Boolean> navBarConsumer;

	public PrettyCategoryPanel(List<MenuSO> menuSOs) {

		add(new MaterialDivider());

		init(menuSOs);

		swipperWidget = new SwipperWidget("");
		swipperWidget.setFixContentHeight(true);
		for (Integer level : collections.keySet()) {
			String menuLevel = String.valueOf(level);
			MaterialCollection collection = collections.get(level);
			swipperWidget.addSwipeItem(menuLevel, collection, level < 4);
		}

		add(swipperWidget);
	}

	@Override
	public void swippeToBase() {
		swipperWidget.show("0");
		actualLevel = 0;
	}

	public void swippeToLevel(int level, MenuSO menuSO) {
		actualLevel = level;
		MaterialCollection collection = collections.get(level);
		collection.clear();

		String nameTop = new String(
				menuSO.getParentMenuSo() == null ? "Categories" : menuSO.getParentMenuSo().getName());

		MaterialCollectionItem titeal = getCollectionItemForFirst(nameTop, IconType.KEYBOARD_BACKSPACE, x -> {
			swippeBackToLevel(level - 1);
		});
		titeal.setTextColor(Color.AMBER_DARKEN_3);
		collection.add(titeal);

		String showAll = new String(menuSO.getName());
		final MaterialCollectionItem itemAllSub = getCollectionItemForSecond(showAll, IconType.GRAIN, x -> {

		});
		itemAllSub.setBackgroundColor(Color.GREY_LIGHTEN_4);

		itemAllSub.addClickHandler(c -> {
			ClientUtils.addTimer(x -> {
				collection.setActive(collection.getWidgetIndex(itemAllSub) + 1);
				MenuPlace menuPlace = new MenuPlace().handleCategory("Tout afficher", menuSO.getName());
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);

				// if (navBarConsumer != null) {
				// navBarConsumer.accept(true);
				// }

			}, 50);
		});

		collection.add(itemAllSub);

		for (MenuSO subMenu : menuSO.getSubMenuSos()) {
			IconType iconType = subMenu.hasSubMenus() ? IconType.KEYBOARD_ARROW_RIGHT : IconType.STOP;
			MaterialCollectionItem item = getCollectionItem(subMenu, iconType, level + 1);
			collection.add(item);
		}

		swipperWidget.swipeTo("" + level, h -> {
			ClientUtils.addTimer(f -> {
				swippingDone = true;
			}, 500);
		});
	}

	public void swippeBackToLevel(int level) {
		actualLevel = level;
		if (swippingDone) {
			swippingDone = false;
			swipperWidget.swipeBackTo("" + level, x -> {
				MaterialCollection collection = collections.get(level + 1);
				collection.clear();
				ClientUtils.addTimer(f -> {
					swippingDone = true;
				}, 500);
			});
		}
	}

	private MaterialCollectionItem getCollectionItem(MenuSO menuSO, IconType iconType, Integer nextLevel) {

		MaterialCollectionItem collectionItem = new MaterialCollectionItem();
		collectionItem.setHeight("40px");
		collectionItem.setBorder("0px");
		collectionItem.setTitle(menuSO.getDescription());

		MaterialLink link = new MaterialLink(menuSO.getName());
		link.setPaddingTop(10);
		if (iconType != null) {
			link.setIconType(iconType);
			link.setIconColor(Color.AMBER_DARKEN_3);
			link.getIcon().setMarginTop(-2);
			link.getIcon().setIconFontSize(menuSO.hasSubMenus() ? 20 : 15, Unit.PX);
			link.getIcon().setMarginRight(0);
			link.getIcon().setMarginLeft(12);
		}
		collectionItem.add(link);

		if (menuSO.hasSubMenus()) {
			link.addClickHandler(x -> {
				if (swippingDone) {
					swippingDone = false;
					ClientUtils.addTimer(c -> {
						MaterialCollection collection = collections.get(nextLevel - 1);
						collection.setActive(collection.getWidgetIndex(collectionItem) + 1);
					}, 20);
					swippeToLevel(nextLevel, menuSO);
				}
			});
		} else {
			link.addClickHandler(v -> {
				ClientUtils.addTimer(x -> {
					MaterialCollection collection = collections.get(nextLevel - 1);
					collection.setActive(collection.getWidgetIndex(collectionItem) + 1);
					MenuPlace menuPlace = new MenuPlace().handleCategory("Tout afficher", menuSO.getName());
					CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);
					// if (navBarConsumer != null) {
					// navBarConsumer.accept(true);
					// }

				}, 50);
			});

		}
		return collectionItem;

	}

	private MaterialCollectionItem getCollectionItemForFirst(String text, IconType iconType,
			Consumer<Boolean> consumer) {
		MaterialCollectionItem collectionItem = new MaterialCollectionItem();
		collectionItem.setHeight("40px");
		collectionItem.setBorder("0px");
		collectionItem.setBackgroundColor(Color.GREY_LIGHTEN_3);

		MaterialLink link = new MaterialLink(text);
		link.setPaddingTop(10);
		link.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		link.setTextColor(Color.AMBER_DARKEN_3);

		if (iconType != null) {
			link.setIconType(iconType);
			link.setIconColor(Color.AMBER_DARKEN_3);
			link.getIcon().setMarginTop(-8);
		}
		if (consumer != null) {
			link.addClickHandler(x -> {
				consumer.accept(true);
			});
		}

		collectionItem.add(link);

		return collectionItem;
	}

	private MaterialCollectionItem getCollectionItemForSecond(String text, IconType iconType,
			Consumer<Boolean> clickAll) {
		MaterialCollectionItem collectionItem = new MaterialCollectionItem();
		collectionItem.setHeight("40px");
		collectionItem.setBorder("0px");
		collectionItem.setBackgroundColor(Color.GREY_LIGHTEN_3);
		collectionItem.setFontWeight(FontWeight.BOLD);

		MaterialLink link = new MaterialLink(text);
		link.setPaddingTop(10);
		link.setIconType(iconType);
		link.setIconColor(Color.AMBER_DARKEN_3);
		link.getIcon().setMarginLeft(12);
		// link.getIcon().setIconFontSize(menuSO.hasSubMenus() ? 20 : 15, Unit.PX);
		link.getIcon().setMarginRight(0);
		link.getIcon().setIconFontSize(20, Unit.PX);
		link.getIcon().setIconColor(Color.AMBER_DARKEN_3);
		collectionItem.add(link);

		link.addClickHandler(x -> {
			clickAll.accept(true);
		});

		return collectionItem;
	}

	private void init(List<MenuSO> menuSOs) {
		baseCategories = getNewMaterialCollection();
		baseCategories.add(getCollectionItemForFirst("Categories", IconType.VIEW_MODULE, null));

		collections = new HashMap<>();
		collections.put(0, baseCategories);
		collections.put(1, getNewMaterialCollection());
		collections.put(2, getNewMaterialCollection());
		collections.put(3, getNewMaterialCollection());
		collections.put(4, getNewMaterialCollection());

		menus = new HashMap<>();
		for (MenuSO menuSO : menuSOs) {
			if (menuSO.isMenuCategory()) {
				menus.put(menuSO.getName(), menuSO.getSubMenuSos());

				MaterialCollectionItem item = getCollectionItem(menuSO, IconType.KEYBOARD_ARROW_RIGHT, 1);
				baseCategories.add(item);
			}

		}
	}

	private MaterialCollection getNewMaterialCollection() {
		MaterialCollection collection = new MaterialCollection();
		collection.setBorder("0px");
		collection.setMargin(0);
		return collection;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	public void setNavBarConsumer(Consumer<Boolean> navBarConsumer) {
		this.navBarConsumer = navBarConsumer;
	}

	public int getActualLevel() {
		return actualLevel;
	}
}