package com.materials.server.handler.comment;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.shared.action.comment.CommentAction;
import com.materials.shared.result.GeneralResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class CommentHandler implements ActionHandler<CommentAction, GeneralResult> {

	private CommentReadHelper readHelper = new CommentReadHelper();
	private CommentUpdateHelper updateHelper = new CommentUpdateHelper();

	@Override
	public GeneralResult execute(CommentAction action, ExecutionContext context) throws DispatchException {

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
	public void rollback(CommentAction action, GeneralResult result, ExecutionContext context)
			throws DispatchException {
	}

	@Override
	public Class<CommentAction> getActionType() {
		return CommentAction.class;
	}

}
