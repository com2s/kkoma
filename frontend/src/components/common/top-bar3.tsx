"use client";

import NavigateBeforeIcon from "@mui/icons-material/NavigateBefore";
import styles from "./top-bar3.module.scss";
import { useRouter } from "next/navigation";

export default function TopBar3() {
  const router = useRouter();

  return (
    <>
      <div className={styles.header}>
        <NavigateBeforeIcon width="9" height="15" onClick={() => router.back()} />
      </div>
      <div className={styles.headerSpacer}></div>
    </>
  );
}
