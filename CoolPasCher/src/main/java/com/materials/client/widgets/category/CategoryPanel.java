package com.materials.client.widgets.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.materials.client.CoolPasCherUI;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.swipe.SwipperWidget;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;

public class CategoryPanel extends MaterialPanel implements CategoryPanelView {

	private Presenter presenter;

	private List<CategoryCO> list = new ArrayList();

	private SwipperWidget swipperWidget;
	private MaterialCollection baseCategories;
	private MaterialCollection subCategories;
	private MaterialCollectionItem actualSelectedCategory;
	private boolean swippingDone = true;
	private boolean subCategoryModus = false;

	private MaterialPanel extraPanel;

	public CategoryPanel() {
		swipperWidget = new SwipperWidget("");
		swipperWidget.setFixContentHeight(true);
		extraPanel = new MaterialPanel();

		add(swipperWidget);
		add(extraPanel);

		baseCategories = new MaterialCollection();
		subCategories = new MaterialCollection();

		baseCategories.setBorder("0px");
		subCategories.setBorder("0px");

		baseCategories.setMargin(0);
		subCategories.setMargin(0);

		swipperWidget.addSwipeItem("baseCategories", baseCategories, true);
		swipperWidget.addSwipeItem("subCategories", subCategories, true);
		// swipperWidget.addSwipeItem("extraPanel", extraPanel, false);

		baseCategories.add(getCollectionItemForBase("Categories", IconType.VIEW_MODULE, true));
	}

	public void show() {
		swipperWidget.show("baseCategories");
	}

	public void goBack() {
		if (swippingDone) {
			swippingDone = false;

			swipperWidget.swipeBackTo("baseCategories", x -> {
				subCategories.clear();
				ClientUtils.addTimer(f -> {
					swippingDone = true;
					subCategoryModus = false;
				}, 500);
			});
		}

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	public void addWidGetToBottom(MaterialWidget widget) {
		extraPanel.add(widget);
	}

	public void addBaseCategory(String categoryName, String title) {
		if (getBaseCategoryCO(categoryName) == null) {
			list.add(new CategoryCO(categoryName, title));
		}

		MaterialCollectionItem item = getCollectionItemForBase(categoryName, IconType.KEYBOARD_ARROW_RIGHT, false);
		item.setTitle(title);

		baseCategories.add(item);
	}

	public void addSubCategoryToBase(String baseCategory, String subGategoryName, String title) {
		CategoryCO cat = getBaseCategoryCO(baseCategory);
		if (cat != null) {
			CategoryCO categoryCO = cat.addItem(subGategoryName, title);
		}
	}

	private MaterialCollection getCollection(String categoryName) {
		MaterialCollection collection = new MaterialCollection();
		collection.setBorder("0px");
		collection.setMargin(0);
		List<CategoryCO> submenus = getBaseCategoryCO(categoryName).getItems();
		Collections.sort(list, Comparator.comparing(CategoryCO::getName));

		String titel = new String("Categories");
		MaterialCollectionItem titeal = getCollectionItem(titel, IconType.NAVIGATE_BEFORE, true, true);
		collection.add(titeal);
		titeal.setTextColor(Color.AMBER_DARKEN_3);

		titeal.addClickHandler(x -> {
			goBack();
		});

		String showAll = new String(categoryName);
		MaterialCollectionItem itemAllSub = getCollectionItem(showAll, IconType.GRAIN, false, false);
		itemAllSub.setBackgroundColor(Color.GREY_LIGHTEN_4);
		itemAllSub.setFontWeight(FontWeight.BOLD);
		// link.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		collection.add(itemAllSub);

		for (CategoryCO subMenu : submenus) {

			IconType iconType = subMenu.hasSubItems() ? IconType.KEYBOARD_ARROW_RIGHT : IconType.STOP;

			MaterialCollectionItem item = getCollectionItem(subMenu.getName(), iconType, false, false);
			item.setTitle(subMenu.getTitle());
			collection.add(item);
			item.addClickHandler(c -> {

				ClientUtils.addTimer(x -> {
					collection.setActive(collection.getWidgetIndex(item) + 1);
					MenuPlace menuPlace = new MenuPlace().handleCategory(subMenu.getName(), categoryName);
					CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);

				}, 50);

			});
		}

		itemAllSub.addClickHandler(c -> {
			ClientUtils.addTimer(x -> {
				collection.setActive(collection.getWidgetIndex(itemAllSub) + 1);
				MenuPlace menuPlace = new MenuPlace().handleCategory("Tout afficher", categoryName);
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);

			}, 50);
		});

