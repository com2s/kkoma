"use client";

import styles from "@/components/my-page/my-points.module.scss";
import { getMyPoints } from "../common/common-ftn";
import { Button } from "@mui/material";
import AttachMoneyIcon from "@mui/icons-material/AttachMoney";
import TollIcon from "@mui/icons-material/Toll";
import { useEffect, useState } from "react";

export default function MyPoints() {
  {
    /* '내 포인트'에 천의 자리를 표시하는 ',' 표시와 끝에 P 를 붙인다. */
  }
  const [point, setPoint] = useState(0);
  const [success, setSuccess] = useState(true);

  const getPoint = async () => {
    const res = await getMyPoints();
    setSuccess(res.success);
    if (res.success) {
      setPoint(res.data.balance);
    } else {
      setPoint(0);
    }
  };

  useEffect(() => {
    getPoint();
  }, []);
  // const points = 36590;
  const formattedPoints = (points: number) => {
    const res = points ?? 0;
    return res.toLocaleString();
  };
  return (
    <div className={styles.container}>
      <div className="flex justify-between items-center p-4">
        <span className="text-lg px-4 font-semibold">내 포인트</span>
        <span className="text-xl px-4 font-bold text-red-500">
          {success? `${formattedPoints(point)}P` : `포인트 조회 실패`}
          </span>
      </div>
      <div className="flex justify-between items-center p-4 btn-line">
        <Button
          startIcon={<TollIcon color="primary" />}
          variant="outlined"
          className={styles.btn}
          onClick={() => alert("포인트 송금 페이지로 이동합니다.")}
        >
          <span className="text-black font-semibold">송금</span>
        </Button>
        <Button
          startIcon={<AttachMoneyIcon color="error" />}
          variant="outlined"
          className={styles.btn}
          onClick={() => alert("포인트 충전 페이지로 이동합니다.")}
        >
          <span className="text-black font-semibold">충전</span>
        </Button>
      </div>
    </div>
  );
}
