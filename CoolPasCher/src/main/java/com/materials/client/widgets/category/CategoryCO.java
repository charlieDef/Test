package com.materials.client.widgets.category;

import java.util.ArrayList;
import java.util.List;

import gwt.material.design.client.constants.IconType;

public class CategoryCO {

	private String name;
	private String title;
	private int marginLeft;
	private IconType iconType;
	private List<CategoryCO> items = new ArrayList<>();
	private CategoryCO parent;

	public CategoryCO() {

	}

	public CategoryCO(String category) {
		setCategory(category);
	}

	public CategoryCO(String category, String title) {
		setCategory(category);
		setTitle(title);
	}

	public CategoryCO(String category, String title, IconType iconType) {
		setCategory(category);
		setTitle(title);
		setIconType(iconType);
	}

	// public void addItem(String item, String title) {
	// if (items == null) {
	// items = new ArrayList<CategoryCO>();
	// }
	// items.add(new CategoryCO(item, title));
	// }

	public CategoryCO addItem(String item, String title, IconType iconType) {

		CategoryCO categoryCO = addItem(item, title);
		categoryCO.setIconType(iconType);

		return categoryCO;
	}

	public void addChild(CategoryCO categoryCO) {
		setParent(this);
		items.add(categoryCO);
	}

	public CategoryCO addItem(String item, String title) {
		CategoryCO categoryCO = new CategoryCO(item, title);
		categoryCO.setParent(this);
		items.add(categoryCO);
		return categoryCO;
	}

	public void removeItem(String item) {

		for (CategoryCO categoryCO : items) {
			if (categoryCO.getName().equals(item)) {
				items.remove(categoryCO);
				categoryCO.setParent(null);
			}
		}
	}

	public List<CategoryCO> getItems() {
		return items;
	}

	public void setCategory(String category) {
		this.name = category;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean hasSubItems() {
		return items != null && items.size() > 0;
	}

	public CategoryCO getParent() {
		return parent;
	}

	public void setParent(CategoryCO parent) {
		this.parent = parent;
	}

	public IconType getIconType() {
		return iconType;
	}

	public void setIconType(IconType iconType) {
		this.iconType = iconType;
	}

	public int getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}
}
