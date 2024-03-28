"use client";

import styles from "@/components/lists/lists-detail-profile.module.scss";
import Avatar from "@mui/material/Avatar";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import Link from "next/link";

interface UserProfileProps {
  propsId: string;
  memberSummary:
    | {
        profileImage: string;
        nickname: string;
        preferredPlace: string;
      }
    | undefined;
}

export default function UserProfile({
  propsId,
  memberSummary,
}: UserProfileProps) {
  console.log("UserProfileProps: ", memberSummary);

  return (
    <div>
      {memberSummary === undefined ? (
        <div className={styles.container}>유저 정보가 없습니다.</div>
      ) : (
        <div className={styles.container}>
          <Avatar
            src={memberSummary.profileImage}
            alt="Profile Image"
            // sx={{ width: 56, height: 56 }}
            className={`${styles.responsiveImg} mr-4`}
          />
          {/* 프로필사진이 없을 경우? */}
          {/* <Avatar {...stringAvatar('Kent Dodds')} /> */}
          <div className={`${styles.nickname} min-w-32 text-pretty mr-1 `}>
            <h4 className="">{memberSummary.nickname ?? "닉네임 미등록"}</h4>
            <span className="text-slate-500">
              {memberSummary.preferredPlace ?? "지역 미등록"}
            </span>
          </div>
          <button
            onClick={() => alert("유저 프로필 보기")}
            className="min-w-28"
          >
            <ArrowForwardIosIcon />
          </button>
          {/* <Link href="/my-page/my-profile" passHref>
        <span className="text-center font-medium min-w-28 bg-gray-100 flex justify-center items-center py-1 rounded-lg cursor-pointer">
          프로필 보기
        </span>
      </Link> */}
        </div>
      )}
    </div>
  );
}
