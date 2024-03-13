import HeaderWithLogo from "@/components/common/headers";
import BabyCard from "@/components/home/babyCard";
import RecommandProductList from "@/components/home/recommandProductList";
import Navigation from "@/components/common/navigation";
import styles from "./home.module.scss";

export default function Home() {
  return (
    <div>
      <Navigation />
      <div className={styles.home}>
        <HeaderWithLogo />
        <BabyCard />
        <RecommandProductList />
      </div>
    </div>
  );
}
