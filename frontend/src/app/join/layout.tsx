"use client";

import TopBar3 from "@/components/common/top-bar3";
import styles from "./join.module.scss";
import { RecoilRoot } from "recoil";

export default function JoinLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <RecoilRoot>
      <div className={styles.join}>
        <TopBar3 />
        {children}
      </div>
    </RecoilRoot>
  );
}
