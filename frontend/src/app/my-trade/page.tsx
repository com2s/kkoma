"use client";

import React, { Suspense, use, useState } from "react";
import TopBar from "@/components/common/top-bar";
import Navigation from "@/components/common/navigation";
import styles from "@/components/my-trade/my-trade.module.scss";
import MySell from "@/components/my-trade/my-sell";
import MyBuy from "@/components/my-trade/my-buy";

import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function CustomTabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ p: 2 }}>{children}</Box>}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

export default function MyTradePage() {
  const [value, setValue] = useState(0);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  return (
    <div className={styles.container}>
      <TopBar />
      {/* 탭 */}
      <Box sx={{ width: "100%", ".MuiBox-root": { padding: 0 } }}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <Tabs
            value={value}
            onChange={handleChange}
            aria-label="basic tabs example"
            centered
            variant="fullWidth"
            // Tabs 컨테이너 스타일
            sx={{
              ".MuiTabs-indicator": {
                backgroundColor: "#e52d2b", // 여기에 원하는 인디케이터 색상 코드를 입력하세요.
              },
              ".MuiButtonBase-root.MuiTab-root.Mui-selected": {
                // 선택된 탭의 스타일
                color: "#e52d2b", // 선택된 탭의 텍스트 색상
                fontSize: "1.15rem", // 선택된 탭의 텍스트 크기
                fontWeight: "bold", // 선택된 탭의 텍스트 굵기
              },
              "& .MuiButtonBase-root": {
                // 모든 탭의 기본 스타일
                color: "rgba(0, 0, 0, 0.5)", // 비활성 탭의 텍스트 색상
                fontSize: "1rem", // 비활성 탭 텍스트의 기본 크기
              },
            }}
          >
            <Tab label="판매" {...a11yProps(0)} />
            <Tab label="구매" {...a11yProps(1)} />
            {/* <Tab label="Item Three" {...a11yProps(2)} /> */}
          </Tabs>
        </Box>
        {/* 본문 */}
        <CustomTabPanel value={value} index={0}>
          <Suspense fallback={<h2>내 판매를 불러오는 중입니다...</h2>}>
            <MySell />
          </Suspense>
        </CustomTabPanel>
        <CustomTabPanel value={value} index={1}>
          <Suspense fallback={<h2>내 구매를 불러오는 중입니다...</h2>}>
            <MyBuy />
          </Suspense>
        </CustomTabPanel>
        {/* <CustomTabPanel value={value} index={2}>
        Item Three
      </CustomTabPanel> */}
      </Box>
      <Navigation />
    </div>
  );
}
