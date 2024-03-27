"use client";

import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import styles from "./kidCard.module.scss";
import { KidBirthFormat } from "@/utils/format";
import { KidSummary } from "@/types/kid";

export default function KidCard(props: KidSummary) {
  return (
    <>
      <div className={styles["btn-container"]}>
        <MoreHorizIcon className={styles.btn} />
      </div>
      <img src="/images/baby-img.png" alt="baby" />
      <span className="text-deco">{props.name ?? "이름을 알려주세요"}</span>
      <span className="text-body2 c-text2">
        {props.birthDate ? KidBirthFormat(props.birthDate) : "생년월일을 알려주세요"}
      </span>
    </>
  );
}
