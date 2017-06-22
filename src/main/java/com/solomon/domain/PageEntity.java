package com.solomon.domain;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/6/20.
 */
public class PageEntity<E> {

    /**
     * 第几页
     */
    private int pageIndex;

    /**
     * 每页显示多少记录
     */
    private int pageSize = 10;

    /**
     * 分页的开始值
     */
    private int pageOffset = 1;

    /**
     * 总共多少条记录
     */
    private int totalRecord;

    /**
     * 总共多少页
     */
    private int totalPage;

    /**
     * 放置具体数据的列表
     */
    private List<E> datas;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 算出总页数  算法：总数%每页记录数==0 ？总数/每页记录数 ：总数/每页记录数 + 1
     * @param totalPage
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<E> getDatas() {
        return datas;
    }

    public void setDatas(List<E> datas) {
        this.datas = datas;
    }
}
