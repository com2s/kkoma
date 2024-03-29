package com.ssafy.kkoma.api.notification.constant;

import com.ssafy.kkoma.api.notification.dto.response.NotiDetail;
import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.product.constant.MyProductType;

public class NotiDetailBuilder {

    // Singleton
    private static NotiDetailBuilder instance;
    private NotiDetailBuilder() { }
    public static NotiDetailBuilder getInstance() {
        if (instance == null) instance = new NotiDetailBuilder();
        return instance;
    }

    private static final StringBuilder sb = new StringBuilder();

    // 1시간 후 상품 '에어팟' 판매/구매 약속이 예정되어 있어요.
    public NotiDetail scheduledDeal(String productTitle, MyProductType myProductType, Long chatRoomId) {
        sb.setLength(0);
        return new NotiDetail(
            sb.append("1시간 후 상품 '").append(productTitle).append("' ")
                .append(myProductType == MyProductType.BUY ? "구매" : "판매")
                .append(" 약속이 예정되어 있어요.").toString(),
            "/chat/" + chatRoomId
            );
    }

    // 상품 '에어팟'에 거래 요청이 들어왔어요.
    public NotiDetail receiveOffer(String productTitle, Long productId) {
        sb.setLength(0);
        return new NotiDetail(
            sb.append("상품 '").append(productTitle).append("'에 거래 요청이 들어왔어요.").toString(),
            "/my-trade/" + productId
        );
    }

    // 상품 '에어팟'의 판매 대금 10원이 입금되었어요. (잔액: 10원)
    public NotiDetail receivePayment(String productTitle, int price, int balance) {
        sb.setLength(0);
        return new NotiDetail(
            sb.append("상품 '").append(productTitle).append("'의 판매 대금 ")
                .append(price).append("원이 입금되었어요. (잔액: ").append(balance).append("원").toString(),
            "https://www.google.com"
        );
    }

    // 상품 '에어팟'의 구매 예치금 10원이 반환되었어요. (잔액: 10원)
    public NotiDetail returnPayment(String productTitle, int price, int balance) {
        sb.setLength(0);
        return new NotiDetail(
            sb.append("상품 '").append(productTitle).append("'의 구매 예치금 ")
                .append(price).append("원이 반환되었어요. (잔액: ").append(balance).append("원)").toString(),
            "https://www.google.com"
        );
    }

    // 포인트 10원이 충전/차감되었어요. (잔액: 10원)
    public NotiDetail changePoint(PointChangeType pointChangeType, int amount, int balance) {
        sb.setLength(0);
        return new NotiDetail(
            sb.append("포인트 ").append(amount).append("원이 ")
                .append(pointChangeType == PointChangeType.CHARGE ? "충전" : "차감")
                .append("되었어요. (잔액: ").append(balance).append("원)").toString(),
            "https://www.google.com"
        );
    }

    // 아이 정보를 입력한 지 3일이 지났어요!
    public NotiDetail remindUpdateKid(int days) {
        sb.setLength(0);
        return new NotiDetail(
            sb.append("아이 정보를 입력한 지 ").append(days)
                .append("일이 지났어요! 정확한 정보 제공을 위해 변동된 정보가 있다면 update를 해볼까요?").toString(),
            "https://www.google.com"
        );
    }

}