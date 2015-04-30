package com.gamr.gamr.Server;

import com.google.gson.annotations.SerializedName;

/**
 * Serialization class for the responses retrieved from the server.
 * Created by Jennifer on 3/23/15.
 */
public class UpdateResponse {
    @SerializedName("affected_rows")
    private int affectedRows;

    /**
     * Necessary class to deserialize the response JSON returned from update_location
     */
    public UpdateResponse() {
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(int affectedRows) {
        this.affectedRows = affectedRows;
    }
}
