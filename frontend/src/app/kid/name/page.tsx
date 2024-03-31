"use client";

import Title from "@/components/common/title";
import TextField from "@mui/material/TextField";
import {
  ButtonContainer,
  SubBtn,
  NormalBtn,
} from "@/components/common/buttons";
import { useRecoilState } from "recoil";
import { kidNameState } from "@/store/kid";
import { useRouter } from "next/navigation";

export default function KidName() {
  const [name, setName] = useRecoilState(kidNameState);
  const router = useRouter();

  const handleOnKeyUp = (event: React.KeyboardEvent<HTMLDivElement>) => {
    if (event.key === "Enter") {
      router.push("/kid/birth");
    }
  };

  return (
    <>
      <Title
        title={`아이의\n이름을 알려주세요`}
        subtitle="별명이나 태명도 좋아요"
      />
      <TextField
        id="standard-basic"
        label="이름"
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
        onChange={(e) => setName(e.target.value)}
        onKeyUp={handleOnKeyUp}
      />
      <ButtonContainer>
        <SubBtn
          next={() => {
            setName(null);
            router.push("/kid/birth");
          }}
          display={name === (null || undefined || "")}
        >
          건너뛰기
        </SubBtn>
        <NormalBtn next={() => router.push("/kid/birth")}>다음</NormalBtn>
      </ButtonContainer>
    </>
  );
}
