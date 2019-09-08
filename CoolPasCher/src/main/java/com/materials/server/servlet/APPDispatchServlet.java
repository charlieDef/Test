package com.materials.server.servlet;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.materials.server.handler.GeneralHandler;
import com.materials.server.handler.carea.CAreaHandler;
import com.materials.server.handler.comment.CommentHandler;
import com.materials.server.handler.content.ContentHandler;
import com.materials.server.handler.menu.MenuHandler;
import com.materials.server.handler.mtemplate.MenuTemplateHandler;
import com.materials.server.handler.rsocial.RSocialHandler;
import com.materials.server.handler.slider.SliderHandler;
import com.materials.server.handler.stat.StatisticHandler;
import com.materials.server.handler.user.UserHandler;

import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.InstanceActionHandlerRegistry;
import net.customware.gwt.dispatch.server.SimpleDispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class APPDispatchServlet extends RemoteServiceServlet implements StandardDispatchService {

	private Dispatch dispatch;

	private GeneralHandler generalActionHandler;
	private MenuHandler menuHandler;

	public APPDispatchServlet() {
		InstanceActionHandlerRegistry registry = new DefaultActionHandlerRegistry();
		registry.addHandler(new ContentHandler());
		menuHandler = new MenuHandler();
		registry.addHandler(menuHandler);
		registry.addHandler(new SliderHandler());
		registry.addHandler(new UserHandler());
		registry.addHandler(new CAreaHandler());
		registry.addHandler(new CommentHandler());
		registry.addHandler(new RSocialHandler());
		registry.addHandler(new MenuTemplateHandler());
		registry.addHandler(new StatisticHandler());

		generalActionHandler = new GeneralHandler();
		registry.addHandler(generalActionHandler);
		dispatch = new SimpleDispatch(registry);

	}

	@Override
	public Result execute(Action<?> action) throws DispatchException {
		try {
			HttpServletRequest httpServletRequest = getThreadLocalRequest();
			generalActionHandler.setHttpServlerRequest(httpServletRequest);
			menuHandler.setServletRequest(httpServletRequest);

			return dispatch.execute(action);
		} catch (RuntimeException e) {
			log("Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e);
			throw e;
		}
	}

}
