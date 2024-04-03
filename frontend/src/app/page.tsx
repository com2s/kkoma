"use client";

import TopBar from "@/components/common/top-bar";
import RecommandProductList from "@/components/home/recommandProductList";
import Navigation from "@/components/common/navigation";
import styles from "./home.module.scss";
import { useEffect, useState } from "react";
import { getKidsSummary } from "@/services/kid";
import KidCardList from "@/components/home/kidCardList";
import { NoKids } from "@/components/home/noKids";
import { KidSummary } from "@/types/kid";
import { isLogin } from "@/utils/getAccessToken";
import { useRouter } from "next/navigation";
import { getRecommendAPI } from "@/services/product";
import { ProductSm } from "@/types/product";

export default function Home() {
  const [kidList, setKidList] = useState<Array<KidSummary>>([]);
  const [selectedName, setSelectedName] = useState<string | null>(null);
  const [recommend, setRecommend] = useState<Array<ProductSm>>([]);
  const router = useRouter();

  const getKidList = async () => {
    const res = await getKidsSummary();
    setKidList(res.data);
  };

  const getRecommendProducts = async () => {
    const res = await getRecommendAPI();
    setRecommend(res);
  };

  const canAccess = async () => {
    if (!(await isLogin())) {
      router.replace("/welcome");
    }
  };

  useEffect(() => {
    canAccess();
    getKidList();
  }, []);

  useEffect(() => {
    if (kidList && kidList.length > 0) {
      setSelectedName(kidList[0].name);
      getRecommendProducts();
    }
  }, [kidList]);

  return (
    <div>
      <Navigation />
      <div className={styles.home}>
        <TopBar />
        {kidList && kidList.length > 0 ? (
          <>
            <KidCardList kidList={kidList} setSelectedName={setSelectedName} />
            {recommend && recommend.length > 0 ? (
              <RecommandProductList name={selectedName} products={recommend} />
            ) : (
              <></>
            )}
          </>
        ) : (
          <NoKids />
        )}
      </div>
    </div>
  );
}
