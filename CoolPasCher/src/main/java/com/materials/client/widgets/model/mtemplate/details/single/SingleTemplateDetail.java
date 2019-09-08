package com.materials.client.widgets.model.mtemplate.details.single;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuTemplateSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.client.widgets.menu.HeaderMenu;
import com.materials.client.widgets.menu.MenuLink;
import com.materials.client.widgets.menu.column1.MenuColumn_1;
import com.materials.client.widgets.menu.column1.MenuColumn_2;
import com.materials.client.widgets.upload.MDFileUploader;
import com.materials.shared.MethodsUtils;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class SingleTemplateDetail extends BaseDetails2 implements SingleTemplateDetailView {

	private static SingleTemplateDetailUiBinder uiBinder = GWT.create(SingleTemplateDetailUiBinder.class);

	interface SingleTemplateDetailUiBinder extends UiBinder<Widget, SingleTemplateDetail> {
	}

	private Presenter presenter;
	private MenuTemplateSO menuTemplateSO;

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	HTMLPanel templateUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialIntegerBox indexUi;

	@UiField
	MDFileUploader fileUploadUi;

	@UiField
	MaterialTextBox liensUi, nameUi, categoryUi;

	@UiField
	MaterialCheckBox activeUi, lockUi, leftAlignUi, buttonStyleUi;

	@UiHandler("backButtonUi")
	void onBackToContentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToTemplateList();
	}

	public SingleTemplateDetail(boolean configure) {
		super(configure);
		add(uiBinder.createAndBindUi(this));
		mdFormUi.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		// materialColumnUi.setTextAlign(TextAlign.CENTER);

		mdFormUi.setFormHandler(new BBarHandler());

		fileUploadUi.setUploadCompletListener((tempUploaID, fileType) -> {

			// String fileName = array[1];
			String urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + tempUploaID;
			menuTemplateSO.setRandomId(tempUploaID);
			// menuTemplateSO.setImgUrl(urlImg);

			buildLayoutModel();
		});

		handleEditEnable();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	public void setEdit() {
		mdFormUi.edit();

	}

	@Override
	public void setMenuTemplateSO(MenuTemplateSO mt) {
		this.menuTemplateSO = mt;

		buildLayoutModel();

		nameUi.setValue(mt.getName());
		indexUi.setValue(mt.getIndex());
		categoryUi.setValue(mt.getCategory());
		buttonStyleUi.setValue(menuTemplateSO.isButtonStyle());
		leftAlignUi.setValue(menuTemplateSO.isLeftAlign());
		liensUi.setValue(getLiens());
		activeUi.setValue(menuTemplateSO.isActive());
	}

	@UiHandler("categoryUi")
	void onCategoryValueChange(ValueChangeEvent<String> event) {

		String value = event.getValue();
		if (value != null && !value.isEmpty()) {
			menuTemplateSO.setCategory(value);
			buildLayoutModel();
		}
	}

	@UiHandler("nameUi")
	void onNameValueChange(ValueChangeEvent<String> event) {

		String value = event.getValue();

		menuTemplateSO.setName(value);
		buildLayoutModel();

	}

	@UiHandler("buttonStyleUi")
	void onValueButtonStyleChange(ValueChangeEvent<Boolean> event) {
		menuTemplateSO.setButtonStyle(buttonStyleUi.getValue());
		buildLayoutModel();
	}

	@UiHandler("leftAlignUi")
	void onValueLeftAlignUiChange(ValueChangeEvent<Boolean> event) {
		menuTemplateSO.setLeftAlign(leftAlignUi.getValue());
		buildLayoutModel();
	}

	@UiHandler("liensUi")
	void onValueChange(ValueChangeEvent<String> event) {
		menuTemplateSO.getTemplateLinks().clear();
		String value = event.getValue();
		if (value != null && !value.isEmpty()) {
			String[] links = value.split("#");
			int i = 0;
			for (String string : links) {
				menuTemplateSO.getTemplateLinks().put(i++, string);
			}
		}
		buildLayoutModel();
	}

	private void buildLayoutModel() {
		templateUi.clear();
		HeaderMenu headerMenu = new HeaderMenu();

		if (menuTemplateSO.getColNr() == 1) {
			MenuColumn_1 col1 = new MenuColumn_1(menuTemplateSO.getMenuSO().getName(), true, null);
			col1.addTitleArea(menuTemplateSO.getName(), 2);
			String img = menuTemplateSO.getImgUrl() != null ? menuTemplateSO.getImgUrl() : "img/tourisme.JPG";
			String name = menuTemplateSO.getName();
			col1.addImage(img, (name != null && !name.isEmpty()) ? MethodsUtils.js_DoRun_Command(name) : null);

			if (menuTemplateSO.getTemplateLinks().size() == 1) {
				String theLink = menuTemplateSO.getTemplateLinks().values().iterator().next();
				col1.addMenuLink(theLink, MethodsUtils.js_DoRun_Command(menuTemplateSO.getName()),
						menuTemplateSO.isButtonStyle());
			} else {
				for (String link : menuTemplateSO.getTemplateLinks().values()) {
					col1.addMenuLink(link, MethodsUtils.js_DoRun_Command(link), menuTemplateSO.isButtonStyle());
				}
			}

			headerMenu.addMenu(col1);
		} else if (menuTemplateSO.getColNr() == 2) {
			MenuColumn_2 column_2 = new MenuColumn_2(menuTemplateSO.getMenuSO().getName());
			column_2.addTitleArea(menuTemplateSO.getCategory(), 2);
			String img = menuTemplateSO.getImgUrl() != null ? menuTemplateSO.getImgUrl() : "img/tourisme.JPG";

			MenuLink[] links = getMenulinks(menuTemplateSO);
			column_2.addImageAndLinks(img, links, menuTemplateSO.isLeftAlign(), menuTemplateSO.isButtonStyle());
			headerMenu.addMenu(column_2);
		}

		templateUi.add(headerMenu);
	}

	private MenuLink[] getMenulinks(MenuTemplateSO menuTemplateSO2) {
		MenuLink[] menuLinks = new MenuLink[menuTemplateSO2.getTemplateLinks().size()];
		int i = 0;
		for (String str : menuTemplateSO2.getTemplateLinks().values()) {
			menuLinks[i++] = new MenuLink(str, MethodsUtils.js_DoRun_Command(str));
		}
		return menuLinks;
	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
		}

		@Override
		public void onSave() {
			menuTemplateSO.setActive(activeUi.getValue());
			menuTemplateSO.setCategory(categoryUi.getValue());
			menuTemplateSO.setIndex(indexUi.getValue());
			menuTemplateSO.setLock(lockUi.getValue());
			menuTemplateSO.setName(nameUi.getValue());
			presenter.saveTemplate(menuTemplateSO);
		}

		@Override
		public void onCancel() {
		}
	}

	private String getLiens() {
		String result = "";
		for (Iterator<String> it = menuTemplateSO.getTemplateLinks().values().iterator(); it.hasNext();) {
			result += it.next();
			if (it.hasNext()) {
				result += "#";
			}
		}
		return result;
	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}

}
