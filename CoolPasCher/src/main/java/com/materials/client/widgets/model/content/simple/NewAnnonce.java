package com.materials.client.widgets.model.content.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.MenuSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.utils.ConstantsUtils;
import com.materials.client.widgets.ckeditor.MDEditor;
import com.materials.client.widgets.upload.image.SimpleUploader;

import gwt.material.design.addins.client.carousel.MaterialCarousel;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.validator.BlankValidator;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;

public class NewAnnonce extends Composite implements NewAnnonceView {

	private static NewAnnonceUiBinder uiBinder = GWT.create(NewAnnonceUiBinder.class);

	interface NewAnnonceUiBinder extends UiBinder<MaterialWidget, NewAnnonce> {
	}

	private BlankValidator<String> blankValidator = new BlankValidator<String>("Entrez une valeur");
	private Map<String, List<String>> categorie_subMenus = new HashMap();
	private Map<String, CAreaSO> areaSOs = new HashMap();
	private Map<Integer, MaterialPanel> panels = new TreeMap<Integer, MaterialPanel>();
	private ContentSO contentSO;
	private Presenter presenter;
	private MDEditor mdEditor;

	private Consumer<Boolean> saveControllConsumer;

	int index = 0;
	boolean firstRemoved = false;

	@UiField
	MaterialPanel ckEditorUi;

	@UiField
	MaterialIcon deleteIconUi;

	@UiField
	MaterialCheckBox vipUi;

	@UiField
	MaterialLabel supprimerLabelUi;

	private MaterialCarousel carousel;

	@UiField
	MaterialTextBox localiteUi, quartierUi, titelUi, descriptionUi, labelUi, prixUi, description2Ui;

	@UiField
	MaterialComboBox<String> provinceUi, villeUi, categorieUi;

	@UiField
	MaterialPanel materialRowSliderUi;

	@UiField
	MaterialCard materialCardUi;

	@UiField
	SimpleUploader fileUploadUi;

