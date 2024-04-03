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
import { getWishProductAPI, getViewProductAPI, getRecommendAPI } from "@/services/product";
import { ProductSm } from "@/types/product";
import HourlyProductList from "@/components/home/hourlyProductList";
import { NoContents } from "@/components/common/no-contents";
import Image from "next/image";
import { SmallBtn } from "@/components/common/buttons";

export default function Home() {
  const [kidList, setKidList] = useState<Array<KidSummary>>([]);
  const [selectedName, setSelectedName] = useState<string | null>(null);
  const [recommend, setRecommend] = useState<Array<ProductSm>>([]);
  const [bestWishProducts, setBestWishProducts] = useState<Array<ProductSm>>([]);
  const [bestViewProducts, setBestViewProducts] = useState<Array<ProductSm>>([]);
  const router = useRouter();

  const getKidList = async () => {
    const res = await getKidsSummary();
    setKidList(res.data);
  };

  const getRecommendProducts = async () => {
    const res = await getRecommendAPI();
    setRecommend(res);
  };

  const getWishProducts = async () => {
    const res = await getWishProductAPI();
    setBestWishProducts(res);
  };

  const getViewProducts = async () => {
    const res = await getViewProductAPI();
    setBestViewProducts(res);
  };

  const canAccess = async () => {
    if (!(await isLogin())) {
      router.replace("/welcome");
    }
  };

  useEffect(() => {
    canAccess();
    getKidList();
    getWishProducts();
    getViewProducts();
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
        <NoContents>
          <h4 className="c-text3">거래 일정을 확인해볼까요?</h4>
          <Image src="/images/calendar1.svg" alt="kid" width={100} height={100} />
          <SmallBtn next={"/plan"}>확인하러가기</SmallBtn>
        </NoContents>
        {bestWishProducts && bestWishProducts.length > 0 ? (
          <HourlyProductList
            title={"가지고싶다 이 상품"}
            subtitle={"최근 1시간동안 찜이 많았던 상품 보여드릴게요"}
            products={bestWishProducts}
          />
        ) : (
          <></>
        )}
        {bestViewProducts && bestViewProducts.length > 0 ? (
          <HourlyProductList
            title={"자꾸자꾸 눈이 가"}
            subtitle={"최근 1시간동안 조회수가 많았던 상품 보여드릴게요"}
            products={bestViewProducts}
          />
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}
