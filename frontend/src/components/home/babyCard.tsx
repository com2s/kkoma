import { colors } from "@mui/material";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import styles from "./babyCard.module.scss";
import Image from "next/image";

export default function BabyCard() {
  return (
    <div className={styles.card}>
      <div className={styles["btn-container"]}>
        <MoreHorizIcon className={styles.btn} />
      </div>
      <Image src="/images/baby-img.png" alt="baby" />
      <span className="text-deco">예콩이</span>
      <span className="text-body2 c-text2">10개월 (D+311)</span>
    </div>
  );
}
