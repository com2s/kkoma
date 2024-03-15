"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import styles from "@/components/common/buttons.module.scss";

declare global {
  interface Window {
    Kakao: any;
  }
}

interface btnProps {
  children: String;
}

export default function KakaoLoginBtn({ children }: btnProps) {
  const KAKAO_AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.NEXT_PUBLIC_KAKAO_JS_KEY}&redirect_uri=${process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI}&response_type=code`;

  const router = useRouter();

  useEffect(() => {
    const kakaoSDK = document.createElement("script");
    kakaoSDK.async = false;
    kakaoSDK.src = `https://t1.kakaocdn.net/kakao_js_sdk/2.6.0/kakao.min.js`;
    kakaoSDK.integrity = `sha384-l+xbElFSnPZ2rOaPrU//2FF5B4LB8FiX5q4fXYTlfcG4PGpMkE1vcL7kNXI6Cci0`;
    kakaoSDK.crossOrigin = "anonymous";
    document.head.appendChild(kakaoSDK);

    const onLoadKakaoAPI = () => {
      if (!window.Kakao.isInitialized()) {
        window.Kakao.init(process.env.KAKAO_JS_KEY);
      }
    };

    kakaoSDK.addEventListener("load", onLoadKakaoAPI);
  }, []);

  return (
    <button
      className={`${styles.btn} ${styles.wide}`}
      onClick={() => router.push(`${KAKAO_AUTH_URI}`)}
    >
      <div>{children}</div>
    </button>
  );
}
