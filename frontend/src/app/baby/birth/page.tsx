"use client";

import Title from "@/components/common/title";
import TextField from "@mui/material/TextField";
import { ButtonContainer, SubBtn, NormalBtn } from "@/components/common/buttons";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import { ChangeEvent, useState } from "react";

export default function BabyBirth() {
  const [isBirthYet, setIsBirthYet] = useState(false);

  const handelChange = (e: ChangeEvent<HTMLInputElement>) => {
    setIsBirthYet(e.target.checked);
  };

  return (
    <>
      <Title title="아이 생년월일을 알려주세요" subtitle="제품 추천에 도움을 줄게요" />
      <FormControlLabel
        control={<Checkbox checked={isBirthYet} onChange={handelChange} />}
        label="아직 출산 전이에요"
      />
      <TextField
        id="standard-basic"
        label={isBirthYet ? "출산예정일" : "생년월일"}
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
      />
      <ButtonContainer>
        <SubBtn next={"/baby/gender"}>건너뛰기</SubBtn>
        <NormalBtn next={"/baby/gender"}>완료</NormalBtn>
      </ButtonContainer>
    </>
  );
}
