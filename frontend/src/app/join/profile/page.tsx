"use client";

import UploadBtn from "@/components/join/uploadBtn";
import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import Title from "@/components/common/title";

export default function AddProfile() {
  return (
    <>
      <Title
        title="프로필 사진을 등록해주세요"
        subtitle="원을 클릭해서 사진을 찾아보세요"
      ></Title>
      <UploadBtn />
      <ButtonContainer>
        <NormalBtn next={"/join/nickname"}>다음</NormalBtn>
      </ButtonContainer>
    </>
  );
}
