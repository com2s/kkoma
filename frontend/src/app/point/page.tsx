import TopBar3 from "@/components/common/top-bar3";
import MyPoints from "@/components/my-page/my-points";
import styles from "./point.module.scss";

export default function Point() {
  return (
    <div className={styles.point}>
      <TopBar3 />
      <MyPoints />
    </div>
  );
}
