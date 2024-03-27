import TopBar2 from "@/components/my-page/children/children-bar";
import MyProfile from "@/components/my-page/my-profile";

import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Children",
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
