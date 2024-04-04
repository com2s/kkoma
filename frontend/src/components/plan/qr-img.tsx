"use client";

import Image from "next/image";
import { useParams } from "next/navigation";

interface Code {
  code: string;
}

export default function QRCode({ code }: Code) {
  const APPURL = process.env.NEXT_PUBLIC_APP_URL;
  const params = useParams<{ id: string }>();
  const url = `${APPURL}/plan/${params.id}/progress?code=${code}`;

  // https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=name=%EB%8B%B9%EC%8B%A0%EC%9D%B4%EC%9B%90%ED%95%98%EB%8A%94%ED%85%8D%EC%8A%A4%ED%8A%B8;date=2019-03-19;post=now

  return (
    <Image
      // src={`https://chart.apis.google.com/chart?cht=qr&chs=250x250&chl=${url}`}
      src={`https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=${url}`}
      alt="qrcode"
      width="250"
      height="250"
    />
  );
}
