"use client";

import TopBar2 from "@/components/my-trade/my-request-bar";
import styles from "@/components/my-trade/my-request.module.scss";
import { getRequesters } from "@/components/my-trade/my-trade-ftn";
import {
  Card,
  CardContent,
  Typography,
  Avatar,
  IconButton,
} from "@mui/material";
import React, { useState, useEffect } from "react";
import Slide from "@mui/material/Slide";
import { TransitionProps } from "@mui/material/transitions";
import CheckIcon from "@mui/icons-material/Check";
import RemoveIcon from "@mui/icons-material/Remove";
import Link from "next/link";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

interface Requester {
  userId: string;
  url: string;
  times: { start: string; end: string }[];
}

interface IParams {
  params: { dealId: string };
}

export default function MyRequest({ params: { dealId } }: IParams) {
  const [requesters, setRequesters] = useState<Requester[]>([]); // requesters 상태와 상태 설정 함수 추가

  useEffect(() => {
    const fetchData = async () => {
      const data = await getRequesters();
      setRequesters(data);
    };
    fetchData();
  }, []);

  function formatTimeRange(start: string, end: string) {
    // 'start'와 'end'에서 날짜 부분이 같은지 확인합니다.
    if (start.slice(0, 10) === end.slice(0, 10)) {
      // YYYY-MM-DD 부분만 비교
      // 같은 날짜일 경우 시작 시간과 종료 시간만 표시합니다.
      return `${start} - ${end.slice(11)}`;
    } else {
      // 다른 날짜일 경우 전체 시간을 표시합니다.
      return `${start} - ${end}`;
    }
  }

  const handleDelete = (userId: string) => {
    if (window.confirm("거래 요청을 취소하시겠습니까?")) {
      alert("거래 요청이 취소되었습니다.");
      console.log(userId);
    }
  };

  return (
    <React.Fragment>
      <TopBar2 />
      {/* <h1>{id}</h1> */}
      {requesters?.map((requester, key) => (
        <Card key={key} variant="outlined" className={styles.card}>
          <Avatar
            alt="Product Image"
            src={requester.url}
            sx={{ width: 80, height: 80 }}
            className={styles.avatar}
            variant="square"
          />
          <CardContent sx={{ padding: 1 }} className={styles.cardMiddle}>
            <Typography variant="h6" component="div">
              판매자 {requester.userId}
            </Typography>
            {/* <Typography color="text.secondary">{requester.productName}</Typography> */}
            {requester.times?.map((time, key) => (
              <Typography key={key} variant="body2">
                {formatTimeRange(time.start, time.end)}
              </Typography>
            ))}
          </CardContent>
          <CardContent className={styles.btns} sx={{ padding: 0 }}>
            <Link href={`/my-trade/${dealId}/${requester.userId}`}>
              <IconButton
                size="small"
                sx={{
                  "&.MuiIconButton-root": {
                    bgcolor: "#ffcf00",
                  },
                  margin: 1,
                }}
              >
                <CheckIcon sx={{ color: "white" }} />
              </IconButton>
            </Link>
            <IconButton
              onClick={() => handleDelete(requester.userId)}
              size="small"
              sx={{
                "&.MuiIconButton-root": {
                  bgcolor: "#764c32",
                },
                margin: 1,
              }}
            >
              <RemoveIcon sx={{ color: "white" }} />
            </IconButton>
          </CardContent>
        </Card>
      ))}
    </React.Fragment>
  );
}
