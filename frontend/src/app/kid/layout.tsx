"use client";

import TopBar3 from "@/components/common/top-bar3";
import styles from "./kid.module.scss";
import { RecoilRoot } from "recoil";

export default function KidLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <RecoilRoot>
      <div className={styles.kid}>
        <TopBar3 />
        {children}
      </div>
    </RecoilRoot>
  );
}
