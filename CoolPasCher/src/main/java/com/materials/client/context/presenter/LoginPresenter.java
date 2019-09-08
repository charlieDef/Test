package com.materials.client.context.presenter;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.place.shared.Place;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuSO;
import com.materials.client.model.UserSO;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.views.content.StartView;
import com.materials.client.widgets.login.LoginWidgetView2;
import com.materials.shared.MethodsUtils;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.GeneralAction;
import com.materials.shared.action.GeneralCommand;
import com.materials.shared.action.user.UserAction;
import com.materials.shared.action.user.UserCommand;

public class LoginPresenter implements LoginWidgetView2.Presenter {

	private LoginWidgetView2 loginWidgetView;
	private StartView startView;

	public LoginPresenter(LoginWidgetView2 loginWidgetView, StartView startView) {
		this.loginWidgetView = loginWidgetView;
		this.loginWidgetView.setPresenter(this);
		this.startView = startView;
	}

	@Override
	public void checkLogin(String userEmail, String pwd) {

		CoolPasCherUI.CLIENT_FACTORY.execute(new GeneralAction(GeneralCommand.LOGIN, new String[] { userEmail, pwd }),
				result -> {
					if (result.getAPPTicket() != null) {
						List<MenuSO> list = MethodsUtils.castList(result.getObjects(), MenuSO.class);
						list.sort(new Comparator<MenuSO>() {
							@Override
							public int compare(MenuSO o1, MenuSO o2) {
								return Integer.valueOf(o1.getIndex()).compareTo(Integer.valueOf(o2.getIndex()));
							}
						});
						UserSO userSO = result.getAPPTicket().getUserLogged();
						CoolPasCherUI.CLIENT_FACTORY.setUserSO(userSO);
						// startView.getAppHeaderPanel().rebuildHeader(list);
						// startView.getAppNavBarPanel().rebuildNavbar(list);

						startView.getAppHeaderPanel().rebuildHeader(list);
						startView.getAppNavBarPanel().rebuildNavbar(list);
						startView.getAppFooterPanel().refreshFooter();
						// startView.showConfiguration(userSO.isAdmin() || userSO.isAdminMaster());

						loginWidgetView.handleSuccessPwd();

					} else {
						loginWidgetView.handleWrongPwd();
					}
				});
	}

	@Override
	public void resetPwd(String userEmail, String pwdResetCode, String newPwd, String newPwdRepeat) {
		CoolPasCherUI.CLIENT_FACTORY.execute(new GeneralAction(GeneralCommand.RESET_PWD,
				new String[] { userEmail, pwdResetCode, newPwd, newPwdRepeat }), result -> {
					if (result.getBooleanValue()) {
						loginWidgetView.showPwdResetSuccesMessage();
						loginWidgetView.close();
					} else {
						loginWidgetView.handleWrongPwd(result.getMessage());
					}
				});
	}

	@Override
	public void resetPwdCode(String userEmail) {

		CoolPasCherUI.CLIENT_FACTORY
				.execute(new GeneralAction(GeneralCommand.RESET_PWD_CODE, new String[] { userEmail, "" }), result -> {
					if (result.getBooleanValue()) {
						loginWidgetView.showPwdResetCodeSuccesMessage();
						// loginWidgetView.close();
					} else {
						loginWidgetView.handleWrongPwd(result.getMessage());
					}
				});

	}

	@Override
	public void fetchUser(String email, Consumer<UserSO> userEmail) {
		CoolPasCherUI.CLIENT_FACTORY.execute(new UserAction(DBAction.READ, UserCommand.BY_EMAIL, email), result -> {
			if (result.getObjects() != null && !result.getObjects().isEmpty()) {
				UserSO userSO = (UserSO) result.getObjects().get(0);
				// userEmail.accept(userSO.getUserImageUrl());
				userEmail.accept(userSO);
			}
		});

	}

	@Override
	public void performLogout() {
		CoolPasCherUI.CLIENT_FACTORY.execute(new GeneralAction(GeneralCommand.LOGOUT, null), result -> {

			List<MenuSO> list = MethodsUtils.castList(result.getObjects(), MenuSO.class);
			list.sort(new Comparator<MenuSO>() {
				@Override
				public int compare(MenuSO o1, MenuSO o2) {
					return Integer.valueOf(o1.getIndex()).compareTo(Integer.valueOf(o2.getIndex()));
				}
			});

			CoolPasCherUI.CLIENT_FACTORY.setUserSO(null);
			startView.getAppHeaderPanel().rebuildHeader(list);
			startView.getAppNavBarPanel().rebuildNavbar(list);
			startView.getAppFooterPanel().refreshFooter();
			// startView.showConfiguration(false);

			Place place = CoolPasCherUI.CLIENT_FACTORY.getPlaceController().getWhere();
			if (place instanceof MenuPlace) {
				MenuPlace menuPlace = (MenuPlace) place;
				CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
						.goTo(new MenuPlace().handleMenu("start", menuPlace.getMenuClicked(), true));
			} else {
				CoolPasCherUI.CLIENT_FACTORY.handleCurrentHistory();
			}

		});

	}

}