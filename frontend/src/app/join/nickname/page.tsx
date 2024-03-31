"use client";

import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import TextField from "@mui/material/TextField";
import { useRecoilState, useRecoilValue } from "recoil";
import { userProfileState, userNicknameState } from "@/store/join";
import { updateMemberAPI } from "@/services/member";
import { uploadImagesAPI } from "@/services/upload";
import { useRouter } from "next/navigation";

export default function AddNickname() {
  const profile = useRecoilValue(userProfileState);
  const [nickname, setNickname] = useRecoilState(userNicknameState);

  const router = useRouter();

  const joinMember = async () => {
    if (nickname !== null) {
      let profileImage;
      if (profile) {
        profileImage = await uploadImagesAPI(profile);
        profileImage = await profileImage[0];
      } else {
        profileImage =
          "https://kkoma.s3.ap-northeast-2.amazonaws.com/images/e3a028fb-3355-4888-afd6-21a15a1f4c95.png";
      }
      const obj = await updateMemberAPI({
        profileImage: profileImage,
        nickname: nickname,
      });

      router.replace("/join/complete");
    }
    //TODO: 에러 페이지로 이동
  };

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
        <NormalBtn
          next={joinMember}
          disabled={nickname == null || nickname == ""}
        >
          완료
        </NormalBtn>
      </ButtonContainer>
    </>
  );
}
