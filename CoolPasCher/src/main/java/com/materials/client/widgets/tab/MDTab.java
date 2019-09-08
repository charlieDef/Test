package com.materials.client.widgets.tab;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import gwt.material.design.client.ui.animate.Transition;

public class MDTab extends Composite {

	public static final String TAB_1 = "my1";
	public static final String TAB_2 = "my2";
	public static final String TAB_3 = "my3";

	private Consumer<Integer> consumer;

	private static MDTabUiBinder uiBinder = GWT.create(MDTabUiBinder.class);

	interface MDTabUiBinder extends UiBinder<MaterialRow, MDTab> {
	}

	private Transition transition = Transition.FADEINLEFT;

	private Map<Integer, MaterialColumn> tabContents = new HashMap<Integer, MaterialColumn>();

	@UiField
	MaterialTab tabUi;

	@UiField
	MaterialRow materialRowUi;

	@UiField
	MaterialCardTitle cardTitelUi;

	private int index = 0;

	@UiField
	MaterialRow contentAreaUi, headerAreaUi, headerAreaUi2;

	@UiField
	MaterialColumn iconUi, titelAreaUi;

	public MDTab(String grid) {
		initWidget(uiBinder.createAndBindUi(this));
		titelAreaUi.setGrid(grid);

		tabUi.addSelectionHandler(event -> {
			Integer selected = event.getSelectedItem();
			MaterialColumn columnContent = tabContents.get(selected);
			CoolPasCherUI.CLIENT_FACTORY.addAnimation(columnContent.getElement(), 400);
			if (consumer != null) {
				this.consumer.accept(selected);
			}
		});
	}

	public void setHeaderTitel(String titel) {
		cardTitelUi.setText(titel);
	}

	public MDTab() {
		initWidget(uiBinder.createAndBindUi(this));

		tabUi.addSelectionHandler(event -> {
			Integer selected = event.getSelectedItem();
			MaterialColumn columnContent = tabContents.get(selected);
			CoolPasCherUI.CLIENT_FACTORY.addAnimation(columnContent.getElement(), 400);
			if (consumer != null) {
				this.consumer.accept(selected);
			}
		});
	}

	public void addnewTab(String name, IconType iconType, String id, Widget widgetContent) {

		// Adding the MaterialTabItem into MaterialTab::dynamicTab
		MaterialTabItem item = new MaterialTabItem();
		item.setWaves(WavesType.ORANGE);
		MaterialLink link = new MaterialLink(name);
		link.setTextColor(Color.AMBER_DARKEN_1);
		link.setHref("#" + id);
		item.add(link);

		if (iconType != null) {
			link.setIconType(iconType);
		}

		tabUi.add(item);

		// Adding a simple content
		MaterialColumn materialColumn = new MaterialColumn();
		contentAreaUi.setBackgroundColor(Color.GREY_LIGHTEN_1);
		materialColumn.setId(id);
		materialColumn.setGrid("s12");
		materialColumn.add(widgetContent);
		tabContents.put(index, materialColumn);

		contentAreaUi.add(materialColumn);

		if (index != 0) {
			// materialColumn.setOpacity(0);
		}
		index++;
	}

	public void setTransition(Transition transition) {
		this.transition = transition;
	}

	public void addWidgetToIconUi(Widget widget) {
		iconUi.setVisible(true);
		iconUi.add(widget);
	}

	public void addWidgetToHeader(Widget widget) {
		headerAreaUi.add(widget);
	}

	public void addWidgetToHeader2(Widget widget, boolean toright) {

		MaterialColumn column = new MaterialColumn();
		if (toright) {
			column.setFloat(Float.RIGHT);
		}

		column.add(widget);
		headerAreaUi2.add(column);
	}

	public void setConsumer(Consumer<Integer> consumer) {
		this.consumer = consumer;
	}

	public void selectTab(String id) {
		tabUi.selectTab(id);
	}
}
