"use client";

import TopBar3 from "@/components/common/top-bar3";
import Title from "@/components/common/title";
import styles from "./qr.module.scss";
import { ProductSmCard } from "@/components/common/product-card";
import QRCode from "@/components/plan/qr-img";
import { DetailParams, ProductSm } from "@/types/product";
import { useEffect, useState } from "react";
import { useParams, useSearchParams } from "next/navigation";
import { getDealCodeAPI } from "@/services/deals";
import { getProductDetail } from "@/components/lists/lists-ftn";

export default function DealPay() {
  const params = useParams<{ id: string }>();
  const query = useSearchParams();
  const productId = query.get("product") ?? "";

  const [code, setCode] = useState<string>();
  const [product, setProduct] = useState<DetailParams>();

  const getCodeAndProduct = async () => {
    const res1 = await getProductDetail(productId);
    setProduct(res1);
    const res2 = await getDealCodeAPI({ dealId: params.id });
    setCode(res2);
  };

  useEffect(() => {
    getCodeAndProduct();
  }, []);

  return (
    <div className={styles.qr}>
      <TopBar3 />
      <Title title="거래를 확정해주세요" subtitle="QR코드를 스캔해주세요" />
      {product ? (
        <ProductSmCard
          product={{
            id: product.data.id,
            thumbnailImage: product.data.productImages[0],
            title: product.data.title,
            price: product.data.price,
          }}
        />
      ) : (
        <></>
      )}
      <div className="flex justify-center w-full">{code ? <QRCode code={code} /> : <></>}</div>
    </div>
  );
}
