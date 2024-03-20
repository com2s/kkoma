import styles from "./productCard.module.scss";
export default function ProductCard() {
  return (
    <div className={styles.card}>
      <img src="/images/sample1.webp" alt="img-sample" />
      <h4>15,000원</h4>
      <span className="text-body">아기 체온조절 모자</span>
      <span className="text-caption c-text2">역삼동</span>
    </div>
  );
}
