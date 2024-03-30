import { NoContents } from "../common/no-contents";
import RecommendOutlinedIcon from "@mui/icons-material/RecommendOutlined";
import Image from "next/image";
import { SmallBtn } from "../common/buttons";

export function NoKids() {
  return (
    <NoContents>
      <h4 className="c-text3">등록된 아이 정보가 없어요</h4>
      <Image src={"/images/Loading.png"} alt="kid" width={100} height={100} />
      <SmallBtn next={"/kid/name"}>등록하고 상품 추천받기</SmallBtn>
    </NoContents>
  );
}
