package com.materials.client.widgets.model.content.annonce.newone;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.model.content.simple.NewAnnonceDetailsView;
import com.materials.client.widgets.model.content.simple.NewAnnonceView;

public interface EditAbleAnnonceView extends IsWidget {

	NewAnnonceDetailsView getNewAnnonceDetailsView();;

	NewAnnonceView getNewAnnonceView();

	void setContentSO(ContentSO contentSO);

	void setPresenter(Presenter presenter);

	void setEdit(boolean edit);

	void selectTab(String id);

	interface Presenter {
		void saveContentSO(ContentSO contentSO);

		void cancel();

		void goBack();

	}
}
