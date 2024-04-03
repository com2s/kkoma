"use client";

import Title from "@/components/common/title";
import TextField from "@mui/material/TextField";
import {
  ButtonContainer,
  SubBtn,
  NormalBtn,
} from "@/components/common/buttons";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import dayjs from "dayjs";
import "dayjs/locale/ko";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { MobileDatePicker } from "@mui/x-date-pickers";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { koKR } from "@mui/x-date-pickers/locales";

import { ChangeEvent, useState } from "react";
import { useRecoilState } from "recoil";
import { kidBirthDateState } from "@/store/kid";
import { useRouter } from "next/navigation";

export default function KidBirth() {
  const [birthDate, setBirthDate] = useRecoilState(kidBirthDateState);
  const [isBirthYet, setIsBirthYet] = useState(false);
  const [disable, setDisable] = useState(false);
  const [errmsg, setErrMsg] = useState<string | null>(null);

  const router = useRouter();

  const handelChange = (e: ChangeEvent<HTMLInputElement>) => {
    setIsBirthYet(e.target.checked);
  };

  return (
    <>
      <Title
        title={`아이의\n생년월일을 알려주세요`}
        subtitle="제품 추천에 도움을 줄게요"
      />
      <div>
        <FormControlLabel
          control={<Checkbox checked={isBirthYet} onChange={handelChange} />}
          label="아직 출산 전이에요"
        />
        {errmsg ? (
          <div className="text-caption c-main">{`${errmsg}`}</div>
        ) : (
          <></>
        )}
      </div>
      <LocalizationProvider
        dateAdapter={AdapterDayjs}
        adapterLocale={"ko"}
        localeText={
          koKR.components.MuiLocalizationProvider.defaultProps.localeText
        }
      >
        <MobileDatePicker
          label="생년월일"
          className="w-full"
          disableFuture={!isBirthYet}
          disablePast={isBirthYet}
          format="YYYY-MM-DD"
          defaultValue={dayjs(birthDate)}
          onError={(e) => {
            if (e) {
              setDisable(true);
              if (e === "disablePast") {
                setErrMsg("현재보다 이전 날짜는 선택할 수 없어요");
              } else {
                setErrMsg("현재보다 이후 날짜는 선택할 수 없어요");
              }
            } else {
              setDisable(false);
              setErrMsg(null);
            }
          }}
          onChange={(e) => setBirthDate(e?.format("YYYY-MM-DD")!)}
        />
      </LocalizationProvider>
      <ButtonContainer>
        <SubBtn
          next={() => {
            setBirthDate(null);
            router.push("/kid/gender");
          }}
        >
          건너뛰기
        </SubBtn>
        <NormalBtn next={"/kid/gender"} disabled={disable}>
          완료
        </NormalBtn>
      </ButtonContainer>
    </>
  );
}
