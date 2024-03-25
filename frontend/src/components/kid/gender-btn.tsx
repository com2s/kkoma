"use client";

import styles from "./gender-btn.module.scss";
import { useRecoilState } from "recoil";
import { kidGenderState } from "@/store/kid";

export default function GenderBtn() {
  const [gender, setGender] = useRecoilState(kidGenderState);

  return (
    <div className={styles.container}>
      <button
        className={
          gender === "MALE"
            ? `${styles.man} ${styles.btn} ${styles.selected}`
            : `${styles.man} ${styles.btn}`
        }
        onClick={() => setGender("MALE")}
      >
        남자
      </button>
      <button
        className={
          gender === "FEMALE"
            ? `${styles.woman} ${styles.btn} ${styles.selected}`
            : `${styles.woman} ${styles.btn}`
        }
        onClick={() => setGender("FEMALE")}
      >
        여자
      </button>
      <button
        className={gender === "UNKNOWN" ? `${styles.btn} ${styles.selected}` : `${styles.btn}`}
        onClick={() => setGender("UNKNOWN")}
      >
        모름
      </button>
    </div>
  );
}
