"use client";

import { styled } from "@mui/material/styles";
import styles from "./uploadBtn.module.scss";

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
  return (
    <div className={styles.container}>
      <label className={styles.uploadBtn}>
        <VisuallyHiddenInput type="file" accept="image/*" />
      </label>
    </div>
  );
}
