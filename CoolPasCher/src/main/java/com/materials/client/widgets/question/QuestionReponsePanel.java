package com.materials.client.widgets.question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.materials.client.CoolPasCherUI;
import com.materials.client.model.CommentSO;
import com.materials.client.model.ContentSO;
import com.materials.client.model.UserSO;
import com.materials.client.widgets.articles.annonce.simple.SimpleViewAbleAnnonceDetailsView;
import com.materials.shared.APPConstants;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.DialogType;
import gwt.material.design.client.constants.FieldType;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogContent;
import gwt.material.design.client.ui.MaterialDialogFooter;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTitle;

public class QuestionReponsePanel extends Composite implements QuestionReponseView {

	private static QuestionReponsePanelUiBinder uiBinder = GWT.create(QuestionReponsePanelUiBinder.class);

	interface QuestionReponsePanelUiBinder extends UiBinder<MaterialCard, QuestionReponsePanel> {
	}

	private List<CommentSO> allQuestions = new ArrayList<CommentSO>();
	private ContentSO contentSO;
	private UserSO actualUser;
	private MyDialog myDialog;

	private SimpleViewAbleAnnonceDetailsView.Presenter presenter;

	@UiField
	MaterialCollapsible materialCollapsibleUi;

	@UiField
	MaterialLabel questionLabelUi;

