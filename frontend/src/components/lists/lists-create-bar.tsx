"use client";

import { useRouter } from "next/navigation";
import styles from "@/components/common/top-bar2.module.scss";
import NavigateBefore from "@mui/icons-material/NavigateBefore";

export default function TopBar2() {
  const router = useRouter();
  return (
    <>
      <div className={styles.header}>
        <NavigateBefore className="c-text2" onClick={() => router.back()} />
        <span className={styles.logo}>판매글 작성</span>
        <button type="submit" className="c-main font-bold rounded">
          작성
        </button>
      </div>
      <div className={styles.headerSpacer}></div>
    </>
  );
}
