package com.wipro.mms.payload;

public class ApiResponse {
    private String message;
    private boolean isSuccess;

    public ApiResponse(String msg, boolean isSuccess) {
        this.message = msg;
        this.isSuccess = isSuccess;

    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
