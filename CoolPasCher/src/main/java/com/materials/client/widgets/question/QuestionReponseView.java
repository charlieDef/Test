package com.materials.client.widgets.question;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CommentSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.articles.annonce.simple.SimpleViewAbleAnnonceDetailsView;

public interface QuestionReponseView extends IsWidget {

	void setPresenter(SimpleViewAbleAnnonceDetailsView.Presenter presenter);

	void addQuestion(CommentSO commentSO);

	void addNewQuestion(CommentSO commentSO);

	void loadQuestions(ContentSO commentSOs);

	void addResponse(String textResponse);

}
