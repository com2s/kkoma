"use client";

import TopBar2 from "@/components/lists/lists-calendar-bar";
import Calendar from "@/components/common/calendar-offer";
import RequestDone from "@/components/lists/request-done";
import { postOffer } from "@/components/lists/lists-ftn";
import Image from "next/image";
import React, { useState } from "react";
import Dialog from "@mui/material/Dialog";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import Slide from "@mui/material/Slide";
import { TransitionProps } from "@mui/material/transitions";
import useMediaQuery from "@mui/material/useMediaQuery";
import { Box, Tab, Tabs, useTheme } from "@mui/material";
import { useRouter } from "next/navigation";
import Title from "@/components/common/title";
import TopBar3 from "@/components/common/top-bar3";
import { ButtonContainer, NormalBtn } from "@/components/common/buttons";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

interface TimeEntry {
  date: string;
  time: string;
}

interface IParams {
  params: { id: string };
}

export default function RequestDeal({ params: { id } }: IParams) {
  const [value, setValue] = useState(0);
  const [open, setOpen] = useState(false);
  const [parentDate0, setParentDate0] = useState("");
  const [parentDate1, setParentDate1] = useState("");
  const [parentTime0, setParentTime0] = useState("");
  const [parentTime1, setParentTime1] = useState("");
  const [selectedTimes, setSelectedTimes] = useState<TimeEntry[]>([]);

  const router = useRouter();

  const handleDate0 = (data: string) => {
    if (data) {
      setParentDate0(data);
    } else {
      setParentDate0("");
    }
  };

  const handleDate1 = (data: string) => {
    if (data) {
      setParentDate1(data);
    } else {
      setParentDate1("");
    }
  };

  const handleTime0 = (data: string) => {
    setParentTime0(data);
  };
  const handleTime1 = (data: string) => {
    setParentTime1(data);
  };

  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const handleClickOpen = async () => {
    const selectedTimes = [
      [parentDate0, parentTime0],
      [parentDate1, parentTime1],
    ];
    handleSelectedTimes();

    const res = await postOffer(id, selectedTimes);
    if (res.success) {
      setOpen(true);
    } else {
      alert("거래 요청을 실패했습니다.");
      console.log(res.error.errorMessage);
    }
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSelectedTimes = () => {
    const newSelectedTimes = []; // 빈 배열 생성

    if (parentDate0 && parentTime0) {
      newSelectedTimes.push({ date: parentDate0, time: parentTime0 });
    }
    if (parentDate1 && parentTime1) {
      newSelectedTimes.push({ date: parentDate1, time: parentTime1 });
    }

    setSelectedTimes(newSelectedTimes); // 새로운 상태 설정
  };

  function a11yProps(index: number) {
    return {
      id: `simple-tab-${index}`,
      "aria-controls": `simple-tabpanel-${index}`,
    };
  }

  return (
    <React.Fragment>
      <TopBar3 />
      <div className="mt-5"></div>
      <Title title="거래 희망 일시를 선택해주세요." subtitle="최대 2개까지 선택할 수 있어요" />
      <Image
        src="/images/calendar1.svg"
        alt="달력"
        width={180}
        height={180}
        className="mx-auto my-6"
      />
      <Box sx={{ borderBottom: 0, borderColor: "divider" }}>
        <Tabs
          value={value}
          onChange={handleChange}
          aria-label="basic tabs example"
          variant="fullWidth"
          sx={{
            margin: "0 auto",
            width: "100%",
            maxWidth: "500px",
            gap: "10px",
          }}
        >
          <Tab
            label={
              <div style={{ textAlign: "center" }}>
                {parentDate0 ? (
                  <div>
                    <p className="text-[15px]">{parentDate0}</p>
                    <p className="mt-1 text-[15px]">{parentTime0}</p>
                  </div>
                ) : (
                  <p>일시 선택</p>
                )}
              </div>
            }
            {...a11yProps(0)}
            sx={{
              margin: "6px",
              padding: "2px",
              borderRadius: "12px",
              height: "5rem",
              minWidth: "3rem",
              "&.MuiTab-root": {
                bgcolor: "#f5f5f5",
              },
            }}
          />
          <Tab
            label={
              <div style={{ textAlign: "center" }}>
                {parentDate1 ? (
                  <div>
                    <p className="text-[15px]">{parentDate1}</p>
                    <p className="mt-1 text-[15px]">{parentTime1}</p>
                  </div>
                ) : (
                  <p>일시 선택</p>
                )}
              </div>
            }
            {...a11yProps(1)}
            sx={{
              margin: "6px",
              padding: "2px",
              borderRadius: "12px",
              height: "5rem",
              minWidth: "3rem",
              "&.MuiTab-root": {
                bgcolor: "#f5f5f5",
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
            selectedTimes={[
              [parentDate0, parentTime0],
              [parentDate1, parentTime1],
            ]}
            index={0}
          ></Calendar>
        )}

        {value === 1 && (
          <Calendar
            sendDateToParent={handleDate1}
            sendTimeToParent={handleTime1}
            selectedTimes={[
              [parentDate0, parentTime0],
              [parentDate1, parentTime1],
            ]}
            index={1}
          ></Calendar>
        )}
      </div>
      <div className="flex justify-center">
        <button
          className="mt-8 mb-8 w-full h-16 bg-primary rounded-xl
         bg-yellow-400 text-black"
          onClick={handleClickOpen}
        >
          <h3>선택</h3>
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
            maxHeight: "100%", // 최대 높이를 화면 높이와 동일하게 설정
            height: "103vh",
          },
        }}
      >
        <div className="flex justify-end">
          <IconButton
            color="inherit"
            onClick={() => router.push("/my-trade")}
            aria-label="close"
            sx={{
              aspectRatio: "1",
              maxWidth: "45px",
              maxHeight: "45px",
              margin: 2,
            }}
          >
            <CloseIcon fontSize="large" />
          </IconButton>
        </div>
        <RequestDone selectedTimes={selectedTimes} location="" />
        <ButtonContainer>
          <NormalBtn next={() => router.push("/my-trade")}>확인</NormalBtn>
        </ButtonContainer>
      </Dialog>
    </React.Fragment>
  );
}
