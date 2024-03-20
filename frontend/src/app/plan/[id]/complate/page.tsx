import Title from "@/components/common/title";
import Image from "next/image";
import TopBar3 from "@/components/common/top-bar3";
import Hearts from "/public/images/Hearts.png";
import styles from "./complate.module.scss";
import { ButtonContainer, SubBtn, NormalBtn } from "@/components/common/buttons";

export default function PlanComplate() {
  return (
    <div className={styles.complate}>
      <TopBar3 />
      <Title title="거래가 완료되었어요" subtitle={`즐거운 거래 하셨나요?\n리뷰를 작성해보세요`} />
      <Image src={Hearts} alt="hearts" width={173} height={173} />
      <ButtonContainer>
        <SubBtn next={"/"}>홈 화면</SubBtn>
        {/* TODO: 리뷰 작성 화면으로 이동*/}
        <NormalBtn next={"/"}>리뷰 작성하기</NormalBtn>
      </ButtonContainer>
    </div>
  );
}
