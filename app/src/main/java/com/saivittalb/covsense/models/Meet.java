package com.saivittalb.covsense.models;

import com.google.firebase.firestore.FieldValue;

public class Meet {
    private FieldValue foundTimestamp;
    private FieldValue lostTimestamp;
    private String status;
    private String latitude;
    private String longitude;
    private String date;
    private String duration;


    public Meet(FieldValue foundTimestamp, FieldValue lostTimestamp, String status, String latitude, String longitude, String date, String duration) {
        this.foundTimestamp = foundTimestamp;
        this.lostTimestamp = lostTimestamp;
        this.status = "ongoing";
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.duration = duration;
    }

    public FieldValue getFoundTimestamp() {
        return foundTimestamp;
    }

    public void setFoundTimestamp(FieldValue foundTimestamp) {
        this.foundTimestamp = foundTimestamp;
    }

    public FieldValue getLostTimestamp() {
        return lostTimestamp;
    }

    public void setLostTimestamp(FieldValue lostTimestamp) {
        this.lostTimestamp = lostTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
