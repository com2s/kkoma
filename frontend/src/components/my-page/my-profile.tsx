"use client";

import styles from "@/components/my-page/my-profile.module.scss";
import { getMySummary } from "./my-page-ftn";
import { MemberSummary } from "@/types/member";
import Avatar from "@mui/material/Avatar";
import Link from "next/link";
import { useState, useEffect } from "react";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";

export default function MyProfile() {
  const [summary, setSummary] = useState<MemberSummary>();
  const [success, setSuccess] = useState(true);

  const getSummary = async () => {
    const mySummary = await getMySummary();
    if (mySummary.success === false) {
      setSuccess(false);
    }
    setSummary(mySummary.data);
  };

  useEffect(() => {
    getSummary();
  }, []);

  return (
    <div className={styles.container}>
      {success === false && <h2>프로필 조회 실패</h2>}
      {success === true && summary && (
        <>
          <Avatar
            src={summary?.profileImage}
            alt="Profile Image"
            // sx={{ width: 56, height: 56 }}
            className={`${styles.responsiveImg} mr-4`}
          />
          {/* 프로필사진이 없을 경우? */}
          {/* <Avatar {...stringAvatar('Kent Dodds')} /> */}
          <div className={`${styles.nickname} min-w-32 text-pretty mr-1 `}>
            <h4 className="">{summary?.nickname ?? "닉네임 미등록"}</h4>
            <span className="text-slate-400">
              {summary?.preferredPlace ?? "주소 미등록"}
            </span>
          </div>
          <Link
            href={`/my-page/my-profile`}
            passHref
            className="w-1/6 min-w-10 max-w-14 flex justify-center"
          >
            <ArrowForwardIosIcon className="text-center w-full h-auto bg-gray-200 flex justify-center items-center py-3 rounded-lg cursor-pointer aspect-square" />
          </Link>
        </>
      )}
    </div>
  );
}
