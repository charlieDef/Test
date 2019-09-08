package com.materials.server.model.stat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.materials.server.model.APPObject;

@Entity
@Table(name = "STAT_ITEM")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class StatItem extends APPObject {

	private static final long serialVersionUID = 1L;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "CREATOR_EMAIL")
	private String creatorEmail;

	@Column(name = "CHOICE")
	private String choice;

	@Column(name = "MESSAGE")
	private String message;

	@ManyToOne(targetEntity = MDStatistic.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "STATISTIC_ID")
	private MDStatistic mdStatistic;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MDStatistic getMdStatistic() {
		return mdStatistic;
	}

	public void setMdStatistic(MDStatistic mdStatistic) {
		this.mdStatistic = mdStatistic;
		mdStatistic.addStatItem(this);
	}

}
