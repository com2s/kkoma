"use client";

import { ButtonContainer, NormalBtn } from "@/components/common/buttons";
import TopBar2 from "@/components/point/point-bar";
import { chargePointAPI } from "@/services/point";
import { Input, InputAdornment } from "@mui/material";
import { ChangeEvent, useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { getMyPoints } from "@/components/common/common-ftn";

export default function PointWithdraw() {
  const router = useRouter();
  const [balance, setBalance] = useState<number>(0);
  const [amount, setAmount] = useState<string>("");
  const [err, setErr] = useState<boolean>(false);
  const [errMsg, setErrMsg] = useState<string>("");

  const changeEnteredNum = (e: ChangeEvent<HTMLInputElement>) => {
    const value: string = e.target.value;
    const removedCommaValue: number = Number(value.replaceAll(",", ""));
    if (removedCommaValue > balance) {
      setErr(true);
      setErrMsg(`최대 출금 가능 포인트: ${balance.toLocaleString()}포인트`);
      setAmount(balance.toLocaleString());
    } else if (removedCommaValue === 0) {
      setErr(true);
      setErrMsg("0원 이상 송금할 수 있어요");
      setAmount("");
    } else if (removedCommaValue.toLocaleString() === "NaN") {
      setErr(true);
      setErrMsg("올바른 숫자를 입력해주세요");
      setAmount("");
    } else {
      setErr(false);
      setErrMsg("");
      setAmount(removedCommaValue.toLocaleString());
    }
  };

  const handleBtn = (num: number) => {
    const removedCommaValue: number = Number(amount.replaceAll(",", "")) + num;
    if (removedCommaValue > balance) {
      setErr(true);
      setErrMsg(`최대 출금 가능 포인트: ${balance.toLocaleString()}포인트`);
      setAmount(balance.toLocaleString());
    } else if (removedCommaValue === 0) {
      setErr(true);
      setErrMsg("0원 이상 송금할 수 있어요");
      setAmount("");
    } else if (removedCommaValue.toLocaleString() === "NaN") {
      setErr(true);
      setErrMsg("올바른 숫자를 입력해주세요");
      setAmount("");
    } else {
      setErr(false);
      setErrMsg("");
      setAmount(removedCommaValue.toLocaleString());
    }
  };

  const startCharge = async () => {
    const removedCommaValue: number = Number(amount.replaceAll(",", ""));
    await chargePointAPI(removedCommaValue);
    alert(`${amount}포인트가 출금 되었어요`);
    router.push("/point");
  };

  const fetchData = async () => {
    const res = await getMyPoints();
    setBalance(await res.data.balance);
  };

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {});

  return (
    <div className="flex flex-col justify-center">
      <TopBar2 title="포인트 송금" />
      <Input
        id="standard-adornment-amount"
        endAdornment={<InputAdornment position="end">P</InputAdornment>}
        placeholder="금액을 입력해주세요"
        sx={{ paddingY: "10px" }}
        type="text"
        value={amount}
        onChange={changeEnteredNum}
        inputMode="numeric"
        error={err}
      />
      {err ? (
        <div className="!text-red-500 text-caption mt-1">{errMsg}</div>
      ) : (
        <div className="text-caption c-text2 mt-1">{`포인트 잔액: ${balance.toLocaleString()}포인트`}</div>
      )}
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
        <NormalBtn next={startCharge} disabled={!amount || amount === "" || err}>
          송금
        </NormalBtn>
      </ButtonContainer>
    </div>
  );
}
