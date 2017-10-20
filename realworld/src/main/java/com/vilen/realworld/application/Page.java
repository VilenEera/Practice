package com.vilen.realworld.application;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by vilen on 17/10/20.
 */
@Data
@Getter
@NoArgsConstructor
public class Page {
    private static final int MAX_LIMIT = 100;
    private int offset = 0;
    private int limit = 20;

    public Page(int offset, int limit) {
        setOffset(offset);
        setLimit(limit);
    }

    private void setOffset(int offset) {
        if (offset > 0) {
            this.offset = offset;
        }
    }

    private void setLimit(int limit) {
        if (limit > MAX_LIMIT) {
            this.limit = MAX_LIMIT;
        } else if (limit > 0) {
            this.limit = limit;
        }
    }
}
