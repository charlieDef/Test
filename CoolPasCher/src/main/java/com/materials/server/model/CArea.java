package com.materials.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "C_AREA")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class CArea extends APPObject {

	private static final long serialVersionUID = 1851369709484248463L;

	@Column(name = "AREA_TYPE")
	private String areaType;

	@Column(name = "TITEL")
	private String titel;

	@Column(name = "AREA_INDEX")
	private int index;

	@Column(name = "TEXT_CONTENT", length = 600000)
	@Lob
	private String textContent;

	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "IMAGE_DATA")
	@Lob
	private byte[] imageData;

	@ManyToOne(targetEntity = Content.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENT_ID")
	private Content content;

	public CArea() {
	}

	public CArea(String titel) {
		this.titel = titel;
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

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
		content.getContentAreas().add(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
