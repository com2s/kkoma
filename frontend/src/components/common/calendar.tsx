"use client";

import React, { useState } from "react";
import { offerTimeState } from "@/store/offer";
import { useRecoilState } from "recoil";
import {
  StyledCalendarWrapper,
  StyledCalendar,
  StyledDelete,
  StyledDate,
  StyledToday,
  StyledDot,
  StyledUnderLine,
} from "@/components/common/calendar-style";
// import ViewCallbackProperties from "react-calendar";
import { Slider, Typography, Box, Button, IconButton } from "@mui/material";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
// 부모 컴포넌트 (ParentComponent.tsx)
// import React, { useState } from 'react';
// import ChildComponent from './ChildComponent';

// export default function ParentComponent() {
//   const [parentDate0, setParentDate0] = useState("");

//   const handleDate0 = (data: string) => {
//     setParentDate0(data);
//   };
//   ......

//   return (
//     <div>
//       <h1>부모 컴포넌트</h1>
//                 <Calendar
//                    sendDateToParent={handleDate0}
//                    sendTimeToParent={handleTime0}
//                    isAccept
//                  >
//                  </Calendar>
//       <p>자식 컴포넌트에서 받은 데이터: {parentDate0}</p>
//     </div>
//   );
// }
interface ChildProps {
  sendDateToParent: (data: string) => void;
  sendTimeToParent: (data: string) => void;
  // ? 를 붙이면 선택적 props가 된다.
  // 거래 요청 수락이면 true, 거래 요청이면 false
  isAccept?: boolean;
}

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

