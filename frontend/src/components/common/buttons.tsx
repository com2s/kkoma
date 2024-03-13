"use client";

import { useRouter } from "next/navigation";
import styles from "./buttons.module.scss";

interface btnProps {
  children: String;
  next: String;
}

export function WideBtn({ children, next }: btnProps) {
  const router = useRouter();

  return (
    <div className={styles.container}>
      <button className={`${styles.btn} ${styles.wide}`} onClick={() => router.push(`${next}`)}>
        <div>{children}</div>
      </button>
    </div>
  );
}
