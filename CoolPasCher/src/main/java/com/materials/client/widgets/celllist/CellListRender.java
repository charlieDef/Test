package com.materials.client.widgets.celllist;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CellListRender<D> extends AbstractCell<D> {

	private CellPropertie<D> cellProperties;
	private String jsDetailsMethodeName, jsContentMethodeName = null;

	public CellListRender(String jsDetailsMethodeName) {
		this.jsDetailsMethodeName = jsDetailsMethodeName;
	}

	public CellListRender(String jsDetailsMethodeName, String jsContentMethodeName) {
		this.jsDetailsMethodeName = jsDetailsMethodeName;
		this.jsContentMethodeName = jsContentMethodeName;
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, D value, SafeHtmlBuilder sb) {
		if (value != null) {
			renderEBObject(value, sb);
		}
	}

	private String renderEBObject(D d, SafeHtmlBuilder sb) {

		String titel = cellProperties.getTitel(d) != null ? cellProperties.getTitel(d) : "";
		String category = cellProperties.getCategory(d);
		String imageurl = cellProperties.getImageUrl(d);
		String description = cellProperties.getDescription(d);
		String longDescription = cellProperties.getLongDescription(d);

		String bColor = cellProperties.getImageBorderColor(d);
		String borderStyle = bColor != null ? "style=\"border:2px solid " + bColor + "\"" : "";

		String cTools = "<img title='" + cellProperties.getToolsIconTooltip(d)
				+ "' style='cursor:pointer;float:right;margin-right:30px;' src='" + cellProperties.getToolsIcon(d)
				+ "' onClick=\"javascript:contentToolsClick()\"/>";

		String cArea = "<img title='" + cellProperties.getDetailIconTooltip(d)
				+ "' style='cursor:pointer;float:right;margin-right:15px;' src='" + cellProperties.getDetailIcon(d)
				+ "' onClick=\"javascript:" + jsContentMethodeName + "\"  />";

		if (jsContentMethodeName != null && cellProperties.addContentsClick(d)) {
			description += cArea;
		}

		if (cellProperties.addToolsClick(d)) {
			description += cTools;
		}

		String image = "<div style=''><ul class='ulCellListWidget'><li class='item'><img title='voir details' onClick=\"javascript:"
				+ jsDetailsMethodeName + "\" " + borderStyle + " class='imgCellListWidget' src='" + imageurl
				+ "'/></li>";
		String name = "<li class='titleCellListWidget'>" + titel + "</li>";
		String smallText = "<li><div class='textCellListWidget'>" + description + "</div>";

		smallText += "</li></li></ul></div>";

		sb.appendHtmlConstant(image);
		sb.appendHtmlConstant(name);
		sb.appendHtmlConstant(smallText);
		return smallText;

	}

	public CellPropertie<D> getCellProperties() {
		return cellProperties;
	}

	public void setCellProperties(CellPropertie<D> cellProperties) {
		this.cellProperties = cellProperties;
	}

}