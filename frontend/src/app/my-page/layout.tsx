import type { Metadata } from "next";

export const metadata: Metadata = {
    title: "My Page",
    description: "User's personal page",
  };

export default function MyPageLayout({
    children,
  }: {
    children: React.ReactNode
  }) {
    return (
        <div>
          {children}
        </div>
    )
  }