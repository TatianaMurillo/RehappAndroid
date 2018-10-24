package com.rehapp.rehappmovil.rehapp.Models;

import com.rehapp.rehappmovil.rehapp.IO.RESPONSES.TherapyResponses.RequestTherapyResponse;
import com.rehapp.rehappmovil.rehapp.IO.RESPONSES.TherapyResponses.ResultListTherapyResponse;


import java.util.List;

public class Therapy {

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public String getNextCursorMark() {
        return nextCursorMark;
    }

    public void setNextCursorMark(String nextCursorMark) {
        this.nextCursorMark = nextCursorMark;
    }

    public RequestTherapyResponse getRequest() {
        return request;
    }

    public void setRequest(RequestTherapyResponse request) {
        this.request = request;
    }

    public ResultListTherapyResponse getResultList() {
        return resultList;
    }

    public void setResultList(ResultListTherapyResponse resultList) {
        this.resultList = resultList;
    }

    private String version;

    private Integer hitCount;

    private String nextCursorMark;

    private RequestTherapyResponse request;

    private ResultListTherapyResponse resultList;


}
