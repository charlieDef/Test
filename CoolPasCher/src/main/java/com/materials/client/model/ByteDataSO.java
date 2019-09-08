package com.materials.client.model;

import com.materials.client.CoolPasCherUI;
import com.materials.server.model.ByteData;

public class ByteDataSO extends APPObjectSO {

	private static final long serialVersionUID = 1964650187051176954L;

	private int index;

	private String randomId;

	private String height;
	private String width;
	private boolean padding;
	private SliderSO sliderSO;

	public ByteDataSO() {
	}

	public ByteDataSO(ByteData byteData) {
		setId(byteData.getId());
		setRandomId(byteData.getRandomId());
		setIndex(byteData.getIndex());
		setHeight(byteData.getHeight());
		setWidth(byteData.getWidth());
		setPadding(byteData.isPadding());

	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public String getImageUrl() {
		String urlImg = "";
		if (getId() <= -20) {
			urlImg = "img/noImage.jpg";
		} else if (getId() <= -10) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + getRandomId();
		} else if (getRandomId() != null && !getRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?bdRandomID=" + getRandomId();
		}
		return urlImg;
	}

	public SliderSO getSliderSO() {
		return sliderSO;
	}

	public void setSliderSO(SliderSO sliderSO) {
		this.sliderSO = sliderSO;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isPadding() {
		return padding;
	}

	public void setPadding(boolean padding) {
		this.padding = padding;
	}

}
