package com.ssafy.kkoma.global.jwt.constant;

import lombok.Getter;

@Getter
public enum GrantType {

    BEARER("Bearer");

    private String type;

    GrantType(String type) {
        this.type = type;
    }

}
