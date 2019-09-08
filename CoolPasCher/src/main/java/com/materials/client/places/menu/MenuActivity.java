package com.materials.client.places.menu;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.AppClientFactory;
import com.materials.client.context.presenter.RegisterPresenter;
import com.materials.client.model.ContentSO;
import com.materials.client.model.MenuSO;
import com.materials.client.model.UserSO;
import com.materials.client.model.stat.StatItemSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.views.content.StartView;
import com.materials.client.views.header.APPHeaderView;
import com.materials.client.views.navbar.AppNavBarView;
import com.materials.client.widgets.articles.sortable.SortableArticle;
import com.materials.client.widgets.contact.ContactWidget;
import com.materials.client.widgets.contact.ContactWidgetView;
import com.materials.client.widgets.document.FileWidget;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonce;
import com.materials.client.widgets.model.content.annonce.newone.EditAbleAnnonceView;
import com.materials.client.widgets.notallowed.EmptyStatePanel;
import com.materials.client.widgets.register.RegisterWidget;
import com.materials.client.widgets.register.RegisterWidgetView;
import com.materials.client.widgets.stats.StatisticPanel;
import com.materials.client.widgets.stats.StatisticPanelView;
import com.materials.shared.APPConstants;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.GeneralAction;
import com.materials.shared.action.GeneralCommand;
import com.materials.shared.action.content.ContentAction;
import com.materials.shared.action.content.ContentCommand;
import com.materials.shared.action.stats.StatisticAction;
import com.materials.shared.action.stats.StatisticCommand;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;

/**
 * 
 * For Media Activity (Menu,Photo, Video and Docs)
 * 
 * @author Charles Mouafo Deffo
 *
 */
public class MenuActivity extends AbstractActivity implements MenuView.Presenter {

	private AppClientFactory clientFactory;
	private MenuPlace place;
	private MenuView view;

	private final StartViewPresenter startViewPresenter;
	private final AppNavBarPresenter navBarPresenter;
	private final AppHeaderPresenter appHeaderPresenter;
	private final StatisticPanelPresenter statisticPanelPresenter;

	public MenuActivity(AppClientFactory clientFactory, MenuPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;
		view = clientFactory.getMenuView();
		startViewPresenter = new StartViewPresenter(clientFactory.getStartView());
		navBarPresenter = new AppNavBarPresenter();
		statisticPanelPresenter = new StatisticPanelPresenter();
		appHeaderPresenter = new AppHeaderPresenter();
		clientFactory.getStartView().getAppNavBarPanel().setPresenter(navBarPresenter);
		clientFactory.getStartView().getAppHeaderPanel().setPresenter(appHeaderPresenter);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		panel.setWidget(view);

		if (place.getMenuClicked().equals(MenuSO.ACCEUIL)) {

			startPage();

		} else if (place.getMenuClicked().equals(MenuSO.STAT)) {

			showStatisticPanel();

		} else if (place.getMenuClicked().equals(MenuSO.FAVORITS)) {
			clientFactory.setScrollIndex(0);
			loadFavorits();

		} else if (place.getMenuClicked().equals(MenuSO.CONTACT)) {

			showContactPanel();

		} else if (place.getMenuClicked().equals(MenuSO.REGISTER)) {

			showRegisterPanel();

		} else if (place.getCategory() != null) {
			navBarPresenter.menuClick(place.getCategory(), place.getParentCategory());

		} else if (place.isNewAnnonce()) {

			appHeaderPresenter.newAnnonce("", "", "");

		} else {
			// normal menu
			clientFactory.getContentsForMenu(place.getMenuClicked(), list -> {
				if (list != null && list.size() > 0) {

					ContentSO contentSO = list.stream().findFirst().get();

					switch (contentSO.getType()) {

					case ContentSO.TYPE_DOCUMENT: {
						FileWidget fileWidget = new FileWidget(list);
						view.setContent(fileWidget);
					}
						break;
					case ContentSO.TYPE_IMAGE: {

						view.setContents(list);
					}
						break;

					case ContentSO.TYPE_VIDEO: {
						view.setContents(list);
					}
						break;

					default: {// Article_Content

						if (contentSO.isIntern()) {
							if (CoolPasCherUI.checkLoggedMember()) {
								view.setContentAreas(list.get(0).getcAreaSOs());
							} else {
								panel.setWidget(new EmptyStatePanel());
							}

						} else {
							view.setContentAreas(list.get(0).getcAreaSOs());
						}

					}
						break;
					}
				} else {
					view.setContent(new HTML(place.getMenuClicked() + "| Contenu vide"));
				}
			});
		}

		if (place.getCategory() == null) {
			clientFactory.getStartView().getAppNavBarPanel().deselecAll();
		}
	}

