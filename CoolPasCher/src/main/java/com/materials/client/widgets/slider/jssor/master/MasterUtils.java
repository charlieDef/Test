package com.materials.client.widgets.slider.jssor.master;

import com.materials.client.model.ByteDataSO;
import com.materials.client.model.SliderSO;

public class MasterUtils {

	private static String PADDING = "border-radius:4px;background-color:white;padding:5px;box-shadow:0px 0px 8px;";

	public static String getImgHtml(SliderSO sliderSO) {
		String html = "";
		switch (sliderSO.getType()) {
		case SliderSO.TYPE_SIMPLE_A:
			html = getSimple_A(sliderSO);
			break;
		case SliderSO.TYPE_SIMPLE_B:
			html = getSimple_B(sliderSO);
			break;
		case SliderSO.TYPE_SIMPLE_C:
			html = getSimple_C(sliderSO);
			break;
		case SliderSO.TYPE_SIMPLE_D:
			html = getSimple_D(sliderSO);
			break;
		case SliderSO.TYPE_SIMPLE_E:
			html = getSimple_E(sliderSO);
			break;
		case SliderSO.TYPE_SIMPLE_F:
			html = getSimple_F(sliderSO);
			break;
		case SliderSO.TYPE_SIMPLE_G:
			html = getSimple_G(sliderSO);
			break;
		default:
			break;
		}
		return html;
	}

	public static String getImgHtmlPresentation(SliderSO sliderSO) {
		String html = "";
		switch (sliderSO.getType()) {
		case SliderSO.TYPE_3CARD_A:
			html = getType_3Card_A(sliderSO);
			break;
		case SliderSO.TYPE_3CARD_B:
			html = getType_3Card_B(sliderSO);
			break;
		case SliderSO.TYPE_3CARD_C:
			html = getType_3Card_C(sliderSO);
			break;
		case SliderSO.TYPE_2CARD_A:
			html = getType_2Card_A(sliderSO);
			break;
		case SliderSO.TYPE_2CARD_B:
			html = getType_2Card_B(sliderSO);
			break;
		case SliderSO.TYPE_1CARD_A:
			html = getType_1Card_A(sliderSO);
			break;
		case SliderSO.TYPE_1CARD_B:
			html = getType_1Card_B(sliderSO);
			break;
		default:
			break;
		}
		return html;
	}

	private static String getType_1Card_A(SliderSO sliderSO) {

		String titel = getText(sliderSO.getTitel());
		String titelColor = getColor(sliderSO.getTitel());
		String titelFontSize = getFontSize(sliderSO.getTitel());

		String titel1 = getText(sliderSO.getTitel1());
		String titelColor1 = getColor(sliderSO.getTitel1());
		String titelFontSize1 = getFontSize(sliderSO.getTitel1());

		String titel2 = getText(sliderSO.getTitel2());
		String titelColor2 = getColor(sliderSO.getTitel2());
		String titelFontSize2 = getFontSize(sliderSO.getTitel2());

		String textInfo = getText(sliderSO.getTextInfo());
		String textInfoColor = getColor(sliderSO.getTextInfo());
		String textInfoSize3 = getFontSize(sliderSO.getTextInfo());

		String builder = new String();
		builder += "<div>";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";

		builder += "<div data-ts=\"39\" data-ts=\"preserve-3d\" style=\"position:absolute;top:61px;left:769px;width:534px;height:352px;box-shadow:0px 0px;\">";
		builder += "<img style=\"position:absolute;top:-9px;left:17px;width:502px;height:365px;max-width:502px;box-shadow:0px 0px;\" src=\"img/1-021.png\" />";
		builder += "<div data-t=\"40\" style=\"position:absolute;top:15px;left:628px;width:488px;height:39px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize + ";color:" + titelColor + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ titel + "</div></div>";

		ByteDataSO byteDataSO1 = sliderSO.getByteData(1);
		String width1 = stringOK(byteDataSO1.getWidth()) ? byteDataSO1.getWidth() : "560";
		String height1 = stringOK(byteDataSO1.getHeight()) ? byteDataSO1.getHeight() : "360";
		String padding = byteDataSO1.isPadding() ? PADDING : "";

		builder += "<img data-t=\"41\" style=\"" + padding + "position:absolute;top:50px;left:308px;width:" + width1
				+ "px;height:" + height1 + "px;max-width:" + width1 + "px;\" src=\"" + byteDataSO1.getImageUrl()
				+ "\" />";

		builder += "<div data-t=\"42\" style=\"position:absolute;top:140px;left:1395px;width:488px;height:39px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize1 + ";color:" + titelColor1 + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ titel1 + "</div>";
		builder += "<div data-t=\"43\" style=\"position:absolute;top:210px;left:1395px;width:488px;height:39px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize2 + ";color:" + titelColor2 + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ titel2 + "</div>";
		builder += "<div data-t=\"44\" style=\"position:absolute;top:280px;left:1393px;width:488px;height:39px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ textInfoSize3 + ";color:" + textInfoColor + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ textInfo + "</div>";

		builder += " </div>";
		return builder;
	}