	public QuestionReponsePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		actualUser = CoolPasCherUI.CLIENT_FACTORY.getActualUserSO();
		myDialog = new MyDialog();
	}

	@Override
	public void setPresenter(SimpleViewAbleAnnonceDetailsView.Presenter presenter) {
		this.presenter = presenter;
	}

	@UiField
	CollapsStyle style;

	@Override
	public void addNewQuestion(CommentSO commentSO) {
		contentSO.getComments().add(commentSO);
		allQuestions.add(commentSO);
		Collections.sort(allQuestions, new Comparator<CommentSO>() {
			@Override
			public int compare(CommentSO o1, CommentSO o2) {
				return o2.getCreationDate().compareTo(o1.getCreationDate());
			}
		});

		materialCollapsibleUi.clear();

		loadQuestions(allQuestions);

	}

	@Override
	public void addQuestion(CommentSO commentSO) {

		if (!allQuestions.contains(commentSO)) {
			allQuestions.add(commentSO);
		}

		MaterialCollapsibleItem collapsibleItem = new MaterialCollapsibleItem();

		collapsibleItem.setPaddingBottom(-10);
		MaterialCollapsibleHeader collapsibleHeader = new MaterialCollapsibleHeader();
		collapsibleItem.add(collapsibleHeader);

		MaterialRow materialRow1 = new MaterialRow();
		materialRow1.setMarginBottom(0);
		materialRow1.setMarginTop(0);
		collapsibleHeader.add(materialRow1);

		DateTimeFormat ttipFormat = DateTimeFormat.getFormat(APPConstants.DATE_FORMAT);
		MaterialLabel commentDateLabel = new MaterialLabel(ttipFormat.format(commentSO.getCreationDate()));
		commentDateLabel.addStyleName(style.collapsDateLabel());
		commentDateLabel.setFloat(Float.RIGHT);
		materialRow1.add(commentDateLabel);

		MaterialLabel labelUserName = new MaterialLabel(commentSO.getPublisherLastName());
		labelUserName.addStyleName(style.collapsItemUserNameLabel());
		// labelUserName.setFloat(Float.LEFT);
		materialRow1.add(labelUserName);

		// ----------------------------------------------
		MaterialRow materialRow = new MaterialRow();
		collapsibleHeader.add(materialRow);

		MaterialColumn column = new MaterialColumn();

		MaterialColumn column2 = new MaterialColumn();

		MaterialLabel commentTitleLabel = new MaterialLabel(commentSO.getCommentTitel());
		column.add(commentTitleLabel);

		commentTitleLabel.addStyleName(style.collapsItemTitleLabel());
		materialRow.add(column);

		MaterialIcon icon = new MaterialIcon(IconType.REPLY);
		// icon.getElement().setPropertyString("_cId", "" + commentSO.getId());
		icon.setIconSize(IconSize.TINY);
		icon.setTitle("Poster réponse");
		column2.setFloat(Float.RIGHT);
		materialRow.add(column2);

		// --------------------------------------------------
		MaterialCollapsibleBody body = new MaterialCollapsibleBody();
		body.addStyleName(style.collapsItemBody());
		collapsibleItem.add(body);

		MaterialLabel commentContent = new MaterialLabel(commentSO.getText());
		commentContent.setMarginLeft(30);

		materialCollapsibleUi.add(collapsibleItem);

		if (actualUser != null && contentSO.getCreator().equals(String.valueOf(actualUser.getId()))
				&& commentSO.getText().contains("Réponse en attente")) {
			column2.add(icon);

			icon.addClickHandler(x -> {

				myDialog.setCommentSO(commentSO);
				myDialog.setMaterialCollapsibleBody(body);
				myDialog.setIcon(icon);
				myDialog.open();

			});

		} else {
			body.add(commentContent);
		}

	}

	interface CollapsStyle extends CssResource {

		String collapsItemUserImg();

		String collapsItemUserNameLabel();

		String collapsItemBody();

		String collapsArea();

		String collapsItemTitleLabel();

		String collapsRating();

		String collapsDateLabel();
	}

	@Override
	public void loadQuestions(ContentSO contentSO) {
		this.contentSO = contentSO;
		questionLabelUi.setText("Questions | " + contentSO.getComments().size());

		List<CommentSO> commentSOs = contentSO.getComments();

		Collections.sort(commentSOs, new Comparator<CommentSO>() {
			@Override
			public int compare(CommentSO o1, CommentSO o2) {
				return o2.getCreationDate().compareTo(o1.getCreationDate());
			}
		});
		if (commentSOs != null && !commentSOs.isEmpty()) {
			commentSOs.stream().filter(CommentSO::isActive).forEach(this::addQuestion);
		}

		if (!allQuestions.isEmpty()) {
			materialCollapsibleUi.open(1);
		}

	}

	private void loadQuestions(List<CommentSO> commentSOs) {

		Collections.sort(commentSOs, new Comparator<CommentSO>() {
			@Override
			public int compare(CommentSO o1, CommentSO o2) {
				return o2.getCreationDate().compareTo(o1.getCreationDate());
			}
		});
		if (commentSOs != null && !commentSOs.isEmpty()) {
			commentSOs.stream().filter(CommentSO::isActive).forEach(this::addQuestion);
		}

		if (!allQuestions.isEmpty()) {
			materialCollapsibleUi.open(1);
		}
		questionLabelUi.setText("Questions | " + commentSOs.size());
	}

	class MyDialog extends MaterialDialog {

		private CommentSO commentSO;

		private MaterialTextArea materialTextArea;
		private MaterialTitle materialTitle;
		private MaterialCollapsibleBody body;
		private MaterialIcon icon;

		public MyDialog() {

			setPadding(10);
			setType(DialogType.BOTTOM_SHEET);
			setDismissible(false);
			materialCollapsibleUi.add(this);

			MaterialDialogContent materialDialogContent = new MaterialDialogContent();
			materialTitle = new MaterialTitle("");
			materialDialogContent.add(materialTitle);

			add(materialDialogContent);

			materialTextArea = new MaterialTextArea("Réponse");
			materialTextArea.setFieldType(FieldType.OUTLINED);
			materialDialogContent.add(materialTextArea);

			MaterialDialogFooter dialogFooter = new MaterialDialogFooter();
			add(dialogFooter);

			MaterialButton buttonPoster = new MaterialButton("Poster");
			buttonPoster.setFloat(Float.LEFT);
			MaterialButton buttonFermer = new MaterialButton("Annuler");
			buttonPoster.setType(ButtonType.FLAT);
			buttonFermer.setType(ButtonType.FLAT);

			dialogFooter.add(buttonFermer);
			dialogFooter.add(buttonPoster);

			buttonFermer.addClickHandler(c -> close());

			buttonPoster.addClickHandler(c -> {

				String value = materialTextArea.getValue();

				if (value != null && !value.isEmpty()) {

					commentSO.setText(value);
					presenter.saveResponse(commentSO);

				}

			});

		}

		public void addResponse(String response) {
			MaterialLabel commentContent = new MaterialLabel(response);
			commentContent.setMarginLeft(15);
			body.add(commentContent);
			icon.removeFromParent();
			close();

		}

		public void setIcon(MaterialIcon icon) {
			this.icon = icon;

		}

		public void setMaterialCollapsibleBody(MaterialCollapsibleBody body) {
			this.body = body;

		}

		public void setCommentSO(CommentSO commentSO) {

			this.commentSO = commentSO;
			materialTextArea.setText("");
			materialTextArea.setPlaceholder("Répondre à la question de " + commentSO.getPublisherLastName());
			materialTitle.setTitle("[ " + commentSO.getCommentTitel() + " ]");
		}

	}

	@Override
	public void addResponse(String textResponse) {
		myDialog.addResponse(textResponse);

	}

}
