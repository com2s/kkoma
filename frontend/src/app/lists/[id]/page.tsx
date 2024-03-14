import { Suspense } from "react";
import styles from "@/components/lists/lists-id.module.scss";
import TopBar2 from "@/components/lists/lists-detail-bar";

interface IParams {
  params: { id: string };
}
// 아래에서 IParams 로 쓰이는데, 이것은 interface로, 타입을 정의하는 것이다.
export async function generateMetadata({ params: { id } }: IParams) {
  // const movie = await getMovie(id);
  return {
    //   title: movie.title,
    title: id,
    description: "Product detail page",
  };
}

export default async function ProductDetail({ params: { id } }: IParams) {
  return (
    <div className={styles.container}>
        <TopBar2 />
      <h1>Product Detail</h1>
      <p>Product detail page</p>
      <span>Product id : {id}</span>
    </div>
  );
}
