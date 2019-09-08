package com.materials.client.widgets.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;

public class MDTree extends Composite {

	private Consumer<String> consumer;

	private MaterialTree materialTree;
	private Map<String, MaterialTreeItem> items = new HashMap<>();
	private Map<String, MaterialTreeItem> parentItems = new HashMap<>();

	MaterialTreeItem itemOpened;

	IconType closed = IconType.CHEVRON_RIGHT;
	IconType opened = IconType.EXPAND_MORE;

	public MDTree(Consumer<String> consumer) {
		this.consumer = consumer;
		materialTree = new MaterialTree();
		initWidget(materialTree);

		materialTree.setOverflow(Overflow.HIDDEN);
		materialTree.addCloseHandler(event -> {
			MaterialTreeItem item = event.getTarget();
			item.setIconType(closed);

		});

		materialTree.addOpenHandler(event -> {

			MaterialTreeItem item = event.getTarget();
			item.setIconType(opened);
			this.consumer.accept(item.getText());
			// collapseOther(item.getText());

		});

	}

	public void addItemToParent(String parent, String itemName) {

		MaterialTreeItem treeItemParent = items.get(parent);
		if (treeItemParent != null) {
			MaterialTreeItem child = new MaterialTreeItem(itemName);
			child.setMarginLeft(40);
			treeItemParent.add(child);

			if (treeItemParent.getIcon() == null) {
				treeItemParent.setIconType(closed);
				treeItemParent.setIconColor(Color.GREY_DARKEN_1);
			}
			items.put(itemName, child);
		}

	}

	private void collapseOther(String itemClicked) {

		for (String name : parentItems.keySet()) {
			if (!name.equals(itemClicked)) {
				parentItems.get(name).collapse();
				parentItems.get(name).setIconType(closed);
			}
		}
	}

	public void addItem(String itemName) {
		MaterialTreeItem item = new MaterialTreeItem(itemName);
		// item.getSpan().setMarginLeft(-10);
		item.setIconType(closed);
		// item.setIconColor(Color.GREY_DARKEN_1);
		materialTree.add(item);
		items.put(itemName, item);
		parentItems.put(itemName, item);

	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		materialTree.collapse();
	}

}
