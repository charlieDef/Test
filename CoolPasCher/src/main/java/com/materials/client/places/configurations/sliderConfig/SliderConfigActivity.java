package com.materials.client.places.configurations.sliderConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.APPObjectSO;
import com.materials.client.model.ByteDataSO;
import com.materials.client.model.SliderSO;
import com.materials.client.model.UserSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.model.slider.celllist.SliderListView;
import com.materials.client.widgets.model.slider.details.SliderDetailView;
import com.materials.client.widgets.model.slider.images.SliderImageDetailsView;
import com.materials.client.widgets.model.slider.images.SliderImagesListView;
import com.materials.client.widgets.model.slider.presentation.SliderPresentationView;
import com.materials.client.widgets.slider.jssor.preview.SliderPreviewView;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.slider.SliderAction;
import com.materials.shared.action.slider.SliderCommand;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.animate.Transition;

/**
 * 
 * For Configuration Activity
 * 
 * @author Charles Mouafo Deffo
 *
 */
public class SliderConfigActivity extends AbstractActivity implements SliderConfigView.Presenter {

	private AppClientFactory clientFactory;
	private SliderConfigPlace place;
	private SliderConfigView view;

	private final SliderListPresenter sliderListPresenter;
	private final SliderDetailPresenter sliderDetailPresenter;
	private final SliderImageListPresenter sliderImageListPresenter;
	private final SliderImageDetailPresenter sliderImageDetailPresenter;
	private final SliderPreviewPresenter sliderPreviewPresenter;

