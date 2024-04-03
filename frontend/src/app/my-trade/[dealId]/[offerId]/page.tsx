"use client";

import TopBar2 from "@/components/my-trade/my-request-calender-bar";
import Calendar from "@/components/common/calendar";
import Accept from "@/components/my-trade/accept";
import { patchOfferAccept } from "@/components/my-trade/my-trade-ftn";
import Image from "next/image";
import React, { useState } from "react";

import Dialog from "@mui/material/Dialog";
import IconButton from "@mui/material/IconButton";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import Slide from "@mui/material/Slide";
import { TransitionProps } from "@mui/material/transitions";
import useMediaQuery from "@mui/material/useMediaQuery";
import { Box, Tab, Tabs, useTheme } from "@mui/material";
import Title from "@/components/common/title";
import { ButtonContainer, NormalBtn } from "@/components/common/buttons";

import { useRouter } from "next/navigation";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

interface IParams {
  params: {
    dealId: string;
    offerId: string;
  };
}

export default function MyTradeCalender({ params: { offerId } }: IParams) {
  const router = useRouter();

  const [value, setValue] = useState(0);
  const [open, setOpen] = useState(false);
  const [parentDate0, setParentDate0] = useState("");
  const [parentTime0, setParentTime0] = useState("");

  const handleDate0 = (data: string) => {
    if (data) {
      setParentDate0(data);
    } else {
      setParentDate0("");
    }
  };

  const handleTime0 = (data: string) => {
    setParentTime0(data);
  };

  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down("sm"));

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const handleClickOpen = async () => {
    const res = await patchOfferAccept(offerId, parentDate0, parentTime0);
    console.log(res);
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
      <div>
        <Title title="거래 일시를 선택해주세요" subtitle="선택하신 날짜로 거래 일정이 확정돼요" />
        <Image
          src="/images/calendar1.svg"
          alt="달력"
          width={180}
          height={180}
          className="mx-auto my-6"
        />
      </div>
      <Box sx={{ borderBottom: 0, borderColor: "divider" }}>
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
                    <p className="text-body">{parentDate0}</p>
                    <p className="mt-1 text-body">{parentTime0}</p>
                  </div>
                ) : (
                  <span className="text-caption">시간 선택</span>
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
            isAccept
          ></Calendar>
        )}
      </div>
      <ButtonContainer>
        <NormalBtn next={handleClickOpen} disabled={parentDate0 === "" || parentTime0 === ""}>
          거래 수락
        </NormalBtn>
      </ButtonContainer>
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
        </div>
        <Accept />
        {/* 우선 임시로 닫는 버튼 */}
        <ButtonContainer>
          <NormalBtn next={() => router.push("")}>채팅 하러 가기</NormalBtn>
        </ButtonContainer>
      </Dialog>
    </React.Fragment>
  );
}
