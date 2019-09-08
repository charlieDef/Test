package com.materials.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.materials.client.CoolPasCherUI;
import com.materials.server.model.ByteData;
import com.materials.server.model.SliderItem;

public class SliderSO extends APPObjectSO {

	private static final long serialVersionUID = -106241931658458356L;

	public static final String TYPE_SIMPLE_A = "TYPE_SIMPLE_A";
	public static final String TYPE_SIMPLE_B = "TYPE_SIMPLE_B";
	public static final String TYPE_SIMPLE_C = "TYPE_SIMPLE_C";
	public static final String TYPE_SIMPLE_D = "TYPE_SIMPLE_D";
	public static final String TYPE_SIMPLE_E = "TYPE_SIMPLE_E";
	public static final String TYPE_SIMPLE_F = "TYPE_SIMPLE_F";
	public static final String TYPE_SIMPLE_G = "TYPE_SIMPLE_G";
	public static final String TYPE_3CARD_A = "TYPE_3CARD_A";
	public static final String TYPE_3CARD_B = "TYPE_3CARD_B";
	public static final String TYPE_3CARD_C = "TYPE_3CARD_C";
	public static final String TYPE_2CARD_A = "TYPE_2CARD_A";
	public static final String TYPE_2CARD_B = "TYPE_2CARD_B";
	public static final String TYPE_1CARD_A = "TYPE_1CARD_A";
	public static final String TYPE_1CARD_B = "TYPE_1CARD_B";

	private int index;

	private Date creationDate;
	private String titel;
	private String titel2;
	private String titel1;
	private String titelColor;
	private String randomId;
	private String type;
	private boolean active = true;
	private boolean presentation = false;
	private boolean padding = false;
	private ContentSO contentSO;
	private List<ByteDataSO> byteDataSOs = new ArrayList<ByteDataSO>(8);

	public SliderSO() {
	}

	public SliderSO(long id) {
		setId(id);
	}

	public SliderSO(String titel, String titel2) {
		this.titel = titel;
		this.titel2 = titel2;
	}

	public SliderSO(String titel, String titel2, String titel1) {
		this.titel = titel;
		this.titel2 = titel2;
		this.titel1 = titel1;

	}

	public SliderSO(SliderItem slider) {
		setLock(slider.isLock());
		setActive(slider.isActive());
		setCreationDate(slider.getCreationDate());
		setPresentation(slider.isPresentation());
		setId(slider.getId());
		setIndex(slider.getIndex());
		setRandomId(slider.getRandomId());
		setTitel(slider.getTitel());
		setTitel1(slider.getTitel1());
		setTitel2(slider.getTitel2());
		setTextInfo(slider.getTextInfo());
		setType(slider.getType());
		setTitelColor(slider.getTitelColor());

		if (slider.getContent() != null) {
			setContentSO(new ContentSO(slider.getContent()));
		}

		if (slider.getByteDatas() != null && !slider.getByteDatas().isEmpty()) {
			for (ByteData byteData : slider.getByteDatas()) {
				ByteDataSO sMenuSo = new ByteDataSO(byteData);
				sMenuSo.setSliderSO(this);
				byteDataSOs.add(sMenuSo);
			}
		}

	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

	public String getTitel2() {
		return titel2;
	}

	public void setTitel2(String titel2) {
		this.titel2 = titel2;
	}

	public String getTitel1() {
		return titel1;
	}

	public void setTitel1(String titel1) {
		this.titel1 = titel1;
	}

	public String getSliderImageUrl() {
		String urlImg = "";
		if (getId() <= -10) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + getRandomId();
		} else if (getRandomId() != null && !getRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?sliderID=" + getRandomId();
		}
		return urlImg;

	}

	public boolean isPresentation() {
		return presentation;
	}

	public void setPresentation(boolean presentation) {
		this.presentation = presentation;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;
	}

	public ContentSO getContentSO() {
		return contentSO;
	}

	public List<ByteDataSO> getByteDataSOs() {
		return byteDataSOs;
	}

	public void setByteDataSOs(List<ByteDataSO> byteDataSOs) {
		this.byteDataSOs = byteDataSOs;
	}

	public ByteDataSO getByteData(int index) {

		ByteDataSO notExist = new ByteDataSO();
		notExist.setId(-20);
		notExist.setPadding(false);

		ByteDataSO dataSO = byteDataSOs.stream().filter(x -> x.getIndex() == index).findFirst().orElse(notExist);

		return dataSO;

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitelColor() {
		return titelColor;
	}

	public void setTitelColor(String titelColor) {
		this.titelColor = titelColor;
	}

	public boolean isPadding() {
		return padding;
	}

	public void setPadding(boolean padding) {
		this.padding = padding;
	}
}
