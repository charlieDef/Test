package com.materials.client.model;

import com.materials.server.model.RSocial;

public class RSocialSO extends APPObjectSO {

	private static final long serialVersionUID = 1L;

	private String name;
	private String targetUrl;
	private String htmlImg;
	private String tooltip;
	private boolean actif;

	public RSocialSO() {
	}

	public RSocialSO(RSocial rSocial) {
		setId(rSocial.getId());
		setName(rSocial.getName());
		setHtmlImg(rSocial.getHtmlImg());
		setLock(rSocial.isLock());
		setTargetUrl(rSocial.getTargetUrl());
		setTooltip(rSocial.getTooltip());
		setActif(rSocial.isActive());
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getHtmlImg() {
		return htmlImg;
	}

	public void setHtmlImg(String htmlImg) {
		this.htmlImg = htmlImg;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	@Override
	public String toString() {
		return getId() + "|" + getTargetUrl();
	}
}
