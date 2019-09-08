package com.materials.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "SLIDER_ITEM")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class SliderItem extends APPObject {

	private static final long serialVersionUID = 5579522578713869246L;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "TITEL")
	private String titel;

	@Column(name = "TITEL2")
	private String titel2;

	@Column(name = "TITEL1")
	private String titel1;

	@Column(name = "TYPE", nullable = false)
	private String type;

	@Column(name = "FADE_TITEL")
	private String fadeTitel;

	@Column(name = "TITEL_COLOR", nullable = false)
	private String titelColor = "black";

	@Column(name = "IMAGE_DATA")
	@Lob
	private byte[] imageData;

	@Column(name = "S_INDEX")
	private int index;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = false;

	@Column(name = "PRESENTATION", nullable = false)
	private boolean presentation = false;

	@OneToMany(targetEntity = ByteData.class, mappedBy = "sliderItem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<ByteData> byteDatas = new ArrayList<ByteData>(8);

	@OneToOne(targetEntity = Content.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENT_ID")
	private Content content;

	public SliderItem() {
	}

	public SliderItem(String titel) {
		this.titel = titel;
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

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
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

	public String getFadeTitel() {
		return fadeTitel;
	}

	public void setFadeTitel(String fadeTitel) {
		this.fadeTitel = fadeTitel;
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

	public boolean isPresentation() {
		return presentation;
	}

	public void setPresentation(boolean presentation) {
		this.presentation = presentation;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public void addByteData(ByteData data) {
		byteDatas.add(data);
		data.setSliderItem(this);
	}

	public List<ByteData> getByteDatas() {
		return byteDatas;
	}

	public void setByteDatas(List<ByteData> byteDatas) {
		this.byteDatas = byteDatas;
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

}
