package com.materials.server.handler.user;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.user.UserAction;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class UserHandler implements ActionHandler<UserAction, GeneralResult> {

	private UserReadHelper readHelper = new UserReadHelper();
	private UserUpdateHelper updateHelper = new UserUpdateHelper();

	@Override
	public GeneralResult execute(UserAction action, ExecutionContext context) throws DispatchException {

		GeneralResult result = null;

		switch (action.getDbAction()) {

		case READ: {
			List<APPObjectSO> appObjectSOs = new ArrayList<APPObjectSO>();
			appObjectSOs.addAll(readHelper.read(action.getCommand(), action.getKey()));

			result = new GeneralResult(appObjectSOs);
		}
			break;
		case UPDATE: {
			APPObjectSO updated = updateHelper.update(action.getObject());
			result = new GeneralResult(updated);
		}
			break;
		case SAVE_NEW: {
			APPObjectSO saved = updateHelper.save(action.getObject());
			result = new GeneralResult(saved);
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
	public void rollback(UserAction action, GeneralResult result, ExecutionContext context) throws DispatchException {

	}

	@Override
	public Class<UserAction> getActionType() {
		return UserAction.class;
	}
}
