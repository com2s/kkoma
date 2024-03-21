"use client";

import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import TextField from "@mui/material/TextField";
import { useRecoilState } from "recoil";
import { userNicknameState } from "@/store/join";

export default function AddNickname() {
  const [nickname, setNickname] = useRecoilState(userNicknameState);

  return (
    <>
      <h2>{`닉네임을 알려주세요`}</h2>
      <TextField
        id="standard-basic"
        label="닉네임"
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
        onChange={(e) => setNickname(e.target.value)}
      />
      <ButtonContainer>
        <NormalBtn next={"/join/name"} disabled={nickname == null || nickname == ""}>
          다음
        </NormalBtn>
      </ButtonContainer>
    </>
  );
}
