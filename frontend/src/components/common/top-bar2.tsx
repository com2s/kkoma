"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import styles from "@/components/common/top-bar2.module.scss";
import Button from "@mui/material/Button"; // Material-UI 버튼 사용
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
    <div className={`${styles.header} bg-white text-black h-12 flex items-center justify-between px-4 fixed top-0 left-0 right-0`}>
      <div className="flex justify-start basis-1/3">
        <Button
          onClick={() => router.back()}
          startIcon={<ArrowBackIosNewIcon />}
          sx={{ color: "black" }}
        >
        </Button>
      </div>
      <span className="basis-1/3 text-center font-semibold">페이지 타이틀</span>
      <div className="flex justify-end basis-1/3">
        <button className="text-red-500 font-bold py-2 px-4 rounded">
          작성
        </button>
      </div>
    </div>
    <div className="h-12"></div> {/* 상단 바 높이만큼의 빈 공간 */}
    </>
  );
}
