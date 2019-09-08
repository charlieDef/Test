package com.materials.client.model;

import java.util.HashMap;
import java.util.Map;

import com.materials.client.CoolPasCherUI;
import com.materials.server.model.template.MenuTemplate;

public class MenuTemplateSO extends APPObjectSO {

	private static final long serialVersionUID = -5938505345769058946L;

	private int index;
	private String category;
	private String name;
	private boolean leftAlign, buttonStyle;
	private int colNr;
	private boolean active;
	private String randomId;
	private MenuSO menuSO;

	private Map<Integer, String> templateLinks = new HashMap<Integer, String>();

	public MenuTemplateSO() {
		super();
	}

	public MenuTemplateSO(MenuTemplate menuTemplate) {
		setId(menuTemplate.getId());
		setCategory(menuTemplate.getCategory());
		setColNr(menuTemplate.getColNr());
		setLeftAlign(menuTemplate.isLeftAlign());
		setButtonStyle(menuTemplate.isButtonStyle());
		setLock(menuTemplate.isLock());
		setName(menuTemplate.getName());
		setRandomId(menuTemplate.getRandomId());
		setActive(menuTemplate.isActive());
		setIndex(menuTemplate.getIndex());
		getTemplateLinks().putAll(menuTemplate.getTemplateLinks());
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getColNr() {
		return colNr;
	}

	public void setColNr(int colNr) {
		this.colNr = colNr;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Map<Integer, String> getTemplateLinks() {
		return templateLinks;
	}

	public void setTemplateLinks(Map<Integer, String> templateLinks) {
		this.templateLinks = templateLinks;
	}

	public MenuSO getMenuSO() {
		return menuSO;
	}

	public void setMenuSO(MenuSO menuSO) {
		this.menuSO = menuSO;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getImgUrl() {
		String urlImg = "img/subMenu.jpg";
		if (getRandomId() != null && !getRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?mtRandomID=" + getRandomId();
		}
		return urlImg;
		
	}

	public boolean isLeftAlign() {
		return leftAlign;
	}

	public void setLeftAlign(boolean leftAlign) {
		this.leftAlign = leftAlign;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isButtonStyle() {
		return buttonStyle;
	}

	public void setButtonStyle(boolean buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

}
