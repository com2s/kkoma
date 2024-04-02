"use client";

import Image from "next/image";
import { useState, forwardRef, Ref, ReactElement } from "react";
import styles from "./wish-locate.module.scss";

import Map from "@/components/common/map";
import Title from "@/components/common/title";
import TopBar3 from "@/components/common/top-bar3";
import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import { updatePreferredPlaceAPI } from "@/services/member";

export default function WishLocate() {
  const [location, setLocation] = useState("");
  const [regionCode, setRegionCode] = useState("");

  const handleUpdate = async () => {
    await updatePreferredPlaceAPI(regionCode);
  };

  return (
    <div className={styles.locate}>
      <TopBar3 />
      <div className="flex justify-end pb-2 w-full "></div>
      <Title title="선호하는 거래 장소를 선택해주세요" subtitle="시군구 단위로 선택할 수 있어요" />
      <Image src={"/images/Pin.png"} alt="pin" width={100} height={100} />
      {location ? (
        <div className="text-caption !text-white bg-gray-500 py-1 px-2 rounded-xl w-fit	text-center">
          {location}
        </div>
      ) : (
        <></>
      )}
      <Map setRegion={setLocation} setRegionCode={setRegionCode} />
      <div className="w-full">
        <ButtonContainer>
          <NormalBtn next={handleUpdate}>선택</NormalBtn>
        </ButtonContainer>
      </div>
    </div>
  );
}
