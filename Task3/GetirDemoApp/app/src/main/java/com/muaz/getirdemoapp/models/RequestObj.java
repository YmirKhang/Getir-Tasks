package com.muaz.getirdemoapp.models;

import java.io.Serializable;

/**
 * Created by muazekici on 8.02.2018.
 */

public class RequestObj implements Serializable {

    String startDate;
    String endDate;
    int minCount;
    int maxCount;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
