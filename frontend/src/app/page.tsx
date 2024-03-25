import TopBar from "@/components/common/top-bar";
import BabyCard from "@/components/home/kidCard";
import RecommandProductList from "@/components/home/recommandProductList";
import Navigation from "@/components/common/navigation";
import styles from "./home.module.scss";

export default function Home() {
  return (
    <div>
      <Navigation />
      <div className={styles.home}>
        <TopBar />
        <BabyCard />
        <RecommandProductList />
      </div>
    </div>
  );
}
