import React, { useState, useEffect } from "react";
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
import { getDeal } from "@/components/my-trade/my-trade-ftn";

interface Deal {
  id: string;
  url: string;
  title: string;
  productName: string;
  town: string;
  time: string;
  views: number;
  likes: number;
  status: string;
  status2: string;
}

export default function MyBuy() {
  const [deals, setDeals] = useState<Deal[]>([]);
  const [selectedChip, setSelectedChip] = useState<string>("모두");
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  // 현재 열린 메뉴의 ID를 저장하기 위한 상태 추가
  const [openMenuId, setOpenMenuId] = useState<string | null>(null);
  const open = Boolean(anchorEl);

  useEffect(() => {
    const fetchBuying = async () => {
      const myDeal: Deal[] = await getDeal();
      const buyingDeals = myDeal.filter((deal) => deal.status === "구매");
      setDeals(buyingDeals);
    };
    fetchBuying();
  }, []);

  const handleChipClick = (chip: string) => {
    setSelectedChip(chip);
  };

  const handleMenuClick = (
    event: React.MouseEvent<HTMLElement>,
    dealId: string
  ) => {
    setAnchorEl(event.currentTarget);
    setOpenMenuId(dealId); // 메뉴가 열린 카드의 ID 저장
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    setOpenMenuId(null); // 메뉴 닫을 때 ID 초기화
  };

  const filteredDeals = deals
    .filter((deal) =>
      selectedChip === "모두" ? true : deal.status2 === selectedChip
    )
    .sort((a, b) => new Date(b.time).getTime() - new Date(a.time).getTime());

  return (
    <div>
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
      <div>
        {filteredDeals.map((deal) => (
          <Card key={deal.id} variant="outlined" className={styles.card}>
            <Avatar
              alt="Product Image"
              src={deal.url}
              sx={{ width: 80, height: 80 }}
              className={styles.avatar}
              variant="square"
            />
            <CardContent sx={{ padding: 1 }} className={styles.cardMiddle}>
              <Typography variant="h6" component="div">
                {deal.title}
              </Typography>
              <Typography color="text.secondary">{deal.productName}</Typography>
              <Typography variant="body2">
                {deal.town} | {deal.time}
              </Typography>
              <Typography variant="body2">
                Views: {deal.views} | Likes: {deal.likes}
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
                <MenuItem onClick={handleMenuClose}>
                  열린 카드 id : {openMenuId}
                </MenuItem>
                <MenuItem onClick={handleMenuClose}>2번째 버튼 임시</MenuItem>
              </Menu>
              <Typography
                variant="body2"
                textAlign={"center"}
                sx={{
                  mt: 2,
                  fontWeight: "bold",
                  width: "55px",
                  color:
                  // deal.status2 의 값에 따라 색상을 다르게 표시
                    deal.status2 === "요청 취소"
                      ? "crimson"
                      : deal.status2 === "거래 완료"
                      ? "dimgray"
                      : deal.status2 === "요청 중"
                      ? "orange"
                      : "black", // 기본값
                }}
              >
                {deal.status2}
              </Typography>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}
