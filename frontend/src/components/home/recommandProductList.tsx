import ProductCard from "@/components/home/productCard";
import styles from "./recommandProductList.module.scss";

export default function recommandProductList() {
  return (
    <div className={styles.container}>
      <h4>예콩이를 위한 추천 상품</h4>
      <div className={styles.list}>
        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
      </div>
    </div>
  );
}
