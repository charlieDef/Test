package com.materials.client.places.configurations;

import com.materials.client.CoolPasCherUI;
import com.materials.client.widgets.swipe.SwipperWidget;

import gwt.material.design.client.ui.MaterialPanel;

public abstract class AbstractControlPanel extends MaterialPanel {

	protected ConfigControlPanelView configControlPanel;
	protected SwipperWidget swipperWidget;

	public AbstractControlPanel(boolean addSwipper) {
		configControlPanel = new ConfigControlPanel();
		add(configControlPanel);

		if (addSwipper) {
			swipperWidget = new SwipperWidget("1500px");
			add(swipperWidget);
		}

	}
	
	protected void scrollToTop(){
		CoolPasCherUI.CLIENT_FACTORY.scrollToTop();
	}

}
