package com.materials.server.model.stat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.materials.server.model.APPObject;

@Entity
@Table(name = "MDSTATISTIC")
@PrimaryKeyJoinColumn(name = "APP_OBJECT_ID")
public class MDStatistic extends APPObject {

	private static final long serialVersionUID = 4370851253182712457L;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "TITEL")
	private String titel;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "INTERN", nullable = false)
	private boolean intern;

	@Column(name = "ACTIVE", nullable = false)
	private boolean active = true;

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "IMAGE_DATA")
	@Lob
	private byte[] imageData;

	@Column(name = "ACTIVE_FROM")
	private Date activeFrom;

	@Column(name = "ACTIVE_TO")
	private Date activeTo;

	@Column(name = "CHOICE_NR", columnDefinition = "integer DEFAULT 0")
	private int choicesNr = 0;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "STATISTIC_CHOICES", joinColumns = @JoinColumn(name = "STATISTIC_ID"))
	@MapKeyColumn(name = "propKey", length = 128)
	@OrderBy(value = "propKey")
	@Column(name = "propValue", length = 2048)
	private Map<String, String> statisticChoices;

	@OneToMany(targetEntity = StatItem.class, mappedBy = "mdStatistic", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<StatItem> statItems = new ArrayList<StatItem>(0);

	public MDStatistic() {
		statisticChoices = new HashMap<String, String>();
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isIntern() {
		return intern;
	}

	public void setIntern(boolean intern) {
		this.intern = intern;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public Date getActiveFrom() {
		return activeFrom;
	}

	public void setActiveFrom(Date activeFrom) {
		this.activeFrom = activeFrom;
	}

	public Date getActiveTo() {
		return activeTo;
	}

	public void setActiveTo(Date activeTo) {
		this.activeTo = activeTo;
	}

	public int getChoicesNr() {
		return choicesNr;
	}

	public void setChoicesNr(int choicesNr) {
		this.choicesNr = choicesNr;
	}

	public Map<String, String> getStatisticChoices() {
		return statisticChoices;
	}

	public void setStatisticChoices(Map<String, String> statisticChoices) {
		this.statisticChoices = statisticChoices;
	}

	public List<StatItem> getStatItems() {
		return statItems;
	}

	public void setStatItems(List<StatItem> statItems) {
		this.statItems = statItems;
	}

	public void addStatItem(StatItem item) {
		getStatItems().add(item);
	}

	public void removeStatItem(StatItem item) {
		getStatItems().remove(item);
	}

	public String getChoiceName(int choiceNr) {
		return getStatisticChoices().getOrDefault(choiceNr, null);
	}

}