	public SliderConfigActivity(AppClientFactory clientFactory, SliderConfigPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;

		view = clientFactory.getSliderConfigView();
		sliderListPresenter = new SliderListPresenter(view.getSliderListView());
		sliderDetailPresenter = new SliderDetailPresenter(view.getSliderDetailView());
		sliderImageListPresenter = new SliderImageListPresenter(view.getSliderImagesListView());
		sliderImageDetailPresenter = new SliderImageDetailPresenter(view.getSliderImageDetailsView());
		sliderPreviewPresenter = new SliderPreviewPresenter(view.getSliderPreviewView());
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		UserSO userSO = clientFactory.getActualUserSO();
		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			panel.setWidget(view);
			sliderListPresenter.initData();
		} else {
			MaterialPanel materialPanel = clientFactory.getEmptyStatePanel();
			panel.setWidget(materialPanel);
			ClientUtils.animeIn(materialPanel, Transition.FADEINLEFT);
		}
		clientFactory.scrollAppToTop(false);
	}

	class SliderImageDetailPresenter implements SliderImageDetailsView.Presenter {

		private SliderImageDetailsView sliderImageDetailsView;

		public SliderImageDetailPresenter(SliderImageDetailsView sliderImageDetailsView) {
			this.sliderImageDetailsView = sliderImageDetailsView;
			this.sliderImageDetailsView.setPresenter(this);

		}

		@Override
		public void swipeBackToImagesList() {
			view.goBackToImagesList((MaterialWidget) sliderImageDetailsView);

		}

		@Override
		public void saveImage(ByteDataSO byteDataSO) {
			DBAction dbAction = byteDataSO.getId() <= -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new SliderAction(dbAction, byteDataSO), x -> {
				ByteDataSO saved = (ByteDataSO) x.getObject();
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					view.getSliderImagesListView().getCellList().addItem(saved);
				}
				view.getSliderImagesListView().getCellList().refresh();
			});
		}

	}

	class SliderPreviewPresenter implements SliderPreviewView.Presenter {

		private SliderPreviewView sliderPreviewView;

		public SliderPreviewPresenter(SliderPreviewView sliderPreviewView) {
			this.sliderPreviewView = sliderPreviewView;
			this.sliderPreviewView.setPresenter(this);
		}

		@Override
		public void swipeBackToSliderList() {
			view.goBackToSliderList((MaterialWidget) sliderPreviewView);

		}

	}

	class SliderPresentationPresenter implements SliderPresentationView.Presenter {

		SliderPresentationView sliderPresentationView;

		public SliderPresentationPresenter(SliderPresentationView sliderPresentationView) {
			this.sliderPresentationView = sliderPresentationView;
			sliderPresentationView.setPresenter(this);
		}

		@Override
		public void backToSliderList() {
			view.goBackToSliderList((MaterialWidget) sliderPresentationView);
		}

		@Override
		public void saveSliderPresentation(SliderSO sliderSO) {

			DBAction dbAction = sliderSO.getId() <= -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new SliderAction(dbAction, sliderSO), x -> {
				SliderSO saved = (SliderSO) x.getObject();
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					view.getSliderListView().getCellList().addItem(saved);
				}
				view.getSliderListView().getCellList().refresh();
			});
		}

	}

	class SliderImageListPresenter implements SliderImagesListView.Presenter {

		private SliderImagesListView sliderImagesListView;
		private SliderSO sliderSO;

		public SliderImageListPresenter(SliderImagesListView sliderImagesListView) {
			this.sliderImagesListView = sliderImagesListView;
			this.sliderImagesListView.setPresenter(this);
		}

		@Override
		public void swipeBackToSliderList() {
			view.goBackToSliderList((MaterialWidget) sliderImagesListView);
		}

		@Override
		public void swipeToSliderImageDetail(ByteDataSO byteDataSO) {

			view.getSliderImageDetailsView().setByteData(byteDataSO);
			view.showSliderImageDetail();

		}

		@Override
		public void newSliderImage() {
			ByteDataSO byteDataSO = new ByteDataSO();
			byteDataSO.setId(-10);
			byteDataSO.setHeight("");
			byteDataSO.setWidth("");
			byteDataSO.setPadding(false);
			byteDataSO.setIndex(0);
			byteDataSO.setSliderSO(sliderSO);

			view.getSliderImageDetailsView().setByteData(byteDataSO);
			view.showSliderImageDetail();

		}

		@Override
		public void deleteSliderImages(List<ByteDataSO> byteDataSOs) {
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new SliderAction(DBAction.DELETE, new ArrayList<APPObjectSO>(byteDataSOs)),
							x -> {
								if (x.getBooleanValue()) {
									sliderImagesListView.getCellList().removeSelected();
								}
							});
				}
			});
		}

		private void initData(SliderSO sliderSO) {
			this.sliderSO = sliderSO;
			sliderImagesListView.setData(sliderSO);
		}

	}

	class SliderDetailPresenter implements SliderDetailView.Presenter {

		private SliderDetailView sliderDetailView;
		private SliderSO sliderSO;

		public SliderDetailPresenter(SliderDetailView sliderDetailView) {
			this.sliderDetailView = sliderDetailView;
			this.sliderDetailView.setPresenter(this);
		}

		@Override
		public void backToSliderList() {
			view.goBackToSliderList((MaterialWidget) sliderDetailView);

		}

		private void initData() {

		}

		@Override
		public void saveSlider(SliderSO sliderSO) {
			this.sliderSO = sliderSO;

			DBAction dbAction = sliderSO.getId() <= -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new SliderAction(dbAction, sliderSO), x -> {
				SliderSO saved = (SliderSO) x.getObject();
				this.sliderSO = saved;
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					view.getSliderListView().getCellList().addItem(saved);
				}
				view.getSliderListView().getCellList().refresh();
			});
		}
	}

	class SliderListPresenter implements SliderListView.Presenter {

		private SliderListView sliderListView;

		public SliderListPresenter(SliderListView sliderListView) {
			this.sliderListView = sliderListView;
			this.sliderListView.setPresenter(this);
		}

		@Override
		public void showSliderContent(SliderSO sliderSO) {

			sliderImageListPresenter.initData(sliderSO);
			view.showSliderImagesList();
		}

		@Override
		public void showSliderPreview(SliderSO sliderSO) {
			view.getSliderPreviewView().setSliderSO(sliderSO);

			view.showSliderPreview();

		}

		@Override
		public void swipeToSliderDetail(SliderSO sliderSO) {
			view.getSliderDetailView().setSliderSO(sliderSO);
			view.showSliderDetail();
		}

		@Override
		public void newSlider(boolean presentation) {
			SliderSO slider = new SliderSO();
			slider.setId(-10);
			slider.setTitel("titre");
			slider.setTitel1("titre1");
			slider.setType("");
			slider.setTitel2("titre3");
			slider.setCreationDate(new Date());
			slider.setActive(false);
			slider.setLock(true);
			slider.setPresentation(presentation);
			// slider.setSliderImageUrl(Index.APP_RESOURCE.slider1().getSafeUri().asString());

			swipeToSliderDetail(slider);
			view.getSliderDetailView().setEdit();
		}

		@Override
		public void deleteSliders(List<SliderSO> sliderSOs) {
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new SliderAction(DBAction.DELETE, new ArrayList<APPObjectSO>(sliderSOs)),
							x -> {
								if (x.getBooleanValue()) {
									sliderListView.getCellList().removeSelected();
								}
							});
				}
			});
		}

		private void initData() {
			clientFactory.execute(new SliderAction(DBAction.READ, SliderCommand.ALL_SLIDER, null), result -> {
				List<SliderSO> list = MethodsUtils.castList(result.getObjects(), SliderSO.class);
				sliderListView.setData(list);
				view.showSliderList();
			});
		}

		// @Override
		// public void newPresentationSlider() {
		// SliderSO sliderSO = new SliderSO();
		// sliderSO.setId(-10);
		// sliderSO.setTitel("titre");
		// sliderSO.setTitel1("titre1");
		// sliderSO.setTitel2("titre3");
		// sliderSO.setPresentation(true);
		// sliderSO.setCreationDate(new Date());
		// sliderSO.setByteDataSOs(getNewByteDataSOs(sliderSO));
		//
		// // swipeToSliderPresentation(sliderSO);
		// }

	}

	// class SliderContentAreaListPresenter implements
	// SliderContentAreaListView.Presenter {
	// private ContentSO contentSO;
	// private SliderContentAreaListView sliderContentAreaListView;
	//
	// public SliderContentAreaListPresenter(SliderContentAreaListView
	// sliderContentAreaListView) {
	// this.sliderContentAreaListView = sliderContentAreaListView;
	// sliderContentAreaListView.setPresenter(this);
	// }
	//
	// // @Override
	// // public void swipeToSliderContentAreaDetail(CAreaSO cAreaSO) {
	// // view.getSliderContentAreaView().setContentArea(cAreaSO);
	// // view.showSliderContentAreaDetail();
	// //
	// // }
	//
	// @Override
	// public void newSliderContentArea(String type) {
	// CAreaSO areaSO = new CAreaSO();
	// areaSO.setId(-10);
	// areaSO.setAreaType(type);
	// areaSO.setCreationDate(new Date());
	// areaSO.setLock(true);
	// areaSO.setContentSO(contentSO);
	// areaSO.setCreator("UNKNOW");
	// // areaSO.setImageUrl(type.equals("H") ? "img/slider1.jpg" :
	// "img/african.jpg");
	// areaSO.setTextContent("Votre text ici");
	// areaSO.setTitel("Votre Titre");
	// areaSO.setIndex(0);
	// areaSO.setRandomId(null);
	// swipeToSliderContentAreaDetail(areaSO);
	// }
	//
	// private void initData(ContentSO contentSO) {
	// this.contentSO = contentSO;
	// sliderContentAreaListView.setData(contentSO);
	// }
	//
	// @Override
	// public void deleteSliderContentAreas(List<CAreaSO> cAreaSOs) {
	// MBoaOnlineUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
	// MBoaOnlineUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer
	// ?", ok -> {
	// if (ok) {
	// clientFactory.execute(new CAreaAction(DBAction.DELETE, new
	// ArrayList<APPObjectSO>(cAreaSOs)), x -> {
	// if (x.getBooleanValue()) {
	// cAreaSOs.forEach(ca -> contentSO.getcAreaSOs().remove(ca));
	// sliderContentAreaListView.getCellList().removeSelected();
	// }
	// });
	// }
	// });
	// }
	//
	// @Override
	// public void swipeBackToSliderList() {
	// view.goBackToSliderList((MaterialWidget) sliderContentAreaListView);
	// }
	// }

	// class SliderContentAreaPresenter implements ContentAreaView.Presenter {
	//
	// private ContentAreaView contentAreaView;
	//
	// public SliderContentAreaPresenter(ContentAreaView contentAreaView) {
	// this.contentAreaView = contentAreaView;
	// contentAreaView.setPresenter(this);
	// }
	//
	// // @Override
	// // public void backToContentAreaList() {
	// // view.goBackToSliderContentAreaList((MaterialWidget) contentAreaView);
	// // }
	//
	// @Override
	// public void saveContentArea(CAreaSO cAreaSO) {
	// DBAction dbAction = cAreaSO.getId() <= -10 ? DBAction.SAVE_NEW :
	// DBAction.UPDATE;
	// clientFactory.execute(new CAreaAction(dbAction, cAreaSO), x -> {
	//
	// CAreaSO saved = (CAreaSO) x.getObject();
	// if (dbAction.equals(DBAction.SAVE_NEW)) {
	// cAreaSO.setId(saved.getId());
	// view.getSliderContentAreaListView().getCellList().addItem(saved);
	// }
	// view.getSliderContentAreaListView().getCellList().refresh();
	// });
	// }
	// }

	private List<ByteDataSO> getNewByteDataSOs(SliderSO sliderSO) {

		List<ByteDataSO> dataSOs = new ArrayList<ByteDataSO>();
		int index = -18;
		for (int i = 1; i < 9; i++) {
			ByteDataSO byteDataSO = new ByteDataSO();
			byteDataSO.setId(index);
			byteDataSO.setIndex(i);
			byteDataSO.setSliderSO(sliderSO);
			dataSOs.add(byteDataSO);
			index += 1;
		}
		return dataSOs;
	}

}
