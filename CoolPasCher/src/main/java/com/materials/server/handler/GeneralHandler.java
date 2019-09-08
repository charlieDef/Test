package com.materials.server.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.APPTicket;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.client.model.WebSiteSO;
import com.materials.server.handler.content.ContentReadHelper;
import com.materials.server.handler.menu.MenuReadHelper;
import com.materials.server.handler.rsocial.RSocialReadHelper;
import com.materials.server.handler.slider.SliderReadHelper;
import com.materials.server.handler.stat.StatisticReadHelper;
import com.materials.server.model.Content;
import com.materials.server.model.TempFile;
import com.materials.server.model.User;
import com.materials.server.model.WebSite;
import com.materials.shared.APPConstants;
import com.materials.shared.action.GeneralAction;
import com.materials.shared.action.content.ContentCommand;
import com.materials.shared.action.menu.MenuCommand;
import com.materials.shared.action.slider.SliderCommand;
import com.materials.shared.action.stats.StatisticCommand;
import com.materials.shared.result.GeneralResult;
import com.materials.utils.APPUtils;
import com.materials.utils.APP_DB_Utils;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

//@ActionHandlerBean
//@Transactional(readOnly = true)
public class GeneralHandler implements ActionHandler<GeneralAction, GeneralResult> {

	private HttpServletRequest servletRequest;
	private SliderReadHelper sliderReader = new SliderReadHelper();
	private MenuReadHelper menuReader = new MenuReadHelper();
	private ContentReadHelper contentReader = new ContentReadHelper();
	private RSocialReadHelper rsocialReader = new RSocialReadHelper();
	private StatisticReadHelper statisticReadHelper = new StatisticReadHelper();

	@Override
	public GeneralResult execute(GeneralAction action, ExecutionContext context) throws DispatchException {

		GeneralResult result = null;

		switch (action.getCommand()) {

		case READ_MENUS_SLIDERS_ARTICLES_RSOCIAUX_WEBSITE: {
			List<APPObjectSO> list = new ArrayList<APPObjectSO>();

			menuReader.setHttpServlerRequest(servletRequest);

			list.addAll(sliderReader.read(SliderCommand.HOME_SLIDER, null));
			list.addAll(menuReader.read(MenuCommand.ALL_MENUS, null));
			list.addAll(contentReader.read(ContentCommand.ANNONCES_BY_PAGE_NR, "0"));
			// list.addAll(rsocialReader.read(RSocialCommand.ACTIVE_RSOCIAUX, null));
			list.addAll(statisticReadHelper.read(StatisticCommand.ALL_ACTIVE_STATISTICS, null, null));

			WebSite site = getWebsite();
			list.add(new WebSiteSO(site));
			result = new GeneralResult(verify(), list);

			if (servletRequest != null && servletRequest.getSession().isNew()) {
				List<Long> contentIds = new ArrayList<Long>();
				servletRequest.getSession().setAttribute("CONTENTS", contentIds);
			}

		}
			break;
		case SEND_MAIL: {
			String email = action.getArguments().get(APPConstants.MAIL_EMAIL);
			String nom = action.getArguments().get(APPConstants.MAIL_NOM);
			String prenom = action.getArguments().get(APPConstants.MAIL_PRENOM);
			String mssg = action.getArguments().get(APPConstants.MAIL_MESSAGE);
			String blog = action.getArguments().get(APPConstants.MAIL_BLOG);

			String mssg0 = "<b>Blog / SiteWeb : </b>" + blog + "<br><b>Internaute: </b>" + nom + ", " + prenom
					+ " <br><br>" + mssg;

			boolean success = APPUtils.sendMessage(mssg0, email, nom, prenom, "Poh Loon [Nouveau Message]");
			result = new GeneralResult(success);
		}
			break;
		case LOGIN: {

			String l_email = action.getArray()[0];
			String l_pass = action.getArray()[1];

			APPTicket appTicket = check(l_email, l_pass);

			result = getGeneraResultForLogin(appTicket);
		}
			break;
		case LOGOUT: {
			APPTicket appTicket = check(null, null);
			result = getGeneraResultForLogin(appTicket);
		}
			break;
		case CHECK_LOGIN: {

			APPTicket appTicket = verify();
			result = getGeneraResultForLogin(appTicket);
		}
			break;
		case RESET_PWD: {
			String array[] = action.getArray();
			result = reset(array[0], array[1], array[2], array[3]);
		}
			break;
		case RESET_PWD_CODE: {
			result = setResetPwdCode(action.getArray()[0]);
		}
			break;
		case DELETE_TEMPFILE: {
			TempFile tempFile = APP_DB_Utils.getTempFile(action.getKey());
			if (tempFile != null) {
				APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				return new GeneralResult(true);
			}
		}
			break;

		case SAVE_SITE: {
			WebSiteSO webSiteSO = (WebSiteSO) action.getObject();
			WebSite webSite = toWebsite(webSiteSO);
			if (webSite != null) {
				APP_DB_Utils.saveObjectToDatabase(webSite);
				return new GeneralResult(true);
			}
		}
			break;
		case INCREMENT_CONTENT_VIEW: {

			List<Long> list = (List<Long>) servletRequest.getSession().getAttribute("CONTENTS");
			Content content = APP_DB_Utils.findObject(action.getObject().getId(), Content.class);
			if (!list.contains(content.getId())) {
				content.setViewed(content.getViewed() + 1);
				list.add(content.getId());
				APP_DB_Utils.saveObjectToDatabase(content);
			}
			result = new GeneralResult(new ContentSO(content));
		}
			break;
		default:
			break;
		}
		return result;
	}

