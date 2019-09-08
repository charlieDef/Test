package com.materials.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "CONTENT")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class Content extends APPObject {

	private static final long serialVersionUID = -3625538469817183489L;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "TITEL")
	private String titel;

	@Column(name = "VIEWED")
	private int viewed;

	@Column(name = "VIEW_ABLE", nullable = false)
	private int viewAble;

	@Column(name = "INTERN", nullable = false)
	private boolean intern;

	@Column(name = "SHOW_TO_HOME", nullable = false)
	private boolean showToHome;

	@Column(name = "VIP", nullable = false)
	private boolean vip;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DESCRIPTION2")
	private String description2 = "";

	@Column(name = "C_TYPE", nullable = false)
	private String type = "";

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = true;

	@Column(name = "IMAGE_DATA")
	@Lob
	private byte[] imageData;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "LONG_DESCRIPTION", length = 600000)
	@Lob
	private String longDescription = "";

	@Column(name = "PROVINCE")
	private String province = "";

	@Column(name = "VILLE")
	private String ville = "";

	@Column(name = "QUARTIER")
	private String quartier = "";

	@Column(name = "LOCALITE")
	private String localite = "";

	@Column(name = "PRIX", nullable = false)
	private String prix = "";

	@ManyToOne(targetEntity = Menu.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_ID")
	private Menu menu;

	@OneToMany(targetEntity = Comment.class, mappedBy = "content", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<Comment>(0);

	@OneToMany(targetEntity = CArea.class, mappedBy = "content", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<CArea> contentAreas = new ArrayList<CArea>(0);

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "CONTENT_PROPERTIES", joinColumns = @JoinColumn(name = "CONTENT_ID"))
	@MapKeyColumn(name = "propKey", length = 128)
	@OrderBy(value = "propKey")
	@Column(name = "propValue", length = 2048)
	private Map<String, String> contentProperties;

	public Content() {
		contentProperties = new HashMap<String, String>();
	}

	public Content(String titel) {
		this.titel = titel;
		contentProperties = new HashMap<String, String>();
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public int getViewed() {
		return viewed;
	}

	public void setViewed(int viewed) {
		this.viewed = viewed;
	}

	public boolean isShowToHome() {
		return showToHome;
	}

	public void setShowToHome(boolean showToHome) {
		this.showToHome = showToHome;
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public void addContentArea(CArea area) {
		contentAreas.add(area);
		area.setContent(this);
	}

	public Map<String, String> getContentProperties() {
		return contentProperties;
	}

	public void setContentProperties(Map<String, String> contentProperties) {
		this.contentProperties = contentProperties;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setContent(this);
	}

	public List<CArea> getContentAreas() {
		return contentAreas;
	}

	public void setContentAreas(List<CArea> contentAreas) {
		this.contentAreas = contentAreas;
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

	public boolean isIntern() {
		return intern;
	}

	public void setIntern(boolean intern) {
		this.intern = intern;
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
}
