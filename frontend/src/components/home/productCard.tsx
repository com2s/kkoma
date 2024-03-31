import styles from "./productCard.module.scss";
import Image from "next/image";

export default function ProductCard() {
  return (
    <div className={styles.card}>
      <Image
        src="/images/sample1.webp"
        width={246}
        height={246}
        alt="img-sample"
        // style={{ width: "246px", height: "246px" }}
      />
      <h4>15,000원</h4>
      <span className="text-body">아기 체온조절 모자</span>
      <span className="text-caption c-text2">역삼동</span>
    </div>
  );
}
