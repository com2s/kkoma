import { getProductDetail } from "./lists-ftn";
import styles from "@/components/lists/lists-detail-content.module.scss";

// 인터페이스
import { DetailParams } from "@/types/product";
import { calcElapsedMinutes } from "@/utils/format";

import { Typography } from "@mui/material";

interface DetailTypes {
  propsId: string;
  product: DetailParams["data"] | undefined;
}

export default async function DetailContent({ propsId, product }: DetailTypes) {
  return (
    <div className={styles.container}>
      {product === undefined ? (
        <div>상품 정보가 없습니다.</div>
      ) : (
        <div>
          <div className={styles.title}>
            <div>
              <p className="text-body2 c-text3">
                {product.dealPlace ?? "거래 장소 null"}
                &#183;
                {calcElapsedMinutes(product.elapsedMinutes)}
              </p>
              <div className="flex justify-between my-2">
                <h2>{product.title}</h2>
                <div>{product.status}</div>
              </div>
              <div className="views">
                <p className="text-body2 c-text3">
                  조회 {product.viewCount} &#183; 거래 요청 {product.offerCount}{" "}
                  &#183; 찜{product.wishCount}
                </p>
              </div>
            </div>
          </div>

          <h2 className={styles.price}>{product.price.toLocaleString()}원</h2>
          <p className="text-body">{product.description}</p>
        </div>
      )}
    </div>
  );
}
