import APIModule from "@/utils/apiModule";

interface MemberInfo {
  profileImage: string;
  nickname: string;
  name: string;
  phone: string;
}

export const updateMemberAPI = async (memberInfo: MemberInfo) => {
  const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
  const obj = await res.json();

  return obj;
};
