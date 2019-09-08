package com.materials.client.widgets.model.menu;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.MenuSO;
import com.materials.client.widgets.base.BaseDetails2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.form.MDFormPanel;
import com.materials.shared.MethodsUtils;

import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.addins.client.combobox.events.SelectItemEvent;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextBox;

public class MenuDetails extends BaseDetails2 implements MenuDetailsView {

	private static MenuDetailsUiBinder uiBinder = GWT.create(MenuDetailsUiBinder.class);

	interface MenuDetailsUiBinder extends UiBinder<Widget, MenuDetails> {
	}

	private MenuSO menuSO;
	private Presenter presenter;

	private Map<Long, String> map = new LinkedHashMap<>();

	@UiField
	MaterialLabel textHeaderLabelUi;

	@UiField
	MDFormPanel mdFormUi;

	@UiField
	MaterialIntegerBox indexUi;

	@UiField
	MaterialTextBox nameUi, descriptionUi, textInfoUi;

	@UiField
	MaterialCheckBox actifUi, lockUi, hasContentUi, ddAlignRightUi, memberOnlyUi, menuCategoryUi;

	@UiField
	MaterialComboBox<String> parentMenuListBoxUi, categoryListUi, typeListUi;

	@UiHandler("backButtonUi")
	void onBackToContentList(ClickEvent event) {
		mdFormUi.resetMode();
		presenter.backToMenuList();
	}

	public MenuDetails(boolean configure) {
		super(configure);
		add(uiBinder.createAndBindUi(this));

		initListBoxes();

		mdFormUi.setFormHandler(new BBarHandler());

		handleEditEnable();
	}

	private void initListBoxes() {
		parentMenuListBoxUi.setAllowBlank(true);
		parentMenuListBoxUi.setPlaceholder("--//--");
		categoryListUi.setAcceptableValues(
				Arrays.asList("", "Normal_Single", "Normal_Multi", "Photo", "Video", "Document", "Autres"));
		typeListUi.setAcceptableValues(Arrays.asList("BaseMenu", "SubMenu"));

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setMenuSO(MenuSO menuSO) {
		textHeaderLabelUi.setText("Details::: " + menuSO.getName());
		this.menuSO = menuSO;

		// parentMenuListBoxUi.set
		actifUi.setValue(menuSO.isActive());
		menuCategoryUi.setValue(menuSO.isMenuCategory());
		hasContentUi.setValue(menuSO.isHasContents());
		nameUi.setValue(menuSO.getName());
		categoryListUi.setSingleValue(menuSO.getCategory());
		ddAlignRightUi.setValue(menuSO.isDropDownAlignRight());
		memberOnlyUi.setValue(menuSO.isMemberOnly());
		typeListUi.setSingleValue(menuSO.getType());
		lockUi.setValue(menuSO.isLock());
		indexUi.setValue(menuSO.getIndex());
		descriptionUi.setValue(menuSO.getDescription());
		textInfoUi.setValue(menuSO.getTextInfo());

		if (menuSO.getParentMenuSo() != null) {
			parentMenuListBoxUi.setSingleValue(menuSO.getParentMenuSo().getName());
		} else {
			parentMenuListBoxUi.setSelectedIndex(0);
		}

		textHeaderLabelUi.setText("Menu::" + MethodsUtils.getRecursiveMenu(menuSO));
		// parentMenuListBoxUi.setEnabled(false);
		// categoryListUi.setEnabled(false);
	}

	@Override
	public void setEdit() {
		mdFormUi.edit();

	}

	class BBarHandler implements ButtonBarEditHandler {

		@Override
		public void onEdit() {
			boolean enableList = menuSO.getType() != null && menuSO.getType().equals("SubMenu");
			parentMenuListBoxUi.setEnabled(enableList);
			categoryListUi.setEnabled(enableList);

		}

		@Override
		public void onSave() {

			menuSO.setActive(actifUi.getValue());
			menuSO.setName(nameUi.getValue());
			menuSO.setCategorie(categoryListUi.getSingleValue());
			menuSO.setType(typeListUi.getSingleValue());
			menuSO.setLock(lockUi.getValue());
			menuSO.setIndex(indexUi.getValue());
			menuSO.setHasContents(hasContentUi.getValue());
			menuSO.setMenuCategory(menuCategoryUi.getValue());
			menuSO.setMemberOnly(memberOnlyUi.getValue());
			menuSO.setDescription(descriptionUi.getValue());
			menuSO.setTextInfo(textInfoUi.getValue());
			menuSO.setDropDownAlignRight(ddAlignRightUi.getValue());
			if (parentMenuListBoxUi.getValue() != null && !parentMenuListBoxUi.getValue().isEmpty()) {
				menuSO.setParentMenuSo(new MenuSO(getId(parentMenuListBoxUi.getSingleValue())));
			} else {
				menuSO.setParentMenuSo(null);
			}
			presenter.saveMenu(menuSO);
		}

		@Override
		public void onCancel() {
			parentMenuListBoxUi.setEnabled(false);
			categoryListUi.setEnabled(false);
			categoryListUi.setSingleValue(menuSO.getCategory());
			typeListUi.setSingleValue(menuSO.getType());

			if (menuSO.getId() == -10) {
				presenter.backToMenuList();
			}
		}
	}

	private Long getId(String name) {
		long id = 0;
		for (Long theId : map.keySet()) {
			if (map.get(theId).equals(name)) {
				return theId;
			}
		}
		return id;

	}

	@Override
	public void initMenuSelectBoxData(Map<Long, String> map) {

		this.map = map;

		List<String> list = new LinkedList<>();
		list.add("");
		list.addAll(map.values());
		parentMenuListBoxUi.setAcceptableValues(list);
	}

	@UiHandler("typeListUi")
	void onMenuTyPeChange(SelectItemEvent<String> event) {

		String type = typeListUi.getSingleValue();
		if (type != null && type.equals("BaseMenu")) {
			parentMenuListBoxUi.setEnabled(false);
			categoryListUi.setEnabled(false);
			categoryListUi.setSelectedIndex(0);
			parentMenuListBoxUi.setSelectedIndex(0);

			if (menuSO.getParentMenuSo() != null) {
				parentMenuListBoxUi.setSingleValue(menuSO.getParentMenuSo().getName());
			}

		} else {
			parentMenuListBoxUi.setEnabled(true);
			categoryListUi.setEnabled(true);
			parentMenuListBoxUi.setSelectedIndex(1);
			categoryListUi.setSelectedIndex(0);
		}

	}

	@Override
	public void setMDFormPanel() {
		mdFormPanel = mdFormUi;

	}

}
