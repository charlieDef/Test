package com.materials.client.widgets.celllist;

public interface CellPropertie<D> {

	Long getId(D d);

	String getTitel(D d);

	default String getCategory(D d) {
		return "";
	}

	default String getDetailIcon(D d) {
		return "img/file-text.png";
	};

	default String getToolsIcon(D d) {
		return "img/view-green.png";
	};

	default String getDetailIconTooltip(D d) {
		return "Voir le contenu";
	};

	default String getToolsIconTooltip(D d) {
		return "Voir le contenu";
	};

	String getDescription(D d);

	default boolean addToolsClick(D d) {
		return false;
	}

	default boolean addContentsClick(D d) {
		return true;
	}

	default boolean addDetailClick(D d) {
		return false;
	}

	default String getLongDescription(D d) {
		return "";
	}

	String getImageUrl(D d);

	default String getImageBorderColor(D d) {
		return null;
	}

}
