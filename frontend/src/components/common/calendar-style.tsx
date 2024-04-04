"use client";

import Calendar from "react-calendar";
import styled from "styled-components";
import "react-calendar/dist/Calendar.css";
import { Noto_Sans_KR } from "next/font/google";

const notoSansKr = Noto_Sans_KR({ subsets: ["cyrillic"] });

export const StyledCalendarWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  position: relative;
  .react-calendar {
    font-family: ${notoSansKr.style.fontFamily};
    width: 100%;
    border: 1px solid #d9d9d9;
    border-radius: 0.5rem;
    padding: 2%;
    padding-top: 18px;
    padding-bottom: 120px;
    background-color: white;
  }

  /* 전체 폰트 컬러 */
  .react-calendar__month-view {
    abbr {
      color: #656565;
    }
  }

  /* 이웃하는 달 날짜 컬러 */
  .react-calendar__month-view__days__day--neighboringMonth {
    abbr {
      color: #999999;
    }
  }

  /* 네비게이션 가운데 정렬 */
  .react-calendar__navigation {
    justify-content: center;
    align-items: center;
  }

  /* 네비게이션 폰트 설정 */
  .react-calendar__navigation button {
    font-weight: 600;
    font-size: 1rem;
  }

  /* 네비게이션 버튼 컬러 */
  .react-calendar__navigation button:focus {
    background-color: white;
  }

  /* 네비게이션 비활성화 됐을때 스타일 */
  .react-calendar__navigation button:disabled {
    background-color: white;
    color: #010101;
  }

  /* 년/월 상단 네비게이션 칸 크기 줄이기 */
  .react-calendar__navigation__label {
    flex-grow: 0 !important;
  }

  /* 요일 밑줄 제거 */
  .react-calendar__month-view__weekdays abbr {
    padding-bottom: 10px;
    text-decoration: none;
    font-weight: 600;
  }

  /* 일요일에만 빨간 폰트 */
  .react-calendar__month-view__weekdays__weekday--weekend abbr[title="일요일"] {
    color: #d10000;
  }

  /* 토요일에만 파란 폰트 */
  .react-calendar__month-view__weekdays__weekday--weekend abbr[title="토요일"] {
    color: #0000d1;
  }

  /* 오늘 날짜 폰트 컬러 */
  .react-calendar__tile--now {
    background: none;
  }

  /* 네비게이션 월 스타일 적용 */
  .react-calendar__year-view__months__month {
    border-radius: 0.8rem;
    background-color: #f5f5f5;
    padding: 0;
  }

  /* 네비게이션 현재 월 스타일 적용 */
  .react-calendar__tile--hasActive {
    background-color: #ffcf00;
    abbr {
      color: white;
    }
  }

  /* 일 날짜 간격 */
  .react-calendar__tile {
    padding: 12px;
    position: relative;
  }

  /* 네비게이션 월 스타일 적용 */
  .react-calendar__year-view__months__month {
    flex: 0 0 calc(33.3333% - 10px) !important;
    margin-inline-start: 5px !important;
    margin-inline-end: 5px !important;
    margin-block-end: 10px;
    padding: 20px 6.6667px;
    font-size: 0.9rem;
    font-weight: 600;
    color: #999999;
  }

  /* 선택한 날짜 스타일 적용 */
  .react-calendar__tile:enabled:hover,
  .react-calendar__tile:enabled:focus,
  .react-calendar__tile--active {
    background-color: #ffcf00;
    border-radius: 50%;
  }

  .red {
    abbr {
      color: #e52d2b;
      font-weight: 700;
    }
  }
`;

export const StyledCalendar = styled(Calendar)``;

/* 오늘 버튼 스타일 */
export const StyledDelete = styled.div`
  cursor: pointer;
  position: absolute;
  left: 6%;
  top: 8%;
  background-color: #dcdcdc;
  color: #00008b;
  width: 13%;
  min-width: fit-content;
  height: 1.8rem;
  text-align: center;
  margin: 0 auto;
  line-height: 1.6rem;
  border-radius: 15px;
  font-size: 0.8rem;
  font-weight: 700;
  &:hover {
    transform: scale(1.1);
  }
`;

/* 오늘 버튼 스타일 */
export const StyledDate = styled.div`
  cursor: pointer;
  position: absolute;
  right: 6%;
  top: 9%;
  background-color: #ffcf00;
  color: #764c32;
  width: 14%;
  min-width: fit-content;
  height: 1.5rem;
  text-align: center;
  margin: 0 auto;
  line-height: 1.6rem;
  border-radius: 15px;
  font-size: 0.8rem;
  font-weight: 600;
  &:hover {
    transform: scale(1.1);
  }
`;

/* 오늘 날짜에 텍스트 삽입 스타일 */
export const StyledToday = styled.div`
  font-size: x-small;
  color: #764c32;
  min-width: 3rem;
  font-weight: 600;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%);
`;

/* 특정 날짜에 점 표시 스타일 */
export const StyledDot = styled.div`
  background-color: #764c32;
  border-radius: 50%;
  width: 0.4rem;
  height: 0.4rem;
  position: absolute;
  top: 60%;
  left: 50%;
  transform: translateX(-50%);
`;

/* 특정 날짜에 밑줄 표시 스타일 */
export const StyledUnderLine = styled.div`
  background-color: #764c32;
  border-radius: 4px;
  width: 2.5rem;
  height: 0.5rem;
  position: absolute;
  top: 60%;
  left: 50%;
  transform: translateX(-50%);
`;
