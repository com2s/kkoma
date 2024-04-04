"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState, useEffect } from "react";

import { getRecentList } from "@/components/my-page/my-page-ftn";
import { RecentList } from "@/types/member";
import { ProductCard } from "@/components/common/product-card";

import { Button } from "@mui/material";
import Image from "next/image";

export default function MyRecent() {
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(20);
  const [recentList, setRecentList] = useState<RecentList>();
  const [success, setSuccess] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      const res = await getRecentList(page, size);
      setSuccess(res.success);
      setRecentList(res);
    };
    fetchData();
  }, [page, size]);

  return (
    <div>
      {success === false && (
        <h3 className="p-2">최근 본 상품을 불러오는데 실패했습니다.</h3>
      )}
      {success && recentList?.data.empty && (
        <div className="flex-row justify-center m-4 rounded-3xl bg-slate-100">
        <Image
          src={"/images/list-empty.png"}
          alt="빈 목록"
          height={300}
          width={300}
          style={{ margin: "auto", padding: "24px"}}
        />
        <h4 className="mt-4 pb-8 text-center font-semibold">최근 본 글이 없습니다.</h4>
      </div>
      )}
      {success && (
        <div>
          {recentList?.data.content.map((product) => (
            <div className="my-10 cursor-pointer" key={product.id}>
              <ProductCard product={product} next={`/lists/${product.id}`} />
            </div>
          ))}
          <div className="flex justify-around mt-8">
            <Button
              onClick={() => setPage(page - 1)}
              disabled={recentList?.data.first || page === 0}
              sx={{ color: "black", fontSize: "1rem" }}
            >
              이전
            </Button>
            <Button
              onClick={() => setPage(page + 1)}
              disabled={recentList?.data.last}
              sx={{ color: "black", fontSize: "1rem" }}
            >
              다음
            </Button>
          </div>
        </div>
      )}
    </div>
  );
}
