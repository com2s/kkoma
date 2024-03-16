"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function KakaoOauth() {
  const router = useRouter();

  useEffect(() => {
    router.push("/join/profile");
  }, []);
  return (
    <div>
      <div>로..딩...중..</div>
    </div>
  );
}
