"use client";

import TopBar3 from "@/components/common/top-bar3";
import styles from "./baby.module.scss";
import { RecoilRoot } from "recoil";

export default function BabyLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <RecoilRoot>
      <div className={styles.baby}>
        <TopBar3 />
        {children}
      </div>
    </RecoilRoot>
  );
}
