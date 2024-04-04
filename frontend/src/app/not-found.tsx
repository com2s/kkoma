"use client";

import {
  ButtonContainer,
  NormalBtn,
  SubBtn,
} from "@/components/common/buttons";
import Image from "next/image";
import { useRouter } from "next/navigation";

export default function NotFound() {
  const router = useRouter();

  return (
    <>
      <div className="flex flex-col items-center pt-2">
        <Image
          src={"/images/Telescope.png"}
          alt="not-found"
          width={100}
          height={100}
        />
        <h2 className="text-center">요청하신 페이지를 찾을 수 없어요</h2>
        <h4 className="c-text3">주소가 맞는지 확인해주세요</h4>
      </div>
      <div>
        <ButtonContainer>
          <SubBtn next={() => router.back()}>이전 페이지</SubBtn>
          <NormalBtn next={"/"}>홈화면</NormalBtn>
        </ButtonContainer>
      </div>
    </>
  );
}
