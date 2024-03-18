"use client";

import TopBar2 from "@/components/my-trade/my-request-calender-bar";
import Image from "next/image";
import React, { useState } from "react";
import Dialog from "@mui/material/Dialog";
import ListItemText from "@mui/material/ListItemText";
import ListItemButton from "@mui/material/ListItemButton";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import CloseIcon from "@mui/icons-material/Close";
import Slide from "@mui/material/Slide";
import { TransitionProps } from "@mui/material/transitions";
import useMediaQuery from "@mui/material/useMediaQuery";
import { useTheme } from "@mui/material";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export default function MyTradeCalender() {
  const [open, setOpen] = useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <React.Fragment>
      <TopBar2 />
      <div className="px-4 my-14">
        <h1>거래하고 싶은 시간을 선택해주세요.</h1>
        <p className="my-6 text-gray-400">
          거래 가능 시간은 최대 3개까지 선택할 수 있어요.
        </p>
        <Image
          src="/images/calendar1.svg"
          alt="달력"
          width={300}
          height={300}
          className="mx-auto my-6"
        />
      </div>
      <div className="my-8">캘린더 영역</div>
      <div className="flex justify-center">
        <button
          className="my-8 w-5/6 h-14 bg-primary rounded-xl
         bg-yellow-400 text-black"
          onClick={handleClickOpen}
        >
          <h3>거래 수락</h3>
        </button>
      </div>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        TransitionComponent={Transition}
      >
        {fullScreen === true && (
          <IconButton
            color="inherit"
            onClick={handleClose}
            aria-label="close"
            sx={{
              aspectRatio: "1",
              maxWidth: "40px",
              margin: 2,
            }}
          >
            <ArrowBackIosNewIcon />
          </IconButton>
        )}
        {fullScreen === false && (
          <div style={{ display: "flex", justifyContent: "flex-end" }}>
            <IconButton
              color="inherit"
              onClick={handleClose}
              aria-label="close"
              sx={{
                aspectRatio: "1",
                maxWidth: "40px",
                margin: 1,
              }}
            >
              <CloseIcon />
            </IconButton>
          </div>
        )}
        <List>
          <ListItemButton>
            <ListItemText primary="거래 수락이 완료되었어요!" secondary="이제 1대1 채팅이 가능해요!" />
          </ListItemButton>
          <Divider />
          <ListItemButton>
            <ListItemText
              primary="이미지 영역"
              secondary="이 아래에도 버튼 필요"
            />
          </ListItemButton>
        </List>
      </Dialog>
    </React.Fragment>
  );
}
