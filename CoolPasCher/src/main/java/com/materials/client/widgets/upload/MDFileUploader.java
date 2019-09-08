package com.materials.client.widgets.upload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.materials.client.CoolPasCherUI;

import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import gwt.material.design.addins.client.fileuploader.events.TotalUploadProgressEvent;
import gwt.material.design.client.events.DragOverEvent;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialProgress;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class MDFileUploader extends Composite {

	private String lastUploaded = null;;
	private boolean deleteLast = true;

	private String fileName;

	private MDFileUploaderListener listener;

	@UiField
	MaterialFileUploader cardUploaderUi;

	@UiField
	MaterialProgress progressUi;

	@UiField
	MaterialLabel lblNameUi, lblSizeUi;

	public MDFileUploader() {
		initWidget(uiBinder.createAndBindUi(this));

		cardUploaderUi.setUrl(CoolPasCherUI.MODUL_BASE_FILEUPLOAD);

		// Added the progress to card uploader
		cardUploaderUi.addTotalUploadProgressHandler(new TotalUploadProgressEvent.TotalUploadProgressHandler() {
			@Override
			public void onTotalUploadProgress(TotalUploadProgressEvent event) {
				progressUi.setPercent(event.getProgress());
			}
		});

		cardUploaderUi.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {

				if (lastUploaded != null && deleteLast) {
					CoolPasCherUI.CLIENT_FACTORY.deleteTempFile(lastUploaded);
				}

				fileName = event.getTarget().getName();
				lblNameUi.setText(fileName);
				lblSizeUi.setText(event.getTarget().getType());

				String tempFileId = event.getResponse().getBody();
				String[] array = tempFileId.split("_");
				String rId = array[0];
				String fileType = array[2];
				lastUploaded = rId;

				if (listener != null) {
					listener.onUploadComplet(rId, fileType);
				}
			}
		});

		cardUploaderUi.addDragOverHandler(new DragOverEvent.DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {

				MaterialAnimation animation = new MaterialAnimation(cardUploaderUi);
				animation.setDelay(0);
				animation.setTransition(Transition.RUBBERBAND);
				animation.animate();
			}
		});
	}

	public void setEnabled(boolean enabled) {
		cardUploaderUi.setEnabled(enabled);
		progressUi.setPercent(0);
		lblNameUi.setText("");
		lblSizeUi.setText("");

	}

	public void setServletUrl(String url) {
		cardUploaderUi.setUrl(url);
	}

	public void setUploadCompletListener(MDFileUploaderListener listener) {
		this.listener = listener;
	}

	private static MDFileUploaderUiBinder uiBinder = GWT.create(MDFileUploaderUiBinder.class);

	interface MDFileUploaderUiBinder extends UiBinder<Widget, MDFileUploader> {
	}

	public String getFileName() {
		return fileName;
	}

	public void setDeleteLast(boolean deleteLast) {
		this.deleteLast = deleteLast;
	}

	public void showAllLabel(boolean visibility) {
		progressUi.setVisible(visibility);
		lblNameUi.setVisible(visibility);
		lblSizeUi.setVisible(visibility);
	}
}
