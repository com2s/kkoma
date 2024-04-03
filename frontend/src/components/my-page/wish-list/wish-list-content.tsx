"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useState, useEffect, use } from "react";
import Button from "@mui/material/Button"; // Material-UI 버튼 사용
import { Avatar, Card, CardContent, Typography } from "@mui/material";
import Image from "next/image";

import { getMyWishes } from "@/components/my-page/my-page-ftn";
import { WishContent, WishData } from "@/types/member";
import { ProductCard } from "@/components/common/product-card";

export default function WishList() {
  const [wishes, setWishes] = useState<WishContent[]>([]);
  const [data, setData] = useState<WishData>();
  const [page, setPage] = useState(0);
  const [success, setSuccess] = useState(true);

  const router = useRouter();

  const fetchData = async () => {
    const res = await getMyWishes(page);
    setSuccess(res.success);
    setData(res.data);
    setWishes(res.data.content);
  };

  useEffect(() => {
    fetchData();
  }, [page]);

  return (
    <>
      {success === false && (
        <h3 className="p-2">찜 목록을 불러오는데 실패했습니다.</h3>
      )}
      {success === true && data?.empty && (
        <div className="flex-row justify-center m-4 rounded-3xl bg-slate-100">
          <Image
            src={"/images/wish-empty.png"}
            alt="빈 목록"
            height={300}
            width={300}
            style={{ margin: "auto", padding: '24px'}}
          />
          <h4 className="mt-4 pb-8 text-center font-semibold">찜 목록이 비어있습니다.</h4>
        </div>
      )}
      {success === true && wishes.length > 0 && (
        <>
          <div className="grid grid-cols-1 gap-4">
            {wishes.map((wish) => (
              <div key={wish.id} className="cursor-pointer my-10">
                <ProductCard product={wish} next={`/lists/${wish.id}`} />
              </div>
            ))}
          </div>
          <div className="flex justify-around mt-4">
            <Button
              onClick={() => setPage(page - 1)}
              disabled={data?.first || page === 0}
              sx={{ color: "black", fontSize: "1rem" }}
            >
              이전
            </Button>
            <Button
              onClick={() => setPage(page + 1)}
              disabled={data?.last}
              sx={{ color: "black", fontSize: "1rem" }}
            >
              다음
            </Button>
          </div>
        </>
      )}
    </>
  );
}
