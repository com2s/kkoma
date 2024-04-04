import styles from "./no-contents.module.scss";

export function NoContents({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <div className={styles.container}>{children}</div>;
}
