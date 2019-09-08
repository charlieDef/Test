package com.materials.client.model;

import java.util.Date;

import com.materials.client.CoolPasCherUI;
import com.materials.server.model.Comment;

public class CommentSO extends APPObjectSO {

	private static final long serialVersionUID = 1858704613784110097L;
	private String text;
	private boolean active = true;
	private Date creationDate;
	private String commentTitel;
	private String publisherLastName;
	private String publisherEmail;
	private String publisherRandomId;
	private String titelContent;
	private String blogName;
	private int rating;
	private ContentSO contentSO;

	public CommentSO() {
		super();
	}

	public CommentSO(Comment comment) {
		setId(comment.getId());
		setText(comment.getText());
		setActive(comment.isActive());
		setCreationDate(comment.getCreationDate());
		setPublisherLastName(comment.getPublisher());
		setPublisherEmail(comment.getPublisherEmail());
		setBlogName(comment.getPublisherBlog());
		setPublisherRandomId(comment.getPublisherRandomId());
		setRating(comment.getRating());
		setCommentTitel(comment.getCommentTitel());
		if (comment.getContent() != null) {
			setTitelContent(comment.getContent().getTitel());
		}

		// setContentSO(comment.getContent() != null ? new
		// ContentSO(comment.getContent()) : null);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPublisherLastName() {
		return publisherLastName;
	}

	public void setPublisherLastName(String publisher) {
		this.publisherLastName = publisher;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public ContentSO getContentSO() {
		return contentSO;
	}

	public void setContentSO(ContentSO contentSO) {
		this.contentSO = contentSO;
	}

	public void setPublisherEmail(String publisherEmail) {
		this.publisherEmail = publisherEmail;
	}

	public String getPublisherEmail() {
		return publisherEmail;
	}

	public void setPublisherRandomId(String publisherRandomId) {
		this.publisherRandomId = publisherRandomId;
	}

	public String getPublisherRandomId() {
		return publisherRandomId;
	}

	public String getPublisherImageUrl() {
		String urlImg = "img/a_user.jpg";
		if (getPublisherRandomId() != null && !getPublisherRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?userID=" + getPublisherRandomId();
		}
		return urlImg;
	}

	public String getTitelContent() {
		return titelContent;
	}

	public void setTitelContent(String titelContent) {
		this.titelContent = titelContent;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getCommentTitel() {
		return commentTitel;
	}

	public void setCommentTitel(String commentTitel) {
		this.commentTitel = commentTitel;
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

}
