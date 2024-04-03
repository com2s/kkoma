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
import { ButtonContainer, NormalBtn, SubBtn } from "@/components/common/buttons";
import { StaticMap } from "@/components/common/staticmap";
import { getMyPoints } from "@/components/common/common-ftn";

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

  const handleRequest = async (id: string) => {
    if (product?.data.status === "SALE") {
      const res = await getMyPoints();
      const myPoints = await res.data.balance;
      if (product && Number(myPoints) > product?.data.price) {
        router.push(`/lists/${id}/request`);
      } else {
        alert("포인트가 부족합니다. 먼저 포인트를 충전해주세요.");
        router.push("/point/charge");
      }
    } else {
      alert("이미 거래중이거나 거래 완료된 상품입니다.");
    }
  };

  const settings = {
    // centerMode: true,
    autoplay: product && product.data.productImages.length > 1 ? true : false,
    // 이동부터 다음 이동까지의 시간
    autoplaySpeed: 5000,
    dots: true,
    arrows: false,
    infinite: product && product.data.productImages.length > 1 ? true : false,
    // 이동하는데 걸리는 시간
    speed: 750,
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
        <div className="flex items-start justify-between gap-2">
          <div className="flex items-start gap-1 min-w-fit">
            <ShareLocationIcon className="c-text1" />
            <div className={`min-w-fit text-body2 ${styles.dealPlace}`}>거래 장소</div>
          </div>
          <div className="text-body2 c-text2">{product?.data.dealPlace}</div>
        </div>
      </div>
      {product?.data.x && product.data.y && (
        <StaticMap lat={product?.data.x} lng={product?.data.y} />
      )}
      {myId && myId === product?.data.memberSummary.memberId.toString() ? (
        <ButtonContainer>
          <NormalBtn next={`/my-trade/${id}`}>요청 보기</NormalBtn>
          <SubBtn next={`/chat/${product?.data.chatRoomId}`}>채팅 확인</SubBtn>
        </ButtonContainer>
      ) : (
        <ButtonContainer>
          <NormalBtn next={() => handleRequest(id)}>거래 요청</NormalBtn>
          <SubBtn next={`/chat/${product?.data.chatRoomId}`}>채팅 문의</SubBtn>
        </ButtonContainer>
      )}
    </>
  );
}
