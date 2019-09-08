package com.materials.server.handler.carea;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.carea.CAreaAction;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class CAreaHandler implements ActionHandler<CAreaAction, GeneralResult> {

	private CAreaReadHelper readHelper = new CAreaReadHelper();
	private CAreaUpdateHelper updateHelper = new CAreaUpdateHelper();

	@Override
	public GeneralResult execute(CAreaAction action, ExecutionContext context) throws DispatchException {

		GeneralResult result = null;

		switch (action.getDbAction()) {

		case READ: {
			List<APPObjectSO> list = readHelper.read(action.getCommand(), action.getKey());
			result = new GeneralResult(list);
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
	public void rollback(CAreaAction action, GeneralResult result, ExecutionContext context) throws DispatchException {
	}

	@Override
	public Class<CAreaAction> getActionType() {
		return CAreaAction.class;
	}
}
