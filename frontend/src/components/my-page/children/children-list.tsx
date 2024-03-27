"use client";

import { Card, CardContent, Typography, IconButton } from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { KidsSummary, getKidsSummary } from "@/services/kid";
import { useState, useEffect } from "react";
import Link from "next/link";

export default function ChildrenList() {
  const [children, setChildren] = useState<KidsSummary>();
  const today = new Date();

  useEffect(() => {
    const fetchChildren = async () => {
      const data = await getKidsSummary();
      console.log(data);
      setChildren(data);
    };
    fetchChildren();
  }, []);

  // "birthDate": "2024-03-26" 와 today를 비교하여 생일이 지났는지 예정인지 판단
  const isBirth = (birthDate: string | null) => {
    if (birthDate === null) return "미정";

    const birth = new Date(birthDate);
    if (birth <= today) {
      return "생";
    } else {
      return "예정";
    }
  };

  if (children?.success === false) {
    return (
      <div>
        <h3>정보를 불러오는 데 실패했습니다.</h3>
        <p>{children.error.errorMessage}</p>
      </div>
    );
  }

  return (
    <div>
      <h1>아이 정보</h1>
      {children?.data?.map((child, index) => (
        <Card
          key={index}
          className="my-6 flex justify-between border-yellow-300 rounded-xl
          border-2 p-2"
        >
          <CardContent className="w-48">
            <Typography variant="h5">{child.name ?? '이름'}</Typography>
            <Typography variant="h6">
              {child.birthDate} {isBirth(child.birthDate)}
            </Typography>
          </CardContent>
          <CardContent className="bg-blue-200 rounded-xl max-h-24 max-w-24 aspect-square my-auto flex justify-center min-w-fit">
            <Typography variant="h6" className="flex my-auto">
              {child.gender ?? "미정"}
            </Typography>
          </CardContent>
          <IconButton className="w-16 my-auto aspect-square">
            <ArrowForwardIosIcon />
          </IconButton>
        </Card>
      ))}
      <Card
        className="my-6 flex justify-center border-gray-300 rounded-xl h-32
          border-2 p-4"
      > 
      <Link href={`/kid/birth`} className="flex justify-center my-auto w-1/2 h-full rounded-xl hover:bg-gray-100 transition duration-300 ease-in-out">
        <CardContent className="flex justify-center items-center">
          <Typography variant="h5" className="my-auto">
            아이 추가
          </Typography>
        </CardContent>
        </Link>
      </Card>
    </div>
  );
}
