"use client";

import TopBar2 from "@/components/my-trade/my-request-bar";
import styles from "@/components/my-trade/my-request.module.scss";
// 인터페이스
// import { Requester }
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
  id: number;
  memberProfile: {
    id: number;
    nickname: string;
    profileImage: string;
  }
  offerTime: [
    {
      offerDate: string;
      startTime: {
        hour: number;
        minute: number;
        second: number;
        nano: number;
      },
      endTime: {
        hour: number;
        minute: number;
        second: number;
        nano: number;
      }
    }
  ]
}

interface IParams {
  params: { dealId: string };
}

export default function MyRequest({ params: { dealId } }: IParams) {
  const [requesters, setRequesters] = useState<Requester[]>([]); // requesters 상태와 상태 설정 함수 추가

  useEffect(() => {
    const fetchData = async () => {
      const data = await getRequesters(dealId);
      console.log(data);
      setRequesters(data);
    };
    fetchData();
  }, []);

  const handleDelete = (userId: number) => {
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
            src={requester.memberProfile.profileImage}
            sx={{ width: 80, height: 80 }}
            className={styles.avatar}
            variant="square"
          />
          <CardContent sx={{ padding: 1 }} className={styles.cardMiddle}>
            <Typography variant="h6" component="div">
              판매자 {requester.memberProfile.nickname}
            </Typography>
            {/* <Typography color="text.secondary">{requester.productName}</Typography> */}
            {requester.offerTime?.map((time, key) => (
              <Typography key={key} variant="body2">
                {time.offerDate} | {time.startTime.hour}시 {time.startTime.minute}분 ~ {time.endTime.hour}시 {time.endTime.minute}분
              </Typography>
            ))}
          </CardContent>
          <CardContent className={styles.btns} sx={{ padding: 0 }}>
            <Link href={`/my-trade/${dealId}/${requester.memberProfile.id}`}>
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
              onClick={() => handleDelete(requester.memberProfile.id)}
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
      {(!requesters || requesters.length === 0 ) && (
        <h2 className="p-4">아직 요청이 없습니다.</h2>
      )}
    </React.Fragment>
  );
}
