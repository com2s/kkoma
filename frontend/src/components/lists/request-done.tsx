import Image from "next/image";
import LocationOnOutlinedIcon from "@mui/icons-material/LocationOnOutlined";
import ScheduleIcon from "@mui/icons-material/Schedule";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import { Avatar, IconButton } from "@mui/material";

interface TimeEntry {
  date: string;
  time: string;
}

interface ChildProps {
  // ? 를 붙이면 선택적 props가 된다.
  sellerId?: string;
  selectedTimes?: TimeEntry[];
  location?: string;
}

export default function RequestDone({
  sellerId,
  selectedTimes,
  location,
}: ChildProps) {
  return (
    <div className="px-4">
      <h2 className="text-pretty">거래 요청이 완료 되었어요!</h2>
      <p className="my-2 text-gray-400">거래가 확정되면 알려드릴게요.</p>
      <Image
        src={"/images/message2.svg"}
        alt="요청 완료"
        width={300}
        height={300}
        className="mx-auto my-4"
      />
      <div className="flex my-8 w-full justify-between content-center">
        <Avatar
          src="/images/avatar1.jpg"
          alt="판매자 프로필"
          className="mr-2 my-auto"
        />
        <div className="w-full">
          <h4>판매자 아이디</h4>
          <p className="text-caption">평균 거래 확정시간 : 3시간</p>
        </div>
        <IconButton onClick={()=>alert("판매자 프로필 페이지")}>
          <ArrowForwardIosIcon />
        </IconButton>
      </div>
      {selectedTimes?.map((timeEntry, index) => (
        <div key={index} className="mb-2">
          <CalendarMonthIcon />
          <span className="text-body mx-2">{timeEntry.date}</span>
          <ScheduleIcon />
          <span className="text-body mx-2">{timeEntry.time}</span>
        </div>
      ))}
      <LocationOnOutlinedIcon />
      <span className="text-body">강남역 5번 출구 앞</span>
    </div>
  );
}
