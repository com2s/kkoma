"use client";

import Title from "@/components/common/title";
import TextField from "@mui/material/TextField";
import { ButtonContainer, SubBtn, NormalBtn } from "@/components/common/buttons";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import { ChangeEvent, useState } from "react";
import { BirthdaySelecter } from "@/components/kid/birthday-selecter";
import { useRecoilState } from "recoil";
import { kidYearState, kidMonthState, kidDateState } from "@/store/kid";

export default function BabyBirth() {
  const [year, setYear] = useRecoilState(kidYearState);
  const [month, setMonth] = useRecoilState(kidMonthState);
  const [date, setDate] = useRecoilState(kidDateState);

  const [isBirthYet, setIsBirthYet] = useState(false);
  const [open, setOpen] = useState(false);

  const handelChange = (e: ChangeEvent<HTMLInputElement>) => {
    setIsBirthYet(e.target.checked);
  };

  return (
    <>
      <Title title={`아이의\n생년월일을 알려주세요`} subtitle="제품 추천에 도움을 줄게요" />
      <FormControlLabel
        control={<Checkbox checked={isBirthYet} onChange={handelChange} />}
        label="아직 출산 전이에요"
      />
      <TextField
        id="standard-basic"
        label={isBirthYet ? "출산예정일" : "생년월일"}
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
        value={year + "년 " + month + "월 " + date + "일"}
        onClick={() => setOpen(true)}
      />
      <BirthdaySelecter
        title={isBirthYet ? "출산예정일" : "생년월일"}
        open={open}
        setOpen={setOpen}
      />
      <ButtonContainer>
        <SubBtn next={"/kid/gender"}>건너뛰기</SubBtn>
        <NormalBtn next={"/kid/gender"}>완료</NormalBtn>
      </ButtonContainer>
    </>
  );
}
