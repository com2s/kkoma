import TopBar3 from "@/components/common/top-bar3";
import styles from "./baby.module.scss";

export default function BabyLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <div className={styles.baby}>
      <TopBar3 />
      {children}
    </div>
  );
}
