"use client";

import React, { useState } from "react";
import Calendar from "react-calendar";
// import "react-calendar/dist/Calendar.css";
import "@/components/common/calendar.module.scss"; // scss import

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
//       <ChildComponent sendDataToParent={handleData} />
//       <p>자식 컴포넌트에서 받은 데이터: {parentData}</p>
//     </div>
//   );
// }
interface ChildProps {
  sendDataToParent: (data: string) => void;
}

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

export default function ChildComponent({ sendDataToParent }: ChildProps) {
  // 시간을 제외한 오늘 날짜로 초기화

  const [value, setValue] = useState<Value>(() => {
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0); // 시간, 분, 초, 밀리초를 0으로 설정합니다.
    return currentDate;
  });

  const handelChange = (value: Value) => {
    if (value) {
      setValue(value);
      // const data = `${value?.toLocaleDateString()}`;
      // sendDataToParent(data);
    }
  };

  return (
    <div className={`text-center`}>
      <h2>부모에게 데이터 보내기</h2>
      <Calendar
        onChange={handelChange}
        value={value}
        minDetail="month" // 상단 네비게이션에서 '월' 단위만 보이게 설정
        maxDetail="month" // 상단 네비게이션에서 '월' 단위만 보이게 설정
        locale="ko-KR" // 한국어로 설정
        className={`mx-auto`} // 가운데 정렬
      />
      {/* <div className="text-gray-500 mt-4">{value?.toLocaleDateString()}</div> */}
    </div>
  );
}
