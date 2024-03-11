import type { Metadata } from "next";


export const metadata: Metadata = {
    title: "Products List",
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