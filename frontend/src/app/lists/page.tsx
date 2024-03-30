"use client";

import React, { useState, useEffect } from "react";
import TopBar from "@/components/common/top-bar";
import styles from "@/components/lists/lists.module.scss";
import Navigation from "@/components/common/navigation";
import { ProductCard } from "@/components/common/product-card";
import { getCategoryAPI, getSearchProductAPI } from "@/services/product";

// 인터페이스
import { SearchParms } from "@/types/search";
import { Category, ProductSm } from "@/types/product";

import Link from "next/link";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";
import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import Box from "@mui/material/Box";
import Fab from "@mui/material/Fab";
import { InputAdornment, TextField, useTheme } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import SearchIcon from "@mui/icons-material/Search";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";
import MenuItem from "@mui/material/MenuItem";

export default function ListPage() {
  const theme = useTheme();
  const [categoryOptions, setCategoryOptions] = useState<Array<Category>>([
    { id: null, name: "카테고리" },
  ]);
  const statusOptions = [
    { id: "SALE", name: "판매중" },
    { id: "PROGRESS", name: "거래중" },
    { id: "PROGRESS", name: "거래중" },
  ];
  const [anchorEls, setAnchorEls] = useState({
    region: null,
    category: null,
    status: null,
  });
  const [showSearch, setShowSearch] = useState(false); // 검색 필드 표시 여부
  const [products, setProducts] = useState<Array<ProductSm>>();
  const [searchQuery, setSearchQuery] = useState<SearchParms>({
    regionCode: null,
    categoryId: null,
    memberId: null,
    keyword: "",
    status: null,
    page: 0,
    size: 10,
    sort: "createdAt,desc",
  });

  // 검색어 변경 처리
  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery((prev) => ({ ...prev, keyword: event.target.value }));
  };

  // 검색어 초기화
  const clearSearch = () => {
    setSearchQuery((prev) => ({ ...prev, keyword: null }));
  };

  const handleCategoryClick = (option: number | null) => {
    setSearchQuery((prev) => ({ ...prev, categoryId: option }));
    setAnchorEls({ ...anchorEls, category: null });
  };

  const handleCategory = (e: any) => {
    setAnchorEls({ ...anchorEls, category: e.currentTarget });
  };

  const fetchData = async () => {
    const product = await getSearchProductAPI(searchQuery);
    setProducts(product?.content);
  };

  const fetchOptions = async () => {
    const category = await getCategoryAPI();
    setCategoryOptions([{ id: null, name: "카테고리" }, ...category]);
  };

  useEffect(() => {
    // fetchOptions();
  }, []);

  useEffect(() => {
    fetchData();
  }, [searchQuery]);

  return (
    <div className={styles.container}>
      <TopBar />
      <div
        className={
          showSearch ? "mt-2 mb-2 flex justify-center items-center" : ""
        }
      >
        {showSearch && (
          <Box className="flex items-center justify-center w-full px-2">
            <TextField
              variant="outlined"
              placeholder="검색어를 입력해주세요."
              value={searchQuery.keyword}
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
          </Box>
        )}
      </div>
      <Stack
        direction="row"
        spacing={1}
        className={`${styles.chips} flex-wrap justify-between gap-2 `}
      >
        <Stack
          direction="row"
          spacing={1}
          className={`${styles.chips} flex-wrap gap-2 `}
        >
          <Box className={styles.box}>
            <Chip
              label={
                categoryOptions.find(
                  (item) => item.id == searchQuery?.categoryId
                )?.name
              }
              onClick={handleCategory}
              color={searchQuery?.categoryId ? "primary" : "default"}
              variant={searchQuery?.categoryId ? "filled" : "outlined"}
              className={styles.chip}
            />
            <Menu
              anchorEl={anchorEls.category}
              open={Boolean(anchorEls.category)}
            >
              {categoryOptions?.map((option, k) => (
                <MenuItem
                  key={k}
                  onClick={() => handleCategoryClick(option.id)}
                >
                  {option.name}
                </MenuItem>
              ))}
            </Menu>
          </Box>
        </Stack>
        <Box>
          <IconButton
            aria-label="search"
            onClick={() => setShowSearch(!showSearch)}
          >
            <SearchIcon
              style={{
                color: showSearch ? "pink" : "inherit",
              }}
            />
          </IconButton>
        </Box>
      </Stack>
      <Link href="lists/create">
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
      <div className="flex flex-col gap-5 mt-3">
        {products && products.length > 0 ? (
          products.map((item) => (
            <Link href={`/lists/${item.id}`} key={item.id}>
              <ProductCard product={item} />
            </Link>
          ))
        ) : (
          <div>거래글이 없습니다.</div>
        )}
      </div>
      {/* 본문 리스트 끝 */}
      <Navigation />
    </div>
  );
}
