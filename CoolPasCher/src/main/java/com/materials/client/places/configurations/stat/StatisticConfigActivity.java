package com.materials.client.places.configurations.stat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.materials.client.CoolPasCherUI;
import com.materials.client.context.AppClientFactory;
import com.materials.client.model.APPObjectSO;
import com.materials.client.model.UserSO;
import com.materials.client.model.stat.StatItemSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.client.utils.ClientUtils;
import com.materials.client.widgets.model.stat.cellist.StatisticItemListView;
import com.materials.client.widgets.model.stat.cellist.StatisticListView;
import com.materials.client.widgets.model.stat.dashboard.MDDashboardView;
import com.materials.client.widgets.model.stat.details.StatisticDetailsView;
import com.materials.client.widgets.stats.StatisticPanelView;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.stats.StatisticAction;
import com.materials.shared.action.stats.StatisticCommand;

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
public class StatisticConfigActivity extends AbstractActivity implements StatisticConfigView.Presenter {

	private AppClientFactory clientFactory;
	private StatisticConfigPlace place;
	private StatisticConfigView view;

	private final StatisticListPresenter statisticListPresenter;
	private final StatisticItemListPresenter statisticItemListPresenter;
	private final StatisticDetailsPresenter statisticDetailsPresenter;
	private final StatisticPanelPresenter statisticPanelPresenter;
	private final DashBoardPresenter dashBoardPresenter;
	//

	public StatisticConfigActivity(AppClientFactory clientFactory, StatisticConfigPlace place) {
		this.clientFactory = clientFactory;
		this.place = place;

		view = clientFactory.getStatisticConfigView();
		statisticListPresenter = new StatisticListPresenter(view.getStatisticListView());
		statisticItemListPresenter = new StatisticItemListPresenter(view.getStatisticItemListView());
		statisticDetailsPresenter = new StatisticDetailsPresenter(view.getStatisticDetail());
		statisticPanelPresenter = new StatisticPanelPresenter(view.getStatisticItemPanelView());
		dashBoardPresenter = new DashBoardPresenter(view.getMdDashboard());
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		UserSO userSO = clientFactory.getActualUserSO();
		if (userSO != null && (userSO.isAdmin() || userSO.isAdminMaster())) {
			panel.setWidget(view);
			statisticListPresenter.initData();
		} else {
			MaterialPanel materialPanel = clientFactory.getEmptyStatePanel();
			panel.setWidget(materialPanel);
			ClientUtils.animeIn(materialPanel, Transition.FADEINLEFT);
		}
		clientFactory.scrollAppToTop(false);

	}

	class StatisticPanelPresenter implements StatisticPanelView.Presenter {

		private StatisticPanelView statisticPanelView;

		public StatisticPanelPresenter(StatisticPanelView statisticPanelView) {
			this.statisticPanelView = statisticPanelView;
			statisticPanelView.setPresenter(this);
		}

		@Override
		public void save(StatItemSO statItem) {
			// TODO Auto-generated method stub

		}

		@Override
		public void goBackToItemList() {
			view.goBackToStatisticItemsList((MaterialWidget) statisticPanelView);

		}

	}

	class StatisticListPresenter implements StatisticListView.Presenter {

		private StatisticListView statisticListView;

		public StatisticListPresenter(StatisticListView statisticListView) {
			this.statisticListView = statisticListView;
			this.statisticListView.setPresenter(this);
		}

		private void initData() {
			clientFactory.execute(new StatisticAction(DBAction.READ, StatisticCommand.ALL_STATISTICS, null), result -> {
				List<StatisticSO> list = MethodsUtils.castList(result.getObjects(), StatisticSO.class);
				statisticListView.setData(list);
				view.showStatisticList();
			});
		}

		@Override
		public void showStatisticContent(StatisticSO statisticSO) {
			view.getStatisticItemListView().setData(statisticSO);
			view.showStatisticItemsList();
		}

		@Override
		public void swipeToStatisticDetail(StatisticSO statisticSO) {
			view.getStatisticDetail().setStatistic(statisticSO);
			view.showStatisticDetail();
		}

