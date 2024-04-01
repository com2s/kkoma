"use client";

import React, {useState, useEffect } from "react";
import styles from "@/components/my-page/my-detail.module.scss";
import { getMyInfo } from "@/components/my-page/my-page-ftn";
import { MyInfo } from "@/types/member";

import Avatar from "@mui/material/Avatar";

export default function MyProfileSummary() {
  const [myDetail, setMyDetail] = useState<MyInfo>();
  useEffect(() => {
    const fetchMyDetail = async () => {
      const response = await getMyInfo();
      setMyDetail(response);
    }
    fetchMyDetail();
  }, []);


  return (
    <div className="px-4">
      <div className="flex justify-between h-28">
        <div className={`${styles.nickname} min-w-28 text-pretty mr-1 ml-4`}>
          <h4 className="my-4">
            {myDetail?.data?.nickname ?? "닉네임 미등록"}
          </h4>
          <span className="text-slate-400">#{myDetail?.data?.id}</span>
        </div>
        <Avatar
          src={myDetail?.data?.profileImage ?? ""}
          alt="Profile Image"
          // sx={{ width: 56, height: 56 }}
          className={`${styles.responsiveImg} mx-4`}
        />
      </div>
    </div>
  );
}