	private WebSite getWebsite() {
		WebSite webSite = APP_DB_Utils.querySingleObject("SELECT u FROM WebSite u", null, WebSite.class);
		if (webSite == null) {

			webSite = new WebSite("testShortName", "testLongName");
			webSite.setAdresse("testAdresse");
			webSite.setCopyRightText("test");
			webSite.setDescription("test");
			webSite.setMailSendSuccessText("test");
			webSite.setEmail("test");
			webSite.setEmailAdmin("test");
			webSite.setEmailPresident("test");
			webSite.setEzuw(APPUtils.getEncriptedPwd("test"));
			webSite.setLongName("test");
			webSite.setShortName("test");
			webSite.setTel("test");
			webSite.setName("testName");
			APP_DB_Utils.saveObjectToDatabase(webSite);
		}
		return webSite;
	}

	@Override
	public Class<GeneralAction> getActionType() {
		return GeneralAction.class;
	}

	@Override
	public void rollback(GeneralAction action, GeneralResult result, ExecutionContext context)
			throws DispatchException {
	}

	private APPTicket check(String email, String pwd) {

		APPTicket pBTicket = null;
		if (email == null && pwd == null) {
			servletRequest.getSession().setAttribute("appTicket", null);
			return pBTicket;
		}

		User user = APP_DB_Utils.querySingleObject("SELECT u FROM User u WHERE u.email ='" + email + "'", null,
				User.class);
		if (user != null) {
			String decriptedPwd = APPUtils.getDecriptedPwd(user.getZuwu());
			if (decriptedPwd.equals(pwd)) {
				updateUserFavorits(user);
				UserSO userSO = new UserSO(user);

				pBTicket = new APPTicket(new Date());
				pBTicket.setUserLogged(userSO);
				servletRequest.getSession().setAttribute("appTicket", pBTicket);
			}
		}
		return pBTicket;
	}

	private GeneralResult getGeneraResultForLogin(APPTicket appTicket) {
		menuReader.setHttpServlerRequest(servletRequest);

		List<APPObjectSO> list = new ArrayList<APPObjectSO>();
		list.addAll(menuReader.read(MenuCommand.ALL_BASE_MENU_FOR_HEADER, null));
		GeneralResult result = new GeneralResult(appTicket, list);

		return result;
	}

	private GeneralResult reset(String email, String pwdResetCode, String newPwd, String newPwdRepeat) {

		GeneralResult result = null;
		boolean changed = false;

		User user = APP_DB_Utils.querySingleObject("SELECT u FROM User u WHERE u.email ='" + email + "'", null,
				User.class);

		String mssg = "";
		if (user != null) {

			long validity = user.getPwdChangeCodeValidity().getTime();
			long actual = new Date().getTime();
			boolean codeValid = (actual - validity) < new Long(86400000);

			if (codeValid) {
				if (pwdResetCode.equals(user.getPwdChangeCode())) {
					if (newPwd.equals(newPwdRepeat)) {
						String encriptedPwd = APPUtils.getEncriptedPwd(newPwd);
						user.setZuwu(encriptedPwd);
						user.setPwdChangeCode("");
						user.setPwdChangeCodeValidity(null);
						user.setPwdChangeRequired(false);
						APP_DB_Utils.saveObjectToDatabase(user);
						changed = true;
						mssg = "Mot de pass reinitialisé avec succès !!";
					} else {
						mssg = "Les mots de pass ne sont pas identiques!!";
					}
				} else {
					mssg = "Code pour reinitialisation du mot de pass incorrect!!";
				}
			} else {
				mssg = "Code pour reinitialisation du mot de pass expiré !!";
			}

		} else {
			mssg = "Aucun utilisateur associé à cet email !!";
		}
		result = new GeneralResult(mssg);
		result.setValue(changed);

		return result;
	}

	private GeneralResult setResetPwdCode(String email) {

		GeneralResult result = null;

		User user = APP_DB_Utils.querySingleObject("SELECT u FROM User u WHERE u.email ='" + email + "'", null,
				User.class);
		if (user != null) {

			String randomCode = RandomStringUtils.randomAlphanumeric(10);
			user.setPwdChangeCode(randomCode);
			user.setPwdChangeRequired(true);
			user.setPwdChangeCodeValidity(new Date());

			APP_DB_Utils.saveObjectToDatabase(user);

			// SendMail
			String mssg = "Connectez vous en utilisant le code suivant:<br><b>" + randomCode + "</b><br>"
					+ "<br>Reinitialisez  ensuite votre mot de pass<br><br>Cordialement,<br><br>MBOA-ONLINE";

			String subject = "Administrateur [mboa-online.com]";

			boolean success = APPUtils.sendMessage(mssg, user.getEmail(), user.getName(), user.getLastName(), subject);
			result = new GeneralResult(success);

		}
		return result;
	}

