import { Metadata } from "next"

// 하위 페이지에 따라 다른 메타데이터를 제공
export const metadata :Metadata= {
  title: {
    template: '%s님의 프로필',
    default: 'Loading...'
  },
  description: '프로필 페이지',
}

export default function ProfileLayout({
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
