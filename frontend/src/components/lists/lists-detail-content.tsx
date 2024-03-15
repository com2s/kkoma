import { Typography } from "@mui/material";
import { getProductDetail } from "./lists-ftn";

import styles from "@/components/lists/lists-detail-content.module.scss";

interface IParams {
  id: string;
}

export default async function DetailContent(props: IParams) {
  const detail = await getProductDetail(props.id);

  return (
    <div className={styles.container}>
      <div className={styles.title}>
        <div>
          <p>{detail.time}</p>
          <h1>{detail.productName}</h1>
          <div className="views">
            <p>
              조회수 : {detail.views} | 좋아요 : {detail.likes}
            </p>
            <p>{detail.town}</p>
          </div>
        </div>
        <Typography
          variant="h4"
          sx={{
            mt: 2,
            fontWeight: "bold",
            margin: "auto 0",
            color:
              detail.status === "거래 중"
                ? "crimson"
                : detail.status === "거래 완료"
                ? "dimgray"
                : detail.status === "판매 중"
                ? "orange"
                : "black", // 기본값
          }}
        >
          <span>{detail.status}</span>
        </Typography>
      </div>

      <h2 className={styles.price}>
        {parseInt(detail.price).toLocaleString()}원
      </h2>
      <p className={styles.content}>
        노인은 늘 바다를 ‘라 마르’라고 생각했는데, 그것은 사람들이 바다에 애정을
        느낄 때 부르는 스페인어다. 때로는 바다를 사랑하는 사람들도 바다에 관해
        나쁘게 말할 때가 있는데, 그럴 때조차도 바다는 늘 여성인 것처럼 불린다.
        몇몇 젊은 어부, 낚싯줄에 찌 대신 부표를 연결해 사용하고 상어 간으로
        큰돈을 벌었을 때 사들인 모터보트를 타고 다니는 이들은 바다를 남성형인
        ‘엘 마르’라고 불렀다. 그들은 바다를 경쟁자나 장소, 심지어 적이라고
        불렀다. 하지만 노인은 늘 바다를 여성으로 생각했고, 큰 호의를
        베풀어주거나 베풀어주지 않는 무언가로 생각했는데, 만일 바다가 사납거나
        사악한 짓을 한다면 그것은 바다로서도 어쩔 수 없어서 그러는 것이었다.
      </p>
    </div>
  );
}
