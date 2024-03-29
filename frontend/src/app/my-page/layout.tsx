import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "마이 페이지",
  description: "User's personal page",
};

export default function MyPageLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return <div>{children}</div>;
}
