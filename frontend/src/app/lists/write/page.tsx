import styles from "@/components/lists/lists-write.module.scss";
import TopBar2 from "@/components/lists/lists-write-bar";

// interface IParams {
//   params: { id: string };
// }
// 아래에서 IParams 로 쓰이는데, 이것은 interface로, 타입을 정의하는 것이다.
// export async function generateMetadata({ params: { id } }: IParams) {
export async function generateMetadata() {
  // const movie = await getMovie(id);
  return {
    //   title: movie.title,
    title: "Write a post",
    description: "Writing a post",
  };
}

export default async function ProductDetail() {
  return (
    <div className={styles.container}>
        <TopBar2 />
      <h1>Writing page</h1>
      <p>Write here</p>
    </div>
  );
}
