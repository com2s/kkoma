"use client";

import { Card, CardContent, Typography, IconButton } from "@mui/material";
import ChildCareIcon from '@mui/icons-material/ChildCare';
import AddIcon from '@mui/icons-material/Add';
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
    if (birthDate === null) return "(미정)";

    const birth = new Date(birthDate);
    if (birth <= today) {
      return "(생)";
    } else {
      return "(예정)";
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
      {children?.data?.map((child, index) => (
        <Card
          className="my-6 flex justify-between border-yellow-300 rounded-xl
          border-2 min-w-fit w-2/3 mx-auto"
        >
          <Link
            href={`/my-page/children/${child.id}`}
            className="my-auto w-full h-full flex justify-between"
            key={index}
          >
            <CardContent className="min-w-42 p-2">
              <Typography variant="h5" className="mb-2">
                {child.name ?? "이름"}
              </Typography>
              <Typography variant="h6">
                {child.birthDate} {isBirth(child.birthDate)}
              </Typography>
            </CardContent>
            <CardContent className="mr-2 bg-blue-200 rounded-xl max-h-24 max-w-24 aspect-square my-auto flex justify-center min-w-fit p-auto">
              <Typography variant="h6" className="flex my-auto h-full">
                {child.gender === "FEMALE"
                  ? "여자"
                  : child.gender === "MALE"
                  ? "남자"
                  : "미정"}
              </Typography>
            </CardContent>
          </Link>
        </Card>
      ))}
      <Card
        className="my-6 mx-auto flex justify-between border-gray-300 rounded-xl h-24
        border-2 w-2/3 min-w-64 hover:bg-gray-100 transition duration-300 ease-in-out"
      >
        <Link href={`/kid/birth`} className="my-auto w-full h-full flex justify-center">
          <CardContent className="flex justify-center items-center">
            <ChildCareIcon className="h-full w-fit"/>
            <AddIcon className="h-full w-fit p-1"/>
          </CardContent>
        </Link>
      </Card>
    </div>
  );
}
