package com.materials.client.widgets.celllist;

import java.util.List;

public interface CellListPresenter<D> {

	void showDetail(D t);

	void newItem();

	void saveToDB(D ebObject);

	void deleteItems(List<D> list);

	void goBack();

	CellListRender<D> getCellListRender();

	public void setCellList(MDCellList<D> cellList);

	public MDCellList<D> getCellList();
}
