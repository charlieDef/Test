package com.materials.server.handler.stat;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.stats.StatisticAction;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class StatisticHandler implements ActionHandler<StatisticAction, GeneralResult> {

	private StatisticReadHelper readHelper = new StatisticReadHelper();
	private StatisticUpdateHelper updateHelper = new StatisticUpdateHelper();

	@Override
	public GeneralResult execute(StatisticAction action, ExecutionContext context) throws DispatchException {

		GeneralResult result = null;
		switch (action.getDbAction()) {

		case READ: {
			List<APPObjectSO> list = readHelper.read(action.getCommand(), action.getKey(), action.getKey2());
			result = new GeneralResult(list);
		}
			break;
		case UPDATE: {
			APPObjectSO updated = updateHelper.update(action.getObject());
			result = new GeneralResult(updated);
		}
			break;
		case SAVE_NEW: {

			APPObjectSO newId = updateHelper.save(action.getObject());
			result = new GeneralResult(newId);
		}
			break;

		case DELETE: {
			boolean updated = updateHelper.delete(action.getObjects());
			result = new GeneralResult(updated);
		}
			break;
		}

		return result;
	}

	@Override
	public void rollback(StatisticAction action, GeneralResult result, ExecutionContext context)
			throws DispatchException {
	}

	@Override
	public Class<StatisticAction> getActionType() {
		return StatisticAction.class;
	}

}
