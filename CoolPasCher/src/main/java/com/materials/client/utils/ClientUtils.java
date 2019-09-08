package com.materials.client.utils;

import java.util.function.Consumer;

import com.google.gwt.user.client.Timer;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class ClientUtils {

	public static void animeOut(MaterialWidget widget, Transition transition) {
		new MaterialAnimation().transition(transition).animate(widget, () -> {
			widget.setOpacity(0);
			widget.setVisible(false);
		});
	}

	public static void animeOut(MaterialWidget widget, Transition transition, int duration) {
		MaterialAnimation anim = new MaterialAnimation();
		anim.setDuration(duration);
		anim.transition(transition).animate(widget, () -> {
			widget.setOpacity(0);
			widget.setVisible(false);
		});
	}

	public static void animeIn(MaterialWidget widget, Transition transition, int duration) {
		widget.setVisible(true);
		MaterialAnimation anim = new MaterialAnimation();
		anim.setDuration(duration);
		anim.transition(transition).animate(widget, () -> {
			widget.setOpacity(1);
			// MBoaOnlineUI.CLIENT_FACTORY.scrollAppToTop(true);
		});
	}

	public static void animeIn(MaterialWidget widget, Transition transition) {
		widget.setVisible(true);
		MaterialAnimation anim = new MaterialAnimation();
		anim.transition(transition).animate(widget, () -> {
			widget.setOpacity(1);
			// MBoaOnlineUI.CLIENT_FACTORY.scrollAppToTop(true);
		});
	}

	public static void animeIn(MaterialWidget widget, Transition transition, Consumer<Boolean> after) {
		widget.setVisible(true);
		MaterialAnimation anim = new MaterialAnimation();
		anim.transition(transition).animate(widget, () -> {
			widget.setOpacity(1);
			// MBoaOnlineUI.CLIENT_FACTORY.scrollAppToTop(true);
			after.accept(true);
		});
	}

	public static void animeIn(MaterialWidget widget, Transition transition, int duration, Consumer<Boolean> after) {
		widget.setVisible(true);
		MaterialAnimation anim = new MaterialAnimation();
		anim.setDuration(duration);
		anim.transition(transition).animate(widget, () -> {
			widget.setOpacity(1);
			// MBoaOnlineUI.CLIENT_FACTORY.scrollAppToTop(true);
			after.accept(true);
		});
	}

	public static void animeOut(MaterialWidget widget, Transition transition, Consumer<Boolean> after) {
		MaterialAnimation anim = new MaterialAnimation();
		anim.transition(transition).animate(widget, () -> {
			widget.setOpacity(0);
			widget.setVisible(false);
			after.accept(true);
		});
	}

	public static void addTimer(Consumer<Boolean> consumer, int delay) {
		new Timer() {
			@Override
			public void run() {
				consumer.accept(true);
			}
		}.schedule(delay);
	}

	public static void addTimer(Consumer<Boolean> consumer, int delay, Consumer<Boolean> after) {
		new Timer() {
			@Override
			public void run() {
				consumer.accept(true);
				if (after != null) {
					after.accept(true);
				}
			}
		}.schedule(delay);
	}
}
