package com.materials.client.places.configurations;

import java.util.LinkedList;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.button.ControlButton;

import gwt.material.design.client.base.viewport.Resolution;
import gwt.material.design.client.base.viewport.ViewPort;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

public class ConfigControlPanel extends MaterialPanel implements ConfigControlPanelView {

	private LinkedList<Widget> buttons = new LinkedList<Widget>();

	public ConfigControlPanel() {

		setOpacity(0);
		setVerticalAlign(VerticalAlign.BOTTOM);

		// setHeight("100%");
		setWidth("60px");
		setRight(0);
		setLayoutPosition(Position.FIXED);

		// getElement().getStyle().setTop(75, Unit.PCT);
		setDepth(20);

		ViewPort.when(Resolution.ALL_MOBILE, Resolution.TABLET).then(viewPortChange -> {
			setBottom(0);

		});

		// ViewPort.when(Resolution.ALL_LAPTOP).then(viewPortChange -> {
		// setTop(100);
		// });

		ClientUtils.addTimer(c -> {
			ClientUtils.animeIn(this, Transition.FADEINRIGHT, 1500);
		}, 500);
	}

	private void addControll(Widget button) {
		add(button);
		buttons.add(button);

	}

	@Override
	public void showControll(Widget widget) {
		clearControll();
		addControll(widget);
	}

	@Override
	public void showControll(LinkedList<ControlButton> buttons) {

		clearControll();

		buttons.forEach(btn -> {
			// btn.setOpacity(0);
			// btn.setOpacity(0);
			addControll(btn);
		});
	}

	public void clearControll() {
		if (!buttons.isEmpty()) {
			buttons.forEach(btn -> {
				remove(btn);
				// btn.setOpacity(0);
				// btn.setOpacity(0);
			});
		}
	}

}
