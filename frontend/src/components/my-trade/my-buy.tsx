import React, { useState, useEffect } from "react";
import { getMyProducts } from "@/components/my-trade/my-trade-ftn";
import { Deal } from "@/types/status";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import MenuItem from "@mui/material/MenuItem";
import Menu from "@mui/material/Menu";
import styles from "@/components/my-trade/sell-buy.module.scss";
import { NoContents } from "../common/no-contents";
import { SmallBtn } from "../common/buttons";
import Image from "next/image";

export default function MyBuy() {
  const [deals, setDeals] = useState<Deal[]>([]);
  const [success, setSuccess] = useState(true);
  const [selectedChip, setSelectedChip] = useState<string>("모두");
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  // 현재 열린 메뉴의 ID를 저장하기 위한 상태 추가
  const [openMenuId, setOpenMenuId] = useState<number | null>(null);
  const open = Boolean(anchorEl);

  useEffect(() => {
    const fetchBuying = async () => {
      const myProducts = await getMyProducts("buy");
      console.log("MyBuy: ", myProducts);
      setDeals(myProducts.data);
      setSuccess(myProducts.success);
    };
    fetchBuying();
  }, []);

  const handleChipClick = (chip: string) => {
    setSelectedChip(chip);
  };

  const handleMenuClick = (
    event: React.MouseEvent<HTMLElement>,
    dealId: number
  ) => {
    setAnchorEl(event.currentTarget);
    setOpenMenuId(dealId); // 메뉴가 열린 카드의 ID 저장
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    setOpenMenuId(null); // 메뉴 닫을 때 ID 초기화
  };

  const handleMenuCloseAndCancel = () => {
    // 거래 요청을 취소할 것인지 확인하고 진행
    if (window.confirm("거래 요청을 취소하시겠습니까?")) {
      // 여기에 거래 요청 취소 API 호출 코드 추가
      handleMenuClose();
      console.log("거래 요청 취소");
    }
  };

  const filteredDeals = deals
    ?.filter((deal) =>
      selectedChip === "모두"
        ? true
        : selectedChip === "요청 중"
        ? deal.status === "SENT"
        : selectedChip === "요청 취소"
        ? deal.status === "CANCELLED"
        : selectedChip === "거래 완료"
        ? deal.status === "SOLD"
        : false
    )
    .sort((a, b) => a.elapsedMinutes - b.elapsedMinutes);

  if (success === false) {
    return (
      <div>
        <h2>구매요청한 글을 불러오는 데 실패했습니다.</h2>
      </div>
    );
  }

  return (
    <React.Fragment>
      <Stack direction="row" spacing={1.5} className={styles.chips}>
        {["모두", "요청 중", "요청 취소", "거래 완료"].map((chip) => (
          <Chip
            key={chip}
            label={chip}
            onClick={() => handleChipClick(chip)}
            color={selectedChip === chip ? "primary" : "default"}
            variant={selectedChip === chip ? "filled" : "outlined"}
          />
        ))}
      </Stack>
      <div className="flex flex-col gap-5">
        {filteredDeals.map((deal) => (
          <Card key={deal.id} variant="outlined" className={styles.card}>
            <Avatar
              alt="Product Image"
              src={deal.thumbnailImage}
              sx={{ width: 80, height: 80 }}
              className={styles.avatar}
              variant="square"
            />
            <CardContent sx={{ padding: 1 }} className={styles.cardMiddle}>
              <Typography variant="h6" component="div">
                {deal.price.toLocaleString()}원{" "}
              </Typography>
              <Typography color="text.secondary">{deal.title}</Typography>
              <Typography variant="body2">
                {deal.dealPlace ?? "거래 장소 null"} •{" "}
                {deal.elapsedMinutes >= 1440 // 1440분 = 24시간
                  ? `${Math.floor(deal.elapsedMinutes / 1440)}일 전`
                  : deal.elapsedMinutes >= 60
                  ? `${Math.floor(deal.elapsedMinutes / 60)}시간 전`
                  : `${deal.elapsedMinutes}분 전`}
              </Typography>
              <Typography variant="caption" className="text-gray-400">
                조회 {deal.viewCount} • 거래 요청 {deal.offerCount} • 찜{" "}
                {deal.wishCount}
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
              <IconButton
                aria-label="settings"
                onClick={(e) => handleMenuClick(e, deal.id)} // 클릭 핸들러에 deal.id 전달
              >
                <MoreVertIcon />
              </IconButton>
              <Menu
                anchorEl={anchorEl}
                open={open && openMenuId === deal.id} // 현재 카드의 메뉴만 열림
                onClose={handleMenuClose}
              >
                <MenuItem onClick={handleMenuCloseAndCancel}>
                  {/* 거래 id : {openMenuId}  */}
                  거래 요청 취소
                </MenuItem>
                {/* <MenuItem onClick={handleMenuClose}>2번째 버튼 임시</MenuItem> */}
              </Menu>
              <Typography
                variant="body1"
                textAlign={"center"}
                sx={{
                  mt: 2,
                  fontWeight: "bold",
                  width: "55px",
                  color:
                    // deal.status2 의 값에 따라 색상을 다르게 표시
                    deal.status === "CANCELLED"
                      ? "crimson"
                      : deal.status === "SOLD"
                      ? "dimgray"
                      : deal.status === "SENT"
                      ? "orange"
                      : "black", // 기본값
                }}
              >
                {deal.status}
              </Typography>
            </CardContent>
          </Card>
        ))}
        {filteredDeals.length === 0 && (
          <NoContents>
            <h4 className="c-text3">구매한 상품이 없어요</h4>
            <Image
              src={"/images/Empty-BOX.png"}
              alt="empty"
              width={100}
              height={100}
            />
            <SmallBtn next={"/lists"}>상품 찾으러 가기</SmallBtn>
          </NoContents>
        )}
      </div>
    </React.Fragment>
  );
}
