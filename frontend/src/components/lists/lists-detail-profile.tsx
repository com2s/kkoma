"use client";

import styles from "@/components/lists/lists-detail-profile.module.scss";
import Image from "next/image";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { useRouter } from "next/navigation";

interface UserProfileProps {
  propsId: string;
  memberSummary:
    | {
        memberId: number;
        profileImage: string;
        nickname: string;
        preferredPlace: string;
      }
    | undefined;
}

export default function UserProfile({ propsId, memberSummary }: UserProfileProps) {
  const router = useRouter();

  return (
    <div>
      {memberSummary === undefined ? (
        <div className={styles.container}>유저 정보가 없습니다.</div>
      ) : (
        <div
          className={styles.container}
          onClick={() => router.push(`/profile/${memberSummary?.memberId}`)}
        >
          <div className="flex items-center gap-3">
            <div className={styles.responsiveImg} style={{ position: "relative" }}>
              <Image
                src={memberSummary.profileImage}
                alt="Profile Image"
                fill
                style={{ objectFit: "cover", borderRadius: "50%" }}
              />
            </div>
            <div className={`${styles.nickname} min-w-32 text-pretty mr-1 `}>
              <h4 className="text-body !font-bold">{memberSummary.nickname ?? "닉네임 미등록"}</h4>
              <span className="c-text3 text-caption">
                {memberSummary.preferredPlace ?? "지역 미등록"}
              </span>
            </div>
          </div>
          <ArrowForwardIosIcon style={{ width: "15px" }} />
        </div>
      )}
    </div>
  );
}
