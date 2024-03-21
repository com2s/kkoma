package com.ssafy.kkoma.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001", "business exception test"),

    // Auth
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "A-008", "관리자 Role이 아닙니다."),

    // Member
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "M-001", "잘못된 회원 타입 입니다.(memberType : KAKAO)"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "M-002", "이미 가입된 회원 입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "M-003", "해당 회원은 존재하지 않습니다."),

    // Kid
    KID_NOT_EXIST(HttpStatus.BAD_REQUEST, "K-001", "해당 아이는 존재하지 않습니다."),
    KID_NOT_MATCHED(HttpStatus.BAD_REQUEST, "K-002", "해당 아이를 회원 정보에서 찾을 수 없습니다."),

    // Product
    PRODUCT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "P-001", "해당 거래 글은 존재하지 않습니다."),

    // Category
    CATEGORY_NOT_EXISTS(HttpStatus.BAD_REQUEST, "C-001", "해당 카테고리는 존재하지 않습니다."),

    // Offer
    OFFER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "O-001", "해당 거래 요청은 존재하지 않습니다."),

    // OfferDetail
    OFFER_DETAIL_NOT_EXISTS(HttpStatus.BAD_REQUEST, "OD-001", "해당 거래에 대한 거래 요청 시간이 존재하지 않습니다."),

    // Point
    POINT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "POINT-001", "해당 포인트는 존재하지 않습니다."),

    // DEAL
    DEAL_NOT_EXISTS(HttpStatus.BAD_REQUEST, "DEAL-001", "해당 거래는 존재하지 않습니다."),
    DEAL_INVALID_STATUS(HttpStatus.BAD_REQUEST, "DEAL-002", "거래 중인 거래 건이 아닙니다."),
    INVALID_BUYER(HttpStatus.BAD_REQUEST, "DEAL-003", "해당 거래에 대한 구매자가 아닙니다."),
    INVALID_SELLER(HttpStatus.BAD_REQUEST, "DEAL-004", "해당 거래에 대한 판매자가 아닙니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "DEAL-005", "QR 코드가 유효하지 않습니다."),

    // Temparary,
    UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "T-004", "회원 정보를 수정에 실패하였습니다."),

    // Util
    IMAGE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "U-001", "업로드 한 이미지가 비어있습니다."),
    S3UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "U-002", "이미지 업로드에 실패하였습니다.");

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

}
