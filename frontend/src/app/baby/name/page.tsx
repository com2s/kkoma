import Title from "@/components/common/title";
import TextField from "@mui/material/TextField";
import { ButtonContainer, SubBtn, NormalBtn } from "@/components/common/buttons";

export default function BabyName() {
  return (
    <>
      <Title title="아이의 이름을 알려주세요" subtitle="별명이나 태명도 좋아요" />
      <TextField
        id="standard-basic"
        label="이름"
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
      />
      <ButtonContainer>
        <SubBtn next={"/baby/birth"}>건너뛰기</SubBtn>
        <NormalBtn next={"/baby/birth"}>완료</NormalBtn>
      </ButtonContainer>
    </>
  );
}
