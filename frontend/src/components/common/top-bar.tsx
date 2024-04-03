"use client";

import LogoIcon from "/public/images/small-logo-icon.svg";
import NotificationsIcon from "@mui/icons-material/Notifications";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import styles from "./top-bar.module.scss";
import Link from "next/link";
import { useEffect, useState } from "react";
import { getUnreadNotificationAPI } from "@/services/notification";

export default function TopBar() {
  const [hasUnread, SetHasUnread] = useState<boolean>(false);

  const fetchData = async () => {
    const res = await getUnreadNotificationAPI();
    SetHasUnread(res);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div className={styles.header}>
        <Link href="/" className={styles.logo}>
          <LogoIcon width="30" height="31" />
          <span className="text-logo">KKOMA</span>
        </Link>
        <Link href="/notifications">
          {hasUnread ? (
            <NotificationsActiveIcon className={styles.hasUnread} fontSize="large" />
          ) : (
            <NotificationsIcon className={styles.notifications} fontSize="large" />
          )}
        </Link>
      </div>
      <div className={styles.headerSpacer}></div>
    </>
  );
}