export default function ChildComponent({
  sendDateToParent,
  sendTimeToParent,
  isAccept = false,
}: ChildProps) {
  const [offerTime, setOfferTime] = useRecoilState(offerTimeState); // 거래 시간
  const [value, setValue] = useState<Value>(new Date());
  const [date, setDate] = useState(""); // 날짜
  const [time, setTime] = useState(""); // 시간
  // 조건부 useState
  const [minute, setMinute] = useState<number | number[]>(
    isAccept ? 0 : [0, 1440]
  );
  const [activeStartDate, setActiveStartDate] = useState<Date | null>(
    new Date()
  );
  // console.log("isAccept : ", isAccept);
  const today = new Date();

  const handleTodayClick = () => {
    const today = new Date();
    setActiveStartDate(today);
    setValue(today);
    if (value) {
      // console.log("value : ", value);
      setDate(value.toString());
      // sendDateToParent(value.toString());
    }
  };
  
  // console.log(offerTime)
  const requestDays = isAccept
    ? offerTime
    : []; // 거래 요청 받은 날짜 예시

  function formatDate(date: Date) {
    const year = date.getFullYear(); // 연도를 가져옵니다.
    const month = (date.getMonth() + 1).toString().padStart(2, "0"); // 월을 가져옵니다. 1을 더하고, '0'을 앞에 붙여 두 자리로 만듭니다.
    const day = date.getDate().toString().padStart(2, "0"); // 일을 가져옵니다. '0'을 앞에 붙여 두 자리로 만듭니다.

    return `${year}-${month}-${day}`; // 'YYYY-MM-DD' 형식으로 포맷하여 반환합니다.
  }

  const handleDateChange = (value: Value) => {
    if (value) {
      // console.log("value : ", value);
      setDate(formatDate(value as Date));
      // console.log(formatDate(value as Date));
    }
  };

  function onChange(nextValue: Value) {
    // console.log("달력클릭");
    setValue(nextValue);
    // console.log("nextValue : ", nextValue);
    // console.log("value : ", value);
  }

  // const handleSetTime = () => {
  //   if (isAccept) {
  //     const selectedTime = `${Math.floor((minute as number) / 60)} : ${(
  //       (minute as number) % 60
  //     )
  //       .toString()
  //       .padStart(2, "0")}`;
  //     setTime(selectedTime);
  //     // console.log("selectedTime : ", time);
  //     sendTimeToParent(selectedTime);
  //   } else {
  //   }
  // };

  const handleMinuteChange = (event: Event, newValue: number | number[], activeThumb: number,) => {
    sendDateToParent(date);
    if (isAccept) {
      setMinute(newValue as number);
      // console.log("minute: ", newValue);
      const selectedTime = `${Math.floor((newValue as number) / 60)}:${(
        (newValue as number) % 60
      )
        .toString()
        .padStart(2, "0")}`;
      setTime(selectedTime);
      // console.log("selectedTime : ", time);
      sendTimeToParent(selectedTime);
    } else {
      // if (activeThumb === 0) {
      //   setMinute([Math.min(newValue[0], minute[1] - minDistance), minute[1]]);
      // } else {
      //   setMinute([minute[0], Math.max(newValue[1], minute[0] + minDistance)]);
      // }
      setMinute(newValue as number[]);
      const selectedTimeStart = `${Math.floor(
        (newValue as number[])[0] / 60
      )}:${((newValue as number[])[0] % 60).toString().padStart(2, "0")}`;

      const selectedTimeEnd = `${Math.floor(
        (newValue as number[])[1] / 60
      )}:${((newValue as number[])[1] % 60).toString().padStart(2, "0")}`;

      const selectedTime = `${selectedTimeStart} ~ ${selectedTimeEnd}`;
      setTime(selectedTime);
      // console.log("selectedTime : ", time);
      sendTimeToParent(selectedTime);
    }
  };

  const resetValues = () => {
    setValue(new Date());
    setDate("");
    setMinute(0);
    setTime("");
    sendDateToParent("");
    sendTimeToParent("");
  };

  // 각 날짜에 적용할 클래스를 반환하는 함수
  const tileClassName = ({
    date,
    view,
  }: {
    date: Date;
    view: string;
  }): string | null => {
    // console.log("date : ", formatDate(date));

    // view가 'month'인 경우에만 스타일을 적용
    if (view === "month") {
      // 특정 날짜들과 같은지 확인
      if (requestDays.find((x) => x.offerDate === formatDate(date))) {
        // 'red' 클래스를 적용
        return "red";
      }
    }
    return null;
  };

  // 분 슬라이더를 위한 값
  const startTime = isAccept ? "11:00" : "00:00";
  const endTime = isAccept ? "18:00" : "24:00";

  // 위 시간을 분 단위로 변경
  const MAX =
    60 * parseInt(endTime.split(":")[0]) + parseInt(endTime.split(":")[1]);
  const MIN =
    60 * parseInt(startTime.split(":")[0]) + parseInt(startTime.split(":")[1]);

  const formatTime = (time: string) => {
    const hour = parseInt(time.split(":")[0]);
    const minute = parseInt(time.split(":")[1]);
    return hour * 60 + minute;
  };

  const minDistance = 10;

  return (
    <div className={`text-center my-6 mx-1`}>
      <StyledCalendarWrapper className="py-2">
        <StyledCalendar
          onChange={onChange}
          onClickDay={handleDateChange}
          value={value}
          tileClassName={tileClassName}
          minDetail="month" // 상단 네비게이션에서 '월' 단위만 보이게 설정
          maxDetail="month" // 상단 네비게이션에서 '월' 단위만 보이게 설정
          locale="ko-KR" // 한국어로 설정
          formatDay={(locale, date) => date.getDate().toString()} // 날짜(숫자)만 표시
          next2Label={null}
          prev2Label={null}
          className={`mx-auto`} // 가운데 정렬
          tileDisabled={({ date }) => {
            // 오늘 이전 날짜는 비활성화
            return date < new Date();
          }}
          // 오늘 날짜로 돌아오는 기능을 위해 필요한 옵션 설정
          activeStartDate={
            activeStartDate === null ? undefined : activeStartDate
          }
          onActiveStartDateChange={({ activeStartDate }) =>
            setActiveStartDate(activeStartDate)
          }
          // 오늘 날짜에 '오늘' 텍스트 삽입하고...
          tileContent={({ date, view }) => {
            let html = [];
            if (
              view === "month" &&
              date.getFullYear() === today.getFullYear() &&
              date.getMonth() === today.getMonth() &&
              date.getDate() === today.getDate()
            ) {
              html.push(<StyledToday key={"today"}>오늘</StyledToday>);
            }
            // 특정 날짜와 일치하는 날에 밑 점 표시
            if (requestDays.find((x) => x.offerDate === formatDate(date))) {
              html.push(<StyledDot key={formatDate(date)} />);
            }
            return <>{html}</>;
          }}
        />
        {/* // 초기화 버튼 추가 */}
        <StyledDelete onClick={resetValues}>
          <DeleteForeverIcon fontSize="small"/>
        </StyledDelete>
        {/* // 오늘 버튼 추가 */}
        <StyledDate onClick={handleTodayClick}>오늘</StyledDate>
        <Box
          sx={{
            marginY: 6,
            marginX: "auto",
            display: "flex",
            maxWidth: "500px",
          }}
        >
          {/* value가 requestDays에 속해있다면... */}
          {requestDays.map((offer, index) => value && offer.offerDate === formatDate(value as Date) ? 
          <div
            className="w-full px-8"
            style={{ position: "absolute", top: "80%", left: 0 }}
            key={index}
          >
            <Slider
              aria-labelledby="minute-slider"
              defaultValue={0}
              value={minute}
              onChange={handleMinuteChange}
              step={10}
              min={formatTime(offer.startTime)}
              max={formatTime(offer.endTime)}
              // disableSwap
              valueLabelDisplay="on"
              valueLabelFormat={(value) =>
                // `${value.toString().padStart(2, "0")}분`
                `${Math.floor(value / 60)}시 ${(value % 60)
                  .toString()
                  .padStart(2, "0")}분`
              }
              className="text-yellow-400"
            />
            <Box sx={{ display: "flex", justifyContent: "space-between" }}>
              <Typography
                variant="body2"
                onClick={() => setMinute(MIN)}
                sx={{ cursor: "pointer" }}
              >
                {offer.startTime}
              </Typography>
              <Typography
                variant="body2"
                onClick={() => setMinute(MAX)}
                sx={{ cursor: "pointer" }}
              >
                {offer.endTime}
              </Typography>
            </Box>
          </div>
           : null
          )}
        </Box>
      </StyledCalendarWrapper>
    </div>
  );
}
