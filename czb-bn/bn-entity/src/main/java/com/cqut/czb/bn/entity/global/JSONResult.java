package com.cqut.czb.bn.entity.global;

import com.cqut.czb.bn.util.constants.ResponseCodeConstants;

public class JSONResult<T> {

    private String message;

    private int code;

    private T data;

    public JSONResult(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public JSONResult(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public JSONResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public JSONResult(T data) {
        this(ResponseCodeConstants.SUCCESS, data);
    }

    private JSONResult(int code) {
        this.code = code;
    }

    public JSONResult() {
        this(ResponseCodeConstants.SUCCESS);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static String CODE_KEY = "code";

    public static String MESSAGE_KEY = "message";

    public static String DATA_KEY = "data";
}
