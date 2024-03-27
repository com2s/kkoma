'use client';

import { RecoilRoot } from "recoil";

export default function MyTradeDealLayout({
    children,
  }: {
    children: React.ReactNode;
  }) {
    return <RecoilRoot>{children}</RecoilRoot>;
  }