"use client";

import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import styles from "./kidCard.module.scss";
import { KidBirthFormat } from "@/utils/format";
import { KidSummary } from "@/types/kid";
import Image from "next/image";
import { useRouter } from "next/navigation";

export default function KidCard(props: KidSummary) {
  const router = useRouter();
  return (
    <div onClick={() => router.push("/my-page/children")} className={styles.card}>
      <Image
        src="/images/baby-img.png"
        width={96}
        height={96}
        alt="baby"
        // style={{ width: "96px", height: "96px" }}
      />
      <span className="text-deco">{props.name ?? "이름을 알려주세요"}</span>
      <span className="text-body2 c-text2">
        {props.birthDate ? KidBirthFormat(props.birthDate) : "생년월일을 알려주세요"}
      </span>
    </div>
  );
}
