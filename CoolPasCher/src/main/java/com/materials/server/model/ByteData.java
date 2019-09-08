package com.materials.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "BYTE_DATA")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class ByteData extends APPObject {

	private static final long serialVersionUID = 3327336752180083025L;

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "D_INDEX")
	private int index;

	@Column(name = "WIDTH")
	private String width;

	@Column(name = "HEIGHT")
	private String height;

	@Column(name = "PADDING", nullable = false)
	private boolean padding = false;

	@Column(name = "IMAGE_DATA")
	@Lob
	private byte[] imageData;

	@ManyToOne(targetEntity = SliderItem.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "SLIDER_ID")
	private SliderItem sliderItem;

	public SliderItem getSliderItem() {
		return sliderItem;
	}

	public void setSliderItem(SliderItem sliderItem) {
		this.sliderItem = sliderItem;
		sliderItem.getByteDatas().add(this);
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
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
