import UploadBtn from "@/components/join/uploadBtn";
import { WideBtn } from "@/components/common/buttons";

export default function AddProfile() {
  return (
    <>
      <h2>{`프로필 사진을 등록해주세요`}</h2>
      <UploadBtn />
      <WideBtn next={"/join/nickname"}>다음</WideBtn>
    </>
  );
}
