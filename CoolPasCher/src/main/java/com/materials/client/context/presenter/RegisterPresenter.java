package com.materials.client.context.presenter;

import com.materials.client.CoolPasCherUI;
import com.materials.client.model.MenuSO;
import com.materials.client.model.UserSO;
import com.materials.client.places.menu.MenuPlace;
import com.materials.client.views.content.StartView;
import com.materials.client.widgets.register.RegisterWidgetView;
import com.materials.shared.action.DBAction;
import com.materials.shared.action.user.UserAction;

public class RegisterPresenter implements RegisterWidgetView.Presenter {

	private RegisterWidgetView registerWidgetView;
	private StartView startView;

	public RegisterPresenter(RegisterWidgetView registerWidgetView, StartView startView) {
		this.registerWidgetView = registerWidgetView;
		this.startView = startView;
		this.registerWidgetView.setPresenter(this);
	}

	@Override
	public void saveMember(UserSO userSO) {
		DBAction dbAction = userSO.getId() == -10 ? DBAction.SAVE_NEW : DBAction.UPDATE;
		CoolPasCherUI.CLIENT_FACTORY.execute(new UserAction(dbAction, userSO), x -> {
			UserSO saved = (UserSO) x.getObject();
			if (saved.getTextInfo().indexOf("ERREUR") > 0) {
				registerWidgetView.showFailText(saved.getTextInfo());
			} else {
				String header = "", content = "";
				if (dbAction.equals(DBAction.UPDATE)) {
					header = "Success";
					content = "Account modifié ";
					CoolPasCherUI.CLIENT_FACTORY.setUserSO(saved);
					// startView.refreshHeaders();
					// startView.refreshNavbar();

					startView.getAppHeaderPanel().refreshHeader();
					startView.getAppNavBarPanel().refreshNavbar();
					startView.getAppFooterPanel().refreshFooter();

				} else {
					header = "Account Actif";
					content = "Compte crée avec success!!";
				}

				registerWidgetView.showConfirmationLighBox(header, content, y -> {
					CoolPasCherUI.CLIENT_FACTORY.getPlaceController()
							.goTo(new MenuPlace().handleMenu("start", MenuSO.ACCEUIL));
				});
			}

		});
	}
}