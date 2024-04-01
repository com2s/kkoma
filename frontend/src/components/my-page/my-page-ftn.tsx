import APIModule from "@/utils/apiModule";

// import exp from "constants";

// export const updateMemberAPI = async (memberInfo: MemberInfo) => {
//   const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
//   const obj = await res.json();

//   return obj;
// };


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
  profileImage: string|null;
  nickname: string|null;
  name: string|null;
  phone: string|null;
}



export async function putMyInfo(data: MemberInfo) {
  const response = await APIModule({
    action: "/members",
    method: "PUT",
    data: data,
  });

  return response;
}

// export interface WishList {

export async function getMyWishes(page?: number, size?: number) {
  const queryObject = {
    page: page?.toString()??'0',
    size: size?.toString()??'20',
  };

  const query = new URLSearchParams(queryObject).toString();

  const response = await APIModule({
    action: `/products/wishes?${query}`,
    method: "GET",
    data: null,
  });

  return response;
}

export async function getMyPosts() {
  const response = await APIModule({
    action: "/members/profile",
    method: "GET",
    data: null,
  });

  return response;
}

export async function getRecentList(page?: number, size?:number) {
  const queryObject = {
    page: page?.toString()??'0',
    size: size?.toString()??'20',
  };

  const query = new URLSearchParams(queryObject).toString();

  const response = await APIModule({
    action: `/products/viewHistories?${query}`,
    method: "GET",
    data: null,
  });

  return response;
}