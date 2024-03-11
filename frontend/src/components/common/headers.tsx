import LogoIcon from "/public/images/small-logo-icon.svg";
import NotificationsIcon from "@mui/icons-material/Notifications";
import styles from "./header.module.scss";

export default function HeaderWithLogo() {
  return (
    <div className={styles.header}>
      <section className={styles.logo}>
        <LogoIcon width="30" height="31" />
        <span className="text-logo">KKOMA</span>
      </section>
      <NotificationsIcon className={styles.notifications} />
    </div>
  );
}
