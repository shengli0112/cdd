package com.cdd.gsl.common.util;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

public class ResultPage<T> implements Serializable {

    private static final long serialVersionUID = 5472321653620726832L;

    private final static int DEFAULT_NAVIGATOR_SIZE = 10;

    //查询开始位置
    //private int startIndex = 1;
    // 当前页
    private int currentPage = 1;
    // 每页显示数量
    private int pageSize = 10;
    //总页数
    private int pageCount = 1;
    // 总条数
    private int totalCount;

    private int navigatorSize;

    // 存放查询结果用的list
    private List<T> items;

    public ResultPage() {

    }

    public ResultPage(int totalCount, int pageSize, int currentPage) {
        this(totalCount, pageSize, currentPage, DEFAULT_NAVIGATOR_SIZE);
    }

    public ResultPage(int totalCount, int pageSize, int currentPage,
                      int navigatorSize) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.pageCount  = operatorPageCount();
        this.navigatorSize = navigatorSize;
    }

    public ResultPage(int totalCount, int pageSize, int currentPage,
                      List<T> items) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.pageCount  = operatorPageCount();
        this.items = items;
    }

    public ResultPage(Page page){
        this.totalCount = (int) page.getTotal();
        this.pageSize = page.getPageSize();
        this.currentPage = page.getPageNum();
        this.pageCount  = page.getPages();
    }


    /**
     * 计算总页数
     * @return
     */
    public int operatorPageCount() {
        int pageCount = 0;
        if (pageSize != 0) {
            pageCount = totalCount / pageSize;
            if (totalCount % pageSize != 0)
                pageCount++;
        }

        return pageCount;
    }

    public int getCurrentPage() {
        currentPage = currentPage < pageCount ? currentPage
                : pageCount;
        currentPage = currentPage < 1 ? 1 : currentPage;

        return currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }



    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

}
