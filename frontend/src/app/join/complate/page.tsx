import Logo from "/public/images/logo-icon.svg";
import { ButtonContainer, SubBtn, NormalBtn } from "@/components/common/buttons";

export default function JoinComplate() {
  return (
    <>
      <div>
        <h2>
          회원가입이
          <br />
          완료되었어요!
        </h2>
        <span className="text-caption c-text3">
          아이 정보를 입력하면
          <br />
          제품을 추천해줄 수 있어요.
        </span>
      </div>
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
