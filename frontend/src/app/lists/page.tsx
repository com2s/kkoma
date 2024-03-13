"use client";

import React, { useState, useEffect } from "react";
import TopBar from "@/components/common/top-bar";
import styles from "@/components/lists/lists.module.scss";
import { getProducts } from "@/components/lists/lists-ftn";

import Link from "next/link";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";
import IconButton from "@mui/material/IconButton";
import MenuItem from "@mui/material/MenuItem";
import Menu from "@mui/material/Menu";
import Box from "@mui/material/Box";
import Fab from "@mui/material/Fab";
import {
  Avatar,
  Card,
  CardContent,
  InputAdornment,
  TextField,
  Typography,
  useTheme,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import EditIcon from "@mui/icons-material/Edit";
import MenuIcon from "@mui/icons-material/Menu";
import SearchIcon from "@mui/icons-material/Search";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";
import UnfoldLessIcon from "@mui/icons-material/UnfoldLess";

// 인터페이스
interface Product {
  id: string;
  url: string;
  price: string;
  productName: string;
  town: string;
  time: string;
  views: number;
  likes: number;
  status: string;
}

// 옵션 데이터
const chipData = {
  region: {
    default: "거래 지역",
    options: ["거래 지역", "지역 1", "지역 2", "지역 3"],
  },
  age: {
    default: "사용 연령",
    options: ["사용 연령", "연령대 1", "연령대 2", "연령대 3"],
  },
  status: {
    default: "거래 상태",
    options: ["거래 상태", "판매 중", "거래 중", "거래 완료"],
  },
};

export default function ListPage() {
  const theme = useTheme();
  const [chips, setChips] = useState({
    region: chipData.region.default,
    age: chipData.age.default,
    status: chipData.status.default,
  });
  const [anchorEls, setAnchorEls] = useState({
    region: null,
    age: null,
    status: null,
  });

  const [showSearch, setShowSearch] = useState(false); // 검색 필드 표시 여부
  const [searchText, setSearchText] = useState(""); // 검색어

  // 검색어 변경 처리
  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchText(event.target.value);
  };

  // 검색어 초기화
  const clearSearch = () => {
    setSearchText("");
  };

  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    const fetchProducts = async () => {
      const product: Product[] = await getProducts();
      setProducts(product);
    };
    fetchProducts();
  }, []);

  const handleChipClick = (
    chipKey: keyof typeof chips,
    event: React.MouseEvent<HTMLDivElement>
  ) => {
    setAnchorEls({ ...anchorEls, [chipKey]: event.currentTarget });
  };

  const handleMenuClose = (chipKey: keyof typeof chips, option: string) => {
    setChips({ ...chips, [chipKey]: option });
    setAnchorEls({ ...anchorEls, [chipKey]: null });
  };

  // 필터링된 제품 목록. 검색어에 따라 필터링
  // const filteredProducts = products

  const filteredProducts = products
    .filter(
      (product) =>
        chips.status === "거래 상태" ? true : product.status === chips.status
      // chips.region, chips.age 에 대해서도 마찬가지로 가능
    )
    .filter((product) =>
      product.productName.toLowerCase().includes(searchText.toLowerCase())
    )
    .sort((a, b) => new Date(b.time).getTime() - new Date(a.time).getTime());

  return (
    <div className={styles.container}>
      <TopBar />
      <div
        className={
          showSearch ? "mt-4 mb-4 flex justify-center items-center" : ""
        }
      >
        {showSearch && (
          <Box className="flex items-center justify-center w-full px-2">
            <TextField
              variant="outlined"
              placeholder="검색어를 입력해주세요."
              value={searchText}
              onChange={handleSearchChange}
              className="w-full md:w-3/4 lg:w-5/6" // 너비 설정을 조금 조정
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton onClick={clearSearch}>
                      <CancelOutlinedIcon />
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
            <IconButton className="ml-2" onClick={() => setShowSearch(false)}>
              <UnfoldLessIcon fontSize="large" />
            </IconButton>
          </Box>
        )}
      </div>
      <Stack
        direction="row"
        spacing={1}
        className={styles.chips}
        justifyContent={"space-between"}
      >
        {Object.entries(chipData).map(
          ([key, { default: defaultLabel, options }]) => (
            <Box key={key} className={styles.box}>
              <Chip
                label={chips[key as keyof typeof chips]}
                onClick={(e) => handleChipClick(key as keyof typeof chips, e)}
                color={
                  chips[key as keyof typeof chips] !== defaultLabel
                    ? "primary"
                    : "default"
                }
                variant={
                  chips[key as keyof typeof chips] !== defaultLabel
                    ? "filled"
                    : "outlined"
                }
                className={styles.chip}
              />
              <Menu
                anchorEl={anchorEls[key as keyof typeof chips]}
                open={Boolean(anchorEls[key as keyof typeof chips])}
                onClose={() =>
                  handleMenuClose(
                    key as keyof typeof chips,
                    chips[key as keyof typeof chips]
                  )
                }
              >
                {options.map((option) => (
                  <MenuItem
                    key={option}
                    onClick={() =>
                      handleMenuClose(key as keyof typeof chips, option)
                    }
                  >
                    {option}
                  </MenuItem>
                ))}
              </Menu>
            </Box>
          )
        )}
        <Box>
          <IconButton
            aria-label="search"
            onClick={() => setShowSearch(!showSearch)}
          >
            <SearchIcon
              style={{
                color: searchText ? "deepskyblue" : "inherit",
                fontSize: searchText ? "30px" : "inherit",
              }}
            />
          </IconButton>
          {/* <IconButton aria-label="menu">
            <MenuIcon />
          </IconButton> */}
        </Box>
      </Stack>
      <Link href="lists/write">
        <Fab
          color="primary"
          sx={{
            "&.MuiFab-root": {
              // MUI Fab 루트에 대한 스타일을 재정의
              boxShadow: theme.shadows[2], // 이는 elevation 1에 해당하는 그림자입니다.
              backgroundColor: "gold",
              color: "black",
            },
          }}
          className={styles.fab}
          aria-label="add"
        >
          <EditIcon />
        </Fab>
      </Link>
      {/* 본문 리스트 시작 */}
      <div>
        {filteredProducts.map((product) => (
          <Link href={`/lists/${product.id}`} key={product.id}>
            <Card key={product.id} variant="outlined" className={styles.card}>
              <Avatar
                alt="Product Image"
                src={product.url}
                sx={{ width: 80, height: 80 }}
                className={styles.avatar}
                variant="square"
              />
              <CardContent sx={{ padding: 1 }} className={styles.cardMiddle}>
                <Typography variant="h6" component="div">
                  {parseInt(product.price).toLocaleString()}원
                </Typography>
                <Typography color="text.secondary">
                  {product.productName}
                </Typography>
                <Typography variant="body2">
                  {product.town} | {product.time}
                </Typography>
                <Typography variant="body2">
                  Views: {product.views} | Likes: {product.likes}
                </Typography>
              </CardContent>
              <CardContent
                sx={{
                  padding: 0,
                  margin: 0,
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "center",
                }}
              >
                <Typography variant="body2">닉네임</Typography>
                <Typography
                  variant="body1"
                  sx={{
                    mt: 2,
                    fontWeight: "bold",
                    color:
                      product.status === "거래 중"
                        ? "crimson"
                        : product.status === "거래 완료"
                        ? "dimgray"
                        : product.status === "판매 중"
                        ? "orange"
                        : "black", // 기본값
                  }}
                >
                  {product.status}
                </Typography>
              </CardContent>
            </Card>
          </Link>
        ))}
      </div>
    </div>
  );
}
