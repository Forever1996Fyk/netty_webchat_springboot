package com.javaweb.michaelkai.common.vo;


import java.util.List;

/**
 * @program: project_base
 * @description: 分页实体
 * @author: YuKai Fan
 * @create: 2019-05-22 14:23
 **/
public class PageResult<T> {
    private long total;

    private List<T> rows;

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}