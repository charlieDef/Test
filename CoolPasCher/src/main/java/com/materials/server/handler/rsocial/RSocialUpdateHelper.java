package com.materials.server.handler.rsocial;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.RSocialSO;
import com.materials.server.model.RSocial;
import com.materials.utils.APP_DB_Utils;

public class RSocialUpdateHelper {

	public APPObjectSO update(APPObjectSO appObjectSO) {

		RSocialSO sliderSO = (RSocialSO) appObjectSO;
		RSocial rSocial = toRSocial(sliderSO, false);
		if (rSocial != null) {
			APP_DB_Utils.saveObjectToDatabase(rSocial);
		}
		return sliderSO;
	}

	public APPObjectSO save(APPObjectSO appObjectSO) {

		RSocialSO rSocialSO = (RSocialSO) appObjectSO;
		RSocial newRSocial = toRSocial(rSocialSO, true);
		APP_DB_Utils.saveObjectToDatabase(newRSocial);
		rSocialSO.setId(newRSocial.getId());

		return rSocialSO;
	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {
		appObjectSOs.forEach(appObjectSO -> {
			RSocialSO rSocialSO = (RSocialSO) appObjectSO;
			APP_DB_Utils.deleteObjectFromDatabase(rSocialSO.getId(), RSocial.class);
		});
		return true;
	}

	private RSocial toRSocial(RSocialSO rSliderSO, boolean isNew) {
		RSocial rSlider = isNew ? new RSocial() : APP_DB_Utils.findObject(rSliderSO.getId(), RSocial.class);
		if (rSlider != null) {
			rSlider.setActive(rSliderSO.isActif());
			rSlider.setLock(rSliderSO.isLock());
			rSlider.setHtmlImg(rSliderSO.getHtmlImg());
			rSlider.setName(rSliderSO.getName());
			rSlider.setTooltip(rSliderSO.getTooltip());
			rSlider.setTargetUrl(rSliderSO.getTargetUrl());
		}

		return rSlider;
	}

}
