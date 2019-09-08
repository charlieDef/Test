package com.materials.server.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "APP_USER")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class User extends APPObject {

	private static final long serialVersionUID = 1531463089770084753L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "LASTNAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ZUWU")
	@Lob
	private String zuwu;

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "TEL", nullable = false)
	private Integer tel;

	@Column(name = "VILLAGE_AREA", nullable = false)
	private String villageArea;

	@Column(name = "CITY", nullable = false)
	private String city;

	@Column(name = "PROFESSION")
	private String profession;

	@Column(name = "COUNTRY", nullable = false)
	private String country;

	@Column(name = "ROLE", nullable = false)
	private String role;

	@Column(name = "FUNCTION", nullable = false)
	private String function;

	@Column(name = "STATUS", nullable = false)
	private String status;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = false;

	@Column(name = "PASSWORD_CHANGE_REQUIRED", nullable = false)
	private boolean pwdChangeRequired = false;

	@Column(name = "PASSWORD_CHANGE_CODE", nullable = false)
	private String pwdChangeCode;

	@Column(name = "PASSWORD_CHANGE_CODE_VALIDITY")
	private Date pwdChangeCodeValidity;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "IMAGE_DATA")
	@Lob
	private byte[] imageData;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "USER_PROPERTIES", joinColumns = @JoinColumn(name = "EB_USER_ID"))
	@MapKeyColumn(name = "propKey", length = 128)
	@OrderBy(value = "propKey")
	@Column(name = "propValue", length = 2048)
	private Map<String, String> userProperties;

	public User() {
		userProperties = new HashMap<String, String>();
	}

	public User(String name) {
		this.name = name;
		userProperties = new HashMap<String, String>();
	}

	public User(String name, String lastName) {
		this.name = name;
		this.lastName = lastName;
		userProperties = new HashMap<String, String>();
	}

	// public long getId() {
	// return id;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZuwu() {
		return zuwu;
	}

	public void setZuwu(String zuwu) {
		this.zuwu = zuwu;
	}

	public Map<String, String> getUserProperties() {
		return userProperties;
	}

	public void setUserProperties(Map<String, String> userProperties) {
		this.userProperties = userProperties;
	}

	public void addPropertie(String propertieKey, String propertieValue) {
		this.userProperties.put(propertieKey, propertieValue);
	}

	public void removePropertie(String propertieKey) {
		if (this.userProperties.get(propertieKey) != null) {
			this.userProperties.remove(propertieKey);
		}
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public Integer getTel() {
		return tel;
	}

	public void setTel(Integer tel) {
		this.tel = tel;
	}

	public String getVillageArea() {
		return villageArea;
	}

	public void setVillageArea(String villageArea) {
		this.villageArea = villageArea;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public boolean isPwdChangeRequired() {
		return pwdChangeRequired;
	}

	public void setPwdChangeRequired(boolean pwdChangeRequired) {
		this.pwdChangeRequired = pwdChangeRequired;
	}

	public String getPwdChangeCode() {
		return pwdChangeCode;
	}

	public void setPwdChangeCode(String pwdChangeCode) {
		this.pwdChangeCode = pwdChangeCode;
	}

	public void setPwdChangeCodeValidity(Date pwdChangeCodeValidity) {
		this.pwdChangeCodeValidity = pwdChangeCodeValidity;
	}

	public Date getPwdChangeCodeValidity() {
		return pwdChangeCodeValidity;
	}

	public void addHashFragmentProperty(String fragmentKey, String value) {
		String newvalue = value + "#";
		if (getUserProperties().get(fragmentKey) != null) {
			if (getUserProperties().get(fragmentKey).isEmpty()) {
				getUserProperties().put(fragmentKey, newvalue);
			} else {
				String pvalue = getUserProperties().get(fragmentKey);
				pvalue += newvalue;
				getUserProperties().put(fragmentKey, pvalue);
			}
		} else {
			getUserProperties().put(fragmentKey, newvalue);
		}
	}

	public void removeHashFragmentProperty(String fragmentKey, String value) {
		if (getUserProperties().get(fragmentKey) != null) {
			String pvalue = getUserProperties().get(fragmentKey);
			pvalue = pvalue.replaceAll(value + "#", "");
			getUserProperties().put(fragmentKey, pvalue);
		}
	}
}
