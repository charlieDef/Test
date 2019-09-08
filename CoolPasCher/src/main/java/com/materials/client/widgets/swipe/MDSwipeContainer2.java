package com.materials.client.widgets.swipe;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class MDSwipeContainer2 extends Composite {

	private static MDSwipeContainerUiBinder uiBinder = GWT.create(MDSwipeContainerUiBinder.class);

	interface MDSwipeContainerUiBinder extends UiBinder<Widget, MDSwipeContainer2> {
	}

	private Transition swipeToIN = Transition.ZOOMIN, switoOUT = Transition.FADEOUT, backToIN = Transition.FADEINLEFT,
			backToOut = Transition.FADEOUT;

	private MaterialWidget actualShowed;

	private int duration = 500;

	@UiField
	SwipeStyle style;

	@UiField
	MaterialPanel containerUi;

	private Map<String, MaterialWidget> widgets = new HashMap<String, MaterialWidget>();

	public MDSwipeContainer2() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void swipeToWidget(String widgetId) {
		MaterialWidget widget = widgets.get(widgetId);
		if (widget != null) {
			containerUi.clear();
			containerUi.add(widget);

			animeIn(widget, swipeToIN);
		}

		if (actualShowed != null) {
			actualShowed.setOpacity(0);
		}

		this.actualShowed = widget;
	}

	public void swipeBackToWidget(String widgetId) {
		MaterialWidget widget = widgets.get(widgetId);
		if (widget != null) {
			containerUi.clear();
			containerUi.add(widget);
			animeIn(widget, backToIN);
		}

		if (actualShowed != null) {
			actualShowed.setOpacity(0);
		}

		this.actualShowed = widget;
	}

	public void addWidgetChild(String widgetId, MaterialWidget materialWidget) {

		widgets.put(widgetId, materialWidget);
		materialWidget.addStyleName(style.child());
		containerUi.add(materialWidget);
	}

	private void animeIn(MaterialWidget widget, Transition transition) {
		MaterialAnimation ann = new MaterialAnimation().transition(transition);
		ann.setDelay(0);
		ann.setDuration(duration);
		ann.animate(widget);
		Timer uu = new Timer() {
			@Override
			public void run() {
				widget.setOpacity(1);
			}
		};
		uu.schedule(300);
	}

	interface SwipeStyle extends CssResource {

		String container();

		String child();

	}

	public void setSwipeToIN(Transition swipeToIN) {
		this.swipeToIN = swipeToIN;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setBackToIN(Transition backToIN) {
		this.backToIN = backToIN;
	}
}
