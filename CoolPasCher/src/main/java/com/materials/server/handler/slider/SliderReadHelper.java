package com.materials.server.handler.slider;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.SliderSO;
import com.materials.server.model.SliderItem;
import com.materials.shared.action.slider.SliderCommand;
import com.materials.utils.APP_DB_Utils;

public class SliderReadHelper {

	public List<SliderSO> read(SliderCommand command, String key) {

		List<SliderSO> list = null;

		switch (command) {

		case HOME_SLIDER: {
			list = getSliders("SELECT s FROM SliderItem s WHERE s.active = '1'");
		}
			break;

		case ALL_SLIDER: {
			list = getSliders("SELECT s FROM SliderItem s");
		}
			break;
		}
		return list;

	}

	private List<SliderSO> getSliders(String query) {
		List<SliderSO> list = new ArrayList<>();
		List<SliderItem> sliderItems = APP_DB_Utils.queryListObjects(query, null, SliderItem.class);
		if (sliderItems != null) {
			sliderItems.forEach(x -> list.add(new SliderSO(x)));
		}
		return list;
	}

}
