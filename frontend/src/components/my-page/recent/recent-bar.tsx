"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import styles from "@/components/common/top-bar2.module.scss";
import Button from "@mui/material/Button"; // Material-UI 버튼 사용
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew"; // Material-UI 뒤로 가기 아이콘

// 차후 각 페이지 별로 상단에 고정된 헤더를 만들기 위한 템플릿으로 사용합니다.
// 사용 예시
{
  /* 
    <div className="fixed top-0 left-0 w-full z-50">
        <TopBar2 />
    </div>
    <div className="px-4 mt-14">
        ...본문...
    </div> 
*/
}
// import AppBar from "@mui/material/AppBar";
// AppBar 컴포넌트는 상단에 고정된 헤더를 만들 때 사용합니다.
// 그래서 이 컴포넌트에 쓰면 헤더가 중복이 되어 에러가 발생합니다.

export default function TopBar2() {
  const router = useRouter();
  return (
    <>
      <div className={styles.header}>
        <Button
          onClick={() => router.back()}
          startIcon={<ArrowBackIosNewIcon />}
          sx={{ color: "black", height: "32px" }}
        ></Button>
        <span className={styles.logo}>최근에 본 게시물</span>
        <div className={styles.notifications}>
          <Button disabled>
          {" "}
          </Button>
        </div>
      </div>
      <div className={styles.headerSpacer}></div>
      {/* 상단 바 높이만큼의 빈 공간 */}
    </>
  );
}
