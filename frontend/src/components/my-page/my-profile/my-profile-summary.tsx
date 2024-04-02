"use client";

import styles from "@/components/my-page/my-detail.module.scss";
import { MyInfo } from "@/types/member";
import Image from "next/image";

interface Props {
  myDetail: MyInfo;
}

export default function MyProfileSummary({ myDetail }: Props) {
  return (
    <div>
      <div className="flex justify-between h-28 mr-2 ml-4">
        <div className={`${styles.nickname} min-w-28 text-pretty`}>
          <h4 className="my-4">{myDetail?.nickname ?? "닉네임 미등록"}</h4>
          <span className="text-slate-400">#{myDetail?.id}</span>
        </div>
        <div className={styles.responsiveImg} style={{ position: "relative" }}>
          <Image
            src={myDetail?.profileImage ?? ""}
            alt="Profile Image"
            fill
            style={{ objectFit: "cover", borderRadius: "50%" }}
          />
        </div>
      </div>
    </div>
  );
}
