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

// 인터페이스
// 매개변수는 받지만 반환값은 없는 함수를 정의합니다.
interface TopBar2Props {
  onFormSubmit: () => void;
}

// 만약 반환값이 있다면 다음과 같이 정의합니다. (예시)
// interface TopBar2Props {
//   onFormSubmit: (param: string) => number;
// }

export default function TopBar2({ onFormSubmit }: TopBar2Props) {
  const router = useRouter();
  return (
    <>
      <div className={styles.header}>
        <Link href="/lists">
          <Button
            // onClick={() => router.back()} // 뒤로 가기 기능을 쓸 경우...
            startIcon={<ArrowBackIosNewIcon />}
            sx={{ color: "black" }}
          ></Button>
        </Link>
        <span className={styles.logo}>페이지 타이틀</span>
        <div className={styles.notifications}>
          <button
            type="submit"
            onClick={onFormSubmit}
            className="text-red-500 font-bold rounded"
          >
            <span>작성</span>
          </button>
        </div>
      </div>
      <div className={styles.headerSpacer}></div>{" "}
      {/* 상단 바 높이만큼의 빈 공간 */}
    </>
  );
}
