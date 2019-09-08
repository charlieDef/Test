package com.materials.client.widgets.swipe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class MDSwipeContainer extends Composite {

	private static MDSwipeContainerUiBinder uiBinder = GWT.create(MDSwipeContainerUiBinder.class);

	interface MDSwipeContainerUiBinder extends UiBinder<Widget, MDSwipeContainer> {
	}

	int zBase = 0;
	private int animationDuration = 650;

	private Transition swipeToIN = Transition.FADEINLEFT, switoOUT = Transition.FADEOUT,
			backToIN = Transition.FADEINLEFT, backToOut = Transition.FADEOUT;

	private MaterialWidget actualShowed;

	@UiField
	SwipeStyle style;

	@UiField
	MaterialPanel containerUi;

	private Map<String, MaterialWidget> widgets = new HashMap<String, MaterialWidget>();
	private Map<String, Integer> depths = new HashMap<String, Integer>();

	public MDSwipeContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void swipeToWidget(String widgetId, Consumer<Boolean> endAnimationConsumer) {
		MaterialWidget widget = widgets.get(widgetId);
		if (widget != null) {
			if (actualShowed != null) {
				animeOut(actualShowed, switoOUT, endAnimationConsumer);
			}
			animeIn(widget, swipeToIN, depths.get(widgetId) + 1, endAnimationConsumer);
			this.actualShowed = widget;
		}
	}

	public void swipeBackToWidget(String widgetId, Consumer<Boolean> endAnimationConsumer) {
		MaterialWidget widget = widgets.get(widgetId);
		if (widget != null) {
			if (actualShowed != null) {
				animeOut(actualShowed, backToOut, endAnimationConsumer);
			}
			animeIn(widget, backToIN, depths.get(widgetId) + 1, endAnimationConsumer);
		}
	}

	public void addWidgetChild(String widgetId, MaterialWidget materialWidget) {
		zBase++;
		widgets.put(widgetId, materialWidget);
		depths.put(widgetId, zBase);
		materialWidget.setOpacity(0);
		materialWidget.addStyleName(style.child());
		containerUi.add(materialWidget);
	}

	// public void removeWidgetChild(String widgetId) {
	// zBase--;
	// if (widgets.containsKey(widgetId)) {
	// widgets.remove(widgetId);
	// containerUi.remove(widgets.get(widgetId));
	//
	// }
	//
	// }

	private void animeOut(MaterialWidget widget, Transition transition, Consumer<Boolean> endAnimationConsumer) {
		MaterialAnimation an = new MaterialAnimation();
		an.setDuration(animationDuration);
		an.setDelay(0);
		an.transition(transition).animate(widget, () -> {
			widget.setOpacity(0);
			widget.setDepth(zBase - 1);

			if (endAnimationConsumer != null) {
				endAnimationConsumer.accept(true);
			}
		});
	}

	private void animeIn(MaterialWidget widget, Transition transition, Integer depth,
			Consumer<Boolean> endAnimationConsumer) {
		MaterialAnimation ma = new MaterialAnimation();
		ma.setDuration(animationDuration);
		ma.setDelay(0);
		ma.transition(transition).animate(widget, () -> {
			widget.setOpacity(1);
			widget.setDepth(depth);
			if (endAnimationConsumer != null) {
				endAnimationConsumer.accept(true);
			}
		});
	}

	interface SwipeStyle extends CssResource {

		String container();

		String child();

	}

	public void setAnimationDuration(int animationDuration) {
		this.animationDuration = animationDuration;
	}

}
