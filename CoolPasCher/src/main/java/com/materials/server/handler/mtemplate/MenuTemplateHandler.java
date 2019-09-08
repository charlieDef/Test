package com.materials.server.handler.mtemplate;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.mtemplate.MenuTemplateAction;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class MenuTemplateHandler implements ActionHandler<MenuTemplateAction, GeneralResult> {

	private MenuTemplateReadHelper readHelper = new MenuTemplateReadHelper();
	private MenuTemplateUpdateHelper updateHelper = new MenuTemplateUpdateHelper();

	@Override
	public GeneralResult execute(MenuTemplateAction action, ExecutionContext context) throws DispatchException {

		GeneralResult result = null;

		switch (action.getDbAction()) {

		case READ: {
			List<APPObjectSO> list = new ArrayList<>();
			list.addAll(readHelper.read(action.getCommand(), action.getKey()));
			result = new GeneralResult(list);
		}
			break;
		case UPDATE: {
			APPObjectSO object = updateHelper.update(action.getObject());
			result = new GeneralResult(object);
		}
			break;
		case SAVE_NEW: {
			APPObjectSO object = updateHelper.save(action.getObject());
			result = new GeneralResult(object);
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
	public void rollback(MenuTemplateAction action, GeneralResult result, ExecutionContext context)
			throws DispatchException {
	}

	@Override
	public Class<MenuTemplateAction> getActionType() {
		return MenuTemplateAction.class;
	}

}
