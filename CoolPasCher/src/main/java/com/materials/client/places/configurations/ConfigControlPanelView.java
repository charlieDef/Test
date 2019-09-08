package com.materials.client.places.configurations;

import java.util.LinkedList;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.widgets.button.ControlButton;

public interface ConfigControlPanelView extends IsWidget {

	void showControll(LinkedList<ControlButton> buttons);

	void showControll(Widget widget);

	void clearControll();

}
