package com.materials.client.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantsUtils {

	public static final String VENTES = "Ventes";
	public static final String ANNONCES = "Annonces";
	public static final String LOCATIONS = "Locations";

	public static final String MBOA_BUTTON_BACKGROUND_COLOR = "#960018";
	public static final String MBOA_BUTTON_TEXT_COLOR = "#ffb74d";
	public static final String MBOA_BUTTON_ICON_COLOR = "rgb(255, 183, 77)";
	public static final String MBOA_FONT_FAMILY = "times new roman, times, serif";

	public static final List<String> RUBRIQUES = Arrays.asList(VENTES, LOCATIONS);

	public static final List<String> RUBRIQUES_VENTE = Arrays.asList("Terrains", "Maisons", "Appartements",
			"Entrepots/Magasins", "Contenaires");

	public static final List<String> RUBRIQUES_LOCATION = Arrays.asList("Terrains", "Maisons", "Appartements / Studios",
			"Appartements Meublés", "Entrepots / Magasins", "Contenaires", "Salle de Fete", "Bureau", "Parking",
			"Boutique");

	public static final List<String> PROVINCES = Arrays.asList("Toutes les régions", "Exrême-Nord", "Nord", "Adamaoua",
			"Centre", "Est", "Sud", "Nord-Ouest", "Sud-Ouest", "Ouest", "Littoral");

	public static final List<String> PROVINCES_2 = Arrays.asList("Exrême-Nord", "Nord", "Adamaoua", "Centre", "Est",
			"Sud", "Nord-Ouest", "Sud-Ouest", "Ouest", "Littoral");

	public static final List<String> TOUTES_VILLES = Arrays.asList("Toutes les villes");

	public static final List<String> VILLES = Arrays.asList("Exrême-Nord", "Nord", "Adamaoua", "Centre", "Est", "Sud",
			"Nord-Ouest", "Sud-Ouest", "Ouest", "Littoral");

	public static final List<String> VILLES_EXTREME_NORD = Arrays.asList("Maroua", "Kousséri", "Yagoua", "Kaélé",
			"Mora", "Mokolo", "Autres Villes");

	public static final List<String> VILLES_NORD = Arrays.asList("Garoua", "Poli", "Guider", "Tcholliré",
			"Autres Villes");

	public static final List<String> VILLES_ADAMAOUA = Arrays.asList("Ngaoundéré", "Tibati", "Tignère", "Banyo",
			"Meiganga", "Autres Villes");

	public static final List<String> VILLES_EST = Arrays.asList("Bertoua", "Yokadouma", "Abong-Mbang", "Batouri",
			"Autres Villes");

	public static final List<String> VILLES_CENTRE = Arrays.asList("Yaoundé", "Nanga-Eboko", "Monatele", "Bafia",
			"Ntui", "Mfou", "Ngoumou", "Éséka", "Akonolinga", "Mbalmayo", "Autres Villes");

	public static final List<String> VILLES_SUD = Arrays.asList("Ebolowa", "Sangmélima", "Kribi", "Ambam",
			"Autres Villes");

	public static final List<String> VILLES_LITTORAL = Arrays.asList("Douala", "Nkongsamba", "Yabassi", "Édéa",
			"Autres Villes");

	public static final List<String> VILLES_OUEST = Arrays.asList("Bafoussam", "Mbouda", "Bafang", "Baham", "Bandjoun",
			"Dschang", "Bangangté", "Foumban", "Autres Villes");

	public static final List<String> VILLES_NORD_OUEST = Arrays.asList("Bamenda", "Fundong", "Kumbo", "Nkambé", "Wum",
			"Mbengwi", "Ndop", "Autres Villes");

	public static final List<String> VILLES_SUD_OUEST = Arrays.asList("Limbé", "Bangem", "Menji", "Mamfé", "Kumba",
			"Mundemba", "Autres Villes");

	public static final List<String> TOUTES_LES_VILLES = new ArrayList<String>() {
		{
			add("Toutes les villes");
			// addAll(VILLES_EXTREME_NORD);
			// addAll(VILLES_NORD);
			// addAll(VILLES_ADAMAOUA);
			// addAll(VILLES_CENTRE);
			// addAll(VILLES_EST);
			// addAll(VILLES_SUD);
			// addAll(VILLES_NORD_OUEST);
			// addAll(VILLES_SUD_OUEST);
			// addAll(VILLES_OUEST);
			// addAll(VILLES_LITTORAL);
		}
	};

	public static final Map<String, List<String>> RUBRIQUES_KEYS = new HashMap<String, List<String>>() {
		{
			put(VENTES, RUBRIQUES_VENTE);
			put(LOCATIONS, RUBRIQUES_LOCATION);
		}
	};

	public static final Map<String, List<String>> PROVINCES_KEYS = new HashMap<String, List<String>>() {
		{
			put("Toutes les régions", TOUTES_VILLES);
			put("Exrême-Nord", VILLES_EXTREME_NORD);
			put("Nord", VILLES_NORD);
			put("Adamaoua", VILLES_ADAMAOUA);
			put("Centre", VILLES_CENTRE);
			put("Est", VILLES_EST);
			put("Sud", VILLES_SUD);
			put("Nord-Ouest", VILLES_NORD_OUEST);
			put("Sud-Ouest", VILLES_SUD_OUEST);
			put("Ouest", VILLES_OUEST);
			put("Littoral", VILLES_LITTORAL);
		}
	};

}
