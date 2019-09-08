package com.materials.server.handler.slider;

import java.util.Date;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.ByteDataSO;
import com.materials.client.model.SliderSO;
import com.materials.server.model.ByteData;
import com.materials.server.model.Content;
import com.materials.server.model.SliderItem;
import com.materials.server.model.TempFile;
import com.materials.utils.APP_DB_Utils;

public class SliderUpdateHelper {

	public APPObjectSO update(APPObjectSO appObjectSO) {

		if (appObjectSO instanceof ByteDataSO) {

			ByteDataSO byteDataSO = (ByteDataSO) appObjectSO;
			ByteData byteData = toByteData(byteDataSO, false);
			APP_DB_Utils.saveObjectToDatabase(byteData);

			return byteDataSO;

		} else {

			SliderSO sliderSO = (SliderSO) appObjectSO;
			SliderItem sliderItem = toSlider(sliderSO, false);
			if (sliderItem != null) {
				APP_DB_Utils.saveObjectToDatabase(sliderItem);
			}

			// updateByteDatas(sliderItem, sliderSO);

			return sliderSO;
		}
	}

	public APPObjectSO save(APPObjectSO appObjectSO) {

		if (appObjectSO instanceof ByteDataSO) {

			ByteDataSO byteDataSO = (ByteDataSO) appObjectSO;

			ByteData byteData = toByteData(byteDataSO, true);
			APP_DB_Utils.saveObjectToDatabase(byteData);

			SliderItem sliderItem = toSlider(byteDataSO.getSliderSO(), false);
			sliderItem.addByteData(byteData);
			APP_DB_Utils.saveObjectToDatabase(sliderItem);

			return byteDataSO;

		} else {
			SliderSO sliderSO = (SliderSO) appObjectSO;
			SliderItem newSliderItem = toSlider(sliderSO, true);
			APP_DB_Utils.saveObjectToDatabase(newSliderItem);
			sliderSO.setId(newSliderItem.getId());

			Content newContent = getNewContent(newSliderItem);
			APP_DB_Utils.saveObjectToDatabase(newContent);

			newSliderItem.setContent(newContent);
			APP_DB_Utils.saveObjectToDatabase(newSliderItem);

			// updateByteDatas(newSliderItem, sliderSO);
			return sliderSO;
		}

	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {
		appObjectSOs.forEach(appObjectSO -> {

			if (appObjectSO instanceof ByteDataSO) {
				ByteDataSO byteDataSO = (ByteDataSO) appObjectSO;
				ByteData byteData = toByteData(byteDataSO, false);

				SliderItem newSliderItem = toSlider(byteDataSO.getSliderSO(), false);
				newSliderItem.getByteDatas().remove(byteData);
				APP_DB_Utils.saveObjectToDatabase(newSliderItem);

				APP_DB_Utils.deleteObjectFromDatabase(byteData.getId(), ByteData.class);
			} else {
				SliderSO menuSO = (SliderSO) appObjectSO;
				APP_DB_Utils.deleteObjectFromDatabase(menuSO.getId(), SliderItem.class);
			}

		});
		return true;
	}

	private SliderItem toSlider(SliderSO sliderSO, boolean isNew) {
		SliderItem sliderItem = isNew ? new SliderItem() : APP_DB_Utils.findObject(sliderSO.getId(), SliderItem.class);
		if (sliderItem != null) {
			sliderItem.setActive(sliderSO.isActive());
			sliderItem.setLock(sliderSO.isLock());
			sliderItem.setCreationDate(sliderSO.getCreationDate());
			sliderItem.setRandomId(sliderSO.getRandomId());
			sliderItem.setPresentation(sliderSO.isPresentation());
			sliderItem.setTitel(sliderSO.getTitel());
			sliderItem.setTitel1(sliderSO.getTitel1());
			sliderItem.setTitel2(sliderSO.getTitel2());
			sliderItem.setTextInfo(sliderSO.getTextInfo());
			sliderItem.setIndex(sliderSO.getIndex());
			sliderItem.setType(sliderSO.getType());
			sliderItem.setTitelColor(sliderSO.getTitelColor());

			// TODO be carefull here --> Image set
			if (sliderSO.getRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(sliderSO.getRandomId());
				if (tempFile != null) {
					sliderItem.setImageData(tempFile.getTempData());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}

		}

		return sliderItem;
	}

	private ByteData toByteData(ByteDataSO byteDataSO, boolean isNew) {
		ByteData byteData = isNew ? new ByteData() : APP_DB_Utils.findObject(byteDataSO.getId(), ByteData.class);
		if (byteData != null) {
			byteData.setIndex(byteDataSO.getIndex());
			byteData.setHeight(byteDataSO.getHeight());
			byteData.setWidth(byteDataSO.getWidth());
			byteData.setPadding(byteDataSO.isPadding());

			// TODO be carefull here --> Image set
			if (byteDataSO.getRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(byteDataSO.getRandomId());
				if (tempFile != null) {
					byteData.setImageData(tempFile.getTempData());
					byteData.setRandomId(byteDataSO.getRandomId());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}

		}

		return byteData;
	}

	private Content getNewContent(SliderItem item) {
		String titel = "Contenu pour le Slider_" + item.getId();
		Content newContent = new Content(titel);
		newContent.setActive(true);
		newContent.setLock(false);
		newContent.setShowToHome(false);
		newContent.setCategory("SliderContent");
		newContent.setType("SLIDER_CONTENT");
		newContent.setCreationDate(new Date());
		newContent.setDescription(titel);
		newContent.setDescription2(titel);
		newContent.setViewAble(1);
		newContent.setViewed(0);
		return newContent;
	}

	private void updateByteDatas(SliderItem sliderItem, SliderSO sliderSO) {
		if (sliderSO.isPresentation() && !sliderSO.getByteDataSOs().isEmpty()) {
			// check ByteDataSO
			for (ByteDataSO byteDataSO : sliderSO.getByteDataSOs()) {
				ByteData byteData = toByteData(byteDataSO, byteDataSO.getId() <= -10);
				sliderItem.addByteData(byteData);
				APP_DB_Utils.saveObjectToDatabase(byteData);
				APP_DB_Utils.saveObjectToDatabase(sliderItem);
				byteDataSO.setId(byteData.getId());
			}
		}

	}

}
