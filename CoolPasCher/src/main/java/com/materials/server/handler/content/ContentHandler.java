package com.materials.server.handler.content;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.content.ContentAction;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class ContentHandler implements ActionHandler<ContentAction, GeneralResult> {

	private ContentReadHelper readHelper = new ContentReadHelper();
	private ContentUpdateHelper updateHelper = new ContentUpdateHelper();

	@Override
	public GeneralResult execute(ContentAction action, ExecutionContext context) throws DispatchException {

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
	public void rollback(ContentAction action, GeneralResult result, ExecutionContext context)
			throws DispatchException {
	}

	@Override
	public Class<ContentAction> getActionType() {
		return ContentAction.class;
	}

}