	class StartViewPresenter implements StartView.Presenter {

		StartView startView;

		public StartViewPresenter(StartView startView) {
			this.startView = startView;
			this.startView.setPresenter(this);
		}

	}

	public void showContactPanel() {
		ContactWidgetView contactWidgetView = new ContactWidget();
		contactWidgetView.setPresenter(new ContactPresenter(contactWidgetView));
		view.setContent(contactWidgetView.asWidget());
		clientFactory.getAppMetaData().updateMetaForContact();
		clientFactory.setReloadContent(true);
		clientFactory.setScrollIndex(0);
	}

	public void showRegisterPanel() {
		RegisterWidgetView registerWidgetView = new RegisterWidget();
		registerWidgetView.setPresenter(new RegisterPresenter(registerWidgetView, clientFactory.getStartView()));
		registerWidgetView.reset();
		registerWidgetView.setUserSO(new UserSO(-10));
		view.setContent(registerWidgetView.asWidget());
		clientFactory.setReloadContent(true);
		clientFactory.setScrollIndex(0);
	}

	class ContactPresenter implements ContactWidgetView.Presenter {

		private ContactWidgetView contactWidgetView;

		public ContactPresenter(ContactWidgetView contactWidgetView) {
			this.contactWidgetView = contactWidgetView;
			this.contactWidgetView.setPresenter(this);
		}

		@Override
		public void sendMessage(String nom, String prenom, String email, String message) {

			Map<String, String> map = new HashMap<String, String>();
			map.put(APPConstants.MAIL_EMAIL, email);
			map.put(APPConstants.MAIL_NOM, nom);
			map.put(APPConstants.MAIL_PRENOM, prenom);
			map.put(APPConstants.MAIL_MESSAGE, message);

			MaterialLoader.loading(true);

			clientFactory.execute(new GeneralAction(GeneralCommand.SEND_MAIL, map, null), result -> {

				MaterialLoader.loading(false);

				if (result.getBooleanValue()) {
					contactWidgetView.showConfirmationLighBox("Message envoyé",
							clientFactory.getWebSiteSO().getMailSendSuccessText(), x -> {
								clientFactory.getPlaceController()
										.goTo(new MenuPlace().handleMenu("start", MenuSO.ACCEUIL));
							});
				} else {
					contactWidgetView.showConfirmationLighBox("Echec d'envoie",
							"Erreur pendant l'envoie: \n" + result.getMessage(), x -> {
							});
				}
			});
		}
	}

	private void showStatisticPanel() {

		StatisticSO statisticSO = clientFactory.getStatisticSOs().isEmpty() ? null
				: clientFactory.getStatisticSOs().get(0);
		UserSO userSO = clientFactory.getActualUserSO();

		if (userSO != null && statisticSO != null) {
			String userEmail = userSO.getEmail();

			clientFactory.execute(new StatisticAction(DBAction.READ, StatisticCommand.STATITEM_BY_USER_AND_STATISTIC,
					userEmail, statisticSO.getLabel()), c -> {

						StatisticPanel statisticPanel = new StatisticPanel(false);
						statisticPanel.setPresenter(statisticPanelPresenter);

						if (!c.getObjects().isEmpty()) {
							StatItemSO statItemSO = (StatItemSO) c.getObjects().get(0);
							statItemSO.setStatisticSO(statisticSO);
							statisticPanel.setStatisticItem(statItemSO);
							statisticPanel.enabled(false);
						} else {
							statisticPanel.setStatisticItem(getStatItemSO(statisticSO));
						}
						view.setContent(statisticPanel);
						clientFactory.setReloadContent(true);
						clientFactory.getScrollHelper().scrollTo(0);
					});
		} else {
			String title = statisticSO != null ? "Connectez vous svp" : "Pas de statistic";
			String description = statisticSO != null
					? "Pour avoir accèss au Statistic, connectez vous en utlisant votre email et mot de pass" : "";
			MaterialPanel materialPanel = new MaterialPanel();
			materialPanel.setHeight("1000px");
			materialPanel.add(new EmptyStatePanel(IconType.ASSESSMENT, title, description));
			view.setContent(materialPanel);
		}

	}

