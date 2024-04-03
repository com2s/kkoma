import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "찜 목록",
  description: "User's Wish-list page",
};

export default function MyWishListLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return <div>{children}</div>;
}
