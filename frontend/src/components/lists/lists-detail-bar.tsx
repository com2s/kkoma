"use client";

import { useRouter } from "next/navigation";
import styles from "@/components/common/top-bar2.module.scss";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import FavoriteIcon from "@mui/icons-material/Favorite";
import NavigateBeforeIcon from "@mui/icons-material/NavigateBefore";
import { Dispatch, SetStateAction } from "react";
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

export default function TopBar2(props: {
  liked: boolean;
  setLiked: Dispatch<SetStateAction<boolean>>;
  handleLiked: Function;
}) {
  const router = useRouter();

  return (
    <>
      <div className={styles.header}>
        <NavigateBeforeIcon className="c-text2" onClick={() => router.back()} />
        {props.liked ? (
          <FavoriteIcon
            sx={{ color: "red" }}
            onClick={() => props.handleLiked()}
          />
        ) : (
          <FavoriteBorderIcon
            className="c-text2"
            onClick={() => props.handleLiked()}
          />
        )}
      </div>
      <div className={styles.headerSpacer}></div>
    </>
  );
}
