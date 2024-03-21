"use client";

import UploadBtn from "@/components/join/uploadBtn";
import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import { useRecoilValue } from "recoil";
import { userProfileState } from "@/store/join";

export default function AddProfile() {
  const profile = useRecoilValue(userProfileState);

  return (
    <>
      <h2>{`프로필 사진을 등록해주세요`}</h2>
      <UploadBtn />
      <ButtonContainer>
        <NormalBtn next={"/join/nickname"} disabled={profile == null}>
          다음
        </NormalBtn>
      </ButtonContainer>
    </>
  );
}
