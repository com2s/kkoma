"use client";

import { PlanCard } from "@/types/plan";
import { useRouter } from "next/navigation";
import Image from "next/image";

let date: Date = new Date();

const list: Array<PlanCard> = [
  {
    id: 1,
    thumbnail_image: "https://picsum.photos/250/250",
    price: 0,
    title: "1번 글입니다.",
    place_detail: "강남구 역삼동 12",
    created_at: new Date("2024-03-11"),
  },
  {
    id: 2,
    thumbnail_image: "https://picsum.photos/250/250",
    price: 0,
    title: "2번 글입니다.",
    place_detail: "서초도서관 앞",
    created_at: new Date("2024-03-15"),
  },
];

function DealCard(deal: PlanCard, key: number) {
  const router = useRouter();

  if (date !== deal.created_at) {
    return (
      <div key={key} className="w-full">
        <div className="text-body2">
          {deal.created_at.getFullYear() +
            "-" +
            ("0" + deal.created_at.getMonth()).slice(-2) +
            "-" +
            deal.created_at.getDate()}
        </div>
        <div className="flex gap-3 w-full my-2">
          <Image
            src={deal?.thumbnail_image}
            alt="thumb"
            width="90"
            height="90"
            className="rounded-xl"
          />
          <div className="w-full">
            <div className="flex justify-between">
              <h4>{deal.price + "원"}</h4>
              <span className="text-body2">거래중</span>
            </div>
            <div className="text-body">{deal.title}</div>
            <div className="text-caption c-text3">{deal.place_detail} &#183; 11:00</div>
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
  return <>{list.map((i, k) => DealCard(i, k))}</>;
}
