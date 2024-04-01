"use client";

import React, { Suspense, useEffect, useState } from "react";
import styles from "@/components/lists/lists-id.module.scss";
import { getProductDetail } from "@/components/lists/lists-ftn";

// 인터페이스
import { DetailParams } from "@/types/product";

import TopBar2 from "@/components/lists/lists-detail-bar";
import Profile from "@/components/lists/lists-detail-profile";
import Content from "@/components/lists/lists-detail-content";
import LocalStorage from "@/utils/localStorage";
import { getIsBuyable } from "@/components/lists/lists-ftn";

import Map from "@/components/common/map";
import Slider from "react-slick";
import Image from "next/image";
import { useRouter } from "next/navigation";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import Link from "next/link";
import ShareLocationIcon from "@mui/icons-material/ShareLocation";
import { sendUnWishAPI, sendWishAPI } from "@/services/wish";
import {
  ButtonContainer,
  NormalBtn,
  SubBtn,
} from "@/components/common/buttons";

interface IParams {
  params: { id: string };
}

export default function ProductDetail({ params: { id } }: IParams) {
  const [product, setProduct] = useState<DetailParams | null>(null);
  const [myId, setMyId] = useState<string | null>(null);
  const [liked, setLiked] = useState(false);

  const router = useRouter();

  const fetchData = async () => {
    setMyId(LocalStorage.getItem("memberId"));
    const res = await getProductDetail(id);
    setProduct(await res);
    setLiked(await res.data.wish);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleLiked = async () => {
    if (liked) {
      await sendUnWishAPI(id);
      setLiked(false);
    } else {
      await sendWishAPI(id);
      setLiked(true);
    }
  };

  const handleRequest = async (id:string) => {
    const isBuyable = await getIsBuyable(id);
    if (isBuyable.success) {
      router.push(`/lists/${id}/request`);
    } else {
      alert(isBuyable.error.errorMessage);
    }
  }

  const handleNull = () => {
    return null;
  }


  const settings = {
    // centerMode: true,
    autoplay: product && product.data.productImages.length > 1 ? true : false,
    // 이동부터 다음 이동까지의 시간
    autoplaySpeed: 2000,
    dots: true,
    arrows: false,
    infinite: product && product.data.productImages.length > 1 ? true : false,
    // 이동하는데 걸리는 시간
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
    <>
      <div className={styles.container}>
        <TopBar2 liked={liked} setLiked={setLiked} handleLiked={handleLiked} />
        <div className={styles.carousel}>
          <Slider {...settings}>
            {product?.data.productImages.map((img, index) => (
              <div key={index} className={styles.image}>
                <Image
                  src={img ?? "/temp-img.svg"} // Route of the image file
                  alt={`Slide ${index + 1}`}
                  priority
                  width={250} // Adjust as needed
                  height={250} // Adjust as needed
                  style={{ margin: "0 auto" }}
                />
              </div>
            ))}
          </Slider>
        </div>
        <Profile propsId={id} memberSummary={product?.data.memberSummary} />
        <Content propsId={id} product={product?.data} />
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-1">
            <ShareLocationIcon className="c-text1" />
            <div className={`min-w-fit text-body2 ${styles.dealPlace}`}>
              거래 장소
            </div>
          </div>
          <div className="text-body2 c-text2">서울시 강남구 테헤란로 10</div>
        </div>
        {/* <div className="text-caption c-text2">{product?.data.dealPlace}</div> */}
        {/* <Map /> */}
      </div>
      {myId && myId === product?.data.memberSummary.memberId.toString() ? (
        <ButtonContainer>
          <NormalBtn next={`/my-trade/${id}`}>요청 보기</NormalBtn>
          <SubBtn next={`/lists/${id}/edit`}>상품 수정</SubBtn>
        </ButtonContainer>
      ) : (
        <ButtonContainer>
          <div onClick={() => handleRequest(id)} className="w-full">
          <NormalBtn next={handleNull}>거래 요청</NormalBtn>
          </div>
          <SubBtn next={`/lists/${id}/chat`}>채팅 문의</SubBtn>
        </ButtonContainer>
      )}
    </>
  );
}
