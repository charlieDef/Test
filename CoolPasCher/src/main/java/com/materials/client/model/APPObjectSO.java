package com.materials.client.model;

import java.io.Serializable;

public abstract class APPObjectSO implements Serializable {

	private static final long serialVersionUID = -6868522179657938850L;
	private long id;
	private boolean lock = true;
	private String textInfo = "OK";
	private String label;

	// Fonctions
	public static String PREBAAL_FONCTION = "fonction";
	public static String F_PRESIDENT = "President(e)";
	public static String F_SECRETAIRE = "Secretaire";
	public static String F_CCULTURELLE = "CCulturelle";
	public static String F_CCOMMUNICATION = "CCommunication";
	public static String F_CCOMPTES = "CComptes";
	public static String F_TRESORIER = "Tresorier";
	public static String F_CENSEUR = "Censeur";
	public static String F_SIMPLE_MEMBRE = "Simple membre";
	public static String F_AUCUNE = "Aucune";
	public static String[] PREBAAL_FONCTIONS = new String[] { "---", F_PRESIDENT, F_SECRETAIRE, F_CCULTURELLE,
			F_CCOMMUNICATION, F_TRESORIER, F_CCOMPTES, F_CENSEUR, F_SIMPLE_MEMBRE };

	// Roles
	public static String MBOA_ROLE = "role";
	public static String R_MEMBRE = "mboa-member";
	public static String R_ADMIN = "mboa-admin";
	public static String R_ADMIN_MASTER = "mboa-admin-master";
	public static String[] MBOA_ROLES = new String[] { "---", R_MEMBRE, R_ADMIN, R_ADMIN_MASTER };

	// Status
	public static String PREBAAL_STATUS = "status";
	public static String STATUS_ETUDIANT = "Etudiant(e)";
	public static String STATUS_TRAVAILLEUR = "Travailleur";
	public static String STATUS_DEBROUILLARD = "Debrouillard";
	public static String[] PREBAAL_ALL_STATUS = new String[] { STATUS_ETUDIANT, STATUS_TRAVAILLEUR, STATUS_DEBROUILLARD,
			"---", };

	// PROPERTIES
	public static String PREBAAL_EPARGNE = "epargne";
	public static String PREBAAL_INSCRIPTION = "inscription";
	public static String PREBAAL_FOND_CAISSE = "fond-de-caisse";

	// SELECTBOXES
	public static String[] PREBAAL_PAYS = new String[] { "Allemagne", "France", "Belgique", "Angleterre", "Espagne",
			"Italie", "Suisse", "Autres", "Cameroun" };

	public static String[] TYPE_DANNONCEURS = new String[] { "Commercant", "Entreprise", "Particulier" };

	public static String[] PREBAAL_DE_ETAT = new String[] { "---", "Baden-Wuerttemberg", "Bayern", "Berlin",
			"Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen",
			"Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein",
			"Thueringen" };

	public static String[] PREBAAL_FINANCE_CATEGORY = new String[] { "---", "Collation", "Inscription",
			"Fond de caisse", "Epargne", "Trouble Bavardages", "Trouble Telephone", "Retard", "Tontine",
			"Sanction Absence", "Sanction Collation" };

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLock(boolean deleteAble) {
		this.lock = deleteAble;
	}

	public boolean isLock() {
		return lock;
	}

	public String getTextInfo() {
		return textInfo;
	}

	public void setTextInfo(String textInfo) {
		this.textInfo = textInfo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
