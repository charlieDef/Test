package com.materials.server.handler.rsocial;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.rsocial.RSocialAction;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class RSocialHandler implements ActionHandler<RSocialAction, GeneralResult> {

	private RSocialReadHelper readHelper = new RSocialReadHelper();
	private RSocialUpdateHelper updateHelper = new RSocialUpdateHelper();

	@Override
	public GeneralResult execute(RSocialAction action, ExecutionContext context) throws DispatchException {

		GeneralResult result = null;

		switch (action.getDbAction()) {

		case READ: {
			List<APPObjectSO> list = new ArrayList<>();
			list.addAll(readHelper.read(action.getCommand(), action.getKey()));

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
	public void rollback(RSocialAction action, GeneralResult result, ExecutionContext context)
			throws DispatchException {

	}

	@Override
	public Class<RSocialAction> getActionType() {
		return RSocialAction.class;
	}

}
