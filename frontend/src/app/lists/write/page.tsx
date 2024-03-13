import styles from "@/components/lists/lists-write.module.scss";
import TopBar2 from "@/components/lists/lists-write-bar";


export async function generateMetadata() {
  return {
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
