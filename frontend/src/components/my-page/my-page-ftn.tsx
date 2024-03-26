import APIModule from "@/utils/apiModule";

// import exp from "constants";

// export const updateMemberAPI = async (memberInfo: MemberInfo) => {
//   const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
//   const obj = await res.json();

//   return obj;
// };
export interface mySummary {
  success: boolean;
  data: {
    profileImage: string;
    nickname: string;
    preferredPlace: string;
  };
  error: {
    errorCode: string;
    errorMessage: string;
  };
}

export async function getMySummary() {
  const response = await APIModule({
    action: "/members/summary",
    method: "GET",
    data: null,
  });

  return response;
}

export async function getMyInfo() {
  const response = await APIModule({
    action: "/members/info",
    method: "GET",
    data: null,
  });

  return response;
}

interface MemberInfo {
  profileImage: string;
  nickname: string;
  name: string;
  phone: string;
}

export async function putMyInfo(data: MemberInfo) {
  const response = await APIModule({
    action: "/members",
    method: "PUT",
    data: data,
  });

  return response;
}
