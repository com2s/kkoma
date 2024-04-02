"use client";

import styles from "@/components/my-page/my-profile.module.scss";
import { getMySummary } from "./my-page-ftn";
import { MemberSummary } from "@/types/member";
import Link from "next/link";
import { useState, useEffect } from "react";
import Image from "next/image";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { useRouter } from "next/navigation";

export default function MyProfile() {
  const router = useRouter();

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
    <div className={styles.container} onClick={() => router.push(`/profile/${summary?.memberId}`)}>
      {success === false && <h2>프로필 조회 실패</h2>}
      {success === true && summary && (
        <>
          <div className={styles.responsiveImg} style={{ position: "relative" }}>
            <Image
              src={summary?.profileImage}
              alt="Profile Image"
              fill
              style={{ objectFit: "cover", borderRadius: "50%" }}
            />
          </div>
          <div className={`${styles.nickname} min-w-32 text-pretty mr-1 `}>
            <h4 className="">{summary?.nickname ?? "닉네임 미등록"}</h4>
            <span className="text-slate-400">{summary?.preferredPlace ?? "주소 미등록"}</span>
          </div>
          <ArrowForwardIosIcon className="text-center" />
        </>
      )}
    </div>
  );
}
