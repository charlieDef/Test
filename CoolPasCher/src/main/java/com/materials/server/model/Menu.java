package com.materials.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.materials.server.model.template.MenuTemplate;

@Entity
@Table(name = "MENU")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class Menu extends APPObject {

	private static final long serialVersionUID = -5093001893548939779L;

	@Column(name = "M_INDEX")
	private int index;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = false;

	@Column(name = "MENU_CATEGORY", nullable = false)
	private boolean menuCategory = false;

	@Column(name = "MEMBER_ONLY", nullable = false)
	private boolean memberOnly = false;

	@Column(name = "HAS_CONTENTS", nullable = false)
	private boolean hasContents = false;

	@Column(name = "DD_ALIGN_RIGHT", nullable = false)
	private boolean dropDownAlignRight = false;;

	@ManyToOne(targetEntity = Menu.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_MENU_ID")
	private Menu parentMenu;

	@OneToMany(targetEntity = Menu.class, mappedBy = "parentMenu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Menu> subMenus = new ArrayList<Menu>(0);

	@OneToMany(targetEntity = Content.class, mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Content> contents = new ArrayList<Content>(0);

	@OneToMany(targetEntity = MenuTemplate.class, mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<MenuTemplate> templates = new ArrayList<MenuTemplate>(0);

	public Menu() {
	}

	public Menu(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public void addSubMenu(Menu menu) {
		menu.setParentMenu(this);
		getSubMenus().add(menu);
	}

	public void removeSubMenu(Menu menu) {
		getSubMenus().remove(menu);
		menu.setParentMenu(null);
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void addContent(Content content) {
		getContents().add(content);
		content.setMenu(this);
	}

	public void removeContent(Content content) {
		getContents().remove(content);
	}

	public String getDescription() {
		return description;
	}

	public boolean isHasContents() {
		return hasContents;
	}

	public void setHasContents(boolean hasContents) {
		this.hasContents = hasContents;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MenuTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<MenuTemplate> templates) {
		this.templates = templates;
	}

	public void addMenuTemplate(MenuTemplate template) {
		getTemplates().add(template);
		template.setMenu(this);
	}

	public void removeMenuTemplate(MenuTemplate template) {
		getTemplates().remove(template);
		template.setMenu(null);
	}

	public boolean isDropDownAlignRight() {
		return dropDownAlignRight;
	}

	public void setDropDownAlignRight(boolean dropDownAlignRight) {
		this.dropDownAlignRight = dropDownAlignRight;
	}

	public boolean isMemberOnly() {
		return memberOnly;
	}

	public void setMemberOnly(boolean memberOnly) {
		this.memberOnly = memberOnly;
	}

	public boolean isMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(boolean menuCategory) {
		this.menuCategory = menuCategory;
	}
}