	public void setHttpServlerRequest(HttpServletRequest threadLocalRequest) {
		this.servletRequest = threadLocalRequest;
	}

	private WebSite toWebsite(WebSiteSO webSiteSO) {

		WebSite webSite = APP_DB_Utils.findObject(webSiteSO.getId(), WebSite.class);
		if (webSite != null) {
			webSite.setAdresse(webSiteSO.getAdresse());
			webSite.setCopyRightText(webSiteSO.getCopyRightText());
			webSite.setDescription(webSiteSO.getDescription());
			webSite.setMailSendSuccessText(webSiteSO.getMailSendSuccessText());
			webSite.setEmail(webSiteSO.getEmail());
			webSite.setEmailAdmin(webSiteSO.getEmailAdmin());
			webSite.setName(webSiteSO.getName());
			webSite.setEmailPresident(webSiteSO.getEmailPresident());
			webSite.setRandomId(webSiteSO.getRandomId());
			// setEzuw(ezuw);
			webSite.setLongName(webSiteSO.getLongName());
			webSite.setShortName(webSiteSO.getShortName());
			webSite.setTel(webSiteSO.getTel());
			// if (webSite.getEzuw() != null && !webSite.getEzuw().isEmpty()) {
			// webSite.setEzuw(APPUtils.getEncriptedPwd(webSite.getEzuw()));
			// }
			// TODO be carefull here --> Image set
			if (webSiteSO.getRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(webSiteSO.getRandomId());
				if (tempFile != null) {
					webSite.setImageData(tempFile.getTempData());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}

			if (webSiteSO.getCguRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(webSiteSO.getCguRandomId());
				if (tempFile != null) {
					webSite.setCguData(tempFile.getTempData());
					webSite.setCguRandomId(webSiteSO.getCguRandomId());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}
		}

		return webSite;
	}

	private APPTicket verify() {

		APPTicket newTicket = new APPTicket();

		if (servletRequest != null && servletRequest.getSession().getAttribute("appTicket") != null) {
			Object object = servletRequest.getSession().getAttribute("appTicket");
			APPTicket ticket = (APPTicket) object;

			long dateActual = new Date().getTime();
			long creation = ticket.getCreationDate().getTime();
			if ((dateActual - creation) > 1200000) {// 20min
				servletRequest.getSession().setAttribute("appTicket", null);

			} else {
				User user = APP_DB_Utils.querySingleObject(
						"SELECT u FROM User u WHERE u.email ='" + ticket.getUserLogged().getEmail() + "'", null,
						User.class);
				newTicket = ticket;
				updateUserFavorits(user);
				newTicket.setUserLogged(new UserSO(user));
				newTicket.setCreationDate(new Date());
			}
		}
		return newTicket;

	}

	void updateUserFavorits(User user) {
		String favorits = user.getUserProperties().getOrDefault(UserSO.FAVORITS, "");
		boolean changed = false;
		if (!favorits.isEmpty()) {
			final List<String> favoritsIds = Arrays.asList(favorits.split("#"));
			List<ContentSO> contents = contentReader.read(ContentCommand.ALL_ARTICLE_CONTENTS, null).stream()
					.filter(x -> (x.isActive() && x.isShowToHome())).collect(Collectors.toList());
			List<String> allIds = contents.stream().map(x -> {
				return String.valueOf(x.getId());
			}).collect(Collectors.toList());

			for (String str : favoritsIds) {
				if (!allIds.contains(str)) {
					favorits = favorits.replaceAll(str + "#", "");
					changed = true;
				}
			}
		}

		if (changed) {
			user.addPropertie(UserSO.FAVORITS, favorits);
			APP_DB_Utils.saveObjectToDatabase(user);
		}
	}

	void updateUserAnnonces(User user) {
		String annonces = user.getUserProperties().getOrDefault(UserSO.ANNONCES, "");
		boolean changed = false;
		if (!annonces.isEmpty()) {
			final List<String> annoncesIds = Arrays.asList(annonces.split("#"));
			List<ContentSO> contents = contentReader.read(ContentCommand.ALL_ARTICLE_CONTENTS, null).stream()
					.filter(x -> (x.isActive() && x.isShowToHome())).collect(Collectors.toList());
			List<String> allIds = contents.stream().map(x -> {
				return String.valueOf(x.getId());
			}).collect(Collectors.toList());

			for (String str : annoncesIds) {
				if (!allIds.contains(str)) {
					annonces = annonces.replaceAll(str + "#", "");
					changed = true;
				}
			}
		}

		if (changed) {
			user.addPropertie(UserSO.ANNONCES, annonces);
			APP_DB_Utils.saveObjectToDatabase(user);
		}
	}

}
