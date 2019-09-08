package com.materials.client.model;

import java.util.Date;

import com.materials.client.CoolPasCherUI;
import com.materials.server.model.CArea;

public class CAreaSO extends APPObjectSO {

	public static final String TYPE_TEXT = "TEXT";
	public static final String TYPE_IMG = "IMG";
	public static final String TYPE_HOR = "HOR";
	public static final String TYPE_VER = "VER";
	public static final String TYPE_VIDEO = "VIDEO";
	public static final String TYPE_ANNONCE = "ANNONCE";

	private static final long serialVersionUID = 8373053319404030532L;
	private int index;
	private String areaType;
	private String titel;
	private String textContent;
	private String creator;
	private String randomId;
	private Date creationDate;
	private ContentSO contentSO;

	public CAreaSO() {
	}

	public CAreaSO(CArea area) {
		setId(area.getId());
		setAreaType(area.getAreaType());
		setCreationDate(area.getCreationDate());
		setCreator(area.getCreator());
		setLock(area.isLock());
		setRandomId(area.getRandomId());
		setTextContent(area.getTextContent());
		setTitel(area.getTitel());
		setIndex(area.getIndex());

	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getImageUrl() {
		String urlImg = "";
		if (getId() == -10) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + getRandomId();
		} else if (getId() == -15) {
			urlImg = getTextInfo();
		} else if (getRandomId() != null && !getRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?caRandomID=" + getRandomId();
		}
		return urlImg;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public ContentSO getContentSO() {
		return contentSO;
	}

	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
