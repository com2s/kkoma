"use client";

import TopBar3 from "@/components/common/top-bar3";
import MyPoints from "@/components/my-page/my-points";
import styles from "./point.module.scss";
import { useEffect, useState } from "react";
import { getPointHistoryAPI } from "@/services/point";

export default function Point() {
  const [history, setHistory] = useState();

  const fetchData = async () => {
    const res = await getPointHistoryAPI();
    setHistory(res);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className={styles.point}>
      <TopBar3 />
      <MyPoints />
      <div></div>
    </div>
  );
}