	private static String getType_1Card_B(SliderSO sliderSO) {

		String titel = getText(sliderSO.getTitel());
		String titelColor = getColor(sliderSO.getTitel());
		String titelFontSize = getFontSize(sliderSO.getTitel());

		String titel1 = getText(sliderSO.getTitel1());
		String titelColor1 = getColor(sliderSO.getTitel1());
		String titelFontSize1 = getFontSize(sliderSO.getTitel1());

		String titel2 = getText(sliderSO.getTitel2());
		String titelColor2 = getColor(sliderSO.getTitel2());
		String titelFontSize2 = getFontSize(sliderSO.getTitel2());

		String textInfo = getText(sliderSO.getTextInfo());
		String textInfoColor = getColor(sliderSO.getTextInfo());
		String textInfoSize3 = getFontSize(sliderSO.getTextInfo());

		ByteDataSO byteDataSO1 = sliderSO.getByteData(1);
		String width1 = stringOK(byteDataSO1.getWidth()) ? byteDataSO1.getWidth() : "560";
		String height1 = stringOK(byteDataSO1.getHeight()) ? byteDataSO1.getHeight() : "360";
		String padding = byteDataSO1.isPadding() ? PADDING : "";

		String builder = new String();
		builder += "<div>";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<img data-t=\"45\"  style=\"" + padding + "position:absolute;top:51px;left:689px;width:" + width1
				+ "px;height:" + height1 + "px;max-width:" + width1 + "px;\"  src=\"" + byteDataSO1.getImageUrl()
				+ "\" />";

		builder += "<div data-t=\"46\" style=\"position:absolute;top:60px;left:-511px;width:403px;height:39px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize + ";color:" + titelColor + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ titel + "</div>";
		builder += "<div data-t=\"47\" style=\"position:absolute;top:125px;left:-511px;width:409px;height:39px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize1 + ";color:" + titelColor1 + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ titel1 + "</div>";
		builder += "<div data-t=\"48\" style=\"position:absolute;top:190px;left:-511px;width:398px;height:39px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize2 + ";color:" + titelColor2 + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ titel2 + "</div>";
		builder += "<div data-t=\"49\" style=\"position:absolute;top:260px;left:-511px;width:396px;height:39px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ textInfoSize3 + ";color:" + textInfoColor + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ textInfo + "</div>";

		builder += " </div>";
		return builder;
	}

