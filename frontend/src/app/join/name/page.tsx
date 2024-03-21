"use client";

import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import TextField from "@mui/material/TextField";
import { useRecoilState } from "recoil";
import { userNameState } from "@/store/join";

export default function AddName() {
  const [name, setName] = useRecoilState(userNameState);

  return (
    <>
      <h2>{`이름을 알려주세요`}</h2>
      <TextField
        id="standard-basic"
        label="이름"
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
        onChange={(e) => setName(e.target.value)}
      />
      <ButtonContainer>
        <NormalBtn next={"/join/phone"} disabled={name == null || name == ""}>
          다음
        </NormalBtn>
      </ButtonContainer>
    </>
  );
}
