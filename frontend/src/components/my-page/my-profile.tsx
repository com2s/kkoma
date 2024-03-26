"use client";

import styles from "@/components/my-page/my-profile.module.scss";
import { getMySummary, mySummary } from "./my-page-ftn";
import Avatar from "@mui/material/Avatar";
import Link from "next/link";
import { useState, useEffect } from "react";

export default function MyProfile() {
  const [summary, setSummary] = useState<mySummary>();

  const getSummary = async () => {
    const mySummary = await getMySummary();
    setSummary(mySummary);
  };

  useEffect(() => {
    getSummary();
  }, []);

  if (summary?.success === false) {
    return <div className={styles.container}>프로필 조회 실패</div>;
  }

  return (
    <div className={styles.container}>
      <Avatar
        src={summary?.data.profileImage}
        alt="Profile Image"
        // sx={{ width: 56, height: 56 }}
        className={`${styles.responsiveImg} mr-4`}
      />
      {/* 프로필사진이 없을 경우? */}
      {/* <Avatar {...stringAvatar('Kent Dodds')} /> */}
      <div className={`${styles.nickname} min-w-32 text-pretty mr-1 `}>
        <h4 className="">{summary?.data.nickname ?? "닉네임 미등록"}</h4>
        <span className="text-slate-400">
          {summary?.data.preferredPlace ?? "주소 미등록"}
        </span>
      </div>
      <Link href="/my-page/my-profile" passHref>
        <span className="text-center font-medium min-w-28 bg-gray-100 flex justify-center items-center py-1 rounded-lg cursor-pointer">
          프로필 보기
        </span>
      </Link>
    </div>
  );
}
