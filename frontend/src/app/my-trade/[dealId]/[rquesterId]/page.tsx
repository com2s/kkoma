"use client";

import TopBar2 from "@/components/my-trade/my-request-calender-bar";
import Calendar from "@/components/common/calendar";
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
import { Box, Tab, Tabs, useTheme } from "@mui/material";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export default function MyTradeCalender() {
  const [value, setValue] = useState(0);
  const [open, setOpen] = useState(false);
  const [parentDate0, setParentDate0] = useState("");
  const [parentDate1, setParentDate1] = useState("");
  const [parentDate2, setParentDate2] = useState("");
  const [parentTime0, setParentTime0] = useState("");
  const [parentTime1, setParentTime1] = useState("");
  const [parentTime2, setParentTime2] = useState("");

  const handleDate0 = (data: string) => {
    if (data) {
      const date = new Date(data);
      const formattedDate = new Intl.DateTimeFormat("ko-KR", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
      }).format(date);
      setParentDate0(formattedDate);
    } else {
      setParentDate0("");
    }
  };

  const handleDate1 = (data: string) => {
    if (data) {
      const date = new Date(data);
      const formattedDate = new Intl.DateTimeFormat("ko-KR", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
      }).format(date);
      setParentDate1(formattedDate);
    } else {
      setParentDate1("");
    }
  };

  const handleDate2 = (data: string) => {
    if (data) {
      const date = new Date(data);
      const formattedDate = new Intl.DateTimeFormat("ko-KR", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
      }).format(date);
      setParentDate2(formattedDate);
    } else {
      setParentDate2("");
    }
  };

  const handleTime0 = (data: string) => {
    setParentTime0(data);
  };
  const handleTime1 = (data: string) => {
    setParentTime1(data);
  };
  const handleTime2 = (data: string) => {
    setParentTime2(data);
  };

  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  function a11yProps(index: number) {
    return {
      id: `simple-tab-${index}`,
      "aria-controls": `simple-tabpanel-${index}`,
    };
  }

  return (
    <React.Fragment>
      <TopBar2 />
      <div className="px-4 my-12 mx-2">
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
      <Box sx={{ borderBottom: 0, borderColor: "divider" }}>
        {/* 탭 쓰려했으나 캘린더의 반응 문제로 취소 */}
        <Tabs
          value={value}
          onChange={handleChange}
          aria-label="basic tabs example"
          variant="fullWidth"
          sx={{ margin: "0 auto", width: "100%", maxWidth: "500px" }}
        >
          <Tab
            label={
              <div style={{ textAlign: "center" }}>
                {parentDate0 ? (
                  <div>
                    <p className="text-[16px]">{parentDate0}</p>
                    <p className="mt-1 text-[16px]">{parentTime0}</p>
                  </div>
                ) : (
                  <p>시간 선택</p>
                )}
              </div>
            }
            {...a11yProps(0)}
            sx={{
              margin: "8px",
              borderRadius: "12px",
              height: "5rem",
              minWidth: "3rem",
              "&.MuiTab-root": {
                bgcolor: "#f5f5f5"
              },
            }}
          />
          <Tab
            label={
              <div style={{ textAlign: "center" }}>
                {parentDate1 ? (
                  <div>
                  <p className="text-[16px]">{parentDate1}</p>
                  <p className="mt-1 text-[16px]">{parentTime1}</p>
                </div>
                ) : (
                  <p>추가 시간 선택</p>
                )}
              </div>
            }
            {...a11yProps(1)}
            sx={{
              margin: "8px",
              borderRadius: "12px",
              minWidth: "3rem",
              "&.MuiTab-root": {
                bgcolor: "#f5f5f5"
              },
            }}
          />
          <Tab
            label={
              <div style={{ textAlign: "center" }}>
                {parentDate2 ? (
                  <div>
                  <p className="text-[16px]">{parentDate2}</p>
                  <p className="mt-1 text-[16px]">{parentTime2}</p>
                </div>
                ) : (
                  <p>추가 시간 선택</p>
                )}
              </div>
            }
            {...a11yProps(2)}
            sx={{
              margin: "8px",
              borderRadius: "12px",
              minWidth: "3rem",
              "&.MuiTab-root": {
                bgcolor: "#f5f5f5"
              },
            }}
          />
        </Tabs>
      </Box>
      <div>
        {value === 0 && (
          <Calendar
            sendDateToParent={handleDate0}
            sendTimeToParent={handleTime0}
          ></Calendar>
        )}

        {value === 1 && (
          <Calendar
            sendDateToParent={handleDate1}
            sendTimeToParent={handleTime1}
          ></Calendar>
        )}

        {value === 2 && (
          <Calendar
            sendDateToParent={handleDate2}
            sendTimeToParent={handleTime2}
          ></Calendar>
        )}
      </div>
      <div className="flex justify-center">
        <button
          className="mt-8 mb-8 w-5/6 h-16 bg-primary rounded-xl
         bg-yellow-400 text-black"
          onClick={handleClickOpen}
        >
          <h3>거래 수락</h3>
        </button>
      </div>
      <Dialog
        // fullScreen={fullScreen}
        fullScreen
        open={open}
        onClose={handleClose}
        TransitionComponent={Transition}
        sx={{
          "& .MuiDialog-paper": {
            maxWidth: "600px", // 최대 너비 설정
            width: "100%", // 너비는 화면 크기에 따라 조정되도록 설정
            maxHeight: "100vh", // 최대 높이를 화면 높이와 동일하게 설정
          },
        }}
      >
        <div className="flex justify-between">
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
          <IconButton
            color="inherit"
            onClick={handleClose}
            aria-label="close"
            size="large"
            sx={{
              aspectRatio: "1",
              margin: 2,
            }}
          >
            <CloseIcon />
          </IconButton>
        </div>
      </Dialog>
    </React.Fragment>
  );
}
