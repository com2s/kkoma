import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "알림",
  description: "User's notifications page",
};

export default function MyNotificationsLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return <div>{children}</div>;
}
