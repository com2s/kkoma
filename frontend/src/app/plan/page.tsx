import Navigation from "@/components/common/navigation";
import TopBar from "@/components/common/top-bar";
import styles from "./plan.module.scss";
import { DealList } from "@/components/plan/list";
export default function Plan() {
  return (
    <>
      <TopBar />
      <div className={styles.plan}>
        <Navigation />
        <DealList />
      </div>
    </>
  );
}
