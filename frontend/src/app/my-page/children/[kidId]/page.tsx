"use client";

import { editKidDetail, getKidDetail, KidInfo } from "@/services/kid";
import React, { useEffect, useState } from "react";
import TextField from "@mui/material/TextField";

interface IParams {
  params: { kidId: string };
}

export default async function ChildDetailPage({ params: { kidId } }: IParams) {
  const [kidDetail, setKidDetail] = useState<KidInfo | null>(null);
  const [success, setSuccess] = useState(true);

  const fetchData = async () => {
    const res = await getKidDetail(parseInt(kidId));
    console.log(res);
    setKidDetail(res.data);
    setSuccess(res.success);
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (success === false) {
    return (
      <div className="border-t-yellow-300 border-t-2">
        <h1>아이 정보를 불러오는데 실패했습니다.</h1>
        <div className="h-64 flex-row">
          <TextField
            id="standard-basic"
            label="이름"
            variant="standard"
            sx={{
                margin: "1rem 0.5rem",
                width: "80%",
            }}
          />
          <TextField
            id="standard-basic"
            label="성별"
            variant="standard"
            sx={{
                margin: "1rem 0.5rem",
                width: "80%",
            }}
          />
          <TextField
            id="standard-basic"
            label="생년월일"
            variant="standard"
            sx={{
                margin: "1rem 0.5rem",
                width: "85%",
            }}
          />
        </div>
      </div>
    );
  }

  return (
    <div className="border-t-yellow-300">
      <h1>아이 정보 수정 가능 {kidId}</h1>
    </div>
  );
}
