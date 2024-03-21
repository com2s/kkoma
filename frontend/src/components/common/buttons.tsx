"use client";

import { useRouter } from "next/navigation";
import styles from "./buttons.module.scss";

interface btnProps {
  children: String;
  next: String | Function;
  disabled?: boolean;
}

export function ButtonContainer({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <div className={styles.container}>{children}</div>;
}

export function WideBtn({ children, next }: btnProps) {
  const router = useRouter();

  return (
    <button className={`${styles.btn} ${styles.wide}`} onClick={() => router.push(`${next}`)}>
      <div>{children}</div>
    </button>
  );
}

export function NormalBtn({ children, next, disabled }: btnProps) {
  const router = useRouter();

  const onclickBtn = () => {
    if (typeof next === "string") {
      router.push(`${next}`);
    } else if (typeof next === "function") {
      next();
    }
  };

  return (
    <button className={`${styles.btn} ${styles.normal}`} onClick={onclickBtn} disabled={disabled}>
      <div>{children}</div>
    </button>
  );
}

export function SubBtn({ children, next }: btnProps) {
  const router = useRouter();

  return (
    <button
      className={`${styles.btn} ${styles.normal} ${styles.gray}`}
      onClick={() => router.push(`${next}`)}
    >
      <div>{children}</div>
    </button>
  );
}
