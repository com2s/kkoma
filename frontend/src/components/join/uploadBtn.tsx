"use client";

import { styled } from "@mui/material/styles";
import styles from "./uploadBtn.module.scss";
import { useState, useEffect } from "react";

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

export default function UploadBtn() {
  const [imgFile, setImgFile] = useState<File | null>();
  const [preview, setPreview] = useState<string | null>("");

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
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreview(reader.result as string);
      };
      reader.readAsDataURL(imgFile);
    } else {
      setPreview(null);
    }
  }, [imgFile]);

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
        <VisuallyHiddenInput type="file" accept="image/*" onChange={onChangeImg} />
      </label>
    </div>
  );
}
