package com.knightboost.moonlight.apm.util.paging;

import java.util.List;

/*
 * Created by Knight-ZXW on 2021/7/14
 * email: nimdanoob@163.com
 */
public class PageDto<T> {
    //当前页
    public int pageNum;
    //页大小
    public int pageSize;
    //总页数
    public int pages;
    //数据总数
    public int total;
    //分页结果
    //the contents of this page
    public List<T> contents;
}
