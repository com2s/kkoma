import ProductCard from "@/components/home/productCard";
import styles from "./recommandProductList.module.scss";

interface Recommand {
  name: string | null;
}

export default function RecommandProductList({ name }: Recommand) {
  return (
    <div className={styles.container}>
      <h4>{name}에게 추천하는 상품</h4>
      <div className={styles.list}>
        <ProductCard />
        <ProductCard />
        <ProductCard />
      </div>
    </div>
  );
}
