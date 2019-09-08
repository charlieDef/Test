package com.materials.client.widgets.search;

public interface SearchPanel4View {

	void setPresenter(Presenter presenter);

	interface Presenter {

		void newAnnonce();

		void searchAnnonce(String label);
	}

}
