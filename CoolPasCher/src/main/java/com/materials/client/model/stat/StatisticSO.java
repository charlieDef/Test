package com.materials.client.model.stat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.materials.client.CoolPasCherUI;
import com.materials.client.model.APPObjectSO;
import com.materials.server.model.stat.MDStatistic;
import com.materials.server.model.stat.StatItem;

public class StatisticSO extends APPObjectSO {

	private static final long serialVersionUID = 1L;

	private Date creationDate;
	private String creator;
	private String category;
	private String titel;
	private String description;
	private boolean intern;
	private boolean active = true;
	private String randomId;
	private Date activeFrom;
	private Date activeTo;
	private int choicesNr = 0;
	private Map<String, String> statisticChoices = new HashMap<String, String>();
	private List<StatItemSO> statItemSOs = new ArrayList<StatItemSO>();

	public StatisticSO() {
		super();
	}

	public StatisticSO(long id) {
		setId(id);
	}

	public StatisticSO(MDStatistic mdStatistic) {
		setId(mdStatistic.getId());
		setActive(mdStatistic.isActive());
		setActiveFrom(mdStatistic.getActiveFrom());
		setActiveTo(mdStatistic.getActiveTo());
		setCategory(mdStatistic.getCategory());
		setChoicesNr(mdStatistic.getChoicesNr());
		setCreationDate(mdStatistic.getCreationDate());
		setCreator(mdStatistic.getCreator());
		setDescription(mdStatistic.getDescription());
		setIntern(mdStatistic.isIntern());
		setLabel(mdStatistic.getLabel());
		setRandomId(mdStatistic.getRandomId());
		setLock(mdStatistic.isLock());
		setTextInfo(mdStatistic.getTextInfo());
		setTitel(mdStatistic.getTitel());

		getStatisticChoices().putAll(mdStatistic.getStatisticChoices());

		for (StatItem statItem : mdStatistic.getStatItems()) {
			StatItemSO statItemSO = new StatItemSO(statItem);
			statItemSO.setStatisticSO(this);
			statItemSOs.add(statItemSO);
		}

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

	public void addChoice(String nr, String choice) {
		getStatisticChoices().put(nr, choice);
	}

	public String getChoiceValue(String choiceNr) {

		return getStatisticChoices().getOrDefault(choiceNr, "");
	}

	public void setStatisticChoices(Map<String, String> statisticChoices) {
		this.statisticChoices = statisticChoices;
	}

	public void setStatItemSOs(List<StatItemSO> statItemSOs) {
		this.statItemSOs = statItemSOs;
	}

	public List<StatItemSO> getStatItemSOs() {
		return statItemSOs;
	}

	public void addStatItem(StatItemSO itemSO) {
		statItemSOs.add(itemSO);
		itemSO.setStatisticSO(this);
	}

	public void removeStatItem(StatItemSO itemSO) {
		if (statItemSOs.remove(itemSO)) {
			itemSO.setStatisticSO(null);
		}
	}

	public String getImageUrl() {
		String urlImg = "";
		if (getId() == -10) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?tempRandomID=" + getRandomId();
		} else if (getId() == -15) {
			urlImg = getTextInfo();
		} else if (getRandomId() != null && !getRandomId().isEmpty()) {
			urlImg = CoolPasCherUI.MODUL_BASE_FILEHELPER + "?statRandomID=" + getRandomId();
		}
		return urlImg;
	}

}
