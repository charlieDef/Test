package com.materials.client.model;

import com.google.gwt.core.shared.GwtIncompatible;
import com.materials.client.CoolPasCherUI;
import com.materials.server.model.WebSite;
import com.materials.utils.APPUtils;

public class WebSiteSO extends APPObjectSO {

	private static final long serialVersionUID = 686780889230245406L;

	private String name;
	private String shortName;
	private String longName;
	private String description;
	private String email;
	private String ezuw;
	private String tel;
	private String adresse;
	private String randomId;
	private String cguRandomId;
	private String copyRightText;
	private String emailAdmin;
	private String emailPresident;
	private String mailSendSuccessText;

	protected WebSiteSO() {
	}

	@GwtIncompatible
	public WebSiteSO(WebSite webSite) {
		setAdresse(webSite.getAdresse());
		setCopyRightText(webSite.getCopyRightText());
		setDescription(webSite.getDescription());
		setEmail(webSite.getEmail());
		setEmailAdmin(webSite.getEmailAdmin());
		setEmailPresident(webSite.getEmailPresident());

		setName(webSite.getName());
		// setEzuw(ezuw);
		setId(webSite.getId());
		setLock(webSite.isLock());
		setLongName(webSite.getLongName());
		setMailSendSuccessText(webSite.getMailSendSuccessText());
		setShortName(webSite.getShortName());
		setRandomId(webSite.getRandomId());
		setCguRandomId(webSite.getCguRandomId());
		setTel(webSite.getTel());

		if (webSite.getEzuw() != null && !webSite.getEzuw().isEmpty()) {
			setEzuw(APPUtils.getDecriptedPwd(webSite.getEzuw()));
		}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		String urlImg = "img/siteLogo.png";
		if (getRandomId() != null && !getRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?siteID=" + getRandomId();
		}
		return urlImg;
	}

	public String getCguRandomId() {
		return cguRandomId;
	}

	public void setCguRandomId(String cguRandomId) {
		this.cguRandomId = cguRandomId;
	}

	public String getCGUUrl() {
		String cguUrl = "";
		if (getCguRandomId() != null && !getCguRandomId().isEmpty()) {
			cguUrl = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?cguID=" + getCguRandomId();
		}
		return cguUrl;
	}

	@Override
	public String toString() {
		return "" + getId();
	}

}
