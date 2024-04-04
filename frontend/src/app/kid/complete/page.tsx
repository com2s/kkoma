import Title from "@/components/common/title";
import Logo from "/public/images/logo-icon.svg";
import { ButtonContainer, NormalBtn } from "@/components/common/buttons";

export default function KidComplate() {
  return (
    <>
      <Title title={`아이 정보 입력이\n완료되었어요`} subtitle="새로운 제품을 찾으러 가볼까요?" />
      <div className="flex w-full justify-center">
        <Logo />
      </div>
      <ButtonContainer>
        <NormalBtn next={"/"}>완료</NormalBtn>
      </ButtonContainer>
    </>
  );
}
