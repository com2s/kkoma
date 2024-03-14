import * as React from "react";
import Link from "next/link";
import AppBar from "@mui/material/AppBar";
// AppBar 컴포넌트는 상단에 고정된 헤더를 만들 때 사용합니다.
// 그래서 이 컴포넌트에 쓰면 헤더가 중복이 되어 에러가 발생합니다.
// 사용 예시
{/* 
    <div className="fixed top-0 left-0 w-full z-50">
        <TopBar />
    </div>
    <div className="px-4 mt-14">
        ...본문...
    </div> 
*/}

export default function TopBar() {
  return (
    <div className="bg-gray-800 text-white h-12 flex justify-between items-center px-4 top-0 left-0 right-0">
      <Link href="/" passHref>
        <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
          KKOMA
        </button>
      </Link>
      <span>상단 바 영역</span>
      <div className="flex">
        <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
          알림
        </button>
      </div>
    </div>
  );
}
