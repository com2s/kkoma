"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { setItemWithExpireTime } from "@/utils/controlStorage";
import Loading from "@/components/common/loading";
import { kakaoLoginAPI } from "@/services/kakaoLogin";
import LocalStorage from "@/utils/localStorage";

export default function KakaoOauth() {
  const router = useRouter();
  const searchParams = useSearchParams();

  const code = searchParams.get("code") ?? "";

  const doLogin = async () => {
    const obj = await kakaoLoginAPI(code);

    if (obj) {
      setItemWithExpireTime("accessToken", obj.accessToken, obj.accessTokenExpireTime);
      setItemWithExpireTime("refreshToken", obj.refreshToken, obj.refreshTokenExpireTime);
      LocalStorage.setItem("grantType", obj.grantType);
      LocalStorage.setItem("memberId", obj.memberId);

      if (obj.memberInfoCompleted && obj.kidInfoCompleted) {
        router.replace("/");
      } else if (!obj.memberInfoCompleted) {
        router.replace("/join/profile");
      } else if (!obj.kidInfoCompleted) {
        const isKid = confirm(
          "로그인 완료!\n아이 정보 입력이 완료되지않았습니다.\n입력하시겠습니까?"
        );
        if (isKid) {
          router.replace("/kid/name");
        } else {
          router.replace("/");
        }
      }
    }
  };

  try {
    doLogin();
    router.replace("/join/profile");
  } catch (e) {
    console.log("error", e);
    router.replace("/error");
  }

  return <Loading />;
}
