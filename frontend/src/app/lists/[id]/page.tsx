"use client";

import React, { Suspense } from "react";
import styles from "@/components/lists/lists-id.module.scss";
import { getImages } from "@/components/lists/lists-ftn";

import TopBar2 from "@/components/lists/lists-detail-bar";
import Profile from "@/components/lists/lists-detail-profile";
import Content from "@/components/lists/lists-detail-content";
import {
  ButtonContainer,
  SubBtn,
  NormalBtn,
} from "@/components/common/buttons";
import Map from "@/components/common/map";
import Slider from "react-slick";
import Image from "next/image";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Button,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import LocationOnOutlinedIcon from "@mui/icons-material/LocationOnOutlined";
import Link from "next/link";

interface IParams {
  params: { id: string };
}
// 아래에서 IParams 로 쓰이는데, 이것은 interface로, 타입을 정의하는 것이다.
// export async function generateMetadata({ params: { id } }: IParams) {
//   // const movie = await getMovie(id);

//   return {
//     //   title: movie.title,
//     title: `상품 ID = ${id}`,
//     description: "Product detail page",
//   };
// }

export default async function ProductDetail({ params: { id } }: IParams) {
  const images = await getImages(id);

  const settings = {
    centerMode: true,
    autoplay: true,
    // 이동부터 다음 이동까지의 시간
    autoplaySpeed: 2000,
    dots: true,
    arrows: false,
    infinite: true,
    // 이동하는데 걸리는 시간
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
    <div className={styles.container}>
      <TopBar2 />
      <div className={styles.carousel}>
        <Slider {...settings}>
          {images.map((img, index) => (
            <div key={index} className={styles.image}>
              <Image
                src={img.img}
                alt={`Slide ${index + 1}: ${img.title}`}
                priority
                width={300} // Adjust as needed
                height={300} // Adjust as needed
                style={{ height: "280px" }}
              />
            </div>
          ))}
        </Slider>
      </div>
      <Profile id={id} />
      <Content id={id} />
      <Accordion className="mt-4">
        {/* 거래 장소 선택(필수항목) */}
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1-content"
          id="panel1-header"
        >
          <LocationOnOutlinedIcon className="mr-4" />
          <span>거래 장소</span>
        </AccordionSummary>
        <AccordionDetails>
          <span>여기서 주소 받아오기</span>
          <Map />
        </AccordionDetails>
      </Accordion>
      <div className="flex gap-8 py-4 px-6">
        {/* <SubBtn next={"/"}>홈 화면</SubBtn>
        <NormalBtn next={"/"}>아이 정보 입력</NormalBtn> */}
        <Link href={`/lists/${id}/request`} className="w-full">
          <button className={`${styles.btn} ${styles.normal}`}>
            거래 요청
          </button>
        </Link>
        <Link href={`/lists/${id}/chat`} className="w-full">
          <button className={`${styles.btn} ${styles.normal} ${styles.gray}`}>
            채팅 문의
          </button>
        </Link>
      </div>
    </div>
  );
}
