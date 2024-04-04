"use client";

import { styled } from "@mui/material/styles";
import styles from "./uploadBtn.module.scss";
import { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import { userProfileState } from "@/store/join";

const VisuallyHiddenInput = styled("input")({
  clip: "rect(0 0 0 0)",
  clipPath: "inset(50%)",
  height: 1,
  overflow: "hidden",
  position: "absolute",
  bottom: 0,
  left: 0,
  whiteSpace: "nowrap",
  width: 1,
});

export async function getServerSideProps() {}

export default function UploadBtn() {
  const [imgFile, setImgFile] = useState<File | null>();
  const [preview, setPreview] = useState<string | null>(
    "https://kkoma.s3.ap-northeast-2.amazonaws.com/images/e3a028fb-3355-4888-afd6-21a15a1f4c95.png"
  );
  const [profile, setProfile] = useRecoilState(userProfileState);

  const onChangeImg = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files !== null) {
      const file = event.target.files[0];
      if (file && file.type.substring(0, 5) === "image") {
        setImgFile(file);
      } else {
        setImgFile(null);
      }
    }
  };

  useEffect(() => {
    if (imgFile) {
      const formData = new FormData();
      formData.set("images", imgFile);
      setProfile(formData);

      const reader = new FileReader();
      reader.onloadend = () => {
        setPreview(reader.result as string);
      };
      reader.readAsDataURL(imgFile);
      setPreview(
        "https://kkoma.s3.ap-northeast-2.amazonaws.com/images/e3a028fb-3355-4888-afd6-21a15a1f4c95.png"
      );
    } else {
      setPreview(
        "https://kkoma.s3.ap-northeast-2.amazonaws.com/images/e3a028fb-3355-4888-afd6-21a15a1f4c95.png"
      );
    }
  }, [imgFile, setProfile]);

  return (
    <div className={styles.container}>
      <label
        className={styles.uploadBtn}
        style={{
          backgroundImage: preview ? `url(${preview})` : "",
          backgroundSize: "cover",
          backgroundRepeat: "no-repeat",
          backgroundPosition: "center center",
        }}
      >
        <VisuallyHiddenInput
          type="file"
          accept="image/*"
          onChange={onChangeImg}
        />
      </label>
    </div>
  );
}
