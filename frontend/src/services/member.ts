import APIModule from "@/utils/apiModule";

interface MemberInfo {
  profileImage: string;
  nickname: string;
  name: string;
  phone: string;
}

export const updateMemberAPI = async (memberInfo: MemberInfo) => {
  try {
    const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
    if (res.success) {
      return res.data;
    } else {
      //TODO: error 페이지로 이동
      throw new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};
