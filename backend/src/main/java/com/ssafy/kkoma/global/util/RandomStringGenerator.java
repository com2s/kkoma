package com.ssafy.kkoma.global.util;

import java.util.UUID;

public class RandomStringGenerator {

    public static String randomUUID(int length) {
        // UUID 생성
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        // 하이픈 제거
        String uuidWithoutHyphens = uuidString.replace("-", "");

        // 앞 부분을 length 만큼 추출
        String randomString = uuidWithoutHyphens.substring(0, length);

        return randomString;
    }

}
