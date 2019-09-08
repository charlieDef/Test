package com.materials.server.handler.carea;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.CAreaSO;
import com.materials.server.model.CArea;
import com.materials.server.model.Content;
import com.materials.server.model.TempFile;
import com.materials.utils.APP_DB_Utils;

public class CAreaUpdateHelper {

	public APPObjectSO update(APPObjectSO appObjectSO) {

		CAreaSO cAreaSO = (CAreaSO) appObjectSO;
		CArea cArea = toCArea(cAreaSO, false);
		if (cArea != null) {
			APP_DB_Utils.saveObjectToDatabase(cArea);
		}
		return cAreaSO;
	}

	public APPObjectSO save(APPObjectSO appObjectSO) {
		CAreaSO cAreaSO = (CAreaSO) appObjectSO;
		CArea newCArea = toCArea(cAreaSO, true);
		Content content = APP_DB_Utils.findObject(cAreaSO.getContentSO().getId(), Content.class);
		if (content != null) {
			APP_DB_Utils.saveObjectToDatabase(newCArea);
			content.addContentArea(newCArea);
			APP_DB_Utils.saveObjectToDatabase(content);
		} else {
			APP_DB_Utils.saveObjectToDatabase(newCArea);
		}
		cAreaSO.setId(newCArea.getId());
		return cAreaSO;
	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {

		appObjectSOs.forEach(appObjectSO -> {
			CAreaSO cAreaSO = (CAreaSO) appObjectSO;
			APP_DB_Utils.deleteObjectFromDatabase(cAreaSO.getId(), CArea.class);
		});
		return true;
	}

	public static CArea toCArea(CAreaSO cAreaSO, boolean isNew) {
		CArea cArea = isNew ? new CArea() : APP_DB_Utils.findObject(cAreaSO.getId(), CArea.class);
		if (cArea != null) {
			cArea.setTitel(cAreaSO.getTitel());
			cArea.setAreaType(cAreaSO.getAreaType());
			cArea.setCreator(cAreaSO.getCreator());
			cArea.setIndex(cAreaSO.getIndex());
			cArea.setTextContent(cAreaSO.getTextContent());
			cArea.setCreationDate(cAreaSO.getCreationDate());
			cArea.setRandomId(cAreaSO.getRandomId());
			cArea.setLock(cAreaSO.isLock());
			// TODO be carefull here
			if (cAreaSO.getRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(cAreaSO.getRandomId());
				if (tempFile != null) {
					cArea.setImageData(tempFile.getTempData());
					cArea.setAreaType(isHorVer(cAreaSO.getAreaType()) ? cAreaSO.getAreaType() : tempFile.getDataType());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}
		}

		return cArea;
	}

	private static boolean isHorVer(String type) {
		boolean result = false;
		if (type != null) {
			result = type.equals("HOR") || type.equals("VER");
		}
		return result;
	}

}
