import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "My Trade",
  description: "User's trade page",
};

export default function MyTradeLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return <div>{children}</div>;
}
