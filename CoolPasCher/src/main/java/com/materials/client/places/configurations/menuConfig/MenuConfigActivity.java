package com.materials.client.places.configurations.menuConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.APPObjectSO;
import com.materials.client.model.CAreaSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.MenuSO;
import com.materials.client.model.MenuTemplateSO;
import com.materials.client.model.UserSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.area.ContentAreaView;
import com.materials.client.widgets.model.content.ContentDetailsView;
import com.materials.client.widgets.model.content.celllist.ContentAreaListView;
import com.materials.client.widgets.model.content.celllist.ContentListView;
import com.materials.client.widgets.model.content.doc.ContentDocumentView;
import com.materials.client.widgets.model.content.pic.ContentPicturesView;
import com.materials.client.widgets.model.menu.MenuDetailsView;
import com.materials.client.widgets.model.menu.celllist.MenuListView;
import com.materials.client.widgets.model.mtemplate.celllist.MenuTemplateListView;
import com.materials.client.widgets.model.mtemplate.details.single.SingleTemplateDetailView;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.carea.CAreaAction;
import com.materials.shared.action.content.ContentAction;
import com.materials.shared.action.menu.MenuAction;
import com.materials.shared.action.menu.MenuCommand;
import com.materials.shared.action.mtemplate.MenuTemplateAction;

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
public class MenuConfigActivity extends AbstractActivity implements MenuConfigView.Presenter {

	private AppClientFactory clientFactory;
	private MenuConfigPlace place;
	private final MenuConfigView view;

	private final MenuListPresenter menuListPresenter;
	private final MenuDetailPresenter menuDetailPresenter;

	private final ContentAreaPresenter contentAreaPresenter;
	private final ContentAreaListPresenter contentAreaListPresenter;
	private final ContentListPresenter contentListPresenter;
	private final ContentDetailsPresenter contentDetailsPresenter;

	private final MenuTemplateListPresenter templateListPresenter;
	private final SingleTemplateDetailPresenter singleTemplateDetailPresenter;

	private final CDocumentPresenter documentPresenter;
	private final CPicturesPresenter picturesPresenter;

	public MenuConfigActivity(AppClientFactory clientFactory, MenuConfigPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;
		view = clientFactory.getMenuConfigView();

		menuListPresenter = new MenuListPresenter(view.getMenuListView());
		menuDetailPresenter = new MenuDetailPresenter(view.getMenuDetailsView());
		contentAreaPresenter = new ContentAreaPresenter(view.getContentAreaView());
		templateListPresenter = new MenuTemplateListPresenter(view.getMenuTemplateListView());
		singleTemplateDetailPresenter = new SingleTemplateDetailPresenter(view.getSingleTemplateDetailView());
		contentAreaListPresenter = new ContentAreaListPresenter(view.getContentAreaListView());
		documentPresenter = new CDocumentPresenter(view.getContentDocumentView());
		picturesPresenter = new CPicturesPresenter(view.getContentPicturesView());
		contentListPresenter = new ContentListPresenter(view.getContentListView());
		contentDetailsPresenter = new ContentDetailsPresenter(view.getContentDetailsView());

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		UserSO userSO = clientFactory.getActualUserSO();

		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			panel.setWidget(view);
			menuListPresenter.initData();
			menuDetailPresenter.initMenuSelectBoxData();
		} else {
			MaterialPanel materialPanel = clientFactory.getEmptyStatePanel();
			panel.setWidget(materialPanel);
			ClientUtils.animeIn(materialPanel, Transition.FADEINLEFT);
		}

