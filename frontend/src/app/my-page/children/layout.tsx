import TopBar2 from "@/components/my-page/children/children-bar";
import MyProfile from "@/components/my-page/my-profile";

import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "아이 정보",
  description: "My Children Page",
};

export default function MyPageLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div>
      <TopBar2 />
      <MyProfile />
      {children}
    </div>
  );
}
