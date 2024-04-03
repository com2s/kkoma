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
import Link from "next/link";
import { NoContents } from "../common/no-contents";
import Image from "next/image";
import { SmallBtn } from "../common/buttons";
import { ProductCard } from "../common/product-card";

export default function MySell() {
  const [deals, setDeals] = useState<Deal[]>([]);
  const [success, setSuccess] = useState(true);
  const [selectedChip, setSelectedChip] = useState<string>("모두");
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  // 현재 열린 메뉴의 ID를 저장하기 위한 상태 추가
  const [openMenuId, setOpenMenuId] = useState<number | null>(null);
  const open = Boolean(anchorEl);

  useEffect(() => {
    const fetchSelling = async () => {
      const myProducts = await getMyProducts("sell");
      setDeals(myProducts.data);
      setSuccess(myProducts.success);
    };
    fetchSelling();
    // console.log("fetchSelling");
  }, []);

  const handleChipClick = (chip: string) => {
    setSelectedChip(chip);
  };

  const handleMenuClick = (event: React.MouseEvent<HTMLElement>, dealId: number) => {
    setAnchorEl(event.currentTarget);
    setOpenMenuId(dealId); // 메뉴가 열린 카드의 ID 저장
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    setOpenMenuId(null); // 메뉴 닫을 때 ID 초기화
  };

  const [isTradeRequestDialogOpen, setIsTradeRequestDialogOpen] = useState(false);

  const handleRequestOpen = () => {
    setIsTradeRequestDialogOpen(true); // 다이얼로그를 여는 함수
    handleMenuClose(); // 메뉴를 닫음
  };

  const handleMenuCloseAndDelete = () => {
    if (window.confirm("이 판매글을 삭제하시겠습니까?")) {
      // 여기에 판매글 삭제 API 호출 코드 추가
      handleMenuClose();
      console.log("판매글 삭제");
    }
  };

  const filteredDeals = deals
    ?.filter((deal) =>
      selectedChip === "모두"
        ? true
        : selectedChip === "판매 중"
        ? deal.status === "SALE"
        : selectedChip === "거래 완료"
        ? deal.status === "SOLD"
        : false
    )
    .sort((a, b) => a.elapsedMinutes - b.elapsedMinutes);

  if (success === false) {
    return (
      <div>
        <h2>판매글을 불러오는 데 실패했습니다.</h2>
      </div>
    );
  }

  return (
    <React.Fragment>
      <Stack direction="row" spacing={1.5} className={styles.chips}>
        {["모두", "판매 중", "거래 완료"].map((chip) => (
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
        {filteredDeals.map((deal, k) => (
          <div className="flex justify-between items-start" key={k}>
            <ProductCard product={deal} next={`/lists/${deal.id}`} />
            <IconButton
              aria-label="menu"
              onClick={(e) => handleMenuClick(e, deal.id)} // 클릭 핸들러에 deal.id 전달
            >
              <MoreVertIcon />
            </IconButton>
            <Menu
              anchorEl={anchorEl}
              open={open && openMenuId === deal.id} // 현재 카드의 메뉴만 열림
              onClose={handleMenuClose}
            >
              <Link href={`/my-trade/${deal.id}`}>
                <MenuItem onClick={handleRequestOpen}>거래요청목록</MenuItem>
              </Link>

              <MenuItem onClick={handleMenuCloseAndDelete}>판매글 삭제</MenuItem>
            </Menu>
          </div>
        ))}
        {filteredDeals.length === 0 && (
          <NoContents>
            <h4 className="c-text3">판매한 상품이 없어요</h4>
            <Image src={"/images/Empty-BOX.png"} alt="empty" width={100} height={100} />
            <SmallBtn next={"/lists/create"}>상품 판매하러 가기</SmallBtn>
          </NoContents>
        )}
      </div>
    </React.Fragment>
  );
}
