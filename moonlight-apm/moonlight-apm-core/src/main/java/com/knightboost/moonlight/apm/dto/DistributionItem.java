package com.knightboost.moonlight.apm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Created by Knight-ZXW on 2022/7/23
 * email: nimdanoob@163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionItem {
    private Long id;
    private String name;
    private Long count;

    public static DistributionItem createOtherItem(long count) {
        return new DistributionItem(0L,"其他", count);
    }
}