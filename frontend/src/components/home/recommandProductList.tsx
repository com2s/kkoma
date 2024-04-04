import ProductCard from "@/components/home/productCard";
import styles from "./recommandProductList.module.scss";
import { ProductSm } from "@/types/product";

interface Recommand {
  name: string | null;
  products: Array<ProductSm>;
}

export default function RecommandProductList({ name, products }: Recommand) {
  return (
    <div className={styles.container}>
      <h4>{name ? name : "아이"}에게 추천하는 상품</h4>
      <div className={styles.list}>
        {products.map((item, k) => (
          <ProductCard key={k} product={item} />
        ))}
      </div>
    </div>
  );
}
