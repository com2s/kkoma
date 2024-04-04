import { ProductSm } from "@/types/product";
import styles from "./productCard.module.scss";
import Image from "next/image";

interface Product {
  product: ProductSm;
  num: number;
}

export default function ProductCardWithNum({ product, num }: Product) {
  return (
    <div className={styles.card}>
      <div style={{ position: "relative", width: "100%", aspectRatio: 1 / 1 }}>
        <Image
          src={product.thumbnailImage}
          fill
          alt="img-sample"
          style={{ borderRadius: "15px" }}
        />
      </div>
      <h4>{product.price.toLocaleString()}원</h4>
      <span className="text-body">{product.title}</span>
      <span className="text-caption c-text2">{product.dealPlace}</span>
    </div>
  );
}
