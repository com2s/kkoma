import Title from "@/components/common/title";
import { ButtonContainer, SubBtn, NormalBtn } from "@/components/common/buttons";
import GenderBtn from "@/components/baby/gender-btn";

export default function BabyBirth() {
  return (
    <>
      <Title title={`아이의\n성별을 알려주세요`} subtitle="제품 추천에 도움을 줄게요" />
      <GenderBtn />
      <ButtonContainer>
        <SubBtn next={"/baby/complate"}>건너뛰기</SubBtn>
      </ButtonContainer>
    </>
  );
}
