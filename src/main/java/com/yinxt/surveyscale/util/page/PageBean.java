package com.yinxt.surveyscale.util.page;

import lombok.Data;

import java.util.List;

/**
 * 分页工具
 */
@Data
public class PageBean<T> {

    /**
     * 当前页
     */
    private int pageNo;
    /**
     * 每页展示记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总记录数
     */
    private long totalNum;
    /**
     * 列表数据
     */
    private List<T> list;

    public PageBean() {

    }

    public PageBean(int pageNo, int pageSize, int totalPage, long totalNum, List<T> list) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalNum = totalNum;
        this.list = list;
    }
}
