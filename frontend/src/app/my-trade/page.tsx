import { Suspense } from "react";
import TopBar from "@/components/common/top-bar";
import styles from "@/components/my-page/my-page.module.scss";

export default function MyTradePage() {
  return (
    <div>
      <div className="fixed top-0 left-0 w-full z-50">
        <TopBar />
      </div>
      <div className="px-4 mt-14">
        <h1>My Trade Page</h1>
      </div>
    </div>
  );
}