	private static String getType_2Card_A(SliderSO sliderSO) {

		String titel = getText(sliderSO.getTitel());
		String titelColor = getColor(sliderSO.getTitel());
		String titelFontSize = getFontSize(sliderSO.getTitel());

		String titel1 = getText(sliderSO.getTitel1());
		String titelColor1 = getColor(sliderSO.getTitel1());
		String titelFontSize1 = getFontSize(sliderSO.getTitel1());

		String builder = new String();
		builder += "<div>";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"33\" style=\"position:absolute;top:330px;left:276px;width:913px;height:123px;box-shadow:0px 0px;\">";
		builder += "<img style=\"position:absolute;top:-1px;left:16px;width:906px;height:131px;max-width:906px;box-shadow:0px 0px;\" src=\"img/1-021.png\" />";
		builder += "<div style=\"position:absolute;top:53px;left:122px;width:763px;height:43px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize + ";color:" + titelColor + ";font-style:italic;line-height:1.2;text-align:center;\">"
				+ titel + "</div>";
		builder += "<div style=\"position:absolute;top:6px;left:43px;width:805px;height:58px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize1 + ";color:" + titelColor1 + ";font-style:italic;line-height:1.2;text-align:center;\">"
				+ titel1 + "</div></div>";

		ByteDataSO byteDataSO1 = sliderSO.getByteData(1);
		ByteDataSO byteDataSO2 = sliderSO.getByteData(2);

		String padding1 = byteDataSO1.isPadding() ? PADDING : "";
		String padding2 = byteDataSO2.isPadding() ? PADDING : "";

		String width1 = stringOK(byteDataSO1.getWidth()) ? byteDataSO1.getWidth() : "440";
		String width2 = stringOK(byteDataSO2.getWidth()) ? byteDataSO2.getWidth() : "440";

		String height1 = stringOK(byteDataSO1.getHeight()) ? byteDataSO1.getHeight() : "250";
		String height2 = stringOK(byteDataSO2.getHeight()) ? byteDataSO2.getHeight() : "250";

		builder += "<img data-t=\"34\" style=\" " + padding1 + "position:absolute;top:50px;left:50px;width:" + width1
				+ "px;height:" + height1 + "px;max-width:" + width1 + "px;\" src=\"" + byteDataSO1.getImageUrl()
				+ "\" />";

		builder += "<img data-t=\"35\" style=\"" + padding2 + "position:absolute;top:50px;left:50px;width:" + width2
				+ "px;height:" + height2 + "px;max-width:" + width2 + "px;\" src=\"" + byteDataSO2.getImageUrl()
				+ "\" />";

		builder += " </div>";
		return builder;
	}

	private static String getType_2Card_B(SliderSO sliderSO) {

		String titel = getText(sliderSO.getTitel());
		String titelColor = getColor(sliderSO.getTitel());
		String titelFontSize = getFontSize(sliderSO.getTitel());

		String titel1 = getText(sliderSO.getTitel1());
		String titelColor1 = getColor(sliderSO.getTitel1());
		String titelFontSize1 = getFontSize(sliderSO.getTitel1());

		String builder = new String();
		builder += "<div>";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"36\" style=\"position:absolute;top:477px;left:0px;width:913px;height:123px;box-shadow:0px 0px;\">";
		builder += "<img style=\"position:absolute;top:0px;left:0px;width:906px;height:131px;max-width:906px;box-shadow:0px 0px;\" src=\"img/1-021.png\" />";
		builder += "<div style=\"position:absolute;top:61px;left:124px;width:763px;height:43px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize1 + ";color:" + titelColor + ";font-style:italic;line-height:1.2;text-align:center;\">"
				+ titel + "</div>";
		builder += "<div style=\"position:absolute;top:8px;left:71px;width:805px;height:58px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize + ";color:" + titelColor1 + ";font-style:italic;line-height:1.2;text-align:center;\">"
				+ titel1 + "</div></div>";

		ByteDataSO byteDataSO1 = sliderSO.getByteData(1);
		ByteDataSO byteDataSO2 = sliderSO.getByteData(2);

		String padding1 = byteDataSO1.isPadding() ? PADDING : "";
		String padding2 = byteDataSO2.isPadding() ? PADDING : "";

		String width1 = stringOK(byteDataSO1.getWidth()) ? byteDataSO1.getWidth() : "440";
		String width2 = stringOK(byteDataSO2.getWidth()) ? byteDataSO2.getWidth() : "440";

		String height1 = stringOK(byteDataSO1.getHeight()) ? byteDataSO1.getHeight() : "250";
		String height2 = stringOK(byteDataSO2.getHeight()) ? byteDataSO2.getHeight() : "250";

		builder += "<img data-t=\"37\" style=\"" + padding1 + "position:absolute;top:50px;left:224px;width:" + width1
				+ "px;height:" + height1 + "px;max-width:" + width1 + "px;\" src=\"" + byteDataSO1.getImageUrl()
				+ "\" />";

		builder += "<img data-t=\"38\" style=\"" + padding2 + "position:absolute;top:50px;left:739px;width:" + width2
				+ "px;height:" + height2 + "px;max-width:" + width2 + "px;\" src=\"" + byteDataSO2.getImageUrl()
				+ "\" />";

		builder += " </div>";
		return builder;
	}

