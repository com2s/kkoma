"use client";

import React, { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
// import "@/components/common/calendar.module.scss"; // scss import
import { Slider, Typography, Box, Button, IconButton } from "@mui/material";
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
// 부모 컴포넌트 (ParentComponent.tsx)
// import React, { useState } from 'react';
// import ChildComponent from './ChildComponent';

// export default function ParentComponent() {
//   const [parentData, setParentData] = useState('');

//   const handleData = (data: string) => {
//     setParentData(data);
//   };

//   return (
//     <div>
//       <h1>부모 컴포넌트</h1>
//       <ChildComponent sendDateToParent={handleData} />
//       <p>자식 컴포넌트에서 받은 데이터: {parentData}</p>
//     </div>
//   );
// }
interface ChildProps {
  sendDateToParent: (data: string) => void;
  sendTimeToParent: (data: string) => void;
}

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

export default function ChildComponent({
  sendDateToParent,
  sendTimeToParent,
}: ChildProps) {
  const [value, setValue] = useState<Value>(new Date());
  const [date, setDate] = useState(""); // 날짜
  const [time, setTime] = useState(""); // 시간
  const [minute, setMinute] = useState<number>(0);

  const handleDateChange = (value: Value) => {
    if (value) {
      console.log("value : ", value);
      setDate(value.toString());
      sendDateToParent(value.toString());
    }
  };

  function onChange(nextValue: Value) {
    console.log("달력클릭")
    setValue(nextValue);
    console.log("nextValue : ", nextValue);
    console.log("value : ", value);
  }

  const handleSetTime = () => {
    const selectedTime = `${Math.floor(minute / 60)} : ${(minute % 60).toString().padStart(2, "0")}`;
    setTime(selectedTime);
    console.log("selectedTime : ", time);
    sendTimeToParent(selectedTime);
  }

  const handleMinuteChange = (event: Event, newValue: number | number[]) => {
    setMinute(newValue as number);
    console.log("minute: ", newValue);
  };

  const resetValues = () => {
    setValue(new Date());
    setDate("");
    setMinute(0);
    setTime("");
    sendDateToParent("");
    sendTimeToParent("");
  };

  // 분 슬라이더를 위한 값
  const startTime = '11:00'
  const endTime = '18:00'

  // 위 시간을 분 단위로 변경
  const MAX = 60 * parseInt(endTime.split(':')[0]) + parseInt(endTime.split(':')[1])
  const MIN = 60 * parseInt(startTime.split(':')[0]) + parseInt(startTime.split(':')[1])
 
  return (
    <div className={`text-center my-6 mx-1`}>
      {date? 
      <Button color="warning" size="large" onClick={resetValues}>
        <DeleteForeverIcon /> 초기화
      </Button>

         : <p>날짜를 선택해 주세요.</p>}
      <Calendar
        onChange={onChange}
        onClickDay={handleDateChange}
        value={value}
        minDetail="month" // 상단 네비게이션에서 '월' 단위만 보이게 설정
        maxDetail="month" // 상단 네비게이션에서 '월' 단위만 보이게 설정
        locale="ko-KR" // 한국어로 설정
        className={`mx-auto mt-6`} // 가운데 정렬
      />
        {date && (
      <Box sx={{ marginY: 6, marginX:'auto', display: "flex", maxWidth: '500px' }}>
        {/* <Select
          labelId="hour-select-label"
          id="hour-select"
          value={hour}
          onChange={handleHourChange}
          // displayEmpty
          // inputProps={{ "aria-label": "Without label" }}
          // label="시간 선택"
          sx={{ minWidth: 100 }}
        >
          {Array.from({ length: 24 }, (_, index) => (
            <MenuItem key={index} value={index.toString().padStart(2, "0")}>
              {index.toString().padStart(2, "0")}시
            </MenuItem>
          ))}
        </Select> */}
        <div className="w-full px-8">
          <Slider
            aria-labelledby="minute-slider"
            defaultValue={0}
            value={minute}
            onChange={handleMinuteChange}
            step={10}
            min={MIN}
            max={MAX}
            valueLabelDisplay="on"
            valueLabelFormat={(value) =>
              // `${value.toString().padStart(2, "0")}분`
              `${Math.floor(value / 60)}시 ${(minute % 60).toString().padStart(2, "0")}분`
            }
            className="text-yellow-400 w-5/6"
          />
          <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography
          variant="body2"
          onClick={() => setMinute(MIN)}
          sx={{ cursor: 'pointer' }}
        >
          {startTime}
        </Typography>
        <Typography
          variant="body2"
          onClick={() => setMinute(MAX)}
          sx={{ cursor: 'pointer' }}
        >
          {endTime}
        </Typography>
      </Box>
        </div>
        <Button variant="outlined" onClick={handleSetTime}>
          {`${Math.floor(minute / 60)}시 ${(minute % 60).toString().padStart(2, "0")}분`} 선택
          {/* {`${(Math.floor(minute / 60)).toString().padStart(2, "0")}시 ${(minute % 60).toString().padStart(2, "0")}분`} 선택 */}
        </Button>
      </Box>
        )}
    </div>
  );
}
