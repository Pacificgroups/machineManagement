package com.wipro.mms.payload;

public class ApiRequest<T>{
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
