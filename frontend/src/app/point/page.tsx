"use client";

import MyPoints from "@/components/my-page/my-points";
import styles from "./point.module.scss";
import { useEffect, useState } from "react";
import { getPointHistoryAPI } from "@/services/point";
import TopBar2 from "@/components/point/point-bar";
import { PointChangeType } from "@/types/point";
import Image from "next/image";
import AccountBalanceIcon from "@mui/icons-material/AccountBalance";
import SavingsIcon from "@mui/icons-material/Savings";
import { PointChangeTypeFormat } from "@/utils/format";

interface PointProduct {
  id: number;
  thumbnail: string;
  title: string;
}

interface PointHistory {
  id: number;
  amount: number;
  balanceAfterChange: number;
  pointChangeType: PointChangeType;
  date: string;
  productInfo: PointProduct | null;
}

export default function Point() {
  const [history, setHistory] = useState<Array<PointHistory>>([]);

  const fetchData = async () => {
    const res = await getPointHistoryAPI();
    setHistory(await res.content);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className={styles.point}>
      <TopBar2 title="이용 내역" />
      <MyPoints />
      <div className="flex flex-col gap-3">
        {history && history.length > 0 ? (
          history.map((item, k) => (
            <div key={k}>
              <div className="flex justify-between items-center">
                {item.productInfo ? (
                  <div className="flex items-center gap-2">
                    <div
                      className="bg-gray-200 rounded-lg p-2"
                      style={{
                        position: "relative",
                        width: "56px",
                        height: "56px",
                      }}
                    >
                      <Image
                        src={item.productInfo.thumbnail}
                        alt="thumbnail"
                        width={56}
                        height={56}
                        className="rounded-lg"
                      />
                    </div>
                    <div>
                      <div className="text-body whitespace-nowrap text-wrap	w-30">
                        {item.productInfo.title}
                      </div>
                      <div className="text-caption c-text3">{`${item.date.substring(
                        0,
                        10
                      )} ${item.date.substring(11, 16)} | ${PointChangeTypeFormat(
                        item.pointChangeType
                      )}`}</div>
                    </div>
                  </div>
                ) : (
                  <div className="flex items-center gap-2">
                    <div className="bg-gray-100 rounded-lg p-2">
                      {item.pointChangeType === "CHARGE" ? (
                        <AccountBalanceIcon
                          style={{ width: "40px", height: "40px" }}
                          className="c-text3"
                        />
                      ) : (
                        <SavingsIcon
                          style={{ width: "40px", height: "40px" }}
                          className="c-text3"
                        />
                      )}
                    </div>
                    <div>
                      <div className="text-body truncate w-40">
                        {item.pointChangeType === "CHARGE" ? "포인트 충전" : "포인트 출금"}
                      </div>
                      <div className="text-caption c-text3">{`${item.date.substring(
                        0,
                        10
                      )} ${item.date.substring(11, 16)} | ${PointChangeTypeFormat(
                        item.pointChangeType
                      )}`}</div>
                    </div>
                  </div>
                )}
                <div className="min-w-fit">
                  <div
                    style={
                      item.pointChangeType === "PAY" || item.pointChangeType === "WITHDRAW"
                        ? { color: "#764c32 !important" }
                        : { color: "#ffcf00 !important" }
                    }
                    className="!font-bold text-end text-body"
                  >
                    {item.pointChangeType === "PAY" || item.pointChangeType === "WITHDRAW"
                      ? "-"
                      : "+"}
                    {item.amount.toLocaleString()}P
                  </div>
                  <div className="text-end text-caption c-text3">{`잔액 ${item.balanceAfterChange.toLocaleString()}P`}</div>
                </div>
              </div>
            </div>
          ))
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}
