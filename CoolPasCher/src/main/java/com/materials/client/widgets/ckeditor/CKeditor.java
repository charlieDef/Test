package com.materials.client.widgets.ckeditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;

public class CKeditor extends Composite implements TakesValue<String> {
	protected TextArea text = new TextArea();
	protected JavaScriptObject editor;

	public CKeditor() {
		text.getElement().setId("25874965898");
		initWidget(text);

		setEditorHeight("500px");
	}

	public CKeditor(String id, String height) {
		text.getElement().setId(id);
		initWidget(text);
		setEditorHeight(height);
		setEditorBasic();

		setLanguage("fr");
		setDefaultFontFamily("Times New Roman");
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		initCKEditor(text.getElement().getId());
	}

	@Override
	protected void onDetach() {
		super.onDetach();

	}

	@Override
	protected void onUnload() {
		super.onUnload();
	}

	private native void initCKEditor(String id) /*-{
		this.@com.materials.client.widgets.ckeditor.CKeditor::editor = $wnd.CKEDITOR
				.replace(id);
	}-*/;

	@Override
	public native void setValue(String value) /*-{
		this.@com.materials.client.widgets.ckeditor.CKeditor::editor
				.setData(value);
	}-*/;

	@Override
	public native String getValue() /*-{
		return this.@com.materials.client.widgets.ckeditor.CKeditor::editor
				.getData();
	}-*/;

	public native void setEditorHeight(String height) /*-{
		$wnd.CKEDITOR.config.height = height;
	}-*/;

	public native void setEditorWidth(String width) /*-{
		$wnd.CKEDITOR.config.width = width;
	}-*/;

	public native void setEditorBasic() /*-{
		$wnd.CKEDITOR.config.toolbar_Basic = [ [ 'Undo', 'Redo', 'Format',
				'FontSize', 'Bold', 'Italic', 'Underline', '-', '-', '-',
				'TextColor', 'BGColor', '-', '-', '-', 'NumberedList', 'Table',
				'BulletedList', '-', 'JustifyLeft', 'JustifyCenter',
				'JustifyRight', '-', 'Outdent', 'Indent', '-', 'SelectAll' ] ];
		$wnd.CKEDITOR.config.toolbar = "Basic";
	}-*/;

	public native void setLanguage(String language) /*-{
		$wnd.CKEDITOR.config.language = language;
	}-*/;

	public native void setDefaultFontFamily(String fFamily) /*-{
		$wnd.CKEDITOR.config.contentsCss = [ "body{font-family:Times New Roman;}" ];
		$wnd.CKEDITOR.config.uiColor = '#e6edf3';
	}-*/;
}
