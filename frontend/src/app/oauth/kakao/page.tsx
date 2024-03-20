"use client";

import { useRouter, useSearchParams } from "next/navigation";
import Loading from "@/components/common/loading";
import axios from "axios";

export default function KakaoOauth() {
  const router = useRouter();
  const searchParams = useSearchParams();

  const SERVER_URL = process.env.NEXT_PUBLIC_API_URL;
  const code = searchParams.get("code") ?? "";

  axios
    .get(`${SERVER_URL}/oauth/kakao?code=${code}`)
    .then((res) => {
      console.log(res.data);
      router.push("/join/profile");
    })
    .catch((e) => {
      //TODO: error 페이지로 이동 필요
      router.push("/");
    });

  return <Loading />;
}
