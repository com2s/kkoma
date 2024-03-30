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

export default function Home() {
  const [kidList, setKidList] = useState<Array<KidSummary>>([]);
  const [selectedName, setSelectedName] = useState<string | null>(null);

  const getKidList = async () => {
    const res = await getKidsSummary();
    setKidList(res.data);
  };

  useEffect(() => {
    getKidList();
  }, []);

  useEffect(() => {
    if (kidList && kidList.length > 0) setSelectedName(kidList[0].name);
  }, [kidList]);

  return (
    <div>
      <Navigation />
      <div className={styles.home}>
        <TopBar />
        {kidList && kidList.length > 0 ? (
          <>
            <KidCardList kidList={kidList} setSelectedName={setSelectedName} />
            <RecommandProductList name={selectedName} />
          </>
        ) : (
          <NoKids />
        )}
      </div>
    </div>
  );
}