		return collection;
	}

	private MaterialCollectionItem getCollectionItem(String text, IconType iconType, boolean isTitel,
			boolean isBackItem) {
		MaterialCollectionItem collectionItem = new MaterialCollectionItem();
		collectionItem.setHeight("40px");
		collectionItem.setBorder("0px");

		MaterialLink link = new MaterialLink(text);
		link.setPaddingTop(10);

		if (isTitel) {
			collectionItem.setBackgroundColor(Color.GREY_LIGHTEN_3);
			link.getElement().getStyle().setFontWeight(FontWeight.BOLD);
			link.setMarginLeft(-7);
		}

		if (isBackItem) {
			link.setTextColor(Color.AMBER_DARKEN_3);
		}

		link.setIconType(iconType);
		link.setIconColor(Color.AMBER_DARKEN_3);
		link.getIcon().setMarginTop(isTitel ? -5 : 2);
		link.getIcon().setIconFontSize(isTitel ? 20 : 15, Unit.PX);

		collectionItem.add(link);

		// link.addClickHandler(x -> {
		// Place place = MBoaOnlineUI.CLIENT_FACTORY.getPlaceController().getWhere();
		// MenuPlace menuPlace = new MenuPlace().handleCategory(menuSO.getName(),
		// menuSO.getParentMenuSo().getName());
		// MBoaOnlineUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);
		//
		// // MBoaOnlineUI.CLIENT_FACTORY.scrollAppToTop(true);
		//
		// });

		return collectionItem;
	}

	private MaterialCollectionItem getCollectionItemForBase(String text, IconType iconType, boolean isFirst) {
		MaterialCollectionItem collectionItem = new MaterialCollectionItem();
		// collectionItem.setWaves(WavesType.DEFAULT);
		collectionItem.setHeight("40px");
		collectionItem.setBorder("0px");

		MaterialLink link = new MaterialLink(text);

		link.setPaddingTop(10);

		if (iconType != null) {
			link.setIconType(iconType);
			link.setIconColor(Color.AMBER_DARKEN_3);
			link.getIcon().setMarginTop(-5);
		}

		if (isFirst) {
			collectionItem.setBackgroundColor(Color.GREY_LIGHTEN_3);
			link.getElement().getStyle().setFontWeight(FontWeight.BOLD);
			link.setTextColor(Color.AMBER_DARKEN_3);

		} else {
			link.addClickHandler(x -> {
				if (swippingDone) {
					swippingDone = false;
					this.actualSelectedCategory = collectionItem;
					ClientUtils.addTimer(c -> {
						baseCategories.setActive(baseCategories.getWidgetIndex(collectionItem) + 1);
					}, 20);

					goToSubCategories(text);
				}

			});

		}
		collectionItem.add(link);

		return collectionItem;
	}

	private CategoryCO getBaseCategoryCO(String category) {
		return list.stream().filter(c -> c.getName().equals(category)).findFirst().orElse(null);
	}

	@Override
	public void swippeToMenu() {
		if (subCategoryModus) {
			goBack();
		}

	}

	public void goToSubCategories(String baseCategory) {
		subCategories.add(getCollection(baseCategory));
		swipperWidget.swipeTo("subCategories", h -> {
			ClientUtils.addTimer(f -> {
				swippingDone = true;
				subCategoryModus = true;

			}, 500);

		});
	}

}
