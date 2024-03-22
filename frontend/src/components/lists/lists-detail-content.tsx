import { getProductDetail } from "./lists-ftn";
import styles from "@/components/lists/lists-detail-content.module.scss";

// 인터페이스
import { DetailParams } from "@/types/product";

import { Typography } from "@mui/material";

interface DetailTypes {
  propsId: string;
  product: DetailParams;
}

export default async function DetailContent({
  propsId,
  product,
}: DetailTypes) {
  return (
    <div className={styles.container}>
      <div className={styles.title}>
        <div>
          <p className="text-gray-400">
            {product.categoryName} • {product.elapsedMinutes}
          </p>
          <h1>{product.title}</h1>
          <div className="views">
            <p className="text-gray-400 text-sm">
              조회 {product.viewCount} • 거래 요청 {product.offerCount} • 찜{" "}
              {product.wishCount}
            </p>
          </div>
        </div>
        <Typography
          variant="h5"
          sx={{
            mt: 2,
            fontWeight: "bold",
            margin: "auto 0",
            color:
              product.status === "SALE"
                ? "orange"
                : product.status === "PROGRESS"
                ? "crimson"
                : product.status === "SOLD"
                ? "dimgray"
                : "black", // 기본값
          }}
        >
          <span>
            {product.status === "SALE"
              ? "판매 중"
              : product.status === "PROGRESS"
              ? "거래 중"
              : "거래 완료"}
          </span>
        </Typography>
      </div>

      <h2 className={styles.price}>{product.price.toLocaleString()}원</h2>
      <p className={styles.content}>
        {product.description}
        {/* 노인은 늘 바다를 ‘라 마르’라고 생각했는데, 그것은 사람들이 바다에 애정을
        느낄 때 부르는 스페인어다. 때로는 바다를 사랑하는 사람들도 바다에 관해
        나쁘게 말할 때가 있는데, 그럴 때조차도 바다는 늘 여성인 것처럼 불린다.
        몇몇 젊은 어부, 낚싯줄에 찌 대신 부표를 연결해 사용하고 상어 간으로
        큰돈을 벌었을 때 사들인 모터보트를 타고 다니는 이들은 바다를 남성형인
        ‘엘 마르’라고 불렀다. 그들은 바다를 경쟁자나 장소, 심지어 적이라고
        불렀다. 하지만 노인은 늘 바다를 여성으로 생각했고, 큰 호의를
        베풀어주거나 베풀어주지 않는 무언가로 생각했는데, 만일 바다가 사납거나
        사악한 짓을 한다면 그것은 바다로서도 어쩔 수 없어서 그러는 것이었다. */}
      </p>
    </div>
  );
}
