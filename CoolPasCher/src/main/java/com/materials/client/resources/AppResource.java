package com.materials.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AppResource extends ClientBundle {

	@Source("/images/ajaxLoader.gif")
	ImageResource loading();

	@Source("/images/bgBase3.png")
	ImageResource background();
	// @ImageOptions(height = 60, width = 60)

	@Source("/images/search.png")

	ImageResource logo();

	@Source("/images/carteCam.png")

	ImageResource camCarte();

	@Source("/images/file-text.png")
	ImageResource content();

	@Source("/images/balloon.png")
	ImageResource comments();

	@Source("/images/eye.png")
	ImageResource details();

	@Source("/images/subMenu.jpg")
	ImageResource menuContent();

	@Source("/images/config.png")
	ImageResource config();

	@Source("/images/pdf.png")
	ImageResource pdfDoc();

	@Source("/images/worldDoc.png")
	ImageResource worldDoc();

	@Source("/images/xlsDoc.png")
	ImageResource xlsDoc();

	@Source("/images/zip.png")
	ImageResource zip();

	@Source("/images/contact2.JPG")
	ImageResource contact();

	@Source("/images/logo1.png")
	ImageResource logo1();

	@Source("/images/logo2.png")
	ImageResource logo2();

	@Source("/images/logo3.png")
	ImageResource logo3();

	@Source("/images/statistic.png")
	ImageResource statistic();

	@Source("/images/graph-bar.png")
	ImageResource grafBar();
	
	@Source("/images/pie-chart.png")
	ImageResource pieChart();

	// @Source("jquery-2.1.1.min.js")
	// TextResource jqueryMinJS();
	//
	// @Source("jquery.easing.1.3.js")
	// TextResource jqueryEaserMinJS();

}