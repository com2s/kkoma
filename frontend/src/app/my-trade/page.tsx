"use client";

import React, { Suspense, use, useState } from "react";
import TopBar from "@/components/common/top-bar";
import styles from "@/components/my-trade/my-trade.module.scss";
import MySell from "./components/my-sell";
import MyBuy from "./components/my-buy";

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
      {value === index && (
        <Box sx={{ p: 2 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

export async function getDeal() {
  // new Promise((resolve) => setTimeout(resolve, 500));
  const myDeal = [
    {
      id: "1",
      url: "https://via.placeholder.com/150?text=Item1",
      title: "Sample Title 1",
      productName: "Product 1",
      town: "동네 6",
      time: "2024-03-05 02:40",
      views: 31,
      likes: 39,
      status: "구매",
      status2: "요청 취소",
    },
    {
      id: "2",
      url: "https://via.placeholder.com/150?text=Item2",
      title: "Sample Title 2",
      productName: "Product 2",
      town: "동네 8",
      time: "2024-03-11 23:51",
      views: 114,
      likes: 18,
      status: "판매",
      status2: "판매 중",
    },
    {
      id: "3",
      url: "https://via.placeholder.com/150?text=Item3",
      title: "Sample Title 3",
      productName: "Product 3",
      town: "동네 4",
      time: "2024-03-01 23:22",
      views: 194,
      likes: 99,
      status: "판매",
      status2: "판매 중",
    },
    {
      id: "4",
      url: "https://via.placeholder.com/150?text=Item4",
      title: "Sample Title 4",
      productName: "Product 4",
      town: "동네 2",
      time: "2024-03-01 23:56",
      views: 46,
      likes: 31,
      status: "판매",
      status2: "거래 완료",
    },
    {
      id: "5",
      url: "https://via.placeholder.com/150?text=Item5",
      title: "Sample Title 5",
      productName: "Product 5",
      town: "동네 5",
      time: "2024-02-28 03:37",
      views: 198,
      likes: 49,
      status: "구매",
      status2: "요청 취소",
    },
    {
      id: "6",
      url: "https://via.placeholder.com/150?text=Item6",
      title: "Sample Title 6",
      productName: "Product 6",
      town: "동네 7",
      time: "2024-03-07 22:42",
      views: 70,
      likes: 36,
      status: "구매",
      status2: "요청 취소",
    },
    {
      id: "7",
      url: "https://via.placeholder.com/150?text=Item7",
      title: "Sample Title 7",
      productName: "Product 7",
      town: "동네 7",
      time: "2024-03-06 23:26",
      views: 36,
      likes: 22,
      status: "판매",
      status2: "판매 중",
    },
    {
      id: "8",
      url: "https://via.placeholder.com/150?text=Item8",
      title: "Sample Title 8",
      productName: "Product 8",
      town: "동네 3",
      time: "2024-02-25 20:48",
      views: 36,
      likes: 58,
      status: "구매",
      status2: "거래 완료",
    },
    {
      id: "9",
      url: "https://via.placeholder.com/150?text=Item9",
      title: "Sample Title 9",
      productName: "Product 9",
      town: "동네 9",
      time: "2024-02-23 09:36",
      views: 12,
      likes: 22,
      status: "판매",
      status2: "거래 완료",
    },
    {
      id: "10",
      url: "https://via.placeholder.com/150?text=Item10",
      title: "Sample Title 10",
      productName: "Product 10",
      town: "동네 4",
      time: "2024-02-13 22:01",
      views: 32,
      likes: 17,
      status: "구매",
      status2: "거래 완료",
    },
    {
      id: "11",
      url: "https://via.placeholder.com/150?text=Item11",
      title: "Sample Title 11",
      productName: "Product 11",
      town: "동네 3",
      time: "2024-02-19 05:00",
      views: 156,
      likes: 29,
      status: "판매",
      status2: "판매 중",
    },
    {
      id: "12",
      url: "https://via.placeholder.com/150?text=Item12",
      title: "Sample Title 12",
      productName: "Product 12",
      town: "동네 10",
      time: "2024-02-14 05:36",
      views: 8,
      likes: 18,
      status: "구매",
      status2: "요청 중",
    },
    {
      id: "13",
      url: "https://via.placeholder.com/150?text=Item13",
      title: "Sample Title 13",
      productName: "Product 13",
      town: "동네 6",
      time: "2024-02-15 16:54",
      views: 116,
      likes: 42,
      status: "구매",
      status2: "거래 완료",
    },
    {
      id: "14",
      url: "https://via.placeholder.com/150?text=Item14",
      title: "Sample Title 14",
      productName: "Product 14",
      town: "동네 4",
      time: "2024-03-04 06:55",
      views: 84,
      likes: 12,
      status: "판매",
      status2: "판매 중",
    },
    {
      id: "15",
      url: "https://via.placeholder.com/150?text=Item15",
      title: "Sample Title 15",
      productName: "Product 15",
      town: "동네 8",
      time: "2024-02-28 07:27",
      views: 135,
      likes: 92,
      status: "구매",
      status2: "요청 취소",
    },
    {
      id: "16",
      url: "https://via.placeholder.com/150?text=Item16",
      title: "Sample Title 16",
      productName: "Product 16",
      town: "동네 8",
      time: "2024-02-27 08:51",
      views: 122,
      likes: 5,
      status: "판매",
      status2: "판매 중",
    },
    {
      id: "17",
      url: "https://via.placeholder.com/150?text=Item17",
      title: "Sample Title 17",
      productName: "Product 17",
      town: "동네 8",
      time: "2024-03-01 20:59",
      views: 197,
      likes: 82,
      status: "판매",
      status2: "거래 완료",
    },
    {
      id: "18",
      url: "https://via.placeholder.com/150?text=Item18",
      title: "Sample Title 18",
      productName: "Product 18",
      town: "동네 6",
      time: "2024-02-15 02:44",
      views: 2,
      likes: 62,
      status: "판매",
      status2: "판매 중",
    },
    {
      id: "19",
      url: "https://via.placeholder.com/150?text=Item19",
      title: "Sample Title 19",
      productName: "Product 19",
      town: "동네 9",
      time: "2024-03-09 17:11",
      views: 182,
      likes: 1,
      status: "구매",
      status2: "거래 완료",
    },
    {
      id: "20",
      url: "https://via.placeholder.com/150?text=Item20",
      title: "Sample Title 20",
      productName: "Product 20",
      town: "동네 9",
      time: "2024-02-28 04:42",
      views: 112,
      likes: 39,
      status: "구매",
      status2: "구매 중",
    },
  ];
  return myDeal;
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
            // textColor="secondary"
            // indicatorColor="secondary"

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
            {/* 기본 텍스트 색 바꾸려면... */}
            {/* <Tab label="Item One" {...a11yProps(0)} sx={{ color: '#ff682e' }} /> */}
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
    </div>
  );
}
