package com.vilen.common.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by vilen on 2017/10/18.
 */
@Data
public class ResultBean<T> implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final int NO_LOGIN = -1;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NO_PERMISSION = 2;
    private String msg = "success";
    private int code = SUCCESS;
    private T data;

    public ResultBean() {
    }

    public ResultBean(T data) {
        this.data = data;
    }

    public ResultBean(Throwable throwable) {
        this.msg = throwable.toString();
        this.code = FAIL;
    }
}
