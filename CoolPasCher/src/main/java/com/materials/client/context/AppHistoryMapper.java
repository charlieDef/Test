
package com.materials.client.context;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuSO;
import com.materials.client.places.configurations.contentConfig.ContentConfigPlace;
import com.materials.client.places.configurations.menuConfig.MenuConfigPlace;
import com.materials.client.places.configurations.rsociaux.RSociauxConfigPlace;
import com.materials.client.places.configurations.siteConfig.WebSiteConfigPlace;
import com.materials.client.places.configurations.sliderConfig.SliderConfigPlace;
import com.materials.client.places.configurations.stat.StatisticConfigPlace;
import com.materials.client.places.configurations.userConfig.UserConfigPlace;
import com.materials.client.places.content.ContentPlace;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.places.mymboa.MyMboaPlace;
import com.materials.client.places.search.SearchPlace;

//@WithTokenizers({ StartPlace.Tokenizer.class, MediaPlace.Tokenizer.class })
public class AppHistoryMapper implements PlaceHistoryMapper {

	public AppHistoryMapper() {
		exportConfigFunction();
		exportMenuFunction();
	}

	@Override
	public Place getPlace(String token) {

		Place place = null;
		if (token != null && !token.isEmpty()) {
			String realToken = token.substring(token.indexOf("/") + 1);
			if (token.indexOf("start/") > 0) {
				place = new MenuPlace().handleMenu("start", realToken);
			} else if (token.indexOf("area/") > 0) {
				String[] areaToken = realToken.split("/");
				place = new MenuPlace().handleCategory(areaToken[1], areaToken[0]);
			} else if (token.indexOf("article/") > 0) {
				place = new ContentPlace("article", realToken);
			} else if (token.indexOf("search/") > 0) {
				place = new SearchPlace(realToken);
			} else if (token.indexOf("myMboa/") > 0) {
				place = new MyMboaPlace(realToken);
			} else if (token.indexOf("configurations/contents") > 0) {
				place = new ContentConfigPlace();
			} else if (token.indexOf("configurations/menus") > 0) {
				place = new MenuConfigPlace();
			} else if (token.indexOf("configurations/users") > 0) {
				place = new UserConfigPlace();
			} else if (token.indexOf("configurations/sliders") > 0) {
				place = new SliderConfigPlace();
			} else if (token.indexOf("configurations/rsociaux") > 0) {
				place = new RSociauxConfigPlace();
			} else if (token.indexOf("configurations/website") > 0) {
				place = new WebSiteConfigPlace();
			} else if (token.indexOf("configurations/statistic") > 0) {
				place = new StatisticConfigPlace();
			}

		} else {
			place = new MenuPlace().handleMenu("start", "");
		}
		return place;
	}

	@Override
	public String getToken(Place place) {
		String token = "!";

		if (place instanceof MenuPlace) {
			MenuPlace menuPlace = (MenuPlace) place;
			token += menuPlace.getArea() + "/" + menuPlace.getMenuClicked();
		} else if (place instanceof ContentPlace) {
			ContentPlace contentPlace = (ContentPlace) place;
			token += contentPlace.getArea() + "/" + contentPlace.getArticleClicked();
		} else if (place instanceof SearchPlace) {
			SearchPlace searchPlace = (SearchPlace) place;
			token += "search/" + searchPlace.getSearchTerm();
		} else if (place instanceof MyMboaPlace) {
			MyMboaPlace mboaPlace = (MyMboaPlace) place;
			token += "myMboa/" + mboaPlace.getMenuClicked();
		} else if (place instanceof ContentConfigPlace) {
			token += "configurations/contents";
		} else if (place instanceof MenuConfigPlace) {
			token += "configurations/menus";
		} else if (place instanceof UserConfigPlace) {
			token += "configurations/users";
		} else if (place instanceof SliderConfigPlace) {
			token += "configurations/sliders";
		} else if (place instanceof RSociauxConfigPlace) {
			token += "configurations/rsociaux";
		} else if (place instanceof WebSiteConfigPlace) {
			token += "configurations/website";
		} else if (place instanceof StatisticConfigPlace) {
			token += "configurations/statistic";
		}
		return token;
	}

	private static void dorun(String param) {
		if (param.equals(MenuSO.LOGIN)) {
			CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().show();
		} else if (param.equals(MenuSO.LOGOUT)) {
			CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().getPresenter().performLogout();
		} else if (param.equals(MenuSO.REGISTER)) {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new MenuPlace().handleMenu("start", MenuSO.REGISTER));
			// MBoaOnlineUI.CLIENT_FACTORY.getStartView().showRegisterPanel(false);
		} else if (param.equals(MenuSO.CONFIGURER)) {
			CoolPasCherUI.CLIENT_FACTORY.getStartView().showRegisterPanel(true);
		} else if (param.contains(MenuSO.MES_ANNONCES)) {
			MyMboaPlace myMboaPlace = new MyMboaPlace(param);
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(myMboaPlace);
		} else {
			MenuPlace place = new MenuPlace().handleMenu("start", param);
			place.setHome(param.equals("home"));
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(place);
		}
	}

	private static void doToast(String param) {

		if (param.equals(ContentConfigPlace.NAME)) {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new ContentConfigPlace());
		} else if (param.equals(MenuConfigPlace.NAME)) {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new MenuConfigPlace());
		} else if (param.equals(UserConfigPlace.NAME)) {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new UserConfigPlace());
		} else if (param.equals(SliderConfigPlace.NAME)) {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new SliderConfigPlace());
		} else if (param.equals(RSociauxConfigPlace.NAME)) {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new RSociauxConfigPlace());
		} else if (param.equals(WebSiteConfigPlace.NAME)) {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new WebSiteConfigPlace());
		} else if (param.equals(StatisticConfigPlace.NAME)) {
			CoolPasCherUI.CLIENT_FACTORY.getPlaceController().goTo(new StatisticConfigPlace());
		}

	}

	private static native void exportMenuFunction()/*-{
	 $wnd.dorun=@com.materials.client.context.AppHistoryMapper::dorun(*);
	 }-*/;

	public static native void exportConfigFunction()/*-{
	 $wnd.doToast =@com.materials.client.context.AppHistoryMapper::doToast(*);
	 }-*/;

}
