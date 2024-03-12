import TopBar from "@/components/common/top-bar";
import BabyCard from "@/components/home/babyCard";
import RecommandProductList from "@/components/home/recommandProductList";
import styles from "./home.module.scss";

export default function Home() {
  return (
    <div className={styles.home}>
      <TopBar />
      <BabyCard />
      <RecommandProductList />
    </div>
  );
}
