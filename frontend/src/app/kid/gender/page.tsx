"use client";

import Title from "@/components/common/title";
import {
  ButtonContainer,
  SubBtn,
  NormalBtn,
} from "@/components/common/buttons";
import GenderBtn from "@/components/kid/gender-btn";
import { useRecoilState, useRecoilValue } from "recoil";
import { kidNameState, kidBirthDateState, kidGenderState } from "@/store/kid";
import { updateKidAPI } from "@/services/kid";
import { useRouter } from "next/navigation";

export default function KidGender() {
  const name = useRecoilValue(kidNameState);
  const birthDate = useRecoilValue(kidBirthDateState);
  const [gender, setGender] = useRecoilState(kidGenderState);

  const router = useRouter();

  const updateKid = async () => {
    const obj = await updateKidAPI({
      name: name,
      birthDate: birthDate,
      gender: gender ?? null,
    });

    router.replace("/kid/complete");
  };

  return (
    <>
      <Title
        title={`아이의\n성별을 알려주세요`}
        subtitle="제품 추천에 도움을 줄게요"
      />
      <GenderBtn />
      <ButtonContainer>
        <SubBtn next={() => setGender(null)}>건너뛰기</SubBtn>
        <NormalBtn next={updateKid}>완료</NormalBtn>
      </ButtonContainer>
    </>
  );
}
