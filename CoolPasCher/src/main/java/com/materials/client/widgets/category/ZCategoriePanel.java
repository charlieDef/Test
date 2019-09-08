package com.materials.client.widgets.category;

import java.util.List;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.materials.client.CoolPasCherUI;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.utils.ClientUtils;
import com.materials.client.views.navbar.AppNavBarView;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialDivider;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;

public class ZCategoriePanel extends MaterialPanel implements ZCategoriePanelView {

	private AppNavBarView.Presenter presenter;

	public ZCategoriePanel(List<CategoryCO> items, String baseName, IconType iconTypeBase) {

		add(new MaterialDivider());
		MaterialCollapsible collapsibleBase = new MaterialCollapsible();
		add(collapsibleBase);

		MaterialCollapsibleItem collapsibleItem = new MaterialCollapsibleItem();
		collapsibleItem.setBackgroundColor(Color.GREY_LIGHTEN_4);
		collapsibleBase.add(collapsibleItem);

		MaterialCollapsibleHeader header = getCollapsibleHeader(baseName, "", iconTypeBase, true, 0);
		MaterialCollapsibleBody body = new MaterialCollapsibleBody();
		collapsibleItem.add(header);
		collapsibleItem.add(body);

		MaterialCollapsible collapsible = new MaterialCollapsible();
		body.add(collapsible);

		for (CategoryCO categoryCO : items) {
			MaterialCollapsibleItem collapsibleItem0 = getMaterialCollapsibleItem(categoryCO, false, 10);
			collapsible.add(collapsibleItem0);
		}

		ClientUtils.addTimer(c -> collapsibleItem.expand(), 4000);

	}

	@Override
	public void setPresenter(AppNavBarView.Presenter presenter) {
		this.presenter = presenter;
	}

	private MaterialCollapsibleItem getMaterialCollapsibleItem(CategoryCO categoryCO, boolean base, int marginLeft) {
		MaterialCollapsibleItem collapsibleItem = new MaterialCollapsibleItem();

		String parent = categoryCO.getParent() != null ? categoryCO.getParent().getName() : "";

		// header
		MaterialCollapsibleHeader header = getCollapsibleHeader(categoryCO.getName(), parent, categoryCO.getIconType(),
				base, marginLeft);
		collapsibleItem.add(header);

		if (categoryCO.getItems() != null && !categoryCO.getItems().isEmpty()) {

			// body
			MaterialCollapsibleBody collapsibleBody = new MaterialCollapsibleBody();
			collapsibleItem.add(collapsibleBody);

			MaterialCollapsible collapsible = new MaterialCollapsible();
			collapsibleBody.add(collapsible);
			for (CategoryCO subCat : categoryCO.getItems()) {
				MaterialCollapsibleItem collapsibleItem0 = getMaterialCollapsibleItem(subCat, false, marginLeft + 20);
				collapsible.add(collapsibleItem0);
			}
		}

		return collapsibleItem;
	}

	private MaterialCollapsibleHeader getCollapsibleHeader(String name, String parentName, IconType iconType,
			boolean base, int marginLeft) {
		MaterialCollapsibleHeader collapsibleHeader = new MaterialCollapsibleHeader();

		// collapsibleHeader.setWaves(WavesType.DEFAULT);

		MaterialLink link = new MaterialLink(name);
		link.setMarginTop(-10);
		link.setMarginLeft(marginLeft);

		link.getIcon().setIconColor(Color.AMBER_DARKEN_3);
		link.setIconType(iconType);
		link.setIconFontSize(20, Unit.PX);
		link.getSpan().setMarginLeft(-10);
		if (base) {
			link.getElement().getStyle().setFontWeight(FontWeight.BOLD);
			link.setTextColor(Color.AMBER_DARKEN_3);
			link.setIconFontSize(25, Unit.PX);
			link.getSpan().setMarginLeft(10);
		} else {
			collapsibleHeader.setHeight("50px");
			link.addClickHandler(c -> {
				ClientUtils.addTimer(x -> {

					// MenuPlace menuPlace = new MenuPlace().handleCategory("Tout afficher", name);
					MenuPlace menuPlace = new MenuPlace().handleCategory(false, name, parentName);
					CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(menuPlace);

				}, 50);
			});
		}

		if (iconType.equals(IconType.STOP) || iconType.equals(IconType.LENS)) {
			link.setIconFontSize(8, Unit.PX);
			link.getIcon().setIconColor(Color.GREY_DARKEN_1);
		}
		// link.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		// link.setTextColor(Color.AMBER_DARKEN_3);
		// link.addClickHandler(c -> presenter.click(name));

		collapsibleHeader.add(link);

		return collapsibleHeader;
	}

}
