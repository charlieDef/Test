package com.materials.client.widgets.model.content.annonce.newone;

import java.util.function.Consumer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.model.ContentSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.buttonbar.ButtonBarEdit2;
import com.materials.client.widgets.buttonbar.ButtonBarEditHandler;
import com.materials.client.widgets.model.content.simple.NewAnnonce;
import com.materials.client.widgets.model.content.simple.NewAnnonceDetails;
import com.materials.client.widgets.model.content.simple.NewAnnonceDetailsView;
import com.materials.client.widgets.model.content.simple.NewAnnonceView;
import com.materials.client.widgets.tab.MDTab;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

public class EditAbleAnnonce extends MaterialPanel implements EditAbleAnnonceView {

	private NewAnnonceDetailsView newAnnonceDetailsView;
	private NewAnnonceView newAnnonceView;
	private ContentSO contentSO;
	private MDTab mdTab;
	private Presenter presenter;
	private ButtonBarEdit2 buttonBarEdit;
	private boolean isNewAnnonce;

	public EditAbleAnnonce(boolean isNewAnnonce) {
		this.isNewAnnonce = isNewAnnonce;

		setMarginTop(0);
		mdTab = getMDTab();
		add(mdTab);

		Consumer<Integer> consumer = x -> {
			if (x == 0) {
				ContentSO contentAnnonceDetails = newAnnonceDetailsView.getContentSO();
				this.newAnnonceView.updateContentSO(contentAnnonceDetails);
			}
		};
		mdTab.setConsumer(consumer);

		if (isNewAnnonce) {
			ClientUtils.addTimer(x -> {
				buttonBarEdit.clickEdit();
			}, 200);
		}

	}

	@Override
	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;
		newAnnonceView.setContent(contentSO);
		newAnnonceDetailsView.setContentSO(contentSO);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	private MDTab getMDTab() {

		this.newAnnonceDetailsView = new NewAnnonceDetails();
		Widget widget = (Widget) newAnnonceDetailsView;
		widget.getElement().getStyle().setMarginTop(10, Unit.PX);
		this.newAnnonceView = new NewAnnonce();

		MDTab mdTab = isNewAnnonce ? new MDTab("s10 m10 l12") : new MDTab();
		mdTab.setTransition(Transition.FADE_IN_IMAGE);
		mdTab.addnewTab("Contenu", IconType.DESCRIPTION, MDTab.TAB_1, (Widget) newAnnonceView);
		mdTab.addnewTab("Cover", IconType.PHOTO_SIZE_SELECT_ACTUAL, MDTab.TAB_2, (Widget) newAnnonceDetailsView);

		String titel = "Nouvelle annonce";
		if (!isNewAnnonce) {
			MaterialIcon icon = new MaterialIcon(IconType.KEYBOARD_BACKSPACE);
			icon.setDepth(0);
			icon.setIconColor(Color.AMBER_DARKEN_3);
			icon.setIconFontSize(40, Unit.PX);

			mdTab.addWidgetToIconUi(icon);
			icon.addClickHandler(x -> presenter.goBack());
			titel = "Details de l'annonce";
		}

		mdTab.setHeaderTitel(titel);

		buttonBarEdit = new ButtonBarEdit2();
		buttonBarEdit.setIconSize(IconSize.SMALL);
		buttonBarEdit.setIconColor(Color.AMBER_DARKEN_3);
		buttonBarEdit.setBarEditHandler(new BBEditHandler());

		Consumer<Boolean> consumer = x -> {
			buttonBarEdit.setSaveEnabled(newAnnonceDetailsView.enableSaveButton() && newAnnonceView.enableSaveButton());
		};
		newAnnonceDetailsView.setSaveControllConsumer(consumer);
		newAnnonceView.setSaveControllConsumer(consumer);

		mdTab.addWidgetToHeader2(buttonBarEdit, false);
		return mdTab;
	}

	@Override
	public NewAnnonceDetailsView getNewAnnonceDetailsView() {
		return newAnnonceDetailsView;
	}

	@Override
	public NewAnnonceView getNewAnnonceView() {
		return newAnnonceView;
	}

	@Override
	public void setEdit(boolean edit) {
		if (edit) {
			buttonBarEdit.clickEdit();
		} else {
			buttonBarEdit.cancelEditMode();
		}

	}

	class BBEditHandler implements ButtonBarEditHandler {
		@Override
		public void onEdit() {
			newAnnonceView.setEdit(true);
			newAnnonceDetailsView.setEdit(true);
		}

		@Override
		public void onSave() {
			newAnnonceDetailsView.extractFromDetail(contentSO);
			newAnnonceView.extractFromAnnonce(contentSO);
			// contentSO = newAnnonceView.getContentSO();
			// ContentSO detailContentSO = newAnnonceDetailsView.getContentSO();
			// contentSO.setTitel(detailContentSO.getTitel());
			// contentSO.setDescription(detailContentSO.getDescription());
			// contentSO.setDescription2(detailContentSO.getDescription2());
			// contentSO.setRandomId(detailContentSO.getRandomId());
			presenter.saveContentSO(contentSO);
		}

		@Override
		public void onCancel() {

			if (isNewAnnonce) {
				presenter.cancel();

			} else {
				ClientUtils.addTimer(x -> {
					newAnnonceView.setEdit(false);
					newAnnonceDetailsView.setEdit(false);
				}, 100);
			}

		}
	}

	@Override
	public void selectTab(String id) {
		mdTab.selectTab(id);
	}

	public void enableSave(boolean enable) {
		buttonBarEdit.setSaveEnabled(enable);
	}

}
