package com.vilen.realworld.core.article;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Created by vilen on 2017/10/17.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class Tag {
    private String id;
    private String name;

    public Tag(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
