import styles from "@/components/lists/lists-detail-content.module.scss";

// 인터페이스
import { DetailParams } from "@/types/product";
import { ProductStatusFormat, calcElapsedMinutes } from "@/utils/format";

interface DetailTypes {
  propsId: string;
  product: DetailParams["data"] | undefined;
}

export default async function DetailContent({ propsId, product }: DetailTypes) {
  return (
    <div className={`divide-y ${styles.container}`}>
      {product === undefined ? (
        <div>상품 정보가 없습니다.</div>
      ) : (
        <div>
          <p className="text-body2 c-text3">
            {product.categoryName ?? "거래 장소 null"}
            &#183;
            {calcElapsedMinutes(product.elapsedMinutes)}
          </p>
          <div className="flex justify-between mb-2">
            <h3 className={styles.title}>{product.title}</h3>
            <div className="min-w-fit">
              {ProductStatusFormat(product.status)}
            </div>
          </div>
          <div className="views">
            <p className="text-caption c-text3">
              조회 {product.viewCount.toLocaleString()} &#183; 거래 요청{" "}
              {product.offerCount}
              &#183; 찜 {product.wishCount}
            </p>
          </div>

          <h2 className={styles.price}>{product.price.toLocaleString()}원</h2>
          <p className={`text-body ${styles.content}`}>{product.description}</p>
        </div>
      )}
    </div>
  );
}
