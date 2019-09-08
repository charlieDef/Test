package com.materials.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENT")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class Comment extends APPObject {

	private static final long serialVersionUID = 901602792471636291L;

	@Column(name = "PUBLISHER")
	private String publisher;

	@Column(name = "PUBLISHER_EMAIL")
	private String publisherEmail;

	@Column(name = "PUBLISHER_RANDOM_ID")
	private String publisherRandomId;

	@Column(name = "RATING", columnDefinition = "integer DEFAULT 0")
	private int rating = 0;

	@Column(name = "PUBLISHER_BLOG")
	private String publisherBlog;

	@Column(name = "TEXT", length = 500000)
	@Lob
	private String text;

	@Column(name = "COMMENT_TITEL")
	private String commentTitel;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = true;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@ManyToOne(targetEntity = Content.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENT_ID")
	private Content content;

	public Comment() {
	}

	public Comment(String text) {
		this.text = text;
	}
	//
	// public long getId() {
	// return id;
	// }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
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

	@Override
	public String toString() {
		return String.valueOf(getId());
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisherEmail() {
		return publisherEmail;
	}

	public void setPublisherEmail(String publisherEmail) {
		this.publisherEmail = publisherEmail;
	}

	public String getPublisherBlog() {
		return publisherBlog;
	}

	public void setPublisherBlog(String publisherBlog) {
		this.publisherBlog = publisherBlog;
	}

	public String getPublisherRandomId() {
		return publisherRandomId;
	}

	public void setPublisherRandomId(String publisherRandomId) {
		this.publisherRandomId = publisherRandomId;
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
}
