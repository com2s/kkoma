'use client';

import React, { useState } from 'react';
import Calendar from 'react-calendar';
import "react-calendar/dist/Calendar.css";

export default function Test() {
  const [value, setValue] = useState(new Date());

  const onChange = (nextValue: Date) => {
    setValue(nextValue);
  }

  return (
    <div>
      <h1>Test</h1>
      <p>다른 페이지에 적용하기 전에 테스트를 하기 위해 만든 페이지입니다.</p>
      <Calendar
      value={value}
    />
    </div>
  );
}