	private static String getType_3Card_A(SliderSO sliderSO) {

		String titel = getText(sliderSO.getTitel());
		String titelColor = getColor(sliderSO.getTitel());
		String titelFontSize = getFontSize(sliderSO.getTitel());

		String titel1 = getText(sliderSO.getTitel1());
		String titelColor1 = getColor(sliderSO.getTitel1());
		String titelFontSize1 = getFontSize(sliderSO.getTitel1());

		String builder = new String();
		builder += "<div data-p=\"230\"> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";

		builder += "<div data-ts=\"preserve-3d\" style=\"position:absolute;top:75px;left:0px;width:665px;height:131px;\">";
		builder += "<img data-t=\"15\" style=\"position:absolute;top:-20px;left:-621px;width:607px;height:131px;max-width:607px;\" src=\"img/1-021.png\" />";
		builder += "<div data-t=\"16\" style=\"position:absolute;top:-2px;left:-348px;width:316px;height:58px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize + ";color:" + titelColor + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ titel + "</div>";
		builder += "<div data-t=\"17\" style=\"position:absolute;top:44px;left:-348px;width:316px;height:43px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize1 + ";color:" + titelColor1 + ";font-style:italic;line-height:1.2;text-align:center;\">"
				+ titel1 + "</div></div>";

		ByteDataSO byteDataSO1 = sliderSO.getByteData(1);
		ByteDataSO byteDataSO2 = sliderSO.getByteData(2);
		ByteDataSO byteDataSO3 = sliderSO.getByteData(3);

		String padding1 = byteDataSO1.isPadding() ? PADDING : "";
		String padding2 = byteDataSO2.isPadding() ? PADDING : "";
		String padding3 = byteDataSO3.isPadding() ? PADDING : "";

		String width1 = stringOK(byteDataSO1.getWidth()) ? byteDataSO1.getWidth() : "175";
		String width2 = stringOK(byteDataSO2.getWidth()) ? byteDataSO2.getWidth() : "220";
		String width3 = stringOK(byteDataSO3.getWidth()) ? byteDataSO3.getWidth() : "243";

		String height1 = stringOK(byteDataSO1.getHeight()) ? byteDataSO1.getHeight() : "165";
		String height2 = stringOK(byteDataSO2.getHeight()) ? byteDataSO2.getHeight() : "234";
		String height3 = stringOK(byteDataSO3.getHeight()) ? byteDataSO3.getHeight() : "452";

		builder += "<img data-t=\"18\" style=\"" + padding1 + "position:absolute;top:182px;left:1389px;width:" + width1
				+ "px;height:" + height1 + "px;max-width:" + width1 + "px;\" src=\"" + byteDataSO1.getImageUrl()
				+ "\" />";

		builder += "<img data-t=\"19\" style=\"" + padding2 + "position:absolute;top:471px;left:43px;width:" + width2
				+ "px;height:" + height2 + "px;max-width:" + width2 + "px;\" src=\"" + byteDataSO2.getImageUrl()
				+ "\" />";
		builder += " <img data-t=\"20\" style=\"" + padding3 + "position:absolute;top:471px;left:636px;width:" + width3
				+ "px;height:" + height3 + "px;max-width:" + width3 + "px;\" src=\"" + byteDataSO3.getImageUrl()
				+ "\" />";

		builder += " </div>";
		return builder;
	}

