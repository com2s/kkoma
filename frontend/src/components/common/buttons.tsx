"use client";

import { useRouter } from "next/navigation";
import styles from "./buttons.module.scss";
import { AppRouterInstance } from "next/dist/shared/lib/app-router-context.shared-runtime";

interface btnProps {
  children: string;
  next: string | Function;
  disabled?: boolean;
  display?: boolean;
}

export function ButtonContainer({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      <div className={styles.container}>
        <div className={styles.child}>{children}</div>
      </div>
      <div className={styles.spacer}></div>
    </>
  );
}

const onclickBtn = (router: AppRouterInstance, next: string | Function) => {
  if (typeof next === "string") {
    router.push(next);
  } else if (typeof next === "function") {
    next();
  }
};

export function WideBtn({ children, next }: btnProps) {
  const router = useRouter();

  return (
    <button
      className={`${styles.btn} ${styles.wide}`}
      onClick={() => onclickBtn(router, next)}
    >
      <div>{children}</div>
    </button>
  );
}

export function NormalBtn({ children, next, disabled }: btnProps) {
  const router = useRouter();

  return (
    <button
      className={`${styles.btn} ${styles.normal}`}
      onClick={() => onclickBtn(router, next)}
      disabled={disabled}
    >
      <div>{children}</div>
    </button>
  );
}

export function SubBtn({ children, next, display = true }: btnProps) {
  const router = useRouter();

  return (
    <button
      className={`${styles.btn} ${styles.normal} ${styles.gray}`}
      style={display ? {} : { display: "none" }}
      onClick={() => onclickBtn(router, next)}
    >
      <div>{children}</div>
    </button>
  );
}

export function SmallBtn({ children, next, display = true }: btnProps) {
  const router = useRouter();

  return (
    <button
      className={`${styles.btn} ${styles.small}`}
      style={display ? {} : { display: "none" }}
      onClick={() => onclickBtn(router, next)}
    >
      <div>{children}</div>
    </button>
  );
}
