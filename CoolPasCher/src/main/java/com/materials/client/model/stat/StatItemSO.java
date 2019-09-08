package com.materials.client.model.stat;

import java.util.Date;

import com.materials.client.model.APPObjectSO;
import com.materials.server.model.stat.StatItem;

public class StatItemSO extends APPObjectSO {

	private static final long serialVersionUID = 1L;
	private Date creationDate;
	private String creator;
	private String creatorEmail;
	private String choice;
	private String message;
	private StatisticSO statisticSO;

	public StatItemSO() {

	}

	public StatItemSO(StatItem statItem) {
		setId(statItem.getId());
		setChoice(statItem.getChoice());
		setCreationDate(statItem.getCreationDate());
		setCreator(statItem.getCreator());
		// setLabel(statItem.getLabel());
		setCreatorEmail(statItem.getCreatorEmail());
		setLock(statItem.isLock());
		setMessage(statItem.getMessage());
		setTextInfo(statItem.getTextInfo());

	}

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

	public StatisticSO getStatisticSO() {
		return statisticSO;
	}

	public void setStatisticSO(StatisticSO statisticSO) {
		this.statisticSO = statisticSO;
	}

}
