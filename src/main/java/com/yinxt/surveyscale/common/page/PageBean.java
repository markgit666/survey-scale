package com.yinxt.surveyscale.common.page;

import com.github.pagehelper.PageInfo;
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
    /**
     * 其他内容
     */
    private Object content;

    public PageBean() {

    }

    public PageBean(int pageNo, int pageSize, int totalPage, long totalNum, List<T> list) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalNum = totalNum;
        this.list = list;
    }

    public PageBean(PageInfo pageInfo) {
        this.pageNo = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalPage = pageInfo.getPages();
        this.totalNum = pageInfo.getTotal();
        this.list = pageInfo.getList();
    }

    public PageBean(PageInfo pageInfo, Object content) {
        this.pageNo = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalPage = pageInfo.getPages();
        this.totalNum = pageInfo.getTotal();
        this.list = pageInfo.getList();
        this.content = content;
    }

}
