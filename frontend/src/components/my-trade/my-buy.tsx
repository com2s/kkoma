import React, { useState, useEffect } from "react";
import { getMyProducts } from "@/components/my-trade/my-trade-ftn";
import { Deal } from "@/types/status";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";
import IconButton from "@mui/material/IconButton";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import MenuItem from "@mui/material/MenuItem";
import Menu from "@mui/material/Menu";
import styles from "@/components/my-trade/sell-buy.module.scss";
import { NoContents } from "../common/no-contents";
import { SmallBtn } from "../common/buttons";
import Image from "next/image";
import { ProductCard } from "../common/product-card";
import { useRouter } from "next/navigation";

export default function MyBuy() {
  const router = useRouter();
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
      router.refresh();
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
        {filteredDeals.map((deal, k) => (
          <ProductCard product={deal} next={`/lists/${deal.id}`} key={k} />
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
