package com.muaz.getirdemoapp.models;

import com.muaz.getirdemoapp.models.objRecord;

import java.util.List;

/**
 * Created by muazekici on 8.02.2018.
 */

public class objResponse {

    int code;
    String msg;
    List<objRecord> records;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<objRecord> getRecords() {
        return records;
    }

    public void setRecords(List<objRecord> records) {
        this.records = records;
    }
}
