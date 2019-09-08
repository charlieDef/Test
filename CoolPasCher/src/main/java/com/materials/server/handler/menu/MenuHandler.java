package com.materials.server.handler.menu;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.APPTicket;
import com.materials.client.model.MenuSO;
import com.materials.shared.action.menu.MenuAction;
import com.materials.shared.action.menu.MenuCommand;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class MenuHandler implements ActionHandler<MenuAction, GeneralResult> {

	private HttpServletRequest servletRequest;
	private MenuReadHelper readHelper = new MenuReadHelper();
	private MenuUpdateHelper updateHelper = new MenuUpdateHelper();

	@Override
	public GeneralResult execute(MenuAction action, ExecutionContext context) throws DispatchException {

		GeneralResult result = null;

		switch (action.getDbAction()) {

		case READ: {
			List<APPObjectSO> list = new ArrayList<>();
			list.addAll(readHelper.read(action.getCommand(), action.getKey()));
			APPTicket appTicket = new APPTicket();
			if (servletRequest.getSession().getAttribute("appTicket") != null) {
				appTicket = (APPTicket) servletRequest.getSession().getAttribute("appTicket");
			}

			result = new GeneralResult(appTicket, list);
		}
			break;
		case UPDATE: {
			APPObjectSO updated = updateHelper.update(action.getObject());
			List<MenuSO> appObjectSOs = readHelper.read(MenuCommand.ALL_BASE_MENU, null);
			result = new GeneralResult(updated);
			result.getObjects().addAll(appObjectSOs);
		}
			break;
		case SAVE_NEW: {
			APPObjectSO saved = updateHelper.save(action.getObject(), action.getKey());
			List<MenuSO> appObjectSOs = readHelper.read(MenuCommand.ALL_BASE_MENU, null);
			result = new GeneralResult(saved);
			result.getObjects().addAll(appObjectSOs);

		}
			break;
		case DELETE: {
			boolean deleted = updateHelper.delete(action.getObjects());
			result = new GeneralResult(deleted);
		}
			break;

		}
		return result;
	}

	@Override
	public void rollback(MenuAction action, GeneralResult result, ExecutionContext context) throws DispatchException {
	}

	@Override
	public Class<MenuAction> getActionType() {
		return MenuAction.class;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
		readHelper.setHttpServlerRequest(servletRequest);
	}

}
