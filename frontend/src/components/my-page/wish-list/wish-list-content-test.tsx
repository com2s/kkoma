"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useState, useEffect, use } from "react";
import Button from "@mui/material/Button"; // Material-UI 버튼 사용

import { getMyWishes } from "@/components/my-page/my-page-ftn";
import { WishContent, WishData } from "@/types/member";
import { Avatar, Card, CardContent, Typography } from "@mui/material";

export default function WishList() {
  const wishes = [
    {
      id: 1,
      price: 10000,
      title: "title",
      thumbnailImage: "/temp-img.svg",
      dealPlace: "dealPlace",
      elapsedMinutes: 60,
      status: "SALE",
    },
    {
      id: 2,
      price: 20000,
      title: "title",
      thumbnailImage: "/temp-img.svg",
      dealPlace: "dealPlace",
      elapsedMinutes: 120,
      status: "SOLD",
    },
    {
      id: 3,
      price: 30000,
      title: "title",
      thumbnailImage: "/temp-img.svg",
      dealPlace: "dealPlace",
      elapsedMinutes: 180,
      status: "PROGRESS",
    },
  ];

  return (
    <>
      <div className="grid grid-cols-1 gap-4">
        <h4>아래는 CSS 테스트용 컴포넌트</h4>
        {wishes.map((wish) => (
          <div key={wish.id} className="">
            <Link href={`/lists/${wish.id}`}>
              <Card
                variant="outlined"
                className="p-2 border-0 flex justify-between"
              >
                <Avatar
                  alt="wish Image"
                  src={wish.thumbnailImage ?? "/temp-img.svg"}
                  sx={{
                    maxWidth: 80,
                    maxHeight: 80,
                    minWidth: 50,
                    minHeight: 50,
                    width: "15%",
                    height: "15%",
                    marginY: "auto",
                  }}
                  className=""
                  variant="square"
                />
                <CardContent sx={{ padding: 1 }} className="w-7/12">
                  <Typography variant="h5" component="div">
                    {wish.price.toLocaleString()}원
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    {wish.title ?? "제목 없음"}
                  </Typography>
                  <Typography variant="body2">
                    {wish.dealPlace ?? "거래 장소 null"} •{" "}
                    {wish.elapsedMinutes >= 1440 // 1440분 = 24시간
                      ? `${Math.floor(wish.elapsedMinutes / 1440)}일 전`
                      : wish.elapsedMinutes >= 60
                      ? `${Math.floor(wish.elapsedMinutes / 60)}시간 전`
                      : `${wish.elapsedMinutes}분 전`}
                  </Typography>
                </CardContent>
                <CardContent
                  sx={{
                    padding: 0,
                    margin: 0,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                  }}
                >
                  <Typography
                    variant="body1"
                    sx={{
                      fontWeight: "bold",
                      minWidth: "65px",
                      textAlign: "center",
                      marginY: "auto",
                      color:
                        wish.status === "SALE"
                          ? "crimson"
                          : wish.status === "SOLD"
                          ? "dimgray"
                          : wish.status === "PROGRESS"
                          ? "orange"
                          : "black", // 기본값
                    }}
                  >
                    {wish.status === "SALE"
                      ? "판매 중"
                      : wish.status === "SOLD"
                      ? "거래 완료"
                      : "거래 중"}
                  </Typography>
                </CardContent>
              </Card>
            </Link>
          </div>
        ))}
      </div>
      <div className="flex justify-around mt-4">
        <Button disabled sx={{ color: "black", fontSize: "1rem" }}>
          이전
        </Button>
        <Button disabled sx={{ color: "black", fontSize: "1rem" }}>
          다음
        </Button>
      </div>
    </>
  );
}
