// use client는 import보다 더 먼저 나와야 합니다.
"use client";

import React, { useEffect, useState } from "react";
import Link from "next/link";
import AppBar from "@mui/material/AppBar";
import BottomNavigation from "@mui/material/BottomNavigation";
import BottomNavigationAction from "@mui/material/BottomNavigationAction";
import { styled } from "@mui/material/styles";
import Fab from "@mui/material/Fab";
import EventAvailableIcon from "@mui/icons-material/EventAvailable";
import FormatListBulletedRoundedIcon from "@mui/icons-material/FormatListBulletedRounded";
import GridViewRoundedIcon from "@mui/icons-material/GridViewRounded";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";
import { useTheme } from "@mui/material/styles";
import Image from "next/image";
import { useMediaQuery } from "@mui/material";

const Header = () => {
  const [value, setValue] = React.useState(-1);

  const theme = useTheme();
  const isSmallScreen = useMediaQuery(theme.breakpoints.down(370));

  const StyledFab = styled(Fab)(({ theme }) => {
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
        backgroundColor: "white",
        borderRadius: "50%",
        boxShadow: "0 -2px 4px 0 rgba(210, 210, 210, 0.25)",
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
          borderRadius: "15px 15px 0 0",
          boxShadow: "0 -15px 15px 0 white",
        }}
        elevation={0} // 기본 그림자 제거
      >
        <BottomNavigation
          showLabels
          value={value}
          onChange={(event, newValue) => {
            setValue(newValue);
          }}
          sx={{
            borderRadius: "15px 15px 0 0",
            boxShadow: "0 -2px 4px 0 rgba(210, 210, 210, 0.25)",
          }}
        >
          <BottomNavigationAction
            label="거래 일정"
            icon={<EventAvailableIcon />}
            component={Link}
            href="/plan"
            // 화면 크기가 400px 이하일 때는 패딩 없애기
            sx={{ minWidth: "55px" }}
          />
          <BottomNavigationAction
            label="내 거래"
            icon={<FormatListBulletedRoundedIcon />}
            component={Link}
            href="/my-trade"
            sx={{ minWidth: "55px" }}
          />
          <BottomNavigationAction label="1" disabled sx={{ width: "0px" }} hidden={isHidden} />
          <BottomNavigationAction
            label="모아보기"
            icon={<GridViewRoundedIcon />}
            component={Link}
            href="/lists"
            sx={{ minWidth: "55px" }}
          />
          <BottomNavigationAction
            label="내 정보"
            icon={<PersonOutlineIcon />}
            component={Link}
            href="/my-page"
            sx={{ minWidth: "55px" }}
          />
        </BottomNavigation>
        <Link
          href="/"
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
