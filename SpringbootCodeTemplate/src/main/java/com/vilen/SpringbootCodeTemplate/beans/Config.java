package com.vilen.SpringbootCodeTemplate.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by vilen on 2017/10/18.
 */
@Data
public class Config implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name, description,value;
    private long id;
    /**
     * 创建者
     */
    private long creator;
}
