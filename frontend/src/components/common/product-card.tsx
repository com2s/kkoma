"use client";

import styles from "./product-card.module.scss";
import Image from "next/image";
import { Product, ProductSm } from "@/types/product";
import { MoneyFormat, ProductStatusFormat } from "@/utils/format";
import { useRouter } from "next/navigation";

export function ProductSmCard(props: { product: ProductSm }) {
  const { thumbnailImage, id, title, price } = props.product;
  const router = useRouter();

  return (
    <div className={styles.card} onClick={() => router.push(`/lists/${id}`)}>
      <Image src={thumbnailImage} alt="product" width={40} height={40} className="rounded" />
      <div className="flex flex-col">
        <span className="text-caption c-text2">{title}</span>
        <span className="text-body">{MoneyFormat(price)}</span>
      </div>
    </div>
  );
}

export function ProductCard(props: { product: ProductSm }) {
  const { thumbnailImage, dealPlace, title, price, status, elapsedMinutes } = props.product;
  return (
    <div className="flex gap-3 w-full my-2">
      <Image src={thumbnailImage} alt="thumb" width="90" height="90" className="rounded-xl" />
      <div className="w-full">
        <div className="flex justify-between">
          <h4>{price.toLocaleString("kr-KR") + "원"}</h4>
          <span className="min-w-fit text-body2">{ProductStatusFormat(status)}</span>
        </div>
        <div className="text-body">{title}</div>
        <div className="text-caption c-text3">
          {dealPlace} &#183; {elapsedMinutes}
        </div>
      </div>
    </div>
  );
}
