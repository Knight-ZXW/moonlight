package com.knightboost.moonlight.apm.util.paging;

import cn.hutool.core.util.PageUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public class PageDtoUtil<T> {

    /**
     * @return
     * @description 内存分页，hutool工具类相关
     * @Param
     * @author tzh
     * @date
     */
    public static <T> PageDto<T> getPageDto(List<T> list, int pageNum, int pageSize) {
        PageDto pageDto = new PageDto();
        //总页数
        int pages = PageUtil.totalPage(list.size(), pageSize);
        pageDto.pages = pages;//总页数
        pageDto.total = list.size();//数据总数
        pageDto.pageSize = pageSize;//页大小

        //开始位置和结束位置
        int[] startEnd = PageUtil.transToStartEnd(pageNum, pageSize);
        //分页后的结果集
        List<?> pageList = null;
        //
        if (startEnd[0]>=list.size()){ //超出范围
            pageList = Collections.EMPTY_LIST;
        } else if (startEnd[1] < list.size()) {
            pageList = list.subList(startEnd[0], startEnd[1]);
        } else {
            pageList = list.subList(startEnd[0], list.size());
        }

        pageDto.pageNum = pageNum;//当前页码
        pageDto.contents = pageList;

        return pageDto;
    }


    /**
     * return spring style page information
     * @return
     */
    public static <T> PageImpl<T> getPage(List<T> list, int pageNum, int pageSize){
        PageDto<T> pageDto = getPageDto(list, pageNum, pageSize);
        PageImpl<T> page;
        if (pageDto.contents.size()>0){
            page = new PageImpl<T>(
                    pageDto.contents,
                    PageRequest.of(pageDto.pageNum, pageDto.contents.size()),
                    list.size());
        }else { //is zero
            page = new PageImpl<T>(
                    pageDto.contents,
                    Pageable.unpaged(),
                    list.size());
        }
        return page;
    }
}
