package com.materials.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import com.materials.server.EntityManager_Access;
import com.materials.server.model.TempFile;

public class APPUtils {

	private static String SERVER_SIDE_URL = APPUtils.getURL_ServerSide() + "/FileHelper?";

	public static TempFile getTempFile(String lastID) {
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("rId", lastID);
		TempFile oldTempFile = APP_DB_Utils.querySingleObject("SELECT e FROM TempFile e WHERE e.randomId = :rId", param,
				TempFile.class);
		if (oldTempFile != null) {
			return oldTempFile;
		}

		return null;
	}

	public static String getURL_ServerSide() {

		HttpServletRequest req = EntityManager_Access.getInstance().getHttpServletRequest();

		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // hostname.com
		int serverPort = req.getServerPort(); // 80
		String contextPath = req.getContextPath(); // /mywebapp
		String servletPath = req.getServletPath(); // /servlet/MyServlet
		servletPath = servletPath.replaceAll("/dispatch", "");
		String pathInfo = req.getPathInfo(); // /a/b;c=123
		String queryString = req.getQueryString(); // d=789

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if (serverPort != 80 && serverPort != 443) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath).append(servletPath);

		if (pathInfo != null) {
			url.append(pathInfo);
		}
		if (queryString != null) {
			url.append("?").append(queryString);
		}
		return url.toString();
	}

	public static String getDecriptedPwd(String pwd) {
		if (pwd != null) {
			try {
				String key = "Nepague1?!BALENG"; // 128 bit key
				// Create key and cipher
				Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance("AES");
				// decrypt the text1
				byte[] base64Decoded = Base64.decodeBase64(pwd.getBytes("utf-8"));
				// decrypt the text2
				cipher.init(Cipher.DECRYPT_MODE, aesKey);
				String decrypted = new String(cipher.doFinal(base64Decoded));
				return decrypted;
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String getEncriptedPwd(String pwd) {

		String key = "Nepague1?!BALENG"; // 128 bit key
		// Create key and cipher
		Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES");
			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(pwd.getBytes());
			byte[] bytesBase64Encoded = Base64.encodeBase64(encrypted);

			return new String(bytesBase64Encoded);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean sendMessage(String mssg, String email, String nom, String prenom, String subject) {
		boolean success = true;
		Session session = APPUtils.getMailSession();
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email, prenom + ", " + nom));
			message.setSubject(subject);
			// message.setText(mssg);
			message.setContent(mssg, "text/html");

			InternetAddress address = new InternetAddress(email, nom + ", " + prenom);
			message.addRecipient(Message.RecipientType.TO, address);

			// try {
			// Thread.sleep(5000);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			Transport.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	public static Session getMailSession() {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		// props.put("mail.smtp.socketFactory.port", "465");
		// props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("prebaal@gmail.com", "LahPohNeket");
			}
		});
		return session;
	}

}
