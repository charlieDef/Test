package com.materials.client.widgets.model.content.annonce;

import com.google.gwt.user.client.ui.IsWidget;
import com.materials.client.model.CommentSO;
import com.materials.client.model.ContentSO;
import com.materials.client.widgets.model.content.flex.owner.PrettyOwnerView;
import com.materials.client.widgets.question.QuestionReponseView;

public interface AnnonceDetailsView extends IsWidget /** Editor<ContentSO> */
{

	void setPresenter(Presenter presenter);

	void setContentSO(ContentSO contentSO);

	interface Presenter {

		void saveNewQuestion(String firstName, String question);

		void sendMail(String email, String message);

		void respondToQuestion(Long questionId, String response);

		void addToFavorits(Long annonceId);

		void removeToFavorits(Long annonceId);

		void saveResponse(CommentSO commentSO);

	}

	PrettyOwnerView getPrettyOwnerView();

	QuestionReponseView getQuestionReponseView();

}
