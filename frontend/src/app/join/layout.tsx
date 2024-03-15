import TopBar3 from "@/components/common/top-bar3";
import styles from "./join.module.scss";

export default function JoinLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <div className={styles.join}>
      <TopBar3 />
      {children}
    </div>
  );
}
