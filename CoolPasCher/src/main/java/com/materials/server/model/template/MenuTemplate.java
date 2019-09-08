package com.materials.server.model.template;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.materials.server.model.APPObject;
import com.materials.server.model.Menu;

@Entity
@Table(name = "MTEMPLATE")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class MenuTemplate extends APPObject {

	private static final long serialVersionUID = -8735315457087893427L;

	@Column(name = "T_INDEX")
	private int index;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "NAME")
	private String name;

	@Column(name = "LEFTALIGN", nullable = false)
	private boolean leftAlign = false;

	@Column(name = "BUTTONSTYLE", nullable = false)
	private boolean buttonStyle = false;

	@Column(name = "COLNR")
	private int colNr;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = false;

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "IMAGE_DATA")
	@Lob
	private byte[] imageData;

	@ManyToOne(targetEntity = Menu.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_ID")
	private Menu menu;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "MTEMPLATE_LINKS", joinColumns = @JoinColumn(name = "MTEMPLATE_ID"))
	@MapKeyColumn(name = "INDEX_KEY", length = 128)
	@OrderBy(value = "INDEX_KEY")
	@Column(name = "LINK_VALUE", length = 2048)
	private Map<Integer, String> templateLinks;

	public MenuTemplate() {
		templateLinks = new HashMap<Integer, String>();
	}

	public boolean isLeftAlign() {
		return leftAlign;
	}

	public void setLeftAlign(boolean leftAlign) {
		this.leftAlign = leftAlign;
	}

	public boolean isButtonStyle() {
		return buttonStyle;
	}

	public void setButtonStyle(boolean buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public void addTemplateLink(int index, String link) {
		templateLinks.put(index, link);
	}

	public String getTemplateLink(int index) {
		return templateLinks.get(index);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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

	public int getColNr() {
		return colNr;
	}

	public void setColNr(int colNr) {
		this.colNr = colNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTemplateLinks(Map<Integer, String> templateLinks) {
		this.templateLinks = templateLinks;
	}

	public Map<Integer, String> getTemplateLinks() {
		return templateLinks;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
