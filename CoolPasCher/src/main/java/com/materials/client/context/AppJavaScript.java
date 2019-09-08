package com.materials.client.context;

import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuSO;
import com.materials.client.places.menu.MenuPlace;

import jsinterop.annotations.JsType;

@JsType
public class AppJavaScript {

	public static void performRun(String param) {
		if (param.equals(MenuSO.LOGIN)) {
			CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().show();
		} else if (param.equals(MenuSO.LOGOUT)) {
			CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().getPresenter().performLogout();
		} else if (param.equals(MenuSO.REGISTER)) {
			CoolPasCherUI.CLIENT_FACTORY.getStartView().showRegisterPanel(false);
		} else if (param.equals(MenuSO.CONFIGURER)) {
			CoolPasCherUI.CLIENT_FACTORY.getStartView().showRegisterPanel(true);
		} else {
			MenuPlace place = new MenuPlace().handleMenu("start", param);
			place.setHome(param.equals("home"));
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(place);
		}
	}

}
