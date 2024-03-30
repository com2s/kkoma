"use client";

import {
  ButtonContainer,
  NormalBtn,
  SubBtn,
} from "@/components/common/buttons";
import Image from "next/image";
import { useRouter } from "next/navigation";

export default function ErrorPage() {
  const router = useRouter();

  return (
    <div className="flex flex-col items-center pt-2">
      <Image
        src={"/images/Telescope.png"}
        alt="not-found"
        width={100}
        height={100}
      />
      <h2 className="text-center">죄송합니다. 잠시 후 시도해주세요</h2>
      <h4 className="c-text3">서버와의 연결이 원활하지 않아요</h4>
      <ButtonContainer>
        <SubBtn next={() => router.back()}>이전 페이지</SubBtn>
        <NormalBtn next={"/"}>홈화면</NormalBtn>
      </ButtonContainer>
    </div>
  );
}
