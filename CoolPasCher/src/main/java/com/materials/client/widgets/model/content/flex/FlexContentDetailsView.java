package com.materials.client.widgets.model.content.flex;

import com.materials.client.model.ContentSO;

public interface FlexContentDetailsView {

	void setPresenter(Presenter presenter);

	void setContentSO(ContentSO contentSO);

	interface Presenter {

		void backToContentList();

		void saveContent(ContentSO contentSO);
	}
}