	private static String getType_3Card_B(SliderSO sliderSO) {

		String titel = getText(sliderSO.getTitel());
		String titelColor = getColor(sliderSO.getTitel());
		String titelFontSize = getFontSize(sliderSO.getTitel());

		String titel1 = getText(sliderSO.getTitel1());
		String titelColor1 = getColor(sliderSO.getTitel1());
		String titelFontSize1 = getFontSize(sliderSO.getTitel1());

		String builder = new String();
		builder += "<div> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";

		builder += "<div data-ts=\"preserve-3d\" style=\"position:absolute;top:319px;left:0px;width:778px;height:131px;\">";
		builder += "<img data-t=\"21\" style=\"position:absolute;top:-20px;left:-621px;width:607px;height:131px;max-width:607px;\" src=\"img/1-021.png\" />";
		builder += "<div data-t=\"22\" style=\"position:absolute;top:44px;left:-348px;width:316px;height:43px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize + ";color:" + titelColor + ";font-style:italic;line-height:1.2;text-align:center;\">"
				+ titel + "</div>";
		builder += "<div data-t=\"23\" style=\"position:absolute;top:-2px;left:-348px;width:316px;height:58px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize1 + ";color:" + titelColor1 + ";font-style:italic;line-height:1.2;text-align:left;\">"
				+ titel1 + "</div></div>";

		ByteDataSO byteDataSO1 = sliderSO.getByteData(1);
		ByteDataSO byteDataSO2 = sliderSO.getByteData(2);
		ByteDataSO byteDataSO3 = sliderSO.getByteData(3);

		String padding1 = byteDataSO1.isPadding() ? PADDING : "";
		String padding2 = byteDataSO2.isPadding() ? PADDING : "";
		String padding3 = byteDataSO3.isPadding() ? PADDING : "";

		String width1 = stringOK(byteDataSO1.getWidth()) ? byteDataSO1.getWidth() : "175";
		String width2 = stringOK(byteDataSO2.getWidth()) ? byteDataSO2.getWidth() : "163";
		String width3 = stringOK(byteDataSO3.getWidth()) ? byteDataSO3.getWidth() : "180";

		String height1 = stringOK(byteDataSO1.getHeight()) ? byteDataSO1.getHeight() : "165";
		String height2 = stringOK(byteDataSO2.getHeight()) ? byteDataSO2.getHeight() : "180";
		String height3 = stringOK(byteDataSO3.getHeight()) ? byteDataSO3.getHeight() : "180";

		builder += "<img data-t=\"24\" style=\"" + padding1 + "position:absolute;top:-172px;left:1112px;width:" + width1
				+ "px;height:" + height1 + "px;max-width:" + width1 + "px;\" src=\"" + byteDataSO1.getImageUrl()
				+ "\" />";

		builder += "<img data-t=\"25\" style=\"" + padding2 + "position:absolute;top:-255px;left:70px;width:" + width2
				+ "px;height:" + height2 + "px;max-width:" + width2 + "px;\" src=\"" + byteDataSO2.getImageUrl()
				+ "\" />";
		builder += " <img data-t=\"26\" style=\"" + padding3 + "position:absolute;top:-199px;left:630px;width:" + width3
				+ "px;height:" + height3 + "px;max-width:" + width3 + "px;\" src=\"" + byteDataSO3.getImageUrl()
				+ "\" />";

		builder += " </div>";
		return builder;
	}

	private static String getType_3Card_C(SliderSO sliderSO) {

		String titel = getText(sliderSO.getTitel());
		String titelColor = getColor(sliderSO.getTitel());
		String titelFontSize = getFontSize(sliderSO.getTitel());

		String titel1 = getText(sliderSO.getTitel1());
		String titelColor1 = getColor(sliderSO.getTitel1());
		String titelFontSize1 = getFontSize(sliderSO.getTitel1());

		String builder = new String();
		builder += "<div> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";

		builder += "<div data-ts=\"preserve-3d\" style=\"position:absolute;top:0px;left:0px;width:778px;height:131px;\">";
		builder += "<img data-t=\"27\" style=\"position:absolute;top:473px;left:495px;width:886px;height:131px;max-width:886px;\" src=\"img/1-021.png\" />";
		builder += "<div data-t=\"28\" style=\"position:absolute;top:370px;left:598px;width:763px;height:43px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize + ";color:" + titelColor + ";font-style:italic;line-height:1.2;text-align:center;\">"
				+ titel + "</div>";
		builder += "<div data-t=\"29\" style=\"position:absolute;top:320px;left:566px;width:805px;height:58px;font-family:Georgia,'Times New Roman',Times,serif;font-size:"
				+ titelFontSize1 + ";color:" + titelColor1 + ";font-style:italic;line-height:1.2;text-align:center;\">"
				+ titel1 + "</div></div>";

		ByteDataSO byteDataSO1 = sliderSO.getByteData(1);
		ByteDataSO byteDataSO2 = sliderSO.getByteData(2);
		ByteDataSO byteDataSO3 = sliderSO.getByteData(3);

		String padding1 = byteDataSO1.isPadding() ? PADDING : "";
		String padding2 = byteDataSO2.isPadding() ? PADDING : "";
		String padding3 = byteDataSO3.isPadding() ? PADDING : "";

		String width1 = stringOK(byteDataSO1.getWidth()) ? byteDataSO1.getWidth() : "180";
		String width2 = stringOK(byteDataSO2.getWidth()) ? byteDataSO2.getWidth() : "180";
		String width3 = stringOK(byteDataSO3.getWidth()) ? byteDataSO3.getWidth() : "180";

		String height1 = stringOK(byteDataSO1.getHeight()) ? byteDataSO1.getHeight() : "180";
		String height2 = stringOK(byteDataSO2.getHeight()) ? byteDataSO2.getHeight() : "180";
		String height3 = stringOK(byteDataSO3.getHeight()) ? byteDataSO3.getHeight() : "180";

		builder += "<img data-t=\"30\" style=\"" + padding1 + "position:absolute;top:70px;left:351px;width:" + width1
				+ "px;height:" + height1 + "px;max-width:" + width1 + "px;\" src=\"" + byteDataSO1.getImageUrl()
				+ "\" />";
		builder += "<img data-t=\"31\" style=\"" + padding2 + "position:absolute;top:75px;left:830px;width:" + width2
				+ "px;height:" + height2 + "px;max-width:" + width2 + "px;\" src=\"" + byteDataSO2.getImageUrl()
				+ "\" />";
		builder += " <img data-t=\"32\" style=\"" + padding3 + "position:absolute;top:70px;left:1195px;width:" + width3
				+ "px;height:" + height3 + "px;max-width:" + width3 + "px;\" src=\"" + byteDataSO3.getImageUrl()
				+ "\" />";
		builder += " </div>";
		return builder;
	}

