package com.materials.server.handler.stat;

import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.CommentSO;
import com.materials.client.model.stat.StatItemSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.server.model.Comment;
import com.materials.server.model.TempFile;
import com.materials.server.model.stat.MDStatistic;
import com.materials.server.model.stat.StatItem;
import com.materials.utils.APP_DB_Utils;

public class StatisticUpdateHelper {

	public APPObjectSO update(APPObjectSO appObjectSO) {

		StatisticSO statisticSO = (StatisticSO) appObjectSO;
		MDStatistic statistic = toStatistic(statisticSO, false);
		if (statistic != null) {
			APP_DB_Utils.saveObjectToDatabase(statistic);
		}
		return statisticSO;
	}

	public APPObjectSO save(APPObjectSO appObjectSO) {

		if (appObjectSO instanceof StatisticSO) {
			StatisticSO statisticSO = (StatisticSO) appObjectSO;
			MDStatistic newStatistic = toStatistic(statisticSO, true);
			APP_DB_Utils.saveObjectToDatabase(newStatistic);
			statisticSO.setId(newStatistic.getId());
			return statisticSO;
		} else {
			StatItemSO statItemSO = (StatItemSO) appObjectSO;

			StatItem statItem = toStatisticItem(statItemSO, true);
			MDStatistic statisticSOParent = APP_DB_Utils.findObject(statItemSO.getStatisticSO().getId(),
					MDStatistic.class);
			statItem.setMdStatistic(statisticSOParent);

			APP_DB_Utils.saveObjectToDatabase(statItem);
			APP_DB_Utils.saveObjectToDatabase(statisticSOParent);
			statItemSO.setId(statItem.getId());
			return statItemSO;
		}

	}

	public boolean delete(List<APPObjectSO> appObjectSOs) {

		appObjectSOs.forEach(appObjectSO -> {
			CommentSO commentSO = (CommentSO) appObjectSO;
			APP_DB_Utils.deleteObjectFromDatabase(commentSO.getId(), Comment.class);
		});
		return true;
	}

	private StatItem toStatisticItem(StatItemSO statItemSO, boolean isNew) {
		StatItem statisticItem = isNew ? new StatItem() : APP_DB_Utils.findObject(statItemSO.getId(), StatItem.class);
		if (statisticItem != null) {
			statisticItem.setChoice(statItemSO.getChoice());
			statisticItem.setCreationDate(statItemSO.getCreationDate());
			statisticItem.setCreator(statItemSO.getCreator());
			statisticItem.setCreatorEmail(statItemSO.getCreatorEmail());
			statisticItem.setLabel(statItemSO.getLabel());
			statisticItem.setTextInfo(statItemSO.getTextInfo());
			statisticItem.setLock(statItemSO.isLock());
			statisticItem.setMessage(statItemSO.getMessage());
		}
		return statisticItem;
	}

	private MDStatistic toStatistic(StatisticSO statisticSO, boolean isNew) {
		MDStatistic statistic = isNew ? new MDStatistic()
				: APP_DB_Utils.findObject(statisticSO.getId(), MDStatistic.class);
		if (statistic != null) {
			statistic.setActive(statisticSO.isActive());
			statistic.setActiveFrom(statisticSO.getActiveFrom());
			statistic.setActiveTo(statisticSO.getActiveTo());
			statistic.setCategory(statisticSO.getCategory());
			statistic.setChoicesNr(statisticSO.getChoicesNr());
			statistic.setCreationDate(statisticSO.getCreationDate());
			statistic.setCreator(statisticSO.getCreator());
			statistic.setDescription(statisticSO.getDescription());
			statistic.setIntern(statisticSO.isIntern());
			statistic.setLabel(statisticSO.getLabel());
			statistic.setRandomId(statisticSO.getRandomId());
			statistic.setLock(statisticSO.isLock());
			statistic.setTextInfo(statisticSO.getTextInfo());
			statistic.setTitel(statisticSO.getTitel());

			if (!statisticSO.getStatisticChoices().keySet().isEmpty()) {
				for (String key : statisticSO.getStatisticChoices().keySet()) {
					statistic.getStatisticChoices().put(key, statisticSO.getStatisticChoices().get(key));
				}

			}

			// TODO be carefull here --> Image set
			if (statisticSO.getRandomId() != null) {
				TempFile tempFile = APP_DB_Utils.getTempFile(statisticSO.getRandomId());
				if (tempFile != null) {
					statistic.setImageData(tempFile.getTempData());
					APP_DB_Utils.deleteObjectFromDatabase(tempFile.getId(), TempFile.class);
				}
			}

		}

		return statistic;
	}

}
