import styles from "./gender-btn.module.scss";

export default function GenderBtn() {
  return (
    <div className={styles.container}>
      <button className={`${styles.man} ${styles.btn}`}>남자</button>
      <button className={`${styles.woman} ${styles.btn}`}>여자</button>
      <button className={`${styles.btn}`}>모름</button>
    </div>
  );
}
