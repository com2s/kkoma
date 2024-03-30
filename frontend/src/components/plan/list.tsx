"use client";

import { PlanCard } from "@/types/plan";
import { useRouter } from "next/navigation";
import Image from "next/image";
import { useEffect, useState } from "react";
import { getDealListAPI } from "@/services/deals";
import { AppRouterInstance } from "next/dist/shared/lib/app-router-context.shared-runtime";
import { ProductCard } from "../common/product-card";
import { NoContents } from "../common/no-contents";
import { NormalBtn, SmallBtn } from "../common/buttons";

export function DealCard(
  deal: PlanCard,
  key: number,
  router: AppRouterInstance
) {
  return (
    <div key={key} className="w-full">
      <ProductCard
        product={{
          id: deal.id,
          thumbnailImage: deal.thumbnailImage,
          dealPlace: deal.dealPlace,
          title: deal.title,
          price: deal.price,
          status: "PROGRESS",
          elapsedMinutes: Number(deal.selectedTime),
        }}
      />
      <button
        className="flex bg-slate-50 text-caption w-full rounded-sm py-1 justify-center my-2"
        onClick={() => {
          router.push(`/plan/${deal.dealId}/qr?product=${deal.id}`);
        }}
      >
        거래 확정하기
      </button>
    </div>
  );
}

export function DealList() {
  const router = useRouter();
  const [list, setList] = useState<Array<PlanCard>>([]);

  const getList = async () => {
    const res = await getDealListAPI();
    setList(res);
  };

  useEffect(() => {
    getList();
  }, []);

  return (
    <>
      {list && list.length > 0 ? (
        list.map((i, k) => {
          if (
            k !== list.length &&
            i?.selectedTime.substring(0, 10) !==
              list[k + 1]?.selectedTime.substring(0, 10)
          ) {
            return (
              <div key={k}>
                <div className="text-body2">
                  {i.selectedTime.substring(0, 10)}
                </div>
                {DealCard(i, k, router)}
              </div>
            );
          }
          return DealCard(i, k, router);
        })
      ) : (
        <NoContents>
          <h4 className="c-text3">거래중인 상품이 없어요</h4>
          <Image
            src={"/images/Empty-BOX.png"}
            alt="empty"
            width={100}
            height={100}
          />
          <SmallBtn next={"/lists"}>상품 찾으러 가기</SmallBtn>
        </NoContents>
      )}
    </>
  );
}
