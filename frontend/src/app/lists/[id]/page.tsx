"use client";

import React, { Suspense } from "react";
import styles from "@/components/lists/lists-id.module.scss";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import TopBar2 from "@/components/lists/lists-detail-bar";
import Slider from "react-slick";
import Image from "next/image";

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

export default function ProductDetail({ params: { id } }: IParams) {
  const settings = {
    centerMode: true,
    autoplay: true,
    autoplaySpeed: 2000,
    dots: true,
    arrows: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
    <div className={styles.container}>
      <TopBar2 />
      <div className="p-20">
        <Slider {...settings} className={styles.carousel}>
          {Array.from({ length: 6 }).map((_, index) => (
            <div key={index} className="w-full h-full flex justify-center items-center px-12">
              <Image
                src="/temp-img.svg"
                alt={`Slide ${index + 1}`}
                width={9000} // 이미지의 너비 설정
                height={9000} // 이미지의 높이 설정
                objectFit="contain"
              />
            </div>
          ))}
        </Slider>
      </div>
    </div>
  );
}

