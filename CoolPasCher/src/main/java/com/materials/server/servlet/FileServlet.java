package com.materials.server.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(FileServlet.class);
	public static Map<String, byte[]> imagesCache, slidersCache, webArticleCache, templateCache, cAreaCache, siteCache,
			byteDataCache, statisticCache;

	// private static final String DB = "94_prebaal_md_DB";
	// private static final String USER = "94_claude";
	// private static final String PWD = "DfZo#04}";
	private static final String DB = "94_cool_pas_cher_db";
	// private static final String DB = "94_mboa_online_db";
	private static final String USER = "94_claude";
	private static final String PWD = "DfZo#04}";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	// LOCAL
	// private static String JDBC_URL = "jdbc:mysql://localhost/94_prebaaldb";
	// PROD
	private static String JDBC_URL = "jdbc:mysql://localhost/" + DB;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileID = req.getParameter("id");
		String sliderID = req.getParameter("sliderID");
		String siteID = req.getParameter("siteID");
		String cRandomID = req.getParameter("cRandomId");
		String mtRandomID = req.getParameter("mtRandomID");
		String cguID = req.getParameter("cguID");
		String userID = req.getParameter("userID");
		String waUploadID = req.getParameter("waUploadID");
		String tempRandomID = req.getParameter("tempRandomID");
		String caRandomID = req.getParameter("caRandomID");
		String bdRandomID = req.getParameter("bdRandomID");
		String docType = req.getParameter("dType");
		String fName = req.getParameter("fName");
		String statRandomID = req.getParameter("statRandomID");

		byte[] bs = null;
		if (caRandomID != null) {
			bs = cAreaCache.get(caRandomID);
			if (bs == null) {
				bs = getImageByte("C_AREA", "IMAGE_DATA", caRandomID, cAreaCache);
			}
		} else if (fileID != null) {
			bs = imagesCache.get(fileID);
			if (bs == null) {
				bs = getImageByte("APPFILE", "FILE_DATA", fileID, imagesCache);
			}
		} else if (sliderID != null) {
			bs = slidersCache.get(sliderID);
			if (bs == null) {
				bs = getImageByte("SLIDER_ITEM", "IMAGE_DATA", sliderID, slidersCache);
			}
		} else if (bdRandomID != null) {
			bs = byteDataCache.get(bdRandomID);
			if (bs == null) {
				bs = getImageByte("BYTE_DATA", "IMAGE_DATA", bdRandomID, byteDataCache);
			}
		} else if (siteID != null) {
			bs = siteCache.get(siteID);
			if (bs == null) {
				bs = getImageByte("WEB_SITE", "IMAGE_DATA", siteID, siteCache);
			}

		} else if (mtRandomID != null) {
			bs = templateCache.get(mtRandomID);
			if (bs == null) {
				bs = getImageByte("MTEMPLATE", "IMAGE_DATA", mtRandomID, templateCache);
			}

		} else if (statRandomID != null) {
			bs = statisticCache.get(statRandomID);
			if (bs == null) {
				bs = getImageByte("MDSTATISTIC", "IMAGE_DATA", statRandomID, statisticCache);
			}

		} else if (cRandomID != null) {
			bs = webArticleCache.get(cRandomID);
			if (bs == null) {
				bs = getImageByte("CONTENT", "IMAGE_DATA", cRandomID, webArticleCache);
			}
		} else if (cguID != null) {
			bs = getCGUByte(cguID);
		} else if (userID != null) {
			bs = getUserByte(userID);
			if (byteArrayCheck3b(bs)) {
				bs = getImageByte("APPFILE", "FILE_DATA", "f_tiH0GW271BxC1vk", webArticleCache);
			}
		} else if (waUploadID != null) {
			bs = getTempUploadedFile(waUploadID, req.getSession());
		} else if (tempRandomID != null) {
			bs = getImageByte("TEMPFILE", "TEMP_DATA", tempRandomID, null);
		}

		if (bs != null) {
			if (docType != null) {
				resp.setContentType(docType + "; name=" + fName);
				resp.addHeader("Content-Disposition", "inline; filename=" + fName);
				resp.setContentLength(bs.length);
			}

			ServletOutputStream servletOutputStream = resp.getOutputStream();
			servletOutputStream.write(bs);
			servletOutputStream.flush();
			servletOutputStream.close();
		}
	}

	private boolean byteArrayCheck3b(final byte[] array) {
		int hits = 0;
		if (array != null) {
			for (byte b : array) {
				if (b != 0) {
					hits++;
				}
			}
		}
		return (hits == 0);
	}

	private byte[] getImageByte(String tableName, String dataColumn, String randomId, Map<String, byte[]> cache) {

		byte[] theByte = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			// Class.forName("org.apache.derby.jdbc.ClientDriver");
			// connection =
			// DriverManager.getConnection("jdbc:derby://127.0.0.1:1527/myvaadin",
			// "myvaadin", "myvaadin");

			Class.forName(DRIVER);
			connection = DriverManager.getConnection(JDBC_URL, USER, PWD);
			connection.setAutoCommit(false);

			ps = connection.prepareStatement("SELECT * FROM " + tableName + " io WHERE io.RANDOM_ID = ?");
			ps.setString(1, randomId);
			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				theByte = rs.getBytes(dataColumn);
				if (cache != null) {
					cache.put(randomId, theByte);
				}
			}

		} catch (SQLException | ClassNotFoundException e) {
			logger.error("", e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
		return theByte;
	}

	private void initCacheData(String tableName, String dataColumnName, Map<String, byte[]> cache) {

		String randomID = null;
		byte[] theByte = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection connection = null;

		try {

			Class.forName(DRIVER);
			connection = DriverManager.getConnection(JDBC_URL, USER, PWD);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement("SELECT * FROM " + tableName);
			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				theByte = rs.getBytes(dataColumnName);
				randomID = rs.getString("RANDOM_ID");
				cache.put(randomID, theByte);
			}

			rs.close();
			ps.close();

			connection.close();

		} catch (SQLException | ClassNotFoundException e) {
			logger.error("", e);
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		imagesCache = new HashMap<String, byte[]>();
		slidersCache = new HashMap<String, byte[]>();
		webArticleCache = new HashMap<String, byte[]>();
		cAreaCache = new HashMap<String, byte[]>();
		siteCache = new HashMap<String, byte[]>();
		templateCache = new HashMap<String, byte[]>();
		byteDataCache = new HashMap<String, byte[]>();
		statisticCache = new HashMap<String, byte[]>();

		initCacheData("APPFILE", "FILE_DATA", imagesCache);
		initCacheData("SLIDER_ITEM", "IMAGE_DATA", slidersCache);
		initCacheData("CONTENT", "IMAGE_DATA", webArticleCache);
		initCacheData("C_AREA", "IMAGE_DATA", cAreaCache);
		initCacheData("WEB_SITE", "IMAGE_DATA", siteCache);
		initCacheData("MTEMPLATE", "IMAGE_DATA", templateCache);
		initCacheData("BYTE_DATA", "IMAGE_DATA", byteDataCache);
		initCacheData("MDSTATISTIC", "IMAGE_DATA", statisticCache);

		Integer count = 0;

		try {
			Class.forName(DRIVER);
			Connection connection = DriverManager.getConnection(JDBC_URL, USER, PWD);
			connection.setAutoCommit(false);

			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			String theDate = formatter.format(new Date());
			PreparedStatement ps4 = connection
					.prepareStatement("SELECT * FROM PBSession pb WHERE pb.day='" + theDate + "'");
			ResultSet rs4 = ps4.executeQuery();

			while (rs4 != null && rs4.next()) {
				count = rs4.getInt("NR");
				// SessionCounter.resetCount(count);
			}

			if (rs4 != null) {
				rs4.close();
			}
			if (ps4 != null) {
				ps4.close();
			}
			connection.close();

		} catch (SQLException | ClassNotFoundException e) {
			logger.error("", e);
		}
	}

	private byte[] getCGUByte(String id) {
		byte[] theByte = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			Class.forName(DRIVER);
			connection = DriverManager.getConnection(JDBC_URL, USER, PWD);
			connection.setAutoCommit(false);

			ps = connection.prepareStatement("SELECT * FROM WEB_SITE fo WHERE fo.CGU_RANDOM_ID = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				theByte = rs.getBytes("CGU_DATA");
			}
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("", e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
		return theByte;
	}

	private byte[] getUserByte(String randomID) {
		byte[] theByte = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			Class.forName(DRIVER);
			connection = DriverManager.getConnection(JDBC_URL, USER, PWD);
			connection.setAutoCommit(false);

			ps = connection.prepareStatement("SELECT * FROM APP_USER fo WHERE fo.RANDOM_ID = ?");
			ps.setString(1, randomID);
			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				theByte = rs.getBytes("IMAGE_DATA");
			}
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("", e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
		return theByte;
	}

	public byte[] getTempUploadedFile(String currentId, HttpSession httpSession) {
		if (httpSession.getAttribute(currentId) != null) {
			byte[] objectd = (byte[]) httpSession.getAttribute(currentId);
			return objectd;
		}
		return null;
	}

	@Override
	public void destroy() {
		super.destroy();
		imagesCache = null;
		slidersCache = null;
		webArticleCache = null;
		siteCache = null;
		templateCache = null;
		byteDataCache = null;
		statisticCache = null;
	}
}