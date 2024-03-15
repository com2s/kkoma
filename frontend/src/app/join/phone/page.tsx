import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import TextField from "@mui/material/TextField";

export default function AddPhone() {
  return (
    <>
      <h2>{`연락처를 알려주세요`}</h2>
      <TextField
        id="standard-basic"
        label="연락처"
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
      />
      <ButtonContainer>
        <NormalBtn next={"/join/complate"}>완료</NormalBtn>
      </ButtonContainer>
    </>
  );
}
