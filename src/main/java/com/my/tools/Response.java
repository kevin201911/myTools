package com.my.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author wq105907
 * @date 2019/5/20
 */
public class Response {
    /**
     * 请求是否成功
     * */
    private boolean success;

    /**
     * 响应码de
     * */
    private String code;

    /**
     * 响应详情
     * */
    private String message;

    /**
     * 响应实体
     * */
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return success + "\n" + code + "\n" + message + "\n" + data;
    }

}