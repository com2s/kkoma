"use client";

import Logo from "/public/images/logo-icon.svg";
import KakaoLoginBtn from "@/components/welcome/kakaoLoginBtn";
import { ButtonContainer } from "@/components/common/buttons";
import styles from "./login.module.scss";
import { isLogin } from "@/utils/getAccessToken";
import { useEffect } from "react";
import { useRouter } from "next/navigation";
import LocalStorage from "@/utils/localStorage";
import { setItemWithExpireTime } from "@/utils/controlStorage";

export default function Join() {
  const router = useRouter();

  useEffect(() => {
    if (process.env.NODE_ENV === "development") {
      setItemWithExpireTime(
        "accessToken",
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE3MTE1MDMxMjIsImV4cCI6MTcxMTUwNDAyMiwibWVtYmVySWQiOjIsInJvbGUiOiJVU0VSIn0.VTjtfoUvBPQhOh_X_19_1Zcfe7jJklVvt8Kbf35Tc87KN66ujOfiDGlglbMgJ3a6a556Rx5iRDVnuK2Ef8nSFg",
        "2024-03-27T01:47:02.000Z"
      );
      setItemWithExpireTime(
        "refreshToken",
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiaWF0IjoxNzExNTAzMTIyLCJleHAiOjE3MTI3MTI3MjIsIm1lbWJlcklkIjoyfQ.RtLYNAVfovkNfKgjzja1L4mY3t_F5doWq2Dl3oNqldjZh7ox5qCbgBZjqirsTpf-bZ9x-MoJQJILFwpNLyWjgw",
        "2024-04-10T01:32:02.000Z"
      );
      LocalStorage.setItem("grantType", "Bearer");
      LocalStorage.setItem("memberId", "2");
    }
    if (isLogin()) router.replace("/");
  }, []);

  return (
    <section className={styles.join}>
      <span className="text-body">따뜻하게 품다. 꼬꼬마켓</span>
      <p className="text-big-logo">KKOMA</p>
      <Logo />
      <div className={styles.loginBtn}>
        <ButtonContainer>
          <KakaoLoginBtn>카카오로 시작</KakaoLoginBtn>
        </ButtonContainer>
      </div>
    </section>
  );
}
