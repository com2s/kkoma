import LogoIcon from "/public/images/small-logo-icon.svg";
import NotificationsIcon from "@mui/icons-material/Notifications";
import styles from "./top-bar.module.scss";

export default function HeaderWithLogo() {
  return (
    <>
      <div className={styles.header}> {/* 상단 바 고정 스타일 */}
        <section className={styles.logo}>
          <LogoIcon width="30" height="31" />
          <span className="text-logo">KKOMA</span>
        </section>
        <NotificationsIcon className={styles.notifications} fontSize="large"/>
      </div>
      <div className={styles.headerSpacer}></div> {/* 상단 바 높이만큼의 빈 공간 */}
    </>
  );
}
