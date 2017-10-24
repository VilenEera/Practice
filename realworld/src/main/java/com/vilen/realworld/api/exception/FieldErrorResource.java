package com.vilen.realworld.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

/**
 * Created by vilen on 17/10/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class FieldErrorResource {
    private String resource;
    private String field;
    private String code;
    private String message;

    public FieldErrorResource(String resource, String field, String code, String message) {

        this.resource = resource;
        this.field = field;
        this.code = code;
        this.message = message;
    }
}