	public NewAnnonce() {
		initWidget(uiBinder.createAndBindUi(this));

		// carousel = new MaterialCarousel();
		// carousel.setShowDots(true);
		// carousel.setShowArrows(false);
		// carousel.setInfinite(true);
		// carousel.setAdaptiveHeight(true);
		prixUi.addValidator(blankValidator);
		prixUi.setValidateOnBlur(true);

		MaterialPanel materialPanel2 = new MaterialPanel();
		materialPanel2.setHeight("450px");

		provinceUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		villeUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		categorieUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		quartierUi.getLabel().getElement().getStyle().setColor("#ffb74d");
		// sousCategorieUi.getLabel().getElement().getStyle().setColor("#ffb74d");

		ckEditorUi.add(new HTML(""));

		deleteIconUi.addClickHandler(c -> {
			int currentIndex = carousel.getCurrentSlideIndex();
			MaterialPanel widget = (MaterialPanel) panels.get(currentIndex);
			if (areaSOs.containsKey(widget.getId())) {
				areaSOs.remove(widget.getId());
				contentSO.getcAreaSOToDelete().add(widget.getId());
				// Window.alert("Contains id: " + widget.getId());
			}
			carousel.remove(currentIndex);
			resort(currentIndex);

			deleteIconUi.setEnabled(index > 0);
			if (saveControllConsumer != null) {
				saveControllConsumer.accept(enableSaveButton());
			}

			// carousel.reload();
		});

		// "https://prebaal.com/prebaalmd/FileHelper?sliderID=RxMW7ZBZD98d9HB1521673941642");
		// materialPanel2.add(image1);
		// carousel.getContainer().add(materialPanel2);

		fileUploadUi.setWidgetContainer(materialCardUi);
		fileUploadUi.addValueChangeHandler(x -> {

			CAreaSO areaSO = new CAreaSO();
			areaSO.setId(-10);

			areaSO.setCreationDate(new Date());
			areaSO.setRandomId(fileUploadUi.getRandomID());
			areaSO.setAreaType(CAreaSO.TYPE_ANNONCE);
			areaSO.setTitel(fileUploadUi.getFileName());
			areaSOs.put(areaSO.getRandomId(), areaSO);

			final MaterialPanel materialPanelNew = new MaterialPanel();
			materialPanelNew.setPaddingBottom(10);
			materialPanelNew.setId(fileUploadUi.getRandomID());
			MaterialImage newImage = new MaterialImage(fileUploadUi.getValue());

			materialPanelNew.setHeight("auto");
			materialPanelNew.setWidth("auto");
			materialPanelNew.add(newImage);
			carousel.add(materialPanelNew);

			if (index == 0) {
				ClientUtils.addTimer(s -> carousel.reload(), 400);
			}
			panels.put(index, materialPanelNew);

			ClientUtils.addTimer(s -> carousel.goToSlide(index++), 200, b -> {
				deleteIconUi.setEnabled(index > 0);
			});

			if (saveControllConsumer != null) {
				saveControllConsumer.accept(enableSaveButton());
			}

		});

		provinceUi.setAcceptableValues(ConstantsUtils.PROVINCES_2);
		villeUi.setAcceptableValues(ConstantsUtils.VILLES_EXTREME_NORD);

		Collection<String> values = CoolPasCherUI.CLIENT_FACTORY.getAnnoncesCategories().values();
		List<String> menusCategories = values.stream().filter(x -> x.contains("||")).collect(Collectors.toList());
		categorieUi.setAcceptableValues(menusCategories);

		// MBoaOnlineUI.CLIENT_FACTORY.getAllBaseMenus(menus -> {
		// List<String> list = menus.stream().filter(x -> x.isMenuCategory() &&
		// !x.getSubMenuSos().isEmpty())
		// .map(x -> {
		// categorie_subMenus.put(x.getName(), getMenuAsString(x.getSubMenuSos()));
		// return x.getName();
		// }).collect(Collectors.toList());
		// categorieUi.setAcceptableValues(list);
		// // sousCategorieUi.setAcceptableValues(categorie_subMenus.get(list.get(0)));
		//
		// });

		// categorieUi.addValueChangeHandler(x -> {
		// String categorie = categorieUi.getSingleValue();
		// //sousCategorieUi.setAcceptableValues(categorie_subMenus.get(categorie));
		// });

		provinceUi.addValueChangeHandler(x -> {
			String value = provinceUi.getSingleValue();
			villeUi.setAcceptableValues(ConstantsUtils.PROVINCES_KEYS.get(value));
			villeUi.setSingleValue(ConstantsUtils.PROVINCES_KEYS.get(value).get(1), true);
			// contentSO.setProvince(value);
		});

		prixUi.addValueChangeHandler(x -> {
			if (saveControllConsumer != null) {
				saveControllConsumer.accept(enableSaveButton());
			}
		});
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void updateContentSO(ContentSO contentSO) {
		titelUi.setValue(contentSO.getTitel());
		descriptionUi.setValue(contentSO.getDescription());
		description2Ui.setValue(contentSO.getDescription2());
	}

	@Override
	public void setContent(ContentSO contentSO) {
		this.carousel = getCarousel();
		materialRowSliderUi.clear();
		materialRowSliderUi.add(carousel);
		this.index = 0;
		this.areaSOs.clear();
		this.panels.clear();
		this.contentSO = contentSO;
		titelUi.setValue(contentSO.getTitel());
		descriptionUi.setValue(contentSO.getDescription());
		description2Ui.setValue(contentSO.getDescription2());
		ckEditorUi.add(new HTML(contentSO.getLongDescription()));
		vipUi.setValue(contentSO.isVip());
		provinceUi.setSingleValue(contentSO.getProvince(), true);

		quartierUi.setValue(contentSO.getQuartier());
		labelUi.setValue(contentSO.getLabel());
		prixUi.setValue(contentSO.getPrix());

		if (contentSO.getCategory() != null && !contentSO.getCategory().isEmpty()) {
			String[] sousCAtegory = contentSO.getCategory().split("_");
			if (sousCAtegory != null) {
				categorieUi.setSingleValue(sousCAtegory[0], true);
			}

		}

		if (!contentSO.getcAreaSOs().isEmpty()) {
			for (CAreaSO areaSO : contentSO.getcAreaSOs()) {
				MaterialPanel materialPanel = getCarouselItem(areaSO);
				carousel.add(materialPanel);
				areaSOs.put(areaSO.getRandomId(), areaSO);
				panels.put(index, materialPanel);
				index++;
			}
			ClientUtils.addTimer(x -> carousel.reload(), 1000);
		}
		deleteIconUi.setEnabled(index > 0);

		ClientUtils.addTimer(x -> {
			villeUi.setSingleValue(this.contentSO.getVille());
		}, 500);

	}

	private List<String> getMenuAsString(List<MenuSO> list) {
		return list.stream().map(MenuSO::getName).collect(Collectors.toList());
	}

	@Override
	public void setEdit(boolean edit) {
		localiteUi.setEnabled(edit);
		labelUi.setEnabled(edit);
		quartierUi.setEnabled(edit);
		prixUi.setEnabled(edit);
		provinceUi.setEnabled(edit);
		villeUi.setEnabled(edit);
		categorieUi.setEnabled(edit);
		// sousCategorieUi.setEnabled(edit);
		vipUi.setEnabled(edit);
		fileUploadUi.setVisible(edit);
		deleteIconUi.setVisible(edit);
		supprimerLabelUi.setVisible(edit);

		ckEditorUi.clear();

		String lDescription = contentSO != null ? contentSO.getLongDescription() : "";
		if (edit) {
			mdEditor = new MDEditor();
			// setHeight("900px");
			ckEditorUi.add(mdEditor);
			mdEditor.setEditorValue(lDescription);
		} else {
			ckEditorUi.add(new HTML(lDescription));
		}
	}

	private MaterialPanel getCarouselItem(CAreaSO areaSO) {
		MaterialPanel materialPanelNew = new MaterialPanel();
		materialPanelNew.setPaddingBottom(10);
		materialPanelNew.setId(areaSO.getRandomId());
		MaterialImage newImage = new MaterialImage(areaSO.getImageUrl());

		materialPanelNew.setHeight("auto");
		materialPanelNew.setWidth("auto");
		materialPanelNew.add(newImage);

		return materialPanelNew;
	}

	private void resort(int indexToRemove) {
		panels.remove(indexToRemove);
		int myIndex = 0;
		Map<Integer, MaterialPanel> pan = new TreeMap<Integer, MaterialPanel>();
		for (Integer integer : panels.keySet()) {
			pan.put(myIndex, panels.get(integer));
			myIndex = myIndex + 1;
		}
		this.panels = pan;
		this.index = myIndex;
		deleteIconUi.setEnabled(index > 0);
		if (this.index == 0) {
			// Window.alert("Index is 0");
			ClientUtils.addTimer(s -> carousel.reload(), 200);

		}
	}

	@Override
	public void extractFromAnnonce(ContentSO contentSOToSave) {
		contentSOToSave.setProvince(provinceUi.getSingleValue());
		contentSOToSave.setVille(villeUi.getSingleValue());
		contentSOToSave.setQuartier(quartierUi.getValue());
		// contentSOToSave.setCategory(categorieUi.getSingleValue() + "_" +
		// sousCategorieUi.getSingleValue());
		contentSOToSave.setCategory(categorieUi.getSingleValue());
		contentSOToSave.setPrix(prixUi.getValue());
		contentSOToSave.setLabel(labelUi.getValue());
		contentSOToSave.setcAreaSOs(new ArrayList<CAreaSO>(areaSOs.values()));
		contentSOToSave.setVip(vipUi.getValue());

		if (localiteUi.getValue() != null) {
			contentSOToSave.setLocalite(localiteUi.getValue());
		}
		contentSOToSave.setLongDescription(mdEditor.getEditorValue());

	}

	private MaterialCarousel getCarousel() {
		MaterialCarousel carousel = new MaterialCarousel();
		carousel.setShowDots(true);
		carousel.setShowArrows(false);
		carousel.setInfinite(true);
		carousel.setAdaptiveHeight(true);
		return carousel;
	}

	public boolean enableSaveButton() {
		return prixUi.getValue() != null && !prixUi.getValue().isEmpty() && !areaSOs.isEmpty();
	}

	public void setSaveControllConsumer(Consumer<Boolean> saveControllConsumer) {
		this.saveControllConsumer = saveControllConsumer;
	}
}
