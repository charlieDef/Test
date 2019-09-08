package com.materials.client.context;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.materials.client.places.configurations.contentConfig.ContentConfigActivity;
import com.materials.client.places.configurations.contentConfig.ContentConfigPlace;
import com.materials.client.places.configurations.menuConfig.MenuConfigActivity;
import com.materials.client.places.configurations.menuConfig.MenuConfigPlace;
import com.materials.client.places.configurations.rsociaux.RSociauxConfigActivity;
import com.materials.client.places.configurations.rsociaux.RSociauxConfigPlace;
import com.materials.client.places.configurations.siteConfig.WebSiteConfigActivity;
import com.materials.client.places.configurations.siteConfig.WebSiteConfigPlace;
import com.materials.client.places.configurations.sliderConfig.SliderConfigActivity;
import com.materials.client.places.configurations.sliderConfig.SliderConfigPlace;
import com.materials.client.places.configurations.stat.StatisticConfigActivity;
import com.materials.client.places.configurations.stat.StatisticConfigPlace;
import com.materials.client.places.configurations.userConfig.UserConfigActivity;
import com.materials.client.places.configurations.userConfig.UserConfigPlace;
import com.materials.client.places.content.ContentActivity;
import com.materials.client.places.content.ContentPlace;
import com.materials.client.places.menu.MenuActivity;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.places.mymboa.MyMboaActivity;
import com.materials.client.places.mymboa.MyMboaPlace;
import com.materials.client.places.search.SearchActivity;
import com.materials.client.places.search.SearchPlace;

public class AppActivityMapper implements ActivityMapper {

	private AppClientFactory clientFactory;

	public AppActivityMapper(AppClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {

		Activity activityToReturn = null;
		if (place instanceof MenuPlace) {
			activityToReturn = new MenuActivity(clientFactory, (MenuPlace) place);
		}
		if (place instanceof MyMboaPlace) {
			activityToReturn = new MyMboaActivity(clientFactory, (MyMboaPlace) place);
		}
		if (place instanceof ContentPlace) {
			activityToReturn = new ContentActivity(clientFactory, (ContentPlace) place);
		}
		if (place instanceof SearchPlace) {
			activityToReturn = new SearchActivity(clientFactory, (SearchPlace) place);
		}
		if (place instanceof ContentConfigPlace) {
			activityToReturn = new ContentConfigActivity(clientFactory, (ContentConfigPlace) place);
		}
		if (place instanceof MenuConfigPlace) {
			activityToReturn = new MenuConfigActivity(clientFactory, (MenuConfigPlace) place);
		}
		if (place instanceof UserConfigPlace) {
			activityToReturn = new UserConfigActivity(clientFactory, (UserConfigPlace) place);
		}
		if (place instanceof SliderConfigPlace) {
			activityToReturn = new SliderConfigActivity(clientFactory, (SliderConfigPlace) place);
		}
		if (place instanceof RSociauxConfigPlace) {
			activityToReturn = new RSociauxConfigActivity(clientFactory, (RSociauxConfigPlace) place);
		}
		if (place instanceof WebSiteConfigPlace) {
			activityToReturn = new WebSiteConfigActivity(clientFactory, (WebSiteConfigPlace) place);
		}
		if (place instanceof StatisticConfigPlace) {
			activityToReturn = new StatisticConfigActivity(clientFactory, (StatisticConfigPlace) place);
		}
		return activityToReturn;
	}
}
