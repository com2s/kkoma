import Logo from "/public/images/logo-icon.svg";
import Title from "@/components/common/title";
import { ButtonContainer, SubBtn, NormalBtn } from "@/components/common/buttons";

export default function JoinComplate() {
  return (
    <>
      <Title
        title="회원가입이 완료되었어요!"
        subtitle="아이 정보를 입력하면 제품을 추천해줄 수 있어요."
      />
      <div className="flex w-full justify-center">
        <Logo />
      </div>
      <ButtonContainer>
        <SubBtn next={"/"}>홈 화면</SubBtn>
        <NormalBtn next={"/"}>아이 정보 입력</NormalBtn>
      </ButtonContainer>
    </>
  );
}
