import { KidSummary } from "@/types/kid";
import KidCard from "./kidCard";
import styles from "./kidCard.module.scss";
import { Dispatch, SetStateAction } from "react";

export default function KidCardList(props: {
  kidList: Array<KidSummary>;
  setSelectedName: Dispatch<SetStateAction<string | null>>;
}) {
  return (
    <div className={styles.list}>
      {props.kidList.map((item, k) => (
        <div key={k} onClick={() => props.setSelectedName(item.name)} className={styles.card}>
          <KidCard {...item} />
        </div>
      ))}
    </div>
  );
}
