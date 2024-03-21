import UploadBtn from "@/components/join/uploadBtn";
import { ButtonContainer, NormalBtn } from "@/components/common/buttons";

export default function AddProfile() {
  return (
    <>
      <h2>{`프로필 사진을 등록해주세요`}</h2>
      <UploadBtn />
      <ButtonContainer>
        <NormalBtn next={"/join/nickname"} disabled={true}>
          다음
        </NormalBtn>
      </ButtonContainer>
    </>
  );
}
