import Logo from "/public/images/logo-icon.svg";
import KakaoLoginBtn from "@/components/welcome/kakaoLoginBtn";
import styles from "./login.module.scss";

export default function Join() {
  return (
    <section className={styles.join}>
      <span className="text-body">따뜻하게 품다. 꼬꼬마켓</span>
      <p className="text-big-logo">KKOMA</p>
      <Logo />
      <div className={styles.loginBtn}>
        <KakaoLoginBtn>카카오로 시작</KakaoLoginBtn>
      </div>
    </section>
  );
}
