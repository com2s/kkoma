"use client";

import React, { useEffect, useState } from "react";
import TopBar2 from "@/components/notifications/notifications-bar";
import {
  getMyNotifications,
  postReadNotification,
} from "@/components/notifications/notifications-ftn";
import {
  Avatar,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
} from "@mui/material";
import Link from "next/link";
import { useRouter } from "next/navigation";

export default function MyNotificationsPage() {
  const [notifications, setNotifications] = useState([]);
  const [page, setPage] = useState({});
  const [success, setSuccess] = useState(true);
  const [today, setToday] = useState(new Date());
  const router = useRouter();

  const fetchData = async () => {
    const res = await getMyNotifications();
    setSuccess(res.success);
    setNotifications(res.data.content);
    setPage(res.data);
    const date = new Date();
    setToday(date);
    console.log("res: ", res);
  };

  const formatDate = (date: Date) => {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${year}-${month}-${day}`;
  };

  const calculateDate = (date: Date) => {
    const diff = today.getTime() - date.getTime();
    if (diff >= 1000 * 3600 * 24) {
      const diffDays = (diff / (1000 * 3600 * 24)).toFixed(0);
      return `${diffDays}일 전`;
    } else if (diff >= 1000 * 3600) {
      const diffHours = (diff / (1000 * 3600)).toFixed(0);
      return `${diffHours}시간 전`;
    } else if (diff >= 1000 * 60) {
      const diffMinutes = (diff / (1000 * 60)).toFixed(0);
      return `${diffMinutes}분 전`;
    } else {
      return "방금 전";
    }
  };

  const handleLinkClick =
    (id: number, destination: string) => async (e: React.MouseEvent) => {
      e.preventDefault(); // 기본 동작 중단
      await postReadNotification(id); // API 요청
      router.push(destination); // 페이지 이동
    };

  return (
    <div>
      <TopBar2 />
      {success === false && <h3>알림을 불러오는 데 실패했습니다.</h3>}
      <List sx={{ width: "100%", minWidth: 260, bgcolor: "background.paper" }}>
        {success === true && notifications.length === 0 && (
          <>
            <ListItem sx={{ paddingX: 0 }}>
              <ListItemAvatar>
                <Avatar
                  src="temp-img.svg"
                  alt="Temp Avatar"
                  sx={{ width: 44, height: 44, marginRight: 2 }}
                />
              </ListItemAvatar>
              <ListItemText
                primary="알림이 없습니다."
                secondary={formatDate(today)}
              />
            </ListItem>
          </>
        )}
        {success === true && notifications.length > 0 && (
          <>
            {notifications.map((notification: any) => (
              <Link
                href={notification.destination}
                key={notification.id}
                passHref
              >
                <a
                  href={notification.destination}
                  onClick={handleLinkClick(
                    notification.id,
                    notification.destination
                  )}
                  style={{ textDecoration: "none" }}
                >
                  <ListItem sx={{ paddingX: 0 }}>
                    <ListItemAvatar>
                      <Avatar
                        src={"temp-img.svg"}
                        alt="Notification Avatar"
                        sx={{ width: 44, height: 44, marginRight: 2 }}
                      />
                    </ListItemAvatar>
                    <ListItemText
                      primary={notification.message}
                      secondary={calculateDate(new Date(notification.sentAt))}
                    />
                  </ListItem>
                </a>
              </Link>
            ))}
          </>
        )}
        <Link href="/">
          <ListItem sx={{ paddingX: 0 }}>
            <ListItemAvatar>
              <Avatar
                src="temp-img.svg"
                alt="Temp Avatar"
                sx={{ width: 44, height: 44, marginRight: 2 }}
              />
            </ListItemAvatar>
            <ListItemText
              primary="메시지 길이 테스트, 향후 지울 것. Browse through the icons below to find the one you need. The search field supports synonyms—for example, try searching for 'hamburger' or 'logout.'"
              secondary="2024-01-01"
            />
          </ListItem>
        </Link>
      </List>
    </div>
  );
}
