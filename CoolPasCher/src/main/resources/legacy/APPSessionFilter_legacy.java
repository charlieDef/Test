package com.materials.server.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.materials.server.EntityManager_Access;
import com.materials.server.model.Content;
import com.materials.server.model.Menu;
import com.materials.server.model.WebSite;
import com.materials.utils.APP_DB_Utils;

public class APPSessionFilter implements Filter {

	public static final String PERSISTENCE_UNIT = "prebaalmdUnit";
	public static EntityManagerFactory entityManagerFactory = null;
	private static final Logger logger = LoggerFactory.getLogger(APPSessionFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String searchEngineFlag = request.getParameter("_escaped_fragment_");
		String queryString = httpServletRequest.getQueryString();
		if (queryString != null && queryString.contains("_escaped_fragment_") || (searchEngineFlag != null)) {

			EntityManager eManager = entityManagerFactory.createEntityManager();
			EntityManager_Access.getInstance().setEntityManager(eManager);

			String html = "";
			String[] array = searchEngineFlag.split("/");
			if (searchEngineFlag != null && searchEngineFlag.contains("start")) {
				String url = httpServletRequest.getRequestURL().toString();
				html = getHtmlForMenu(array[1], url);
			} else if (searchEngineFlag != null && searchEngineFlag.contains("article")) {
				String url = httpServletRequest.getRequestURL().toString();
				html = getHtmlForArticle(array[1], url);
			}

			EntityManager_Access.removeInstance();
			eManager.close();
			ServletOutputStream out = response.getOutputStream();
			out.write(html.getBytes());
			out.close();
		} else {

			EntityManager eManager = null;
			try {
				eManager = entityManagerFactory.createEntityManager();
				EntityManager_Access.getInstance().setEntityManager(eManager);
				EntityManager_Access.getInstance().setHttpServletRequest(httpServletRequest);

				if (httpServletRequest.getScheme().equals("http")) {
					String url = "https://" + httpServletRequest.getServerName() + httpServletRequest.getContextPath()
							+ httpServletRequest.getServletPath();
					if (httpServletRequest.getPathInfo() != null) {
						url += httpServletRequest.getPathInfo();
					}
					httpServletResponse.sendRedirect(url);
				} else {
					chain.doFilter(request, response);
					EntityManager_Access.removeInstance();
				}

				// chain.doFilter(request, response);
				// EntityManager_Access.removeInstance();

			} catch (Exception e) {
				// logger.error("doFilter()", e);
				e.printStackTrace();
			} finally {
				if (eManager != null) {
					eManager.close();
				}
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	private String getHtmlForMenu(String menuName, String appUrl) {
		Menu menu = APP_DB_Utils.querySingleObject("SELECT u FROM Menu u WHERE u.name ='" + menuName + "'", null,
				Menu.class);
		String[] array = new String[7];
		if (menu != null) {
			String theUrl = appUrl + "/prebaalmd/FileHelper?cRandomId=" + menu.getContents().get(0).getRandomId();
			theUrl = theUrl.replaceAll("/Index.html", "");
			array[0] = menu.getName();
			array[1] = "article";
			array[2] = appUrl + "#!start/" + menuName;
			array[3] = theUrl;
			array[4] = menu.getDescription();
			array[5] = menu.getName();
			array[6] = menu.getDescription();
		}

		return getHtml(array);
	}

	private String getHtmlForArticle(String articleTitel, String appUrl) {
		try {
			articleTitel = URLDecoder.decode(articleTitel, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Content content = APP_DB_Utils.querySingleObject(
				"SELECT u FROM Content u WHERE u.titel ='" + articleTitel + "'", null, Content.class);
		String[] array = new String[7];
		if (content != null) {
			String theUrl = appUrl + "/prebaalmd/FileHelper?cRandomId=" + content.getRandomId();
			theUrl = theUrl.replaceAll("/Index.html", "");
			array[0] = content.getTitel();
			array[1] = "article";
			array[2] = appUrl + "#!article/" + articleTitel;
			array[3] = theUrl;
			array[4] = content.getDescription2();
			array[5] = content.getTitel();
			array[6] = content.getDescription2();
		}
		return getHtml(array);
	}

	private String getHtml(String[] array) {
		StringBuilder builder = new StringBuilder();

		builder.append("<!DOCTYPE html>");
		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>" + array[0] + "</title>");
		builder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		builder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0,maximum-scale=1.0\">");
		builder.append("<meta name=\"description\" content=\"" + array[6] + "\">");
		builder.append("<meta property=\"og:site_name\" content=\"Prebaal\">");
		builder.append("<meta property=\"og:type\" content=\"" + array[1] + "\">");
		builder.append("<meta property=\"og:url\" content=\"" + array[2] + "\">");
		builder.append("<meta property=\"og:image\" content=\"" + array[3] + "\">");
		builder.append("<meta property=\"og:description\" content=\"" + array[4] + "\">");
		builder.append("<meta property=\"og:title\" content=\"" + array[5] + "\">");		
		builder.append("</head>");
		builder.append("<body></body>");
		builder.append("</html>");

		return builder.toString();

	}

	// @Override
	// public void destroy() {
	// if (entityManagerFactory != null) {
	// entityManagerFactory.close();
	// }
	// }
}
