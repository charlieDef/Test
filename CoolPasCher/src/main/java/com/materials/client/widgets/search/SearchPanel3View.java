package com.materials.client.widgets.search;

import gwt.material.design.client.ui.MaterialButton;

public interface SearchPanel3View {
	void setPresenter(Presenter presenter);

	interface Presenter {

		void newAnnonce(MaterialButton search, String label, String region, String ville);

		void newAnnonce(String label, String region, String ville);

		void searchAnnonce(String label, String region, String ville);
	}

}
