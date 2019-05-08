package com.white.ghost.programmersupermario.base;

import com.google.gson.annotations.SerializedName;


/**
 * Function: 接口返回数据基础格式
 * Author Name: Chris
 * Date: 2019/5/6 15:12
 */
public class BaseResponse {
    @SerializedName("error_response")
    private ErrorResponse errorResponse;
    private Integer code;
    private String msg;

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public class ErrorResponse {
        private int code;
        private String msg;

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
    }


}
