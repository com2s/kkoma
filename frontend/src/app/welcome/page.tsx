"use client";

import Logo from "/public/images/logo-icon.svg";
import KakaoLoginBtn from "@/components/welcome/kakaoLoginBtn";
import { ButtonContainer } from "@/components/common/buttons";
import styles from "./login.module.scss";
import { isLogin } from "@/utils/getAccessToken";
import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function Join() {
  const router = useRouter();

  useEffect(() => {
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