	private static String getSimple_A(SliderSO sliderSO) {

		String textColor = stringOK(sliderSO.getTitelColor()) ? sliderSO.getTitelColor() : "white";
		String builder = new String();
		builder += "<div data-p=\"230\"> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"0\" style=\"position:absolute;top:471px;left:-1px;width:398px;height:51px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;font-weight:200;color:"
				+ textColor + ";line-height:1.2;text-align:center;background-color:rgba(255,188,5,0);\">"
				+ sliderSO.getTitel() + "</div>";
		builder += "<div data-t=\"1\" style=\"position:absolute;top:-40px;left:125px;width:408px;height:49px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;color:"
				+ textColor + ";line-height:1.2;text-align:center;\">" + sliderSO.getTitel1() + "</div>";
		builder += " </div>";
		return builder;
	}

	private static String getSimple_B(SliderSO sliderSO) {

		String textColor = stringOK(sliderSO.getTitelColor()) ? sliderSO.getTitelColor() : "white";
		String builder = new String();
		builder += "<div data-p=\"230\"> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"2\" style=\"position:absolute;top:473px;left:903px;width:416px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;color:"
				+ textColor
				+ ";font-weight:100;line-height:1.2;text-align:center;background-color:rgba(255,188,5,0);\">"
				+ sliderSO.getTitel() + "</div>";
		builder += "<div data-t=\"3\" style=\"position:absolute;top:106px;left:1405px;width:304px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;color:"
				+ textColor + ";line-height:1.2;text-align:center;\">" + sliderSO.getTitel1() + "</div>";
		builder += "<div data-t=\"4\" style=\"position:absolute;top:152px;left:1396px;width:321px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;color:"
				+ textColor + ";line-height:1.2;text-align:center;\">" + sliderSO.getTitel2() + "</div>";
		builder += " </div>";
		return builder;
	}

	private static String getSimple_C(SliderSO sliderSO) {
		String textColor = stringOK(sliderSO.getTitelColor()) ? sliderSO.getTitelColor() : "white";
		String builder = new String();
		builder += "<div data-p=\"230\"> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"5\" style=\"position:absolute;top:30px;left:-505px;width:498px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;color:"
				+ textColor
				+ ";font-weight:200;line-height:1.2;text-align:center;background-color:rgba(255,188,5,0);\">"
				+ sliderSO.getTitel() + "</div>";
		builder += "<div data-t=\"6\" style=\"position:absolute;top:96px;left:605px;width:351px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;color:"
				+ textColor + ";line-height:1.2;text-align:center;\">" + sliderSO.getTitel1() + "</div>";
		builder += " </div>";
		return builder;
	}

