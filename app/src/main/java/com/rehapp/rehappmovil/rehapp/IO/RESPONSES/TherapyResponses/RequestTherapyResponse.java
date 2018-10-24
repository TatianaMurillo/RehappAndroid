package com.rehapp.rehappmovil.rehapp.IO.RESPONSES.TherapyResponses;

public class RequestTherapyResponse {


    private String query;
    private String resultType;
    private String synonym;
    private String cursorMark;
    private String pageSize;
    private String  sort;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getCursorMark() {
        return cursorMark;
    }

    public void setCursorMark(String cursorMark) {
        this.cursorMark = cursorMark;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
