package com.materials.client.widgets.datagrid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.DataGrid;

public interface DatagridResources extends DataGrid.Resources {

	static final DatagridResources INSTANCE = GWT.create(DatagridResources.class);

	@Override
	@Source({ "com/google/gwt/user/cellview/client/DataGrid.gss", "prebaalDatagrid.gss" })
	DatagridStyle dataGridStyle();

	// @Override
	// @Source("icon_sort_asc3.png")
	// ImageResource dataGridSortAscending();
	//
	// @Override
	// @Source("icon_sort_desc3.png")
	// ImageResource dataGridSortDescending();

	interface DatagridStyle extends DataGrid.Style {

		String dataGridCheckboxCell();

		String dataGridIconCell();

		String dataGridTobBottomCell();

		String dataGridTopCell();

		String dataGridSubCell();

		String dataGridSubCell2();

		String dataGridSubCell3();

		String dataGridTextBoxCell();
	}
}
