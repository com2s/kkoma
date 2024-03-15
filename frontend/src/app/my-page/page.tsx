import { Suspense } from "react";
import styles from "@/components/my-page/my-page.module.scss";
import TopBar from "@/components/common/top-bar";
import MyProfile from "@/components/my-page/my-profile";
import MyPoints from "@/components/my-page/my-points";
import MyPageList from "@/components/my-page/my-page-list";

async function getChildren() {
  // await new Promise((resolve) => setTimeout(resolve, 1000));
  // 로딩을 확인하기 위해 임의의 시간(ms) 지연
  return [
    { id: 1, name: "Child 1" },
    { id: 2, name: "Child 2" },
    { id: 3, name: "Child 3" },
    { id: 4, name: "Child 4" },
    { id: 5, name: "Child 5" },
    { id: 6, name: "Child 6" },
    { id: 7, name: "Child 7" },
    { id: 8, name: "Child 8" },
    { id: 9, name: "Child 9" },
    { id: 10, name: "Child 10" },
  ];
  // TODO: 데이터 페칭
  // const response = await fetch(API_URL);
  // const json = await response.json();
  // return json;
}

export default async function MyPage() {
  const children = await getChildren();
  return (
    <div className={styles.container}>
      <TopBar />
      <MyProfile />
      <MyPoints />
      <MyPageList />
      <ul className={styles.ul}>
        {children.map((child) => (
          <li key={child.id}>{child.name}</li>
        ))}
      </ul>
    </div>
  );
}
