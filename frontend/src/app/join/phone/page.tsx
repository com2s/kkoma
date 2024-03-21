"use client";

import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import TextField from "@mui/material/TextField";
import { useRecoilState, useRecoilValue } from "recoil";
import { userProfileState, userNicknameState, userNameState, userPhoneState } from "@/store/join";
import { updateMemberAPI } from "@/services/member";
import { uploadImagesAPI } from "@/services/upload";
import { useRouter } from "next/navigation";

export default function AddPhone() {
  const profile = useRecoilValue(userProfileState);
  const nickname = useRecoilValue(userNicknameState);
  const name = useRecoilValue(userNameState);
  const [phone, setPhone] = useRecoilState(userPhoneState);

  const router = useRouter();

  const joinMember = async () => {
    if (profile !== null && nickname !== null && name !== null && phone !== null) {
      const profileImage = await uploadImagesAPI(profile);
      const obj = await updateMemberAPI({
        profileImage: profileImage[0],
        nickname: nickname,
        name: name,
        phone: phone,
      });

      router.replace("/join/complete");
    }
  };

  return (
    <>
      <h2>{`연락처를 알려주세요`}</h2>
      <TextField
        id="standard-basic"
        label="연락처"
        variant="standard"
        sx={{ width: "100%", fontWeight: "bold" }}
        onChange={(e) => setPhone(e.target.value)}
      />
      <ButtonContainer>
        <NormalBtn next={joinMember} disabled={phone == null || phone == ""}>
          완료
        </NormalBtn>
      </ButtonContainer>
    </>
  );
}
