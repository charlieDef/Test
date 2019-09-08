package com.materials.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "RSOCIAL")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class RSocial extends APPObject {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "TARGET_URL")
	private String targetUrl;

	@Column(name = "HTML_IMG")
	private String htmlImg;

	@Column(name = "TOOLTIP")
	private String tooltip;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = false;

	public RSocial() {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	//
	// public long getId() {
	// return id;
	// }

	@Override
	public String toString() {
		return getId() + "|" + getTargetUrl();
	}
}
