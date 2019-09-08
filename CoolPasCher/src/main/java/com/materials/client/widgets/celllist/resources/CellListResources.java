package com.materials.client.widgets.celllist.resources;

import com.google.gwt.user.cellview.client.CellList;

public interface CellListResources extends CellList.Resources {

	/**
	 * The styles applied to the table.
	 */
	interface CellListStyle extends CellList.Style {
	}

	@Override
	@Source({ CellList.Style.DEFAULT_CSS, "/ebCellList.css" })
	CellListStyle cellListStyle();

}
