import ProductCard from "@/components/home/productCard";
import styles from "./hourlyProductList.module.scss";
import { ProductSm } from "@/types/product";

interface Products {
  title: string;
  products: Array<ProductSm>;
}

export default function HourlyProductList({ title, products }: Products) {
  return (
    <div className={styles.container}>
      <h4>{title}</h4>
      <div className={styles.list}>
        {products.map((item, k) => {
          return <ProductCard key={k} product={item} />;
        })}
      </div>
    </div>
  );
}
