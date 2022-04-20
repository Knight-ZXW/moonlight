package top.tangyh.basic.base.paging;

import lombok.Data;

import java.util.List;

/*
 * 分页结果
 *  Created by Knight-ZXW on 2022/4/19
 * email: nimdanoob@163.com
 */
@Data
public class PageResult<T> {
    private int total;
    private List<T> items;
    private int curPage;
    private int totalPage;
}
