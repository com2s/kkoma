import type { Metadata } from "next";

import TopBar2 from "@/components/my-page/children/children-bar";
import MyProfile from "@/components/my-page/my-profile";
import ChildList from "@/components/my-page/children/children-list";

export const metadata: Metadata = {
  title: "Children",
  description: "My Children Page",
};

export default function ChildrenPage() {
  return (
    <div>
      <TopBar2 />
      <MyProfile />
      <ChildList />
    </div>
  );
}
