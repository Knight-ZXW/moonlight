package com.knightboost.moonlight.apm.core.result;

import com.knightboost.moonlight.apm.dto.SqlResultEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
public class QueryResultMapHelper {

    public static ArrayList<GroupAggregateInfo> convertMetricMapToEntity(List<SqlResultEntity> groupList) {
        ArrayList<GroupAggregateInfo> groupAggregateInfos = new ArrayList<>();
        for (SqlResultEntity groupDetail : groupList) {
            GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo();
            groupAggregateInfos.add(groupAggregateInfo);
            for (Map.Entry<String, Object> entry : groupDetail.entrySet()) {
                String key = entry.getKey();
                //hash
                //count
                // affectUserCount
                if (key.equals("groupId")) {
                    groupAggregateInfo.setGroupId(((Number) entry.getValue()).longValue());
                }  else if (key.startsWith("aggregate_")) { //todo rename
                    groupAggregateInfo.getMetrics()
                            .put(key.replace("aggregate_", ""), (Number) entry.getValue());
                } else if (key.startsWith("metric_")) {
                    groupAggregateInfo.getMetrics()
                            .put(key, (Number) entry.getValue());
                }
            }
        }
        return groupAggregateInfos;
    }


}
