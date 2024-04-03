"use client";

import React, { useEffect, useState } from "react";
import TopBar2 from "@/components/notifications/notifications-bar";
import {
  getMyNotifications,
  postReadNotification,
} from "@/components/notifications/notifications-ftn";
import { NotificationList } from "@/types/member";
import { Avatar, Button, List, ListItem, ListItemAvatar, ListItemText } from "@mui/material";
import Link from "next/link";
import { useRouter } from "next/navigation";

export default function MyNotificationsPage() {
  const [notifications, setNotifications] = useState<NotificationList["data"]["content"]>([]);
  const [data, setData] = useState<NotificationList["data"]>();
  const [page, setPage] = useState(0);
  const [isLast, setIsLast] = useState<boolean>(true);
  const [success, setSuccess] = useState(true);
  const [today, setToday] = useState(new Date());
  const [isEmptyShow, setIsEmptyShow] = useState<Boolean>(false);
  const router = useRouter();

  const fetchData = async () => {
    const res = await getMyNotifications(page);
    setSuccess(res.success);
    // setNotifications(res.data.content);
    // 기존 데이터 유지하면서 새로운 데이터 추가
    setNotifications([...notifications, ...sortNotifications(res.data.content)]);
    setData(res.data);
    setIsLast(res.data.last);
    const date = new Date();
    setToday(date);
  };

  const formattedToday = (date: Date) => {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hour = date.getHours().toString().padStart(2, "0");
    const minute = date.getMinutes().toString().padStart(2, "0");
    return `${year}년 ${month}월 ${day}일 ${hour}:${minute}`;
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

  const sortNotifications = (notifications: NotificationList["data"]["content"]) => {
    return notifications.sort((a: any, b: any) => {
      return new Date(b.sentAt).getTime() - new Date(a.sentAt).getTime();
    });
  };

  const handleLinkClick = (id: number, destination: string) => async (e: React.MouseEvent) => {
    e.preventDefault(); // 기본 동작 중단
    await postReadNotification(id); // API 요청
    router.push(destination); // 페이지 이동
  };

  useEffect(() => {
    fetchData();
  }, [page]);

  const handlePageMore = () => {
    setIsEmptyShow(true);
    setPage(page + 1);
  };

  return (
    <div>
      <TopBar2 />
      {success === false && <h4 className="p-4">알림을 불러오는 데 실패했습니다.</h4>}
      <List sx={{ width: "100%", minWidth: 260, bgcolor: "background.paper" }}>
        {success === true && notifications.length > 0 && (
          <>
            {notifications.map((notification: any) => (
              <a
                key={notification.id}
                href={notification.destination}
                onClick={handleLinkClick(notification.id, notification.destination)}
                style={{ textDecoration: "none" }}
              >
                <ListItem sx={{ paddingX: 0, minHeight: "90px" }} disabled={notification.readAt}>
                  {/* readAt의 값이 null 이면 disabled = false, 값이 있으면 disabled = true */}
                  <ListItemAvatar>
                    <Avatar
                      src={"temp-img.svg"}
                      alt="Notification Avatar"
                      sx={{ width: 48, height: 48, marginRight: 2 }}
                    />
                  </ListItemAvatar>
                  <ListItemText
                    primary={notification.message}
                    secondary={calculateDate(new Date(notification.sentAt))}
                  />
                </ListItem>
              </a>
            ))}
          </>
        )}
      </List>
      {isLast ? (
        <></>
      ) : (
        <div className="flex justify-around mt-6">
          <Button
            onClick={handlePageMore}
            // disabled={data?.last || data?.empty}
            style={{
              // color: "black",
              fontSize: "1rem",
              fontWeight: "bold",
              padding: "0.5rem 1rem",
              // border: "1px solid black",
              borderRadius: "0.5rem",
              cursor: "pointer",
            }}
          >
            더 보기
          </Button>
        </div>
      )}
    </div>
  );
}
