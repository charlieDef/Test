package com.materials.client.widgets.model.mtemplate.details.single;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.MenuTemplateSO;

public interface SingleTemplateDetailView extends IsWidget {

	void setPresenter(Presenter presenter);

	void setMenuTemplateSO(MenuTemplateSO menuTemplateSO);

	void setEdit();

	interface Presenter {

		void backToTemplateList();

		void saveTemplate(MenuTemplateSO menuTemplateSO);

	}

}
