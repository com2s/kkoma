"use client";

import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import TopBar2 from "@/components/point/point-bar";
import { chargePointAPI } from "@/services/point";
import { Input, InputAdornment } from "@mui/material";
import { ChangeEvent, useState } from "react";
import { useRouter } from "next/navigation";

export default function PointCharge() {
  const router = useRouter();
  const [amount, setAmount] = useState<string>("");

  const changeEnteredNum = (e: ChangeEvent<HTMLInputElement>) => {
    const value: string = e.target.value;
    const removedCommaValue: number = Number(value.replaceAll(",", ""));
    setAmount(removedCommaValue.toLocaleString());
  };

  const handleBtn = (num: number) => {
    const removedCommaValue: number = Number(amount.replaceAll(",", "")) + num;
    setAmount(removedCommaValue.toLocaleString());
  };

  const startCharge = async () => {
    const removedCommaValue: number = Number(amount.replaceAll(",", ""));
    await chargePointAPI(removedCommaValue);
    alert(`${amount}포인트가 충전됐어요`);
    router.push("/point");
  };
  return (
    <div className="flex flex-col justify-center">
      <TopBar2 title="포인트 충전" />
      <Input
        id="standard-adornment-amount"
        endAdornment={<InputAdornment position="end">P</InputAdornment>}
        placeholder="금액을 입력해주세요"
        sx={{ paddingY: "10px" }}
        type="text"
        value={amount}
        onChange={changeEnteredNum}
        inputMode="numeric"
      />
      <div className="flex items-center gap-2 mt-4">
        <button
          className="flex-1 bg-gray-100 rounded-lg text-body2 py-1"
          onClick={() => handleBtn(10000)}
        >
          +1만원
        </button>
        <button
          className="flex-1 bg-gray-100 rounded-lg text-body2 py-1"
          onClick={() => handleBtn(50000)}
        >
          +5만원
        </button>
        <button
          className="flex-1 bg-gray-100 rounded-lg text-body2 py-1"
          onClick={() => handleBtn(100000)}
        >
          +10만원
        </button>
      </div>
      <ButtonContainer>
        <NormalBtn next={startCharge} disabled={!amount || amount === "" || amount === "0"}>
          충전
        </NormalBtn>
      </ButtonContainer>
    </div>
  );
}
