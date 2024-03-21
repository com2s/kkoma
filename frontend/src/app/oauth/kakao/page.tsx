"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { setItemWithExpireTime } from "@/utils/controlStorage";
import Loading from "@/components/common/loading";
import { kakaoLogin } from "@/services/kakaoLogin";
import { Token } from "@/types/token";

export default function KakaoOauth() {
  const router = useRouter();
  const searchParams = useSearchParams();

  const code = searchParams.get("code") ?? "";

  kakaoLogin(code)
    .then((res) => {
      //TODO: accessToken이랑 refreshToken 저장 필요
      const obj: any = res.json();
      setItemWithExpireTime("accessToken", obj.accessToken, obj.accessTokenExpireTime);
      console.log(res.json());
      router.push("/join/profile");
    })
    .catch((e) => {
      // router.push("/error");
      console.log(e);
    });

  return <Loading />;
}
