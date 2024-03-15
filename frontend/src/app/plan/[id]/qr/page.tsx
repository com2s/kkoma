import TopBar3 from "@/components/common/top-bar3";
import Title from "@/components/common/title";
import styles from "./qr.module.scss";
import { ProductCard } from "@/components/common/product-card";
import Image from "next/image";

export default function DealQR() {
  return (
    <div className={styles.qr}>
      <TopBar3 />
      <Title title="거래를 확정해주세요" subtitle="qr코드를 스캔해주세요" />
      <ProductCard />
      <Image
        src="https://chart.apis.google.com/chart?cht=qr&chs=250x250&chl=http://j10a308.p.ssafy.io:3000"
        alt="qrcode"
        width="250"
        height="250"
      />
    </div>
  );
}
