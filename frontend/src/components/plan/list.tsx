"use client";

import { PlanCard } from "@/types/plan";
import { useRouter } from "next/navigation";
import Image from "next/image";
import { useEffect, useState } from "react";
import { getDealListAPI } from "@/services/deals";
import { AppRouterInstance } from "next/dist/shared/lib/app-router-context.shared-runtime";

function DealCard(deal: PlanCard, key: number, router: AppRouterInstance) {
  return (
    <div key={key} className="w-full">
      <div className="flex gap-3 w-full my-2">
        <Image
          src={deal?.thumbnailImage}
          alt="thumb"
          width="90"
          height="90"
          className="rounded-xl"
        />
        <div className="w-full">
          <div className="flex justify-between">
            <h4>{deal.price.toLocaleString("kr-KR") + "원"}</h4>
            <span className="text-body2">거래중</span>
          </div>
          <div className="text-body">{deal.title}</div>
          <div className="text-caption c-text3">
            {deal.dealPlace} &#183; {deal.selectedTime.substring(11, 16)}
          </div>
        </div>
      </div>
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
            i?.selectedTime.substring(0, 10) !== list[k + 1]?.selectedTime.substring(0, 10)
          ) {
            return (
              <>
                <div key={k} className="text-body2">
                  {i.selectedTime.substring(0, 10)}
                </div>
                {DealCard(i, k, router)}
              </>
            );
          }
          return DealCard(i, k, router);
        })
      ) : (
        <div>진행 중인 거래가 없습니다.</div>
      )}
    </>
  );
}
