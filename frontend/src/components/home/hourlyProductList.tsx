import ProductCard from "@/components/home/productCard";
import styles from "./hourlyProductList.module.scss";
import { ProductSm } from "@/types/product";

interface Products {
  title: string;
  subtitle?: string;
  products: Array<ProductSm>;
}

export default function HourlyProductList({ title, subtitle, products }: Products) {
  return (
    <div className={styles.container}>
      <div>
        <h4>{title}</h4>
        {subtitle && <div className="text-caption c-text3 mt-1">{subtitle}</div>}
      </div>
      <div className={styles.list}>
        {products.map((item, k) => {
          return <ProductCard key={k} product={item} />;
        })}
      </div>
    </div>
  );
}
