"use client";

import { useRouter } from "next/navigation";
import styles from "@/components/common/top-bar2.module.scss";
import Button from "@mui/material/Button"; // Material-UI 버튼 사용
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew"; // Material-UI 뒤로 가기 아이콘

interface Props {
  title: string;
}

export default function TopBar2({ title }: Props) {
  const router = useRouter();
  return (
    <>
      <div className={styles.header}>
        <Button
          onClick={() => router.back()}
          startIcon={<ArrowBackIosNewIcon />}
          sx={{ color: "black", height: "32px" }}
        ></Button>
        <span className={styles.logo}>{title}</span>
        <div className={styles.notifications}>
          <Button disabled>{/* <FavoriteBorderIcon /> */}</Button>
        </div>
      </div>
      <div className={styles.headerSpacer}></div> {/* 상단 바 높이만큼의 빈 공간 */}
    </>
  );
}