	private void startPage() {
		clientFactory.buildWidgetsForWelcome(welcomeWidgets -> {
			view.startAcceuil(true);

		});
	}

	private void loadFavorits() {
		UserSO userSO = clientFactory.getActualUserSO();

		if (userSO != null) {
			clientFactory.execute(new ContentAction(DBAction.READ, ContentCommand.FAVORITS,
					userSO.getUserProperties().get(UserSO.FAVORITS)), result -> {
						List<ContentSO> list = MethodsUtils.castList(result.getObjects(), ContentSO.class);

	
						if (list.isEmpty()) {
							String title = "Liste de favorits vide";
							String description = "Pour y en ajoutter: details de l'annonce >>> button switch (en dessous du panel d'images)";
							MaterialPanel materialPanel = new MaterialPanel();
							materialPanel.setHeight("1000px");
							materialPanel.add(new EmptyStatePanel(IconType.FOLDER_SPECIAL, title, description));
							view.setContent(materialPanel);
						} else {
							clientFactory.updateCache(list);
							view.loadFavorits(list);
						}

						clientFactory.setReloadContent(true);

					});
		} else {
			String title = "Connectez vous svp";
			String description = "Pour avoir accèss à vos favorits, connectez vous en utlisant votre email et mot de pass";
			MaterialPanel materialPanel = new MaterialPanel();
			materialPanel.setHeight("1000px");
			materialPanel.add(new EmptyStatePanel(IconType.PERSON, title, description));
			view.setContent(materialPanel);
		}

	}

	class AppNavBarPresenter implements AppNavBarView.Presenter {

		@Override
		public void menuClick(String categoryName, String parentMenu) {

			String category = categoryName.contains("Tout afficher") ? parentMenu : categoryName;

			if (view.getLastCategory()!= null && category.equals(view.getLastCategory())) {
				view.swippeToCategory();
			} else {
				clientFactory.setScrollIndex(0);
				clientFactory.execute(
						new ContentAction(DBAction.READ, ContentCommand.ALL_ANNONCES_BY_CATEGORY, category), result -> {
							final List<ContentSO> list = MethodsUtils.castList(result.getObjects(), ContentSO.class);
							clientFactory.updateCache(list);

							if (list.isEmpty()) {
								view.setContent(new MaterialLabel("Pas de résultats [" + place.getParentCategory()
										+ " >> " + place.getCategory() + "]"));
							} else {
								SortableArticle articlesWidget = new SortableArticle(list, 5, 500, category);
								ClientUtils.addTimer(x -> view.swippeToCategory(articlesWidget, category), 200);
							
							}
						});
			}

		}
	}

	class AppHeaderPresenter implements APPHeaderView.Presenter {

		@Override
		public void newAnnonce(String label, String region, String ville) {
			UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
			if (userSO != null) {
				view.showNewAnnoncePanel(getNewEditableView());
			} else {
				MaterialPanel panel = getLoginStatePanel();
				view.setContent(panel);
			}
			//clientFactory.setScrollIndex(0);
			clientFactory.getScrollHelper().scrollTo(0);
		}

	}

	private EditAbleAnnonce getNewEditableView() {
		EditAbleAnnonce editAbleAnnonceView = new EditAbleAnnonce(true);
		editAbleAnnonceView.setPresenter(new EditAbleNewAnnoncePresenter());
		editAbleAnnonceView.setContentSO(getNewContentSo());
		return editAbleAnnonceView;
	}

