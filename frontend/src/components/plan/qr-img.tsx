"use client";

import { useParams } from "next/navigation";
import Image from "next/image";

export default function QRCode() {
  const APPURL = process.env.NEXT_PUBLIC_APP_URL;

  const params = useParams<{ id: string }>();

  return (
    <Image
      src={`https://chart.apis.google.com/chart?cht=qr&chs=250x250&chl=${APPURL}/plan/${params.id}/mid`}
      alt="qrcode"
      width="250"
      height="250"
    />
  );
}
