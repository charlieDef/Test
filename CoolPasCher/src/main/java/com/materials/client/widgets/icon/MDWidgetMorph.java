package com.materials.client.widgets.icon;

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

public class MDWidgetMorph extends Composite {

	private static MDWidgetMorphUiBinder uiBinder = GWT.create(MDWidgetMorphUiBinder.class);

	interface MDWidgetMorphUiBinder extends UiBinder<Widget, MDWidgetMorph> {
	}

	private boolean targetActif = false;

	private Transition transitionIn = Transition.FADEINRIGHT;
	private Transition transitionOut = Transition.FADEOUT;

	@UiField
	Style style;

	int zBase = 2;

	MaterialWidget source, target;

	@UiField
	MaterialPanel panelBaseUi;

	public MDWidgetMorph(MaterialWidget source, MaterialWidget target) {
		initWidget(uiBinder.createAndBindUi(this));
		this.source = source;
		this.target = target;

		this.source.addStyleName(style.theWidget());
		this.target.addStyleName(style.theWidget());

		this.source.setDepth(2);
		this.target.setDepth(1);

		this.source.setOpacity(1);
		this.target.setOpacity(0);

		panelBaseUi.add(this.source);
		panelBaseUi.add(this.target);
		// panelBaseUi.setLayoutPosition(Position.ABSOLUTE);

	}

	public void showWidgetSource() {
		targetActif = false;
		animeOut(target, transitionOut);
		animeIn(source, transitionIn, zBase + 1);
	}

	public void showWidgetTarget() {
		targetActif = true;
		animeOut(source, transitionOut);
		animeIn(target, transitionIn, zBase + 1);
	}

	private void animeOut(MaterialWidget widget, Transition transition) {
		new MaterialAnimation().transition(transition).animate(widget, () -> {
			widget.setOpacity(0);
			widget.setDepth(zBase - 1);
		});
	}

	private void animeIn(MaterialWidget widget, Transition transition, Integer depth) {
		new MaterialAnimation().transition(transition).animate(widget, () -> {
			widget.setOpacity(1);
			widget.setDepth(depth);
			// setHeight(widget.getOffsetHeight() + "px");
		});
	}

	interface Style extends CssResource {

		String base();

		String theWidget();
	}

	public void setTransitionIn(Transition transitionIn) {
		this.transitionIn = transitionIn;
	}

	public void setTransitionOut(Transition transitionOut) {
		this.transitionOut = transitionOut;
	}

	public boolean isTargetActif() {
		return targetActif;
	}
}
