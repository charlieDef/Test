package com.materials.client.widgets.model.content.pic;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.ContentSO;

public interface ContentPicturesView extends IsWidget {

	void setContentSO(ContentSO contentSO);

	void setPresenter(Presenter presenter);

	void setEdit();

	interface Presenter {

		void saveContentSO(ContentSO contentSO);

		void backToAlbumPhotoList();
	}
}
