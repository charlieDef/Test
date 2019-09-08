package com.materials.client.widgets.swipe;

import java.util.LinkedList;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class SwipeableContainer extends ComplexPanel implements RequiresResize, ProvidesResize {

	private static final double FULL_DIMENSION = 100.0;

	interface SwipeableContainerUiBinder extends UiBinder<DivElement, SwipeableContainer> {
	}

	private static SwipeableContainerUiBinder ourUiBinder = GWT.create(SwipeableContainerUiBinder.class);

	@UiField
	Style style;
	@UiField
	DivElement contentContainerUi;

	private final LinkedList<ChildPosition> childPositions = new LinkedList<>();
	private final boolean swipeVertical;
	private final double contentDimension;

	@UiConstructor
	public SwipeableContainer(boolean swipeVertical, double contentDimension) {
		this.swipeVertical = swipeVertical;
		this.contentDimension = contentDimension;
		DivElement container = ourUiBinder.createAndBindUi(this);
		if (swipeVertical) {
			contentContainerUi.getStyle().setHeight(contentDimension, Unit.PCT);
			container.addClassName(style.swipeVertical());
		} else {
			contentContainerUi.getStyle().setWidth(contentDimension, Unit.PCT);
			container.addClassName(style.swipeHorizontal());
		}
		setElement(container);
	}

	@Override
	public void onResize() {
		getChildren().forEach(child -> {
			if (child instanceof RequiresResize) {
				((RequiresResize) child).onResize();
			}
		});
	}

	@UiChild
	public void addChild(Widget widget, double dimension) {
		double start = childPositions.isEmpty() ? 0 : childPositions.getLast().getEnd();
		childPositions.add(new ChildPosition(start, start + dimension));
		childPositions.getLast().setToWidget(widget);
		widget.addStyleName(style.content());
		insert(widget, contentContainerUi, getWidgetCount(), false);
	}

	public void showWidget(IsWidget widget, Consumer<Boolean> callback) {
		ChildPosition topChildPosition = childPositions.get(getWidgetIndex(widget));
		if (swipeVertical) {
			contentContainerUi.getStyle().setTop(topChildPosition.getOffset(), Unit.PC);
		} else {
			contentContainerUi.getStyle().setLeft(topChildPosition.getOffset(), Unit.PC);
		}
		callback.accept(true);
	}

	public void showWidget(IsWidget widget) {
		ChildPosition topChildPosition = childPositions.get(getWidgetIndex(widget));
		if (swipeVertical) {
			contentContainerUi.getStyle().setTop(topChildPosition.getOffset(), Unit.PC);
		} else {
			contentContainerUi.getStyle().setLeft(topChildPosition.getOffset(), Unit.PC);
		}
	}

	private class ChildPosition {
		private final double start, end;

		private ChildPosition(double start, double end) {
			this.start = start;
			this.end = end;
		}

		private void setToWidget(Widget widget) {
			final double factor = contentDimension / FULL_DIMENSION;
			final double startValue = start / factor, endValue = FULL_DIMENSION - end / factor;
			if (swipeVertical) {
				widget.getElement().getStyle().setTop(startValue, Unit.PC);
				widget.getElement().getStyle().setBottom(endValue, Unit.PC);
			} else {
				widget.getElement().getStyle().setLeft(startValue, Unit.PC);
				widget.getElement().getStyle().setRight(endValue, Unit.PC);
			}
		}

		private double getOffset() {
			return -start;
		}

		private double getEnd() {
			return end;
		}
	}

	interface Style extends CssResource {

		String container();

		String swipeVertical();

		String swipeHorizontal();

		String contentContainer();

		String content();
	}
}