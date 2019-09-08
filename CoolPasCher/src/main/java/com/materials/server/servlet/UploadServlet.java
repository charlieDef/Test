package com.materials.server.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.materials.server.model.TempFile;
import com.materials.utils.APPUtils;
import com.materials.utils.APP_DB_Utils;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String lastUploadID = req.getParameter("lastUploadID");
		String contentID = req.getParameter("contentID");
		// String contentID = "155";

		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(10000000);
			upload.setSizeMax(10000000);

			// Parse the request
			List<FileItem> items;
			try {
				items = upload.parseRequest(req);
				for (FileItem item : items) {
					// process only file upload - discard other form item types
					if (item.isFormField()) {
						continue;
					}

					String fileName = item.getName();
					String fileType = item.getContentType();
					// get only the file name not whole path
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}

					byte[] data = item.get();

					String randomNumber = RandomStringUtils.random(15, true, true);
					randomNumber += String.valueOf(System.currentTimeMillis());

					String user = "Unknow";
					// PBTicket pBTicket = (PBTicket) req.getSession().getAttribute("pbTicket");
					// if (pBTicket != null && pBTicket.getUserLogged() != null) {
					// user = pBTicket.getUserLogged().getName() + " " +
					// pBTicket.getUserLogged().getLastName();
					// }

					saveTempFile(user, randomNumber, fileType, lastUploadID, data);
					resp.setContentType("text/html");
					resp.getWriter().printf(randomNumber + "_" + fileName + "_" + fileType);
					resp.getWriter().flush();
					resp.getWriter().close();
					resp.flushBuffer();

				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveTempFile(String user, String randomNumber, String type, String lastUpload, byte[] data) {
		// save neu
		TempFile neuTempFile = new TempFile(randomNumber);
		neuTempFile.setTempData(data);
		neuTempFile.setCreationDate(new Date());
		neuTempFile.setCreator(user);
		neuTempFile.setDataType(type);
		// req.getSession().setAttribute(randomNumber, data);
		APP_DB_Utils.saveObjectToDatabase(neuTempFile);

		// delete last
		if (StringUtils.isNotBlank(lastUpload)) {
			deleteLastTempFile(lastUpload);
		}

	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	public void destroy() {
	}

	private void deleteLastTempFile(String lastID) {
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("rId", lastID);
		TempFile oldTempFile = APPUtils.getTempFile(lastID);

		if (oldTempFile != null) {
			APP_DB_Utils.deleteObjectFromDatabase(oldTempFile.getId(), TempFile.class);
		}
	}
}