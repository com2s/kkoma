import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "시작 - 꼬꼬마켓",
  description: "꼬꼬마켓과 함께하는 육아",
};

export default function AuthLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <div>{children}</div>;
}