	private ContentSO getNewContentSo() {

		UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();

		ContentSO contentSO = new ContentSO();
		contentSO.setId(-10);
		contentSO.setShowToHome(true);
		contentSO.setActive(true);
		contentSO.setLock(false);
		contentSO.setVip(false);
		contentSO.setCreationDate(new Date());
		contentSO.setType(ContentSO.TYPE_ANNONCE);
		// contentSO.setCreator(clientFactory.getActualUserSO().getEmail());
		contentSO.setTitel("le Titre ici");
		contentSO.setLocalite("");
		contentSO.setDescription("Votre description ici");
		contentSO.setDescription2("Votre seconde description ici");
		contentSO.setLongDescription("Complete Description ici");

		contentSO.setCreator("" + userSO.getId());
		// fillProperties(contentSO, userSO);

		return contentSO;
	}

	class EditAbleNewAnnoncePresenter implements EditAbleAnnonceView.Presenter {
		@Override
		public void saveContentSO(ContentSO contentSO) {
			DBAction dbAction = contentSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;
			clientFactory.execute(new ContentAction(dbAction, contentSO), x -> {
				ContentSO saved = (ContentSO) x.getObject();
				clientFactory.addNewArticle(saved);
				view.swipeBack(true);
			});
		}

		@Override
		public void cancel() {
			view.swipeBack(true);
		}

		@Override
		public void goBack() {
		}
	}

	private MaterialRow getLoginRow() {
		MaterialRow materialRow = new MaterialRow();
		materialRow.setMarginTop(50);

		MaterialButton buttonLogin = new MaterialButton("Connection");
		buttonLogin.setTextColor(Color.DEEP_ORANGE_ACCENT_4);
		buttonLogin.setType(ButtonType.FLAT);
		buttonLogin.setMargin(10);

		MaterialButton buttonRegister = new MaterialButton("S'enregistrer");
		buttonRegister.setType(ButtonType.FLAT);
		buttonRegister.setMargin(10);
		buttonRegister.setTextColor(Color.DEEP_ORANGE_ACCENT_4);
		materialRow.add(buttonLogin);
		materialRow.add(buttonRegister);

		buttonLogin.addClickHandler(x -> {
			CoolPasCherUI.CLIENT_FACTORY.getLoginWidget().show();
		});

		buttonRegister.addClickHandler(x -> {
			clientFactory.getPlaceController().goTo(new MenuPlace().handleMenu("start", MenuSO.REGISTER));
		});

		return materialRow;
	}

	private MaterialPanel getLoginStatePanel() {
		String title = "Connectez vous svp";
		String description = "Pour creer une annonce, connectez vous en utlisant votre email et mot de pass";
		EmptyStatePanel emptyStatePanel = new EmptyStatePanel(IconType.PERSON, title, description);
		emptyStatePanel.addWidget(getLoginRow());
		MaterialPanel panel = new MaterialPanel();
		panel.getElement().getStyle().setProperty("minHeight", "800px");
		// panel.setOpacity(0);
		panel.add(emptyStatePanel);

		return panel;
	}

	@Override
	public void onStop() {
		super.onStop();
		// view.clearContent();
		view.clearAnnoncePanel();
	}

	private StatItemSO getStatItemSO(StatisticSO statisticSO) {

		StatItemSO statItemSO = new StatItemSO();
		statItemSO.setId(-10);
		statItemSO.setMessage("");
		statItemSO.setCreationDate(new Date());
		statItemSO.setLock(false);
		statItemSO.setStatisticSO(statisticSO);
		statItemSO.setChoice("");

		return statItemSO;
	}

	class StatisticPanelPresenter implements StatisticPanelView.Presenter {

		@Override
		public void save(StatItemSO statItem) {

			UserSO userSO = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
			if (userSO != null) {
				statItem.setCreator(userSO.getName());
				statItem.setCreatorEmail(userSO.getEmail());
				CoolPasCherUI.CLIENT_FACTORY.handleCurrentHistory();
				DBAction dbAction = statItem.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;
				clientFactory.execute(new StatisticAction(dbAction, statItem), x -> {
					clientFactory.getPlaceController().goTo(new MenuPlace().handleMenu("start", MenuSO.ACCEUIL, true));
					clientFactory.getConfirmationWidget().show("Enregistré", "Merci pour le Vote !!", g -> {
					});
				});
			}
		}

		@Override
		public void goBackToItemList() {

		}

	}
}
