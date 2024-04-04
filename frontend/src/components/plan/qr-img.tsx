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

  return (
    <Image
      src={`https://chart.apis.google.com/chart?cht=qr&chs=250x250&chl=${url}`}
      alt="qrcode"
      width="250"
      height="250"
    />
  );
}