		@Override
		public void swipeToDashboard(StatisticSO statisticSO) {
			view.getMdDashboard().setStatisticSO(statisticSO);
			view.showDashboard();
		}

		@Override
		public void newStatistic() {
			UserSO userSO = clientFactory.getActualUserSO();
			StatisticSO statisticSO = new StatisticSO();
			statisticSO.setId(-10);
			statisticSO.setTitel("titre ");
			statisticSO.setLabel("Le Label");
			statisticSO.setDescription("La description");
			statisticSO.setCreationDate(new Date());
			statisticSO.setActiveFrom(new Date());
			statisticSO.setActiveTo(new Date());
			statisticSO.setActive(false);
			statisticSO.setLock(false);
			statisticSO.setChoicesNr(2);
			statisticSO.addChoice("1", "Choix 1");
			statisticSO.addChoice("2", "Choix 2");
			statisticSO.setCreator(userSO.getName());

			view.getStatisticDetail().setStatistic(statisticSO);
			view.getStatisticDetail().setEdit();

			view.showStatisticDetail();
		}

		@Override
		public void deleteStatistics(List<StatisticSO> statisticSO) {
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().desactiveNoButton(false);
			CoolPasCherUI.CLIENT_FACTORY.getConfirmationWidget().show("Vraiment Supprimer ?", ok -> {
				if (ok) {
					clientFactory.execute(new StatisticAction(DBAction.DELETE, new ArrayList<APPObjectSO>(statisticSO)),
							x -> {
								if (x.getBooleanValue()) {
									statisticListView.getCellList().removeSelected();
								}
							});
				}
			});
		}

	}

	class StatisticItemListPresenter implements StatisticItemListView.Presenter {

		private StatisticItemListView statisticItemListView;

		public StatisticItemListPresenter(StatisticItemListView statisticItemListView) {
			this.statisticItemListView = statisticItemListView;
			this.statisticItemListView.setPresenter(this);
		}

		@Override
		public void swipeBackToStatisticList() {
			view.goBackToStatisticList((MaterialWidget) statisticItemListView);
		}

		@Override
		public void swipeToStatisticItemDetail(StatItemSO statItemSO) {

		}

		@Override
		public void newStatisticItem() {

			StatItemSO statisticSO = new StatItemSO();
			statisticSO.setId(-10);
			statisticSO.setLabel("Le Label");
			statisticSO.setMessage("");
			statisticSO.setCreationDate(new Date());
			statisticSO.setLock(false);
			statisticSO.setStatisticSO(statisticItemListView.getStatisticSO());

			view.getStatisticItemPanelView().setStatisticItem(statisticSO);
			view.showStatisticItemPanel();
		}

		@Override
		public void deleteStatisticItems(List<StatItemSO> StatItemSOs) {

		}

	}

	class StatisticDetailsPresenter implements StatisticDetailsView.Presenter {

		private StatisticDetailsView statisticDetailsView;
		private StatisticSO statisticSO;

		public StatisticDetailsPresenter(StatisticDetailsView statisticDetailsView) {
			this.statisticDetailsView = statisticDetailsView;
			this.statisticDetailsView.setPresenter(this);
		}

		@Override
		public void save(StatisticSO statisticSO) {
			DBAction dbAction = statisticSO.getId() <= -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;

			clientFactory.execute(new StatisticAction(dbAction, statisticSO), x -> {
				StatisticSO saved = (StatisticSO) x.getObject();
				this.statisticSO = saved;
				if (dbAction.equals(DBAction.SAVE_NEW)) {
					view.getStatisticListView().getCellList().addItem(saved);
				}
				view.getStatisticListView().getCellList().refresh();
			});
		}

		@Override
		public void backToStatisticList() {
			view.goBackToStatisticList((MaterialWidget) statisticDetailsView);
		}
	}

	class DashBoardPresenter implements MDDashboardView.Presenter {

		private MDDashboardView mDDashboardView;

		public DashBoardPresenter(MDDashboardView mDDashboardView) {
			this.mDDashboardView = mDDashboardView;
			this.mDDashboardView.setPresenter(this);
		}

		@Override
		public void swipeBackToStatisticList() {
			view.goBackToStatisticList((MaterialWidget) mDDashboardView);
		}
	}

}