		clientFactory.scrollAppToTop(false);

	}

	class ContentAreaListPresenter implements ContentAreaListView.Presenter {
		private ContentSO contentSO;
		private ContentAreaListView contentAreaListView;

		public ContentAreaListPresenter(ContentAreaListView contentAreaListView) {
			this.contentAreaListView = contentAreaListView;
			contentAreaListView.setPresenter(this);
		}

		@Override
		public void swipeToContentAreaDetail(CAreaSO cAreaSO) {
			contentAreaPresenter.setContentAreaSO(cAreaSO);
			view.showContentAreaDetail();

		}

		@Override
		public void newContentArea(String type) {
			CAreaSO areaSO = new CAreaSO();
			areaSO.setId(-10);
			areaSO.setAreaType(type);
			areaSO.setCreationDate(new Date());
			areaSO.setLock(true);
			areaSO.setCreator("UNKNOW");
			// areaSO.setImageUrl(type.equals(CAreaSO.TYPE_HOR) ?
			// "img/slider1.jpg" :
			// "img/african.jpg");
			areaSO.setTextContent("Votre text ici");
			areaSO.setTitel("Votre Titre");
			areaSO.setIndex(0);
			areaSO.setRandomId(null);
			areaSO.setContentSO(contentSO);
			// contentSO.addCAreaSOs(areaSO);

			swipeToContentAreaDetail(areaSO);
		}

		private void initData(ContentSO contentSO) {
			this.contentSO = contentSO;
			contentAreaListView.setData(contentSO);
		}

		@Override
		public void deleteContentAreas(List<CAreaSO> cAreaSOs) {

			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new CAreaAction(DBAction.DELETE, new ArrayList<APPObjectSO>(cAreaSOs)), x -> {
						if (x.getBooleanValue()) {
							cAreaSOs.forEach(ca -> contentSO.getcAreaSOs().remove(ca));
							contentAreaListView.getCellList().removeSelected();
						}
					});
				}
			});

		}

		@Override
		public void swipeBackToContentList() {
			view.backToContentList((MaterialWidget) contentAreaListView);
		}

		private void addNewCAreaSO(CAreaSO cAreaSO) {
			contentAreaListView.getCellList().addItem(cAreaSO);
		}

		private void refreschList() {
			contentAreaListView.getCellList().refresh();
		}

		public void setData(ContentSO contentSO) {
			this.contentSO = contentSO;
			contentAreaListView.setData(contentSO);
		}

	}

	class ContentAreaPresenter implements ContentAreaView.Presenter {

		private ContentAreaView contentAreaView;
		private CAreaSO areaSO;

		public ContentAreaPresenter(ContentAreaView contentAreaView) {
			this.contentAreaView = contentAreaView;
			contentAreaView.setPresenter(this);
		}

		@Override
		public void backToContentAreaList() {
			view.backToContentAreaList((MaterialWidget) contentAreaView);
		}

		@Override
		public void saveContentArea(CAreaSO cAreaSO) {

			DBAction dbAction = cAreaSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new CAreaAction(dbAction, cAreaSO), x -> {
				this.areaSO = (CAreaSO) x.getObject();
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					contentAreaListPresenter.addNewCAreaSO(areaSO);
				}
				contentAreaListPresenter.refreschList();
			});
		}

		private void setContentAreaSO(CAreaSO areaSO) {
			this.areaSO = areaSO;
			contentAreaView.setContentArea(areaSO);
		}
	}

	class MenuListPresenter implements MenuListView.Presenter {
		private MenuListView menuListView;

		public MenuListPresenter(MenuListView menuListView) {
			this.menuListView = menuListView;
			this.menuListView.setPresenter(this);
		}

		@Override
		public void swipeToMenuDetail(MenuSO menuSO) {
			menuDetailPresenter.setMenuSO(menuSO);
			view.showMenuDetail();
		}

		@Override
		public void newMenu() {
			if (!menuListView.getCellList().isMultiModus()) {
				MenuSO menuSO = new MenuSO("Titre ici");
				menuSO.setId(-10);
				menuSO.setActive(false);
				menuSO.setLock(false);
				menuSO.setParentMenuSo(null);
				menuSO.setCategorie("");

				menuDetailPresenter.initMenuSelectBoxData();
				swipeToMenuDetail(menuSO);
				view.getMenuDetailsView().setEdit();
			}
		}

		@Override
		public void deleteMenus(List<MenuSO> menuSOs) {

			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new MenuAction(DBAction.DELETE, new ArrayList<APPObjectSO>(menuSOs)), x -> {
						if (x.getBooleanValue()) {
							for (MenuSO menuSO : menuSOs) {
								menuSO.getSubMenuSos().forEach(sub -> menuListView.getCellList().removeItem(sub));
							}
							menuListView.getCellList().removeSelected();
						}
					});
				}
			});

		}

		private void initData() {
			showLoader(true);

			clientFactory.execute(new MenuAction(DBAction.READ, MenuCommand.ALL_BASE_MENU, null), result -> {
				menuListView.getCellList().getData().clear();
				List<MenuSO> list = MethodsUtils.castList(result.getObjects(), MenuSO.class);
				// Collections.sort(list, Comparator.comparing(MenuSO::getId));
				list.sort((MenuSO o1, MenuSO o2) -> Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId())));

				for (MenuSO menuSO : list) {
					menuListView.getCellList().addItem(menuSO);
					addSumItem(menuSO);
					// menuListView.getCellList().addItem(menuSO);
					// if (menuSO.getSubMenuSos() != null &&
					// !menuSO.getSubMenuSos().isEmpty()) {
					// for (MenuSO subMenuSO : menuSO.getSubMenuSos()) {
					// menuListView.getCellList().addItem(subMenuSO);
					// }
					// }
				}
				view.showMenuList();
				showLoader(false);
			});
		}

		private void addSumItem(MenuSO menuSO) {
			if (menuSO.getSubMenuSos() != null && !menuSO.getSubMenuSos().isEmpty()) {
				for (MenuSO subMenuSO : menuSO.getSubMenuSos()) {
					menuListView.getCellList().addItem(subMenuSO);
					if (subMenuSO.getSubMenuSos() != null && !subMenuSO.getSubMenuSos().isEmpty()) {
						addSumItem(subMenuSO);
					}
				}
			}
		}

		// @Override
		// public void swipeToMenuList() {
		// view.backToMenuList((MaterialWidget) menuTemplateListView);
		// }

		@Override
		public void showContentList(MenuSO menuSO) {
			// contentListPresenter.contentListView.enableTools(menuSO.getCategory().equals("Photo"));
			contentListPresenter.initData(menuSO);
			view.showContentList();
		}

		private void refreschList() {
			menuListView.getCellList().refresh();
		}

		private void reloadList(List<APPObjectSO> list0, MenuSO select) {
			menuListView.getCellList().getData().clear();
			List<MenuSO> list = MethodsUtils.castList(list0, MenuSO.class);
			list.sort((MenuSO o1, MenuSO o2) -> Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId())));

			for (MenuSO menuSO : list) {
				menuListView.getCellList().addItem(menuSO);

				addSumItem(menuSO);
				// if (menuSO.getSubMenuSos() != null &&
				// !menuSO.getSubMenuSos().isEmpty()) {
				// for (MenuSO subMenuSO : menuSO.getSubMenuSos()) {
				// menuListView.getCellList().addItem(subMenuSO);
				// }
				// }
			}
			menuListView.getCellList().selectItem(select);
		}

		private void showLoader(boolean shows) {
			menuListView.getCellList().showLoader(shows);
		}

		@Override
		public void showTemplateLayoutList(MenuSO menuSO) {
			templateListPresenter.initData(menuSO);
			view.showMenuTemplateList();
		}
	}

	class MenuDetailPresenter implements MenuDetailsView.Presenter {
		private MenuDetailsView menuDetailsView;
		private MenuSO menuSO;
		private boolean reloadList = false;
		private List<APPObjectSO> list = new ArrayList<>();

		public MenuDetailPresenter(MenuDetailsView menuDetailsView) {
			this.menuDetailsView = menuDetailsView;
			menuDetailsView.setPresenter(this);
		}

		@Override
		public void backToMenuList() {

			if (reloadList) {
				menuListPresenter.reloadList(list, menuSO);
				list.clear();
			}

			view.backToMenuList((MaterialWidget) menuDetailsView);
		}

		@Override
		public void saveMenu(MenuSO menuSO) {
			DBAction dbAction = menuSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new MenuAction(dbAction, menuSO), x -> {
				MenuSO saved = (MenuSO) x.getObject();
				this.menuSO = saved;
				this.list = x.getObjects();
				// menuListPresenter.reloadList(x.getObjects(), saved);
				reloadList = true;
			});
		}

		public void setMenuSO(MenuSO menuSO) {
			reloadList = false;
			list.clear();
			this.menuSO = menuSO;
			menuDetailsView.setMenuSO(menuSO);
		}

		private void initMenuSelectBoxData() {
			menuDetailsView.initMenuSelectBoxData(clientFactory.getAnnoncesCategories());
		}

	}

	class SingleTemplateDetailPresenter implements SingleTemplateDetailView.Presenter {

		private final SingleTemplateDetailView templateDetailView;
		private MenuTemplateSO menuTemplateSO;

		public SingleTemplateDetailPresenter(SingleTemplateDetailView templateDetailView) {
			this.templateDetailView = templateDetailView;
			this.templateDetailView.setPresenter(this);
		}

		@Override
		public void backToTemplateList() {
			view.backToTemplateList((MaterialWidget) templateDetailView);
			templateListPresenter.handleIconVisibility();
		}

		@Override
		public void saveTemplate(MenuTemplateSO menuTemplateSO) {
			DBAction dbAction = menuTemplateSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new MenuTemplateAction(dbAction, menuTemplateSO), x -> {
				MenuTemplateSO saved = (MenuTemplateSO) x.getObject();
				this.menuTemplateSO = saved;
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					templateListPresenter.addNewMenuTemplateSO(menuTemplateSO);
				}
				templateListPresenter.refreschList();
			});
		}

		private void setTemplateSO(MenuTemplateSO menuTemplateSO) {
			templateDetailView.setMenuTemplateSO(menuTemplateSO);
		}

		private void edit() {
			templateDetailView.setEdit();
		}

	}

	class ContentListPresenter implements ContentListView.Presenter {

		private ContentListView contentListView;
		private MenuSO menuSO;

		public ContentListPresenter(ContentListView contentListView) {
			this.contentListView = contentListView;
			contentListView.setPresenter(this, x -> view.backToMenuList((MaterialWidget) contentListView));
		}

		@Override
		public void showContentTools(ContentSO contentSO) {

			picturesPresenter.setContentSO(contentSO);
			view.showContentPictures();
		}

		@Override
		public void swipeToContentDetail(ContentSO contentSO, boolean isNew) {

			if (isNew) {
				contentDetailsPresenter.setContentSO(contentSO);
				view.showContentDetails();
			} else {
				switch (contentSO.getType()) {

				case ContentSO.TYPE_IMAGE: {
					contentDetailsPresenter.setContentSO(contentSO);
					view.showContentDetails();
					// picturesPresenter.setContentSO(contentSO);
					// view.showContentPictures();
				}
					break;
				case ContentSO.TYPE_VIDEO: {
					contentDetailsPresenter.setContentSO(contentSO);
					view.showContentDetails();
				}
					break;

				default: {
					contentDetailsPresenter.setContentSO(contentSO);
					view.showContentDetails();
				}
					break;
				}
			}
		}

		// Loading of List<CAreaSO> list
		@Override
		public void showContentAreaList(ContentSO contentSO) {

			switch (contentSO.getType()) {

			case ContentSO.TYPE_IMAGE: {
				contentAreaListPresenter.setData(contentSO);
				view.showContentAreaList();
			}
				break;
			case ContentSO.TYPE_VIDEO: {
				if (!contentSO.getcAreaSOs().isEmpty()) {
					documentPresenter.setCAreaSO(contentSO.getcAreaSOs().get(0));
				} else {
					CAreaSO areaSO = new CAreaSO();
					areaSO.setContentSO(contentSO);
					areaSO.setTitel("Nouveau");
					areaSO.setId(-10);
					documentPresenter.setCAreaSO(areaSO);

				}
				view.showContentDocument();
			}
				break;
			case ContentSO.TYPE_DOCUMENT: {
				if (!contentSO.getcAreaSOs().isEmpty()) {
					documentPresenter.setCAreaSO(contentSO.getcAreaSOs().get(0));
				} else {
					Window.alert("Pas de Contenu!!!");
				}
				view.showContentDocument();
			}
				break;

			default: {
				contentAreaListPresenter.initData(contentSO);
				view.showContentAreaList();
			}
				break;
			}

		}

		@Override
		public void backToMenuList() {
			view.backToMenuList((MaterialWidget) contentListView);
		}

		@Override
		public void newContent() {
			ContentSO ctent = new ContentSO("Non Classé", "[ Description du contenu ici ]", "img/africaGirl.jpg");
			ctent.setId(-10);
			ctent.setCreationDate(new Date());
			ctent.setActive(true);
			ctent.setLock(false);
			ctent.setShowToHome(false);
			ctent.setMenuSO(menuSO);
			ctent.setType(getContentType());

			swipeToContentDetail(ctent, true);
			view.getContentDetailsView().setEdit();
		}

		private String getContentType() {
			String type = "";
			if (menuSO != null) {
				switch (menuSO.getCategory()) {
				case "Document":
					type = ContentSO.TYPE_DOCUMENT;
					break;
				case "Photo":
					type = ContentSO.TYPE_IMAGE;
					break;

				case "Video":
					type = ContentSO.TYPE_VIDEO;
					break;
				default:
					type = ContentSO.TYPE_MENU;
					break;
				}
			}
			return type;
		}

		@Override
		public void deleteContents(List<ContentSO> contentSO) {

			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new ContentAction(DBAction.DELETE, new ArrayList<APPObjectSO>(contentSO)),
							x -> {
								if (x.getBooleanValue()) {
									contentListView.getCellList().removeSelected();
									contentSO.forEach(ctent -> menuSO.getContents().remove(ctent));
								}
							});
				}
			});

		}

		private void initData(MenuSO menuSO) {
			this.menuSO = menuSO;
			contentListView.setData(menuSO.getContents());
			contentListView.setNewContentAble(menuSO.isMultiContents());
		}

		private void addNewContentSO(ContentSO contentSO) {
			contentListView.getCellList().addItem(contentSO);
			// menuSO.addContent(contentSO);
		}

		private void refreschList() {
			contentListView.getCellList().refresh();
		}

	}

	class MenuTemplateListPresenter implements MenuTemplateListView.Presenter {

		private MenuTemplateListView menuTemplateListView;
		private MenuSO menuSO;

		public MenuTemplateListPresenter(MenuTemplateListView menuTemplateListView) {
			this.menuTemplateListView = menuTemplateListView;
			this.menuTemplateListView.setPresenter(this,
					x -> view.backToMenuList((MaterialWidget) menuTemplateListView));
		}

		@Override
		public void swipeBackToMenuList() {
			view.backToMenuList((MaterialWidget) menuTemplateListView);
		}

		@Override
		public void showTemplateDetail(MenuTemplateSO menuTemplateSO) {

			singleTemplateDetailPresenter.setTemplateSO(menuTemplateSO);
			view.showSingleTemplateDetail();

		}

		@Override
		public void newTemplate(int colNr) {

			MenuTemplateSO menuTemplateSO = new MenuTemplateSO();
			menuTemplateSO.setActive(true);
			menuTemplateSO.setId(-10);
			menuTemplateSO.setCategory("New Category");
			menuTemplateSO.setColNr(colNr);
			menuTemplateSO.setIndex(0);
			menuTemplateSO.setMenuSO(menuSO);

			singleTemplateDetailPresenter.edit();
			showTemplateDetail(menuTemplateSO);
		}

		@Override
		public void deleteTemplate(List<MenuTemplateSO> menuTemplateSOs) {

			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(
							new MenuTemplateAction(DBAction.DELETE, new ArrayList<APPObjectSO>(menuTemplateSOs)), x -> {
								if (x.getBooleanValue()) {
									menuTemplateListView.getCellList().removeSelected();
									menuTemplateSOs.forEach(template -> menuSO.getMenuTemplates().remove(template));
								}
							});
				}
			});

		}

		private void initData(MenuSO menuSO) {
			this.menuSO = menuSO;
			menuTemplateListView.setData(menuSO.getMenuTemplates());

		}

		private void addNewMenuTemplateSO(MenuTemplateSO menuTemplateSO) {
			menuTemplateListView.getCellList().addItem(menuTemplateSO);
		}

		private void refreschList() {
			menuTemplateListView.getCellList().refresh();
		}

		private void handleIconVisibility() {
			menuTemplateListView.handleAddIconVisibility();
		}

	}

	class ContentDetailsPresenter implements ContentDetailsView.Presenter {

		private ContentDetailsView contentDetailsView;
		private ContentSO contentSO;

		public ContentDetailsPresenter(ContentDetailsView contentDetailsView) {
			this.contentDetailsView = contentDetailsView;
			contentDetailsView.setPresenter(this);
		}

		@Override
		public void backToContentList() {
			view.backToContentList((MaterialWidget) contentDetailsView);
		}

		@Override
		public void saveContent(ContentSO contentSO) {

			DBAction dbAction = contentSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new ContentAction(dbAction, contentSO), x -> {
				ContentSO saved = (ContentSO) x.getObject();
				this.contentSO = saved;
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					contentListPresenter.addNewContentSO(saved);
				}
				contentListPresenter.refreschList();
			});
		}

		private void setContentSO(ContentSO contentSO) {
			this.contentSO = contentSO;
			contentDetailsView.setContentSO(contentSO);
		}

	}

	class CDocumentPresenter implements ContentDocumentView.Presenter {
		private ContentDocumentView contentDocumentView;
		private CAreaSO areaSO;

		public CDocumentPresenter(ContentDocumentView contentDocumentView) {
			this.contentDocumentView = contentDocumentView;
			contentDocumentView.setPresenter(this);
		}

		@Override
		public void saveCArea(CAreaSO cAreaSO) {

			DBAction dbAction = cAreaSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new CAreaAction(dbAction, cAreaSO), x -> {
				this.areaSO = (CAreaSO) x.getObject();
				contentAreaListPresenter.refreschList();
			});
		}

		@Override
		public void goBackToContentList() {
			view.backToContentList((MaterialWidget) contentDocumentView);

		}

		private void setCAreaSO(CAreaSO areaSO) {
			this.areaSO = areaSO;
			contentDocumentView.setCAreaSO(areaSO);
		}

	}

	class CPicturesPresenter implements ContentPicturesView.Presenter {
		private ContentPicturesView contentPicturesView;
		private ContentSO contentSO;

		public CPicturesPresenter(ContentPicturesView contentPicturesView) {
			this.contentPicturesView = contentPicturesView;
			contentPicturesView.setPresenter(this);
		}

		@Override
		public void saveContentSO(ContentSO contentSO) {
			DBAction dbAction = contentSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;
			clientFactory.execute(new ContentAction(dbAction, contentSO), result -> {
				this.contentSO = (ContentSO) result.getObject();
				contentPicturesView.setContentSO(this.contentSO);
			});

		}

		@Override
		public void backToAlbumPhotoList() {
			view.backToContentList((MaterialWidget) contentPicturesView);

		}

		public void setContentSO(ContentSO contentSO) {
			this.contentSO = contentSO;
			contentPicturesView.setContentSO(contentSO);
		}
	}

}
