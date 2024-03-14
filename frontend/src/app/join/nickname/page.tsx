import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import TextField from "@mui/material/TextField";

export default function AddNickname() {
  return (
    <>
      <h2>{`닉네임을 알려주세요`}</h2>
      <TextField
        id="standard-basic"
        label="닉네임"
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
      />
      <ButtonContainer>
        <NormalBtn next={"/join/name"}>다음</NormalBtn>
      </ButtonContainer>
    </>
  );
}
