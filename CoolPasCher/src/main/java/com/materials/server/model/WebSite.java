package com.materials.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "WEB_SITE")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class WebSite extends APPObject {

	private static final long serialVersionUID = 2654694477786789447L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SHORT_NAME")
	private String shortName;

	@Column(name = "LONG_NAME")
	private String longName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ZUWU")
	private String ezuw;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "ADRESSE")
	private String adresse;

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "CGU_RANDOM_ID")
	private String cguRandomId;

	@Column(name = "COPY_RIGHT_TEXT")
	private String copyRightText;

	@Column(name = "EMAIL_ADMIN")
	private String emailAdmin;

	@Column(name = "EMAIL_PRESIDENT")
	private String emailPresident;

	@Column(name = "MAIL_SEND_SUCCESS_TEXT")
	private String mailSendSuccessText;

	@Column(name = "IMAGE_DATA")
	@Lob
	private byte[] imageData;

	@Column(name = "CGU_DATA")
	@Lob
	private byte[] cguData;

	public WebSite() {
	}

	public WebSite(String shortName, String longName) {
		this.shortName = shortName;
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEzuw() {
		return ezuw;
	}

	public void setEzuw(String ezuw) {
		this.ezuw = ezuw;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public String getCguRandomId() {
		return cguRandomId;
	}

	public void setCguRandomId(String cguRandomId) {
		this.cguRandomId = cguRandomId;
	}

	public byte[] getCguData() {
		return cguData;
	}

	public void setCguData(byte[] cguData) {
		this.cguData = cguData;
	}

	public String getCopyRightText() {
		return copyRightText;
	}

	public void setCopyRightText(String copyRightText) {
		this.copyRightText = copyRightText;
	}

	public String getEmailAdmin() {
		return emailAdmin;
	}

	public void setEmailAdmin(String emailAdmin) {
		this.emailAdmin = emailAdmin;
	}

	public String getEmailPresident() {
		return emailPresident;
	}

	public void setEmailPresident(String emailPresident) {
		this.emailPresident = emailPresident;
	}

	public String getMailSendSuccessText() {
		return mailSendSuccessText;
	}

	public void setMailSendSuccessText(String mailSendSuccessText) {
		this.mailSendSuccessText = mailSendSuccessText;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
