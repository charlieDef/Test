package com.materials.client.widgets.search;

public interface SearchPanel2View {

	void setPresenter(Presenter presenter);

	interface Presenter {

		void configureAnnonce(String categorie, String region, String ville);

		void searchAnnonce(String categorie, String region, String ville);
	}

}
