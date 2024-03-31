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
  return (
    <div>
      {memberSummary === undefined ? (
        <div className={styles.container}>유저 정보가 없습니다.</div>
      ) : (
        <div className={styles.container}>
          <div className="flex">
            <Avatar
              src={memberSummary.profileImage}
              alt="Profile Image"
              className={`${styles.responsiveImg} mr-4`}
            />
            <div className={`${styles.nickname} min-w-32 text-pretty mr-1 `}>
              <h4 className="">{memberSummary.nickname ?? "닉네임 미등록"}</h4>
              <span className="text-slate-500">
                {memberSummary.preferredPlace ?? "지역 미등록"}
              </span>
            </div>
          </div>
          {/* TODO: 유저 프로필로 이동 */}
          <ArrowForwardIosIcon onClick={() => alert("유저 프로필 보기")} />
        </div>
      )}
    </div>
  );
}
