import UploadBtn from "@/components/join/uploadBtn";
import { WideBtn } from "@/components/common/buttons";
import TextField from "@mui/material/TextField";

export default function AddName() {
  return (
    <>
      <h2>{`이름을 알려주세요`}</h2>
      <TextField
        id="standard-basic"
        label="이름"
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
      />

      <WideBtn next={"/join/phone"}>다음</WideBtn>
    </>
  );
}