	private static String getSimple_D(SliderSO sliderSO) {
		String textColor = stringOK(sliderSO.getTitelColor()) ? sliderSO.getTitelColor() : "white";
		String builder = new String();
		builder += "<div data-p=\"230\"> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"7\" style=\"position:absolute;top:332px;left:472px;width:500px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;font-weight:200;line-height:1.2;text-align:center;color:"
				+ textColor + ";background-color:rgba(255,188,5,0);\">" + sliderSO.getTitel() + "</div>";
		builder += "<div data-t=\"8\" style=\"position:absolute;top:473px;left:526px;width:489px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;font-weight:100;line-height:1.2;color:"
				+ textColor + ";text-align:center;background-color:rgba(255,188,5,0);\">" + sliderSO.getTitel1()
				+ "</div>";
		builder += " </div>";
		return builder;
	}

	private static String getSimple_E(SliderSO sliderSO) {
		String textColor = stringOK(sliderSO.getTitelColor()) ? sliderSO.getTitelColor() : "white";
		String builder = new String();
		builder += "<div data-p=\"230\"> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"9\" style=\"color:" + textColor
				+ ";position:absolute;top:30px;left:30px;width:500px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;font-weight:200;line-height:1.2;text-align:center;background-color:rgba(255,188,5,0);\">"
				+ sliderSO.getTitel() + "</div>";
		builder += "<div data-t=\"10\" style=\"color:" + textColor
				+ ";position:absolute;top:79px;left:21px;width:500px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;font-weight:200;line-height:1.2;text-align:center;background-color:rgba(255,188,5,0);\">"
				+ sliderSO.getTitel1() + "</div>";
		builder += " </div>";
		return builder;
	}

	private static String getSimple_F(SliderSO sliderSO) {
		String textColor = stringOK(sliderSO.getTitelColor()) ? sliderSO.getTitelColor() : "white";
		String builder = new String();
		builder += "<div data-p=\"230\"> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"11\" style=\"position:absolute;top:-10px;left:-169px;width:378px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;color:"
				+ textColor + ";line-height:1.2;text-align:center;\">" + sliderSO.getTitel() + "</div>";
		builder += "<div data-t=\"12\" style=\"position:absolute;top:175px;left:318px;width:378px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;color:"
				+ textColor + ";line-height:1.2;text-align:center;\">" + sliderSO.getTitel1() + "</div>";
		builder += " </div>";
		return builder;
	}

	private static String getSimple_G(SliderSO sliderSO) {
		String textColor = stringOK(sliderSO.getTitelColor()) ? sliderSO.getTitelColor() : "white";
		String builder = new String();
		builder += "<div data-p=\"230\"> ";
		builder += "<img data-u=\"image\" src=\"" + sliderSO.getSliderImageUrl() + "\" />";
		builder += "<div data-t=\"13\" style=\"color:" + textColor
				+ ";position:absolute;top:-21px;left:-138px;width:500px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;font-weight:200;line-height:1.2;text-align:center;background-color:rgba(255,188,5,0);\">"
				+ sliderSO.getTitel() + "</div>";
		builder += "<div data-t=\"14\" style=\"color:" + textColor
				+ ";position:absolute;top:86px;left:-123px;width:500px;height:40px;font-family:Georgia,'Times New Roman',Times,serif;font-size:28px;font-weight:200;line-height:1.2;text-align:center;background-color:rgba(255,188,5,0);\">"
				+ sliderSO.getTitel1() + "</div>";
		builder += " </div>";
		return builder;
	}

	private static boolean stringOK(String str) {
		return str != null && !str.isEmpty();
	}

	private static String getColor(String titel) {
		String color = "white";
		if (stringOK(titel)) {
			String[] array = titel.split("---");
			if (array != null) {
				if (array.length >= 2) {
					color = array[1];
				}
			}
		}
		return color;
	}

	private static String getFontSize(String titel) {
		String fontSize = "26px";
		if (stringOK(titel)) {
			String[] array = titel.split("---");
			if (array != null) {
				if (array.length >= 3) {
					fontSize = array[2];
				}
			}
		}
		return fontSize;
	}

	private static String getText(String titel) {
		String titelText = "";
		if (stringOK(titel)) {
			String[] array = titel.split("---");
			if (array != null) {
				if (array.length > 0) {
					titelText = array[0];
				}
			}
		}
		return titelText;
	}

}
