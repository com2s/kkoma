import type { Metadata } from "next";


export const metadata: Metadata = {
      title: "거래글 쓰기",
      description: "Creating a post",
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