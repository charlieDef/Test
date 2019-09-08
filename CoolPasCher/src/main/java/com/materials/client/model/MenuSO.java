package com.materials.client.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.materials.server.model.Content;
import com.materials.server.model.Menu;
import com.materials.server.model.template.MenuTemplate;

public class MenuSO extends APPObjectSO {

	private static final long serialVersionUID = 1L;

	public static String TYPE_BASE_MENU = "BaseMenu";
	public static String TYPE_SUBMENU = "SubMenu";

	private int index;
	private String name;
	private boolean active;
	private boolean hasContents = false;
	private boolean memberOnly = false;
	private boolean dropDownAlignRight = false;;
	private String category;
	private String type;
	private boolean menuCategory = false;
	private String description;
	private MenuSO parentMenuSo;
	private List<MenuSO> subMenuSos = new ArrayList<>(0);
	private List<ContentSO> contents = new ArrayList<ContentSO>(0);
	private List<MenuTemplateSO> menuTemplates = new ArrayList<MenuTemplateSO>(0);

	private int menuLevel;

	public MenuSO() {
		super();
	}

	public MenuSO(long id) {
		setId(id);
	}

	public MenuSO(String name) {
		this.name = name;
	}

	public MenuSO(Menu menu) {
		initAttributes(menu);

		if (!menu.getSubMenus().isEmpty()) {
			for (Menu subMenu : menu.getSubMenus()) {
				MenuSO sMenuSo = new MenuSO(subMenu);
				sMenuSo.setParentMenuSo(this);
				subMenuSos.add(sMenuSo);
			}
		}

		initContentaAttributes(menu);

		initTemplateAttribute(menu);

	}

	private void initContentaAttributes(Menu menu) {
		if (!menu.getContents().isEmpty()) {
			for (Content content : menu.getContents()) {
				ContentSO contentSO = new ContentSO(content);
				contentSO.setMenuSO(this);
				contents.add(contentSO);
			}
		}
	}

	private void initTemplateAttribute(Menu menu) {
		if (!menu.getTemplates().isEmpty()) {
			for (MenuTemplate template : menu.getTemplates()) {
				MenuTemplateSO templateSO = new MenuTemplateSO(template);
				templateSO.setMenuSO(this);
				menuTemplates.add(templateSO);
			}
			menuTemplates.sort(new Comparator<MenuTemplateSO>() {
				@Override
				public int compare(MenuTemplateSO o1, MenuTemplateSO o2) {
					return Integer.valueOf(o1.getIndex()).compareTo(Integer.valueOf(o2.getIndex()));
				}
			});
		}
	}

	private void initAttributes(Menu menu) {
		setId(menu.getId());
		setName(menu.getName());
		setCategorie(menu.getCategory());
		setIndex(menu.getIndex());
		setLock(menu.isLock());
		setActive(menu.isActive());
		setMemberOnly(menu.isMemberOnly());
		setDescription(menu.getDescription());
		setTextInfo(menu.getTextInfo());
		setHasContents(menu.isHasContents());
		setType(menu.getType());
		setMenuCategory(menu.isMenuCategory());
		setDropDownAlignRight(menu.isDropDownAlignRight());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContentSO> getContents() {
		return contents;
	}

	public void addContent(ContentSO contentSO) {
		contents.add(contentSO);
		contentSO.setMenuSO(this);
	}

	public void setContents(List<ContentSO> contents) {
		this.contents = contents;
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

	public void setCategorie(String categorie) {
		this.category = categorie;
	}

	public List<MenuSO> getSubMenuSos() {
		return subMenuSos;
	}

	public void setSubMenuSos(List<MenuSO> subMenuSos) {
		this.subMenuSos = subMenuSos;
	}

	public MenuSO getParentMenuSo() {
		return parentMenuSo;
	}

	public void setParentMenuSo(MenuSO parentMenuSo) {
		this.parentMenuSo = parentMenuSo;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addSubMenuSo(MenuSO menuSO) {
		subMenuSos.add(menuSO);
		menuSO.setParentMenuSo(this);
	}

	public boolean isHasContents() {
		return hasContents;
	}

	public void setHasContents(boolean hasContents) {
		this.hasContents = hasContents;
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MenuTemplateSO> getMenuTemplates() {
		return menuTemplates;
	}

	public void setMenuTemplates(List<MenuTemplateSO> menuTemplates) {
		this.menuTemplates = menuTemplates;
	}

	public boolean isBaseMenu() {
		return getType().equals("BaseMenu");

	}

	public int getTemplateColNr() {
		int nr = 0;
		if (!getMenuTemplates().isEmpty()) {
			nr = getMenuTemplates().get(0).getColNr();
		}
		return nr;
	}

	public boolean isMultiContents() {
		return (category != null && (!category.equals("Normal_Single")));
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

	public int getMenuLevel() {
		int level = 0;
		MenuSO parent = this.parentMenuSo;
		while (parent != null) {
			parent = parent.getParentMenuSo();
			level++;
		}
		return level;
	}

	public boolean hasSubMenus() {
		return getSubMenuSos() != null && !getSubMenuSos().isEmpty();
	}

	// Configuration area
	public static final String GESTION_CONTENTS = "Gestion articles";
	public static final String GESTION_USERS = "Gestion users";
	public static final String GESTION_SLIDERS = "Gestion slider";
	public static final String GESTION_FICHIERS = "Gestion fichiers";
	public static final String GESTION_MENUS = "Gestion menus";
	public static final String MONITORING = "Monitoring";

	public static final String ACCEUIL = "home";
	public static final String FAVORITS = "Mes Favorits";
	public static final String MES_ANNONCES = "Mes Annonces";
	public static final String NEW_ANNONCE = "nouveau";
	public static final String LECTURES = "mes lectures";
	public static final String HEROS_DAFRIQUE = "heros d'afrique";
	public static final String MES_MENTORES = "mes mentors";
	// public static final String CITATIONS = "citations inspirantes";
	public static final String CONTACT = "Contact";
	public static final String STAT = "stat";
	public static final String A_PROPOS = "A propos";
	public static final String CONFIGURATIONS = "Configurations";
	public static final String LOGIN = "Login";
	public static final String LOGOUT = "Logout";
	public static final String SETTINGS = "Settings";
	public static final String CONNECTION = "Connection";
	public static final String REGISTER = "Enregistrement";
	public static final String CONFIGURER = "Configurer";

	public static final String SEARCH = "search";

}
