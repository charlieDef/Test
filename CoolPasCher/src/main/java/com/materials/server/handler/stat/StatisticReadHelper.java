package com.materials.server.handler.stat;

import java.util.ArrayList;
import java.util.List;

import com.materials.client.model.APPObjectSO;
import com.materials.client.model.stat.StatItemSO;
import com.materials.client.model.stat.StatisticSO;
import com.materials.server.model.stat.MDStatistic;
import com.materials.server.model.stat.StatItem;
import com.materials.shared.action.stats.StatisticCommand;
import com.materials.utils.APP_DB_Utils;

public class StatisticReadHelper {

	public List<APPObjectSO> read(StatisticCommand command, String key, String key2) {

		List<APPObjectSO> list = null;

		switch (command) {

		case ALL_ACTIVE_STATISTICS: {
			list = getStatistics("SELECT s FROM MDStatistic s WHERE s.active = '1'");
		}
			break;

		case STATISTIC_BY_LABEL: {
			list = getStatistics("SELECT c FROM MDStatistic c WHERE c.label = '" + key + "'");
		}
			break;

		case ALL_STATISTICS: {
			list = getStatistics("SELECT c FROM MDStatistic c");
		}
			break;
		case ALL_STATITEMS_BY_USER: {
			list = getStatisticItem("SELECT c FROM StatItem c WHERE c.creatorEmail = '" + key + "'");
		}
			break;
		case ALL_STATITEMS_BY_STATISTIC: {
			list = getStatisticItem(
					"SELECT st FROM StatItem st, MDStatistic md WHERE st.mdStatistic.label = '" + key + "'");
		}
			break;
		case STATITEM_BY_USER_AND_STATISTIC: {
			list = getStatisticItem(
					"SELECT item FROM StatItem item, MDStatistic stat WHERE item.creatorEmail = '"+key+"' AND item.mdStatistic.label = '" + key2 + "'");
		}
			break;
		}
		return list;

	}

	private List<APPObjectSO> getStatistics(String query) {
		List<APPObjectSO> list = new ArrayList<>();
		List<MDStatistic> statistics = APP_DB_Utils.queryListObjects(query, null, MDStatistic.class);
		if (statistics != null) {
			statistics.forEach(x -> list.add(new StatisticSO(x)));
		}
		return list;
	}

	private List<APPObjectSO> getStatisticItem(String query) {
		List<APPObjectSO> list = new ArrayList<>();
		List<StatItem> statistics = APP_DB_Utils.queryListObjects(query, null, StatItem.class);
		if (statistics != null && statistics.size() > 0) {
			statistics.forEach(x -> list.add(new StatItemSO(x)));
		}
		return list;
	}

}
