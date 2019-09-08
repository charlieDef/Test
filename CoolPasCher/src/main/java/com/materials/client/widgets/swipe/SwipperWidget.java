package com.materials.client.widgets.swipe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Position;
import com.materials.client.utils.ClientUtils;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

/**
 * 
 * @author Charles
 *
 */
public class SwipperWidget extends MaterialPanel {

	private Map<String, MaterialWidget> map = new HashMap<String, MaterialWidget>();

	private MaterialWidget actual;
	private boolean fixContentHeight = false;
	
	private int delais =450;
	

	public SwipperWidget() {
		setLayoutPosition(Position.RELATIVE);
		getElement().getStyle().setProperty("minHeight", "1000px");
	}

	public SwipperWidget(String minHeight) {
		setLayoutPosition(Position.RELATIVE);
		getElement().getStyle().setProperty("minHeight", minHeight);
	}

	public void addSwipeItem(String name, MaterialWidget materialWidget, boolean hasNextSwipe) {

		if (hasNextSwipe) {
			materialWidget.setLayoutPosition(Position.ABSOLUTE);
		}
		materialWidget.setLeft(0);
		materialWidget.setRight(0);
		materialWidget.setOpacity(0);
		materialWidget.setVisible(false);
		add(materialWidget);
		map.put(name, materialWidget);
	}

	public void show(String name) {
		MaterialWidget materialWidget = map.get(name);
		ClientUtils.animeIn(materialWidget, Transition.FADEINLEFT,delais);

		if (fixContentHeight && actual != null) {
			int milli = materialWidget.getOffsetHeight() < actual.getOffsetHeight() ? 500 : 0;
			ClientUtils.addTimer(x -> {
				getElement().getStyle().setProperty("minHeight", materialWidget.getOffsetHeight() + "px");
			}, milli);
		}

		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
			if (fixContentHeight) {
				getElement().getStyle().setProperty("minHeight", materialWidget.getOffsetHeight() + "px");
			}

		}, 500);
	}

	public void show(String name, Transition transition) {
		MaterialWidget materialWidget = map.get(name);
		ClientUtils.animeIn(materialWidget, transition,delais);
		// fixHeight(materialWidget);

		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
		}, 500);
	}

	public void show(String name, Transition transition, int duration) {
		MaterialWidget materialWidget = map.get(name);
		ClientUtils.animeIn(materialWidget, transition, duration);
		// fixHeight(materialWidget);
		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
		}, 500);
	}

	public void swipeTo(String name) {
		MaterialWidget materialWidget = map.get(name);
		actual.setLayoutPosition(Position.ABSOLUTE);
		ClientUtils.animeOut(actual, Transition.FADEOUT,delais);
		ClientUtils.animeIn(materialWidget, Transition.FADEINLEFT,delais);
		fixHeight(materialWidget);
		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
		}, 500);
	}

	public void swipeTo(String name, Consumer<Boolean> consumer) {
		MaterialWidget materialWidget = map.get(name);
		actual.setLayoutPosition(Position.ABSOLUTE);
		ClientUtils.animeOut(actual, Transition.FADEOUT,delais);
		ClientUtils.animeIn(materialWidget, Transition.FADEINLEFT,delais, c -> {
			consumer.accept(true);
		});
		fixHeight(materialWidget);
		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);

		}, 500);
	}

	public void swipeTo(String name, Transition transition) {
		MaterialWidget materialWidget = map.get(name);
		actual.setLayoutPosition(Position.ABSOLUTE);
		ClientUtils.animeOut(actual, Transition.FADEOUT,delais);
		ClientUtils.animeIn(materialWidget, transition,delais);
		fixHeight(materialWidget);
		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
		}, 500);
	}

	public void swipeTo(String name, Transition transition, int duration) {
		MaterialWidget materialWidget = map.get(name);
		actual.setLayoutPosition(Position.ABSOLUTE);
		ClientUtils.animeOut(actual, Transition.FADEOUT,duration);
		ClientUtils.animeIn(materialWidget, transition, duration);
		fixHeight(materialWidget);
		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
		}, 500);
	}

	public void swipeBackTo(String name) {
		MaterialWidget materialWidget = map.get(name);
		actual.setLayoutPosition(Position.ABSOLUTE);
		ClientUtils.animeOut(actual, Transition.FADEOUTLEFT,delais);
		ClientUtils.animeIn(materialWidget, Transition.FADEIN,delais);

		fixHeight(materialWidget);

		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
		}, 500);
	}

	public void swipeBackTo(String name, Consumer<Boolean> consumer) {
		MaterialWidget materialWidget = map.get(name);
		actual.setLayoutPosition(Position.ABSOLUTE);
		ClientUtils.animeOut(actual, Transition.FADEOUTLEFT,delais);
		ClientUtils.animeIn(materialWidget, Transition.FADEIN,delais);

		fixHeight(materialWidget);

		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
			consumer.accept(true);
		}, 500);
	}

	public void swipeBackTo(String name, Transition transition) {
		MaterialWidget materialWidget = map.get(name);
		actual.setLayoutPosition(Position.ABSOLUTE);
		ClientUtils.animeOut(actual, transition,delais);
		ClientUtils.animeIn(materialWidget, Transition.FADEIN,delais);
		fixHeight(materialWidget);

		ClientUtils.addTimer(x -> {
			this.actual = materialWidget;
			this.actual.setLayoutPosition(Position.STATIC);
		}, 500);
	}

	public void setFixContentHeight(boolean fixContentHeight) {
		this.fixContentHeight = fixContentHeight;
	}

	private void fixHeight(MaterialWidget materialWidget) {
		if (fixContentHeight && actual != null) {
			int milli = materialWidget.getOffsetHeight() < actual.getOffsetHeight() ? 380 : 0;
			ClientUtils.addTimer(x -> {
				getElement().getStyle().setProperty("minHeight", materialWidget.getOffsetHeight() + "px");
			}, milli);
		}
	}

	public boolean isSwippeReady(String name) {
		MaterialWidget materialWidget = map.get(name);
		return materialWidget != actual;
	}
	
	public void setDelais(int delais) {
		this.delais = delais;
	}

}
