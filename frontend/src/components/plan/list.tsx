"use client";

import { PlanCard } from "@/types/plan";
import { useRouter } from "next/navigation";
import Image from "next/image";
import { useEffect, useState } from "react";
import { getDealListAPI } from "@/services/deals";

let date: Date = new Date();

function DealCard(deal: PlanCard, key: number) {
  const router = useRouter();

  if (date !== deal.created_at) {
    return (
      <div key={key} className="w-full">
        <div className="text-body2">
          {/* TODO: 거래 일정 날짜 표시
            {deal.created_at.getFullYear() +
            "-" +
            ("0" + deal.created_at.getMonth()).slice(-2) +
            "-" +
            deal.created_at.getDate()} */}
        </div>
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
            <div className="text-caption c-text3">{deal.dealPlace} &#183; 11:00</div>
          </div>
        </div>
        <button
          className="flex bg-slate-50 text-caption w-full rounded-sm py-1 justify-center my-2"
          onClick={() => {
            router.push(`/plan/${deal.id}/qr`);
          }}
        >
          거래 확정하기
        </button>
      </div>
    );
  } else {
    return <div key={key}>{deal.title}</div>;
  }
}

export function DealList() {
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
      {list && list.length !== 0 ? (
        list.map((i, k) => DealCard(i, k))
      ) : (
        <div>진행 중인 거래가 없습니다.</div>
      )}
    </>
  );
}
