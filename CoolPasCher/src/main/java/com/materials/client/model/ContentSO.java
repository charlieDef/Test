package com.materials.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.materials.client.CoolPasCherUI;
import com.materials.server.model.CArea;
import com.materials.server.model.Comment;
import com.materials.server.model.Content;
import com.materials.server.model.Menu;

public class ContentSO extends APPObjectSO {

	private static final long serialVersionUID = 1L;

	public static final String PP_CREATOR_NAME = "pcName";
	public static final String PP_CREATOR_LASTNAME = "pcLastname";
	public static final String PP_CREATOR_EMAIL = "pcEmail";
	public static final String PP_CREATOR_TEXT = "pcText";
	public static final String PP_CREATOR_TEL = "pcTel";
	public static final String PP_CREATOR_COUNTRY = "pcCountry";
	public static final String PP_CREATOR_CITY = "pcCity";
	public static final String PP_CREATOR_TYPE = "pcType";
	public static final String PP_CREATOR_RANDOM_ID = "pcRandomId";

	public static final String TYPE_IMAGE = "PHOTO_CONTENT";
	public static final String TYPE_DOCUMENT = "DOCUMENT_CONTENT";
	public static final String TYPE_VIDEO = "VIDEO_CONTENT";
	public static final String TYPE_ANNONCE = "ANNONCE_CONTENT";
	public static final String TYPE_MENU = "MENU_CONTENT";
	public static final String TYPE_ARTICLE = "ARTICLE_CONTENT";
	public static final String TYPE_SLIDER = "SLIDER_CONTENT";
	public static final String TYPE_H_ADVERTISEMENT = "H_ADVERTISEMENT_CONTENT";
	public static final String TYPE_V_ADVERTISEMENT = "V_ADVERTISEMENT_CONTENT";
	public static final String TYPE_FLEX = "FLEX_CONTENT";

	private String titel;
	private String type;
	private String category;
	private String description, province, ville, quartier, prix;
	private String description2, longDescription, localite;
	private boolean active, intern, vip;
	private int viewed;
	private int viewAble = 1;
	private boolean showToHome;
	private Date creationDate;
	private String creator;
	private String randomId;
	private MenuSO menuSO;
	private List<CommentSO> comments = new ArrayList<CommentSO>();
	private List<CAreaSO> cAreaSOs = new ArrayList<CAreaSO>();
	private List<String> cAreaSOToDelete = new ArrayList<String>();
	private Map<String, String> contentProperties = new HashMap<String, String>();

	public ContentSO() {

	}

	public ContentSO(Content content) {
		setId(content.getId());
		setActive(content.isActive());
		setLock(content.isLock());
		setCategory(content.getCategory());
		setCreationDate(content.getCreationDate());
		setDescription(content.getDescription());
		setDescription2(content.getDescription2());
		setShowToHome(content.isShowToHome());
		setViewed(content.getViewed());
		setViewAble(content.getViewAble());
		setType(content.getType());
		setTitel(content.getTitel());
		setRandomId(content.getRandomId());
		setCreator(content.getCreator());
		setIntern(content.isIntern());
		setTextInfo(content.getTextInfo());
		getContentProperties().putAll(content.getContentProperties());

		setLongDescription(content.getLongDescription());
		setProvince(content.getProvince());
		setVille(content.getVille());
		setQuartier(content.getQuartier());
		setLocalite(content.getLocalite());
		setVip(content.isVip());

		setLabel(content.getLabel());
		setPrix(content.getPrix());

		// if (content.getMenu() != null) {
		// Menu menu = content.getMenu();
		// MenuSO contentMenuSO = new MenuSO(menu);
		// setMenuSO(contentMenuSO);
		// fixParentMenu(menu, contentMenuSO);
		// }

		for (Comment comment : content.getComments()) {
			CommentSO commentSO = new CommentSO(comment);
			commentSO.setContentSO(this);
			comments.add(commentSO);
		}

		for (CArea area : content.getContentAreas()) {
			CAreaSO cAreaSO = new CAreaSO(area);
			cAreaSO.setContentSO(this);
			cAreaSOs.add(cAreaSO);
		}

	}

	public ContentSO(String titel) {
		this.titel = titel;
	}

	public ContentSO(String category, String description, String titelImageUrl) {
		this.category = category;
		this.description = description;
	}

	public ContentSO(String category, String titel, String description, String titelImageUrl) {
		this.titel = titel;
		this.category = category;
		this.description = description;
	}

	public ContentSO(long id) {
		setId(id);
	}

	public List<CommentSO> getComments() {
		return comments;
	}

	public void setComments(List<CommentSO> comments) {
		this.comments = comments;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getTitelImageUrl() {
		String urlImg = "";
		if (getRandomId() != null && !getRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?cRandomId=" + getRandomId();
		}
		return urlImg;

	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public void setMenuSO(MenuSO menuSO) {
		this.menuSO = menuSO;
	}

	public MenuSO getMenuSO() {
		return menuSO;
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

	public void setShowToHome(boolean showToHome) {
		this.showToHome = showToHome;
	}

	public boolean isShowToHome() {
		return showToHome;
	}

	public int getViewed() {
		return viewed;
	}

	public void setViewed(int viewed) {
		this.viewed = viewed;
	}

	public List<CAreaSO> getcAreaSOs() {
		return cAreaSOs;
	}

	public void setcAreaSOs(List<CAreaSO> cAreaSOs) {
		this.cAreaSOs = cAreaSOs;
	}

	public void addCAreaSOs(CAreaSO areaSO) {
		cAreaSOs.add(areaSO);
		areaSO.setContentSO(this);
	}

	public int getViewAble() {
		return viewAble;
	}

	public void setViewAble(int viewAble) {
		this.viewAble = viewAble;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setIntern(boolean intern) {
		this.intern = intern;
	}

	public boolean isIntern() {
		return intern;
	}

	public Map<String, String> getContentProperties() {
		return contentProperties;
	}

	public void setContentProperties(Map<String, String> contentProperties) {
		this.contentProperties = contentProperties;
	}

	void fixParentMenu(Menu menu, MenuSO menuSO) {
		Menu parentMenu = menu.getParentMenu();
		if (parentMenu != null) {
			MenuSO parentMenuSO = new MenuSO(parentMenu);
			menuSO.setParentMenuSo(parentMenuSO);
			fixParentMenu(parentMenu, parentMenuSO);
		}
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public boolean isVip() {
		return vip;
	}

	public String getLocalite() {
		return localite;
	}

	public void setLocalite(String localite) {
		this.localite = localite;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public void clearDeletedCAreas() {
		cAreaSOToDelete.clear();
	}

	public List<String> getcAreaSOToDelete() {
		return cAreaSOToDelete;
	}

	public void addProperty(String propertieKey, String propertieValue) {
		contentProperties.put(propertieKey, propertieValue);
	}

	public String getPropertyValue(String key) {
		return contentProperties.get(key);
	}
}
