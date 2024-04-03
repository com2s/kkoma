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
    ROLE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "M-004", "해당 권한은 존재하지 않습니다."),

    // Kid
    KID_NOT_EXIST(HttpStatus.BAD_REQUEST, "K-001", "해당 아이는 존재하지 않습니다."),
    KID_NOT_MATCHED(HttpStatus.BAD_REQUEST, "K-002", "해당 아이를 회원 정보에서 찾을 수 없습니다."),

    // Product
    PRODUCT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "P-001", "해당 거래 글은 존재하지 않습니다."),
    VIEW_OVERFLOW(HttpStatus.INTERNAL_SERVER_ERROR, "P-002", "해당 게시 글의 조회수가 최대값에 도달하였습니다."),

    // Category
    CATEGORY_NOT_EXISTS(HttpStatus.BAD_REQUEST, "C-001", "해당 카테고리는 존재하지 않습니다."),

    // Offer
    OFFER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "O-001", "해당 거래 요청은 존재하지 않습니다."),
    INVALID_ACCEPT(HttpStatus.BAD_REQUEST, "O-002", "판매 중인 상품에만 거래를 수락할 수 있습니다."),

    // OfferDetail
    OFFER_DETAIL_NOT_EXISTS(HttpStatus.BAD_REQUEST, "OD-001", "해당 거래에 대한 거래 요청 시간이 존재하지 않습니다."),
    LACKING_OFFER_DETAIL(HttpStatus.BAD_REQUEST, "OD-002", "1개 이상의 거래 가능 시간 범위를 선택해주세요."),

    // Point
    POINT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "POINT-001", "해당 포인트는 존재하지 않습니다."),
    POINT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "POINT-002", "포인트가 부족하여 거래 요청을 할 수 없습니다."),
    BALANCE_BELOW_ZERO(HttpStatus.BAD_REQUEST, "POINT-003", "포인트 잔액이 0원 미만이 되도록 인출할 수 없습니다."),

    // DEAL
    DEAL_NOT_EXISTS(HttpStatus.BAD_REQUEST, "DEAL-001", "해당 거래는 존재하지 않습니다."),
    DEAL_INVALID_STATUS(HttpStatus.BAD_REQUEST, "DEAL-002", "거래 중인 거래 건이 아닙니다."),
    INVALID_BUYER(HttpStatus.BAD_REQUEST, "DEAL-003", "해당 거래에 대한 구매자가 아닙니다."),
    INVALID_SELLER(HttpStatus.BAD_REQUEST, "DEAL-004", "해당 거래에 대한 판매자가 아닙니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "DEAL-005", "QR 코드가 유효하지 않습니다."),

    // CHAT
    CHAT_ROOM_NOT_EXISTS(HttpStatus.BAD_REQUEST, "CHAT-001", "해당 채팅방은 존재하지 않습니다."),

    // Temporary,
    UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "T-004", "회원 정보를 수정에 실패하였습니다."),

    // Area
    AREA_LEVEL_RANGE_VALID(HttpStatus.BAD_REQUEST, "AR-001", "유효하지 않은 지역 레벨입니다."),
    SUB_AREA_NOT_EXISTS(HttpStatus.BAD_REQUEST, "AR-002", "하위 지역코드가 존재하지 않습니다."),
    AREA_NOT_EXISTS(HttpStatus.BAD_REQUEST, "AR-003", "해당 법정코드에 대한 지역 정보가 존재하지 않습니다"),

    // Util
    IMAGE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "U-001", "업로드 한 이미지가 비어있습니다."),
    S3UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "U-002", "이미지 업로드에 실패하였습니다."),
    AUTO_COMPLETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "U-003", "자동완성 카테고리 리스트를 불러올 수 없습니다."),

    // WishList
    WISH_LIST_ALREADY_VALID(HttpStatus.BAD_REQUEST, "W-001", "이미 찜한 거래 글입니다"),
    WISH_LIST_ALREADY_NOT_VALID(HttpStatus.BAD_REQUEST, "W-002", "이미 찜 취소한 거래 글입니다"),
    WISH_COUNT_ZERO(HttpStatus.BAD_REQUEST, "W-003", "찜 수가 0이라 더 이상 감소될 수 없습니다"),
    WISH_LIST_DOES_NOT_EXISTS(HttpStatus.BAD_REQUEST, "W-004", "찜한 기록이 없어서 찜을 취소할 수 없습니다."),

    // Notification
    NOTI_NOT_EXISTS(HttpStatus.BAD_REQUEST, "NOTI-001", "해당 알림은 존재하지 않습니다."),
    ;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

}
