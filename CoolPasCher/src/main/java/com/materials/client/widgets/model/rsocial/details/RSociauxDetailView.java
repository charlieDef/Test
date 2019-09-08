package com.materials.client.widgets.model.rsocial.details;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.RSocialSO;

public interface RSociauxDetailView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setRSocial(RSocialSO rSocialSO);

	void setEdit();

	interface Presenter {

		void backToRSocialList();

		void saveRSocial(RSocialSO rSocialSO);
	}

}
