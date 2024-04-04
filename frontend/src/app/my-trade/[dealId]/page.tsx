"use client";

import TopBar2 from "@/components/my-trade/my-request-bar";
import styles from "@/components/my-trade/my-request.module.scss";
// 인터페이스
import { Requester } from "@/types/offer";
import { getRequesters } from "@/components/my-trade/my-trade-ftn";
import { offerTimeState } from "@/store/offer";
import { patchOfferReject } from "@/components/my-trade/my-trade-ftn";
import { Card, CardContent, Typography, Avatar, IconButton } from "@mui/material";
import React, { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import Slide from "@mui/material/Slide";
import { TransitionProps } from "@mui/material/transitions";
import CheckIcon from "@mui/icons-material/Check";
import RemoveIcon from "@mui/icons-material/Remove";
import Image from "next/image";
import { useRouter } from "next/navigation";
import { NoContents } from "@/components/common/no-contents";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

interface IParams {
  params: { dealId: string };
}

export default function MyRequest({ params: { dealId } }: IParams) {
  const [requesters, setRequesters] = useState<Requester[]>([]); // requesters 상태와 상태 설정 함수 추가
  const [success, setSuccess] = useState(true);
  const [offerTime, setOfferTime] = useRecoilState(offerTimeState);
  const router = useRouter();

  useEffect(() => {
    const fetchData = async () => {
      const res = await getRequesters(dealId);
      setRequesters(res.data);
      setSuccess(res.success);
    };
    fetchData();
  }, [dealId]);

  const handleDelete = async (requestId: number) => {
    if (window.confirm("거래 요청을 취소하시겠습니까?")) {
      await patchOfferReject(requestId);
      alert("거래 요청이 취소되었습니다.");
    }
  };

  const clickRequest = (times: Requester["offerTimes"], id: number) => {
    setOfferTime(times);
    router.replace(`/my-trade/${dealId}/${id}`);
  };

  return (
    <React.Fragment>
      <TopBar2 />
      {success === false && <h1>요청자 정보를 불러오는데 실패했습니다.</h1>}
      {success === true && requesters && (
        <>
          {requesters?.map((requester, key) => (
            <div className="flex gap-3 w-full my-2 items-start" key={key}>
              <div
                style={{ position: "relative", width: "90px", aspectRatio: 1 / 1 }}
                className="rounded-xl bg-gray-100"
              >
                <Image
                  src={requester.memberProfile.profileImage}
                  alt="thumb"
                  fill
                  className="rounded-xl"
                  style={{ objectFit: "cover" }}
                />
              </div>
              <div className="w-full">
                <div className="flex justify-between">
                  <span className="text-body !font-bold">{requester.memberProfile.nickname}</span>
                </div>
                {requester.offerTimes?.map((time, k) => (
                  <div className="text-caption c-text3 my-1" key={k}>
                    {`${time.offerDate} ${time.startTime.split(":").slice(0, 2).join(":")} ~ 
                      ${time.endTime.split(":").slice(0, 2).join(":")}`}
                  </div>
                ))}
              </div>
              <IconButton
                size="small"
                sx={{
                  "&.MuiIconButton-root": {
                    bgcolor: "#ffcf00",
                  },
                  margin: 1,
                }}
                onClick={() => clickRequest(requester.offerTimes, requester.offerId)}
              >
                <CheckIcon sx={{ color: "white" }} />
              </IconButton>
              {/* </Link> */}
              <IconButton
                onClick={() => handleDelete(requester.offerId)}
                size="small"
                sx={{
                  "&.MuiIconButton-root": {
                    bgcolor: "#764c32",
                  },
                  margin: 1,
                }}
              >
                <RemoveIcon sx={{ color: "white" }} />
              </IconButton>
            </div>
          ))}
        </>
      )}
      {requesters.length === 0 && (
        <NoContents>
          <h4 className="c-text3">아직 거래 요청이 없어요</h4>
          <Image src={"/images/Empty-BOX.png"} alt="empty" width={100} height={100} />
        </NoContents>
      )}
    </React.Fragment>
  );
}
