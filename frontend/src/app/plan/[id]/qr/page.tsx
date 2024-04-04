"use client";

import SockJS from "sockjs-client";
import { CompatClient, Stomp } from "@stomp/stompjs";
import TopBar3 from "@/components/common/top-bar3";
import Title from "@/components/common/title";
import styles from "./qr.module.scss";
import { ProductSmCard } from "@/components/common/product-card";
import QRCode from "@/components/plan/qr-img";
import { DetailParams, ProductSm } from "@/types/product";
import { useEffect, useState } from "react";
import { useParams, useRouter, useSearchParams } from "next/navigation";
import { getDealCodeAPI } from "@/services/deals";
import { getProductDetail } from "@/components/lists/lists-ftn";
import LocalStorage from "@/utils/localStorage";
import { NoContents } from "@/components/common/no-contents";

let ws: CompatClient;

export default function DealPay() {
  const router = useRouter();
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

  const connectComplete = () => {
    const serverURL = process.env.NEXT_PUBLIC_API_URL + "/chat";
    const socket = new SockJS(serverURL);
    ws = Stomp.over(socket);
    process.env.NODE_ENV === "production" ? (ws.debug = () => {}) : "";
    ws.connect(
      {},
      () => {
        ws.subscribe(`/topic/deals/${params.id}`, (res) => {
          console.log(res.body);
          router.replace(`/plan/${params.id}/complate`);
        });
      },
      (e: any) => {
        console.log("소켓 연결 실패", e);
      }
    );
  };

  useEffect(() => {
    getCodeAndProduct();
    if (product?.data.memberSummary.memberId !== Number(LocalStorage.getItem("memberId"))) {
      connectComplete();
    }
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
      <div className="flex justify-center w-full">
        {product?.data.memberSummary.memberId !== Number(LocalStorage.getItem("memberId")) &&
        code ? (
          <QRCode code={code} />
        ) : (
          <NoContents>
            <h4 className="c-text2 whitespace-pre-wrap text-center">
              {"구매자의 QR코드를 스캔하고\n거래를 확정해보세요"}
            </h4>
            <span className="text-caption c-text3 whitespace-pre-wrap text-center">
              {"기본 카메라 어플을 사용하면\nQR코드를 스캔할 수 있어요"}
            </span>
          </NoContents>
        )}
      </div>
    </div>
  );
}
