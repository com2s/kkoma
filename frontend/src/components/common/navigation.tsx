// use client는 import보다 더 먼저 나와야 합니다.
"use client";

import React, { useState } from "react";
import Link from "next/link";
import Button from "@mui/material/Button";
import AppBar from "@mui/material/AppBar";
import BottomNavigation from "@mui/material/BottomNavigation";
import BottomNavigationAction from "@mui/material/BottomNavigationAction";
import { styled } from "@mui/material/styles";
import Fab from "@mui/material/Fab";
import EventAvailableIcon from "@mui/icons-material/EventAvailable";
import ListAltIcon from "@mui/icons-material/ListAlt";
import GridViewIcon from "@mui/icons-material/GridView";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import { useTheme } from "@mui/material/styles";
import Image from "next/image";

const Header = () => {
  const [value, setValue] = React.useState(-1);

  const theme = useTheme();

  const StyledFab = styled(Fab)({
    width: 80,
    height: 80,
    position: "absolute",
    zIndex: 1,
    top: -30,
    left: 0,
    right: 0,
    margin: "0 auto",
    "&.MuiFab-root": {
      // MUI Fab 루트에 대한 스타일을 재정의
      // boxShadow: "0 -2px 0px 1px rgba(0, 0, 0, 0.1), 0px -1px 0px 0px rgba(0, 0, 0, 0.06)",
      boxShadow: theme.shadows[0], // 이는 elevation 0에 해당하는 그림자
      border : "1px solid rgba(0, 0, 0, 0.2)",
      backgroundColor: "white",
    },
  });

  return (
    <header>
      <AppBar
        position="fixed"
        color="default"
        sx={{
          top: "auto",
          left: 0,
          bottom: 0,
          maxWidth: "600px",
          minWidth: "320px",
          margin: "auto",
          borderTop: "1px solid rgba(0, 0, 0, 0.2)",
        }}
        elevation={0} // 기본 그림자 제거
      >
        <BottomNavigation
          showLabels
          value={value}
          onChange={(event, newValue) => {
            setValue(newValue);
          }}
        >
          <BottomNavigationAction
            label="거래 일정"
            icon={<EventAvailableIcon />}
            component={Link}
            href="/plan"
          />
          <BottomNavigationAction
            label="내 거래"
            icon={<ListAltIcon />}
            component={Link}
            href="/my-trade"
          />
          <BottomNavigationAction label="" disabled />
          <BottomNavigationAction
            label="모아보기"
            icon={<GridViewIcon />}
            component={Link}
            href="/lists"
          />
          <BottomNavigationAction
            label="내 정보"
            icon={<PersonOutlineIcon />}
            component={Link}
            href="/my-page"
          />
        </BottomNavigation>
        <StyledFab
          //   color="white"
          aria-label="add"
          size="large"
          href="/"
          onClick={() => {
            setValue(-1);
          }}
        >
          {/* <AddIcon fontSize="large"/> */}
          <Image
            src="/chicken-home.svg"
            alt="Home Logo"
            className="z-2"
            width={50}
            height={50}
            priority
          />
        </StyledFab>
      </AppBar>
    </header>
  );
};

export default Header;
