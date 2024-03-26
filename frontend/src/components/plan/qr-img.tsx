"use client";

import { useParams } from "next/navigation";
import Image from "next/image";

interface Code {
  code: string;
}

export default function QRCode({ code }: Code) {
  const APPURL = process.env.NEXT_PUBLIC_APP_URL;
  const params = useParams<{ id: string }>();

  return (
    <Image
      src={`https://chart.apis.google.com/chart?cht=qr&chs=250x250&chl=${APPURL}/plan/${params.id}/progress?code=${code}`}
      alt="qrcode"
      width="250"
      height="250"
    />
  );
}
