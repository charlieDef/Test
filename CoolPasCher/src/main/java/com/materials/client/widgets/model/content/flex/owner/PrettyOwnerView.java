package com.materials.client.widgets.model.content.flex.owner;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.widgets.articles.annonce.simple.SimpleViewAbleAnnonceDetailsView;

public interface PrettyOwnerView extends IsWidget {

	void setPresenter(SimpleViewAbleAnnonceDetailsView.Presenter presenter);

	void setOwnerInfo(Map<String, String> map);

}
