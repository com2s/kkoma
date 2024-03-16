// use client는 import보다 더 먼저 나와야 합니다.
"use client";

import React, { useEffect, useState } from "react";
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
import { useMediaQuery } from "@mui/material";

const Header = () => {
  const [value, setValue] = React.useState(-1);

  const theme = useTheme();

  const StyledFab = styled(Fab)(({ theme }) => {
    const isSmallScreen = useMediaQuery(theme.breakpoints.down(370));

    return {
      width: isSmallScreen ? "60px" : "80px",
      height: isSmallScreen ? "60px" : "80px",
      position: "absolute",
      zIndex: 1,
      top: isSmallScreen ? "-24px" : "-30px", // Adjust top position as well if needed
      left: 0,
      right: 0,
      margin: "0 auto",
      "&.MuiFab-root": {
        boxShadow: theme.shadows[0],
        border: "1px solid rgba(0, 0, 0, 0.2)",
        backgroundColor: "white",
      },
    };
  });

  const [size, setSize] = useState({ width: 50, height: 50 });
  const [isHidden, setIsHidden] = useState(false);

  useEffect(() => {
    const updateSize = () => {
      if (window.innerWidth > 370) {
        setSize({ width: 50, height: 50 });
        setIsHidden(false);
      } else {
        setSize({ width: 40, height: 40 });
        setIsHidden(true);
      }
    };

    window.addEventListener("resize", updateSize);
    updateSize(); // Initial check

    return () => window.removeEventListener("resize", updateSize);
  }, []);

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
            // 화면 크기가 400px 이하일 때는 패딩 없애기
            sx={{ padding: "0", minWidth: "55px" }}
          />
          <BottomNavigationAction
            label="내 거래"
            icon={<ListAltIcon />}
            component={Link}
            href="/my-trade"
            sx={{ padding: "0", minWidth: "55px" }}
          />
          <BottomNavigationAction
            label="1"
            disabled
            sx={{ padding: "0", width: "0px", }}
            hidden={isHidden}
          />
          <BottomNavigationAction
            label="모아보기"
            icon={<GridViewIcon />}
            component={Link}
            href="/lists"
            sx={{ padding: "0", minWidth: "55px" }}
          />
          <BottomNavigationAction
            label="내 정보"
            icon={<PersonOutlineIcon />}
            component={Link}
            href="/my-page"
            sx={{ padding: "0", minWidth: "55px" }}
          />
        </BottomNavigation>
        <Link href="/"
          onClick={() => {
            setValue(-1);
          }}
          hidden={isHidden}
          >
        <StyledFab
          //   color="white"
          aria-label="add"
          size="large"
          
        >
          {/* <AddIcon fontSize="large"/> */}
          <Image
            src="/chicken-home.svg"
            alt="Home Logo"
            className="z-2"
            width={size.width}
            height={size.height}
            priority
          />
        </StyledFab>
        </Link>
      </AppBar>
    </header>
  );
};

export default Header;
