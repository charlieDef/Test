package com.materials.client.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.materials.client.CoolPasCherUI;
import com.materials.server.model.User;

public class UserSO extends APPObjectSO {

	private static final long serialVersionUID = 1L;

	public static final String FAVORITS = "Favorits";
	public static final String ANNONCES = "Annonces";

	private String name;
	private String lastName;
	private String email;
	private Integer tel;
	private String villageArea;
	private String city;
	private String country;
	private String role;
	private String function;
	private String status;
	private String zuwu;
	private String profession;
	private String randomId;
	private boolean active;
	private Date creation;
	private Date pwdChangeCodeValidity;
	private boolean pwdChangeRequired = false;
	private String pwdChangeCode;

	private Map<String, String> userProperties = new HashMap<String, String>();

	public UserSO() {
	}

	public UserSO(long id) {
		setId(id);
	}

	public UserSO(String name) {
		this.name = name;
	}

	public UserSO(String name, String lastName) {
		this.name = name;
		this.lastName = lastName;
	}

	public UserSO(User user) {
		setId(user.getId());
		setEmail(user.getEmail());
		setLock(user.isLock());
		setLastName(user.getLastName());
		setName(user.getName());
		setCity(user.getCity());
		setProfession(user.getProfession());
		setCountry(user.getCountry());
		setFunction(user.getFunction());
		setRole(user.getRole());
		setStatus(user.getStatus());
		setTel(user.getTel());
		setPwdChangeCode(user.getPwdChangeCode());
		setPwdChangeRequired(user.isPwdChangeRequired());

		setVillageArea(user.getVillageArea());
		// setZuwu(user.getZuwu());
		setCreation(user.getCreationDate());
		setRandomId(user.getRandomId());
		getUserProperties().putAll(user.getUserProperties());
		// setUserProperties(user.getUserProperties());
	}

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

	@Override
	public String toString() {
		return String.valueOf(getId());
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

	public boolean isAdminMaster() {
		return getRole().equals(R_ADMIN_MASTER);
	}

	public boolean isAdmin() {
		// String fonction = getUserProperties().get(PREBAAL_ROLE);
		return getRole().equals(R_ADMIN) || getRole().equals(R_ADMIN_MASTER);
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public String getUserImageUrl() {
		String urlImg = "img/a_user.jpg";
		if (getRandomId() != null && !getRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?userID=" + getRandomId();
		}
		return urlImg;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public Boolean isPrebaalMember() {
		boolean isMember = getRole().equals(R_MEMBRE) || isAdmin() || isAdminMaster();
		return isMember;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public void setPwdChangeCodeValidity(Date pwdChangeCodeValidity) {
		this.pwdChangeCodeValidity = pwdChangeCodeValidity;
	}

	public Date getPwdChangeCodeValidity() {
		return pwdChangeCodeValidity;
	}

	public int getFavoritsNumber() {
		int number = 0;
		if (getUserProperties().get(FAVORITS) != null) {
			String[] array = getUserProperties().get(FAVORITS).split("#");
			if (array != null) {
				number = array.length;
			}
		}
		return number;
	}

	public String[] getFavoritsIds() {
		String[] array = null;
		if (getUserProperties().get(FAVORITS) != null) {
			array = getUserProperties().get(FAVORITS).split("#");
		}
		return array;
	}

	public int getAnnoncesNumber() {
		int number = 0;
		if (getUserProperties().get(ANNONCES) != null) {
			String[] array = getUserProperties().get(ANNONCES).split("#");
			if (array != null) {
				number = array.length;
			}
		}
		return number;
	}

	public boolean annonceIsFavorit(Long annonceId) {
		boolean isFavorit = false;
		String idAsString = String.valueOf(annonceId);
		if (getUserProperties().get(FAVORITS) != null) {
			String array = getUserProperties().get(FAVORITS);
			isFavorit = ((array != null) && (array.contains(idAsString)));
		}
		return isFavorit;
	}

	public void addHashFragmentProperty(String key, String value) {
		String newvalue = value + "#";
		if (getUserProperties().get(key) != null) {
			if (getUserProperties().get(key).isEmpty()) {
				getUserProperties().put(key, newvalue);
			} else {
				String pvalue = getUserProperties().get(key);
				pvalue += newvalue;
				getUserProperties().put(key, pvalue);
			}
		} else {
			getUserProperties().put(key, newvalue);
		}
	}

	public void removeHashFragmentProperty(String key, String value) {
		if (getUserProperties().get(key) != null) {
			String pvalue = getUserProperties().get(key);
			pvalue = pvalue.replaceAll(value + "#", "");
			getUserProperties().put(key, pvalue);

		}
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

}
