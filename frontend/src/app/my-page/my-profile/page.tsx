import { Suspense } from "react";
import styles from "@/components/my-page/my-page.module.scss";
import TopBar2 from "@/components/my-page/my-page-profile";

async function getChildren() {
  // await new Promise((resolve) => setTimeout(resolve, 1000));
  // 로딩을 확인하기 위해 임의의 시간(ms) 지연
  return [
    { id: 1, name: "Child 1" },
    { id: 2, name: "Child 2" },
    { id: 3, name: "Child 3" },
    { id: 4, name: "Child 4" },
    { id: 5, name: "Child 5" },
  ];
  // TODO: 데이터 페칭
  // const response = await fetch(API_URL);
  // const json = await response.json();
  // return json;
}

export default async function MyProfileDetail() {
  const children = await getChildren();
  return (
    <div className={styles.container}>
      <TopBar2 />
      <ul className={styles.ul}>
        {children.map((child) => (
          <li key={child.id}>{child.name}</li>
        ))}
      </ul>
    </div>
  );
}
