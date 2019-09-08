package com.materials.shared;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.ContentSO;
import com.materials.client.model.MenuSO;
import com.materials.client.model.WebSiteSO;
import com.materials.client.places.content.ContentPlace;

public class APPMetaData {

	public static final String META_TYPE = "website";
	public static final String META_TITLE = "PREBAAL Poh loon";
	public static final String META_SITE_NAME = "PREBAAL Poh loon";
	public static final String META_URL = "https://www.prebaal.com";
	public static final String META_DESCRIPTION = "PREBAAL Poh loon | Ressortissants Baleng D'Allemagne";

	public Map<String, MetaElement> metaElements = new HashMap<>();

	private MetaElement injectMetaProperty(String property, String value) {

		Document document = Document.get();

		MetaElement metaElement = document.createMetaElement();
		// metaElement.setName(property);
		metaElement.setAttribute("property", property);
		metaElement.setContent(value);
		document.getHead().insertFirst(metaElement);
		return metaElement;
	}

	private void updateMetaName(String name, String value) {

		// Document document = Document.get();
		//
		// MetaElement metaElement = document.createMetaElement();
		// metaElement.setName(name);
		// metaElement.setContent(value);
		// document.getHead().insertFirst(metaElement);

		MetaElement element = metaElements.get(name);

		if (element == null) {
			NodeList<Element> metaList = Document.get().getHead().getElementsByTagName("meta");
			for (int i = 0; i < metaList.getLength(); i++) {

				MetaElement meta = (MetaElement) metaList.getItem(i);
				if (meta.getName() != null && meta.getName().equals(name)) {
					metaElements.put(name, meta);
					element = meta;
					break;
				}
			}
		}
		if (element != null) {
			element.setContent(value);
		}
	}

	public void updateMetaProperty(String property, String value) {

		MetaElement element = metaElements.get(property);

		if (element == null) {
			NodeList<Element> metaList = Document.get().getHead().getElementsByTagName("meta");
			for (int i = 0; i < metaList.getLength(); i++) {

				MetaElement meta = (MetaElement) metaList.getItem(i);
				if (meta.getString().contains(property)) {
					metaElements.put(property, meta);
					element = meta;
					break;
				}
			}
		}
		if (element != null) {
			element.setContent(value);
		}

	}

	// public void initStandardMeta() {
	//
	// WebSiteSO siteSO = PrebaalUI.CLIENT_FACTORY.getWebSiteSO();
	//
	// String title = siteSO.getShortName() + " | " + siteSO.getLongName();
	//
	// Document.get().setTitle(title);
	//
	// updateMetaName("description", siteSO.getLongName());
	// // metaElements.put("description", metaDescription);
	//
	// MetaElement propertyMetaTitle = injectMetaProperty("og:title", title);
	// MetaElement propertyMetaDescription = injectMetaProperty("og:description",
	// siteSO.getLongName());
	// MetaElement propertyMetaImage = injectMetaProperty("og:image",
	// siteSO.getImageUrl());
	// MetaElement propertyMetaURL = injectMetaProperty("og:url", META_URL);
	// MetaElement propertyMetaType = injectMetaProperty("og:type", META_TYPE);
	// MetaElement propertyMetaSiteName = injectMetaProperty("og:site_name",
	// siteSO.getShortName());
	//
	// metaElements.put("og:title", propertyMetaTitle);
	// metaElements.put("og:description", propertyMetaDescription);
	// metaElements.put("og:image", propertyMetaImage);
	// metaElements.put("og:site_name", propertyMetaSiteName);
	// metaElements.put("og:url", propertyMetaURL);
	// metaElements.put("og:type", propertyMetaType);
	//
	// }

	public void updateStandardMeta() {
		WebSiteSO siteSO = CoolPasCherUI.CLIENT_FACTORY.getWebSiteSO();
		String title = siteSO.getShortName() + " | " + siteSO.getLongName();
		Document.get().setTitle(siteSO.getShortName() + " | " + siteSO.getLongName());

		updateMetaName("description", siteSO.getDescription());
		updateMetaProperty("og:title", title);
		updateMetaProperty("og:type", "website");
		updateMetaProperty("og:description", siteSO.getDescription());
		updateMetaProperty("og:image", siteSO.getImageUrl());
		updateMetaProperty("og:url", META_URL);

	}

	public void updateMetaForArticle(ContentSO contentSO) {

		// clientFactory.removeOldMeta();
		String url = GWT.getHostPageBaseURL() + ContentPlace.PREFIX + contentSO.getTitel();

		Document.get().setTitle(contentSO.getTitel());
		updateMetaProperty("og:title", contentSO.getTitel());
		updateMetaProperty("og:type", "article");
		updateMetaName("description", contentSO.getDescription2());
		updateMetaProperty("og:description", contentSO.getDescription2());
		updateMetaProperty("og:image", contentSO.getTitelImageUrl());
		updateMetaProperty("og:url", url);
		// injectMetaName("author", contentSO.getCreator());
		// injectMetaName("copyright", "Prebaal Poh loon");
		// injectMetaName("keywords", contentSO.getCategory());

	}

	public void updateMetaForMenu(MenuSO menuSO) {

		WebSiteSO siteSO = CoolPasCherUI.CLIENT_FACTORY.getWebSiteSO();

		String menuName = menuSO.getName();
		String parentMenuName = menuSO.getParentMenuSo().getName();

		// clientFactory.removeOldMeta();
		String url = GWT.getHostPageBaseURL() + "#!start/" + menuName;

		Document.get().setTitle(siteSO.getName() + " - " + parentMenuName + " - " + menuName);
		updateMetaProperty("og:title", menuSO.getName());
		updateMetaProperty("og:type", "article");
		updateMetaName("description", menuSO.getDescription());
		updateMetaProperty("og:description", menuSO.getDescription());
		if (menuSO.getContents().size() == 1) {
			updateMetaProperty("og:image", menuSO.getContents().get(0).getTitelImageUrl());
		}

		updateMetaProperty("og:url", url);

	}

	public void updateMetaForContact() {

		WebSiteSO siteSO = CoolPasCherUI.CLIENT_FACTORY.getWebSiteSO();
		// clientFactory.removeOldMeta();
		String url = GWT.getHostPageBaseURL() + "#!start/Contact";

		Document.get().setTitle(siteSO.getName() + " - Contact");

		updateMetaProperty("og:title", siteSO.getName() + " - Contact");
		updateMetaProperty("og:type", "article");
		updateMetaName("description", "Contactez  " + siteSO.getName());
		updateMetaProperty("og:description", "Contactez  " + siteSO.getName());
		updateMetaProperty("og:image", CoolPasCherUI.APP_RESOURCE.contact().getSafeUri().asString());
		updateMetaProperty("og:url", url);

		// if (metaElements.size() > 0) {
		// metaElements.get("description").setContent(siteSO.getName() + " - Contact");
		// metaElements.get("og:title").setContent(siteSO.getName() + " - Contact");
		// metaElements.get("og:description").setContent("Entrer en contact avec la
		// prebaal");
		// metaElements.get("og:image").setContent(PrebaalUI.APP_RESOURCE.contact().getSafeUri().asString());
		// metaElements.get("og:url").setContent(url);
		// metaElements.get("og:site_name").setContent(siteSO.getShortName());
		// metaElements.get("og:type").setContent(META_TYPE);
		// }

	}

}
