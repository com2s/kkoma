"use client";

import Image from "next/image";
import LocationOnOutlinedIcon from "@mui/icons-material/LocationOnOutlined";
import ScheduleIcon from "@mui/icons-material/Schedule";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import { Avatar, IconButton } from "@mui/material";
import { usePathname } from "next/navigation";
import { getProductDetail } from "./lists-ftn";
import { useEffect, useState } from "react";
import { MemberSummary } from "@/types/member";
import Profile from "@/components/lists/lists-detail-profile";
import Title from "../common/title";

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

export default function RequestDone({ sellerId, selectedTimes, location }: ChildProps) {
  const [member, setMember] = useState<MemberSummary>();
  const path = usePathname();
  const id = path.split("/")[2];

  const fetchData = async () => {
    const detail = await getProductDetail(id);
    if (detail.success === true) {
      setMember(detail.data.memberSummary);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="px-4">
      <Title title="거래 요청이 완료되었어요!" subtitle="거래가 확정되면 알림을 보내드릴게요" />
      <Image
        src={"/images/message2.svg"}
        alt="요청 완료"
        width={180}
        height={180}
        className="mx-auto my-4"
      />
      <Profile propsId={id} memberSummary={member} />
      {selectedTimes?.map((timeEntry, index) => (
        <div key={index} className="mb-2">
          <CalendarMonthIcon />
          <span className="text-body mx-2">{timeEntry.date}</span>
          <ScheduleIcon />
          <span className="text-body mx-2">{timeEntry.time}</span>
        </div>
      ))}
      <span className="text-body">{location}</span>
    </div>
  );
}
