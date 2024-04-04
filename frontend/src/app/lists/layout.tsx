import type { Metadata } from "next";


export const metadata: Metadata = {
    title: "거래글 목록",
    description: "Products List Page",
  };

export default function ProductListLayout({
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