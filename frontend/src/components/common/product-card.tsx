"use client";

import styles from "./product-card.module.scss";
import Image from "next/image";
import { ProductSm } from "@/types/product";
import { MoneyFormat } from "@/utils/format";
import { useRouter } from "next/navigation";

export function ProductCard(props: { product: ProductSm }) {
  const { thumbnail_image, id, title, price } = props.product;
  const router = useRouter();

  return (
    //TODO: router.push 이용하여 상세 페이지로 이동해야함.
    <div className={styles.card} onClick={() => router.push("")}>
      <Image src={thumbnail_image} alt="product" width={40} height={40} className="rounded" />
      <div className="flex flex-col">
        <span className="text-caption c-text2">{title}</span>
        <span className="text-body">{MoneyFormat(price)}</span>
      </div>
    </div>
  );
}
