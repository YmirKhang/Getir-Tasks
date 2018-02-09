package com.muaz.getirdemoapp.models;

import com.muaz.getirdemoapp.models.objId;

/**
 * Created by muazekici on 8.02.2018.
 */

public class objRecord {


    objId _id;
    int totalCount;

    public objId get_id() {
        return _id;
    }

    public void set_id(objId _id) {
        this._id = _id;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
