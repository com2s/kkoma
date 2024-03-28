import { Suspense } from "react";
import styles from "@/components/my-page/my-page.module.scss";
import TopBar from "@/components/common/top-bar";
import MyProfile from "@/components/my-page/my-profile";
import MyPoints from "@/components/my-page/my-points";
import MyPageList from "@/components/my-page/my-page-list";
import Navigation from "@/components/common/navigation";

export default async function MyPage() {
  return (
    <div className={styles.container}>
      <TopBar />
      <MyProfile />
      <MyPoints />
      <MyPageList />
      <Navigation />
    </div>
  );
}
