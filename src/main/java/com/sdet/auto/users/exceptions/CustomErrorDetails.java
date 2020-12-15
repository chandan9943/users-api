package com.sdet.auto.users.exceptions;

import java.util.Date;

// simple custom error details bean
public class CustomErrorDetails {

    private Date time_stamp;
    private String message;
    private String error_details;

    public CustomErrorDetails(Date time_stamp, String message, String error_details) {
        this.time_stamp = time_stamp;
        this.message = message;
        this.error_details = error_details;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError_details() {
        return error_details;
    }

    public void setError_details(String error_details) {
        this.error_details = error_details;
    }
